package com.stock.manage.controller;

import com.stock.manage.task.PeriodSalesToDailyTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/period-sales")
public class PeriodSalesController {

    @Autowired
    private PeriodSalesToDailyTask periodSalesToDailyTask;

    /**
     * 手动触发周期销售记录转每日销售记录
     */
    @PostMapping("/convert-to-daily")
    public ResponseEntity<String> convertToDaily() {
        log.info("收到手动触发周期销售记录转换请求");
        try {
            periodSalesToDailyTask.convertPeriodSalesToDaily();
            return ResponseEntity.ok("周期销售记录转换任务执行成功");
        } catch (Exception e) {
            log.error("手动触发周期销售记录转换任务失败", e);
            return ResponseEntity.internalServerError().body("周期销售记录转换任务执行失败：" + e.getMessage());
        }
    }
} 