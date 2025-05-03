package com.stock.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.manage.entity.PeriodSalesRecordDO;
import com.stock.manage.mapper.PeriodSalesRecordMapper;
import com.stock.manage.service.PeriodSalesRecordService;
import org.springframework.stereotype.Service;

@Service
public class PeriodSalesRecordServiceImpl extends ServiceImpl<PeriodSalesRecordMapper, PeriodSalesRecordDO> implements PeriodSalesRecordService {

    // 可以扩展定时任务调用的周期转日销量方法等
}