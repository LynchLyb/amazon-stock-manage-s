package com.stock.manage.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stock.manage.constant.enums.CommonDataSourceEnums;
import com.stock.manage.entity.DailySalesRecordDO;
import com.stock.manage.entity.PeriodSalesRecordDO;
import com.stock.manage.service.DailySalesRecordService;
import com.stock.manage.service.PeriodSalesRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
public class PeriodSalesToDailyTask {

    @Autowired
    private PeriodSalesRecordService periodSalesRecordService;

    @Autowired
    private DailySalesRecordService dailySalesRecordService;

    /**
     * 每天凌晨2点执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void convertPeriodSalesToDaily() {
        log.info("开始执行周期销售记录转每日销售记录任务");
        try {
            // 1. 获取所有未处理的周期销售记录
            QueryWrapper<PeriodSalesRecordDO> wrapper = new QueryWrapper<>();
            wrapper.eq("is_complete_exchange", Boolean.FALSE)
                   .eq("is_deleted", Boolean.FALSE);
            List<PeriodSalesRecordDO> periodRecords = periodSalesRecordService.list(wrapper);

            if (periodRecords.isEmpty()) {
                log.info("没有需要处理的周期销售记录");
                return;
            }

            // 2. 处理每条周期销售记录
            for (PeriodSalesRecordDO periodRecord : periodRecords) {
                try {
                    // 计算周期内的天数
                    long daysBetween = ChronoUnit.DAYS.between(
                            periodRecord.getStartDate(),
                            periodRecord.getEndDate()
                    ) + 1;

                    // 计算每天的平均销量
                    int dailyQuantity = periodRecord.getQuantity() / (int) daysBetween;

                    // 生成每日销售记录
                    LocalDate currentDate = periodRecord.getStartDate();
                    while (!currentDate.isAfter(periodRecord.getEndDate())) {
                        DailySalesRecordDO dailyRecord = new DailySalesRecordDO();
                        dailyRecord.setProductId(periodRecord.getProductId());
                        dailyRecord.setQuantity(dailyQuantity);
                        dailyRecord.setRecordDate(currentDate);
                        dailyRecord.setDataSource(CommonDataSourceEnums.TASK.getCode());
                        dailyRecord.setIsDeleted(Boolean.FALSE);

                        // 保存每日销售记录
                        dailySalesRecordService.save(dailyRecord);
                        
                        currentDate = currentDate.plusDays(1);
                    }

                    // 标记周期销售记录为已处理
                    UpdateWrapper<PeriodSalesRecordDO> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", periodRecord.getId())
                               .set("is_complete_exchange", Boolean.TRUE);
                    periodSalesRecordService.update(updateWrapper);

                    log.info("成功处理周期销售记录: {}", periodRecord.getId());
                } catch (Exception e) {
                    log.error("处理周期销售记录失败: {}", periodRecord.getId(), e);
                }
            }

            log.info("周期销售记录转每日销售记录任务执行完成");
        } catch (Exception e) {
            log.error("执行周期销售记录转每日销售记录任务失败", e);
        }
    }
} 