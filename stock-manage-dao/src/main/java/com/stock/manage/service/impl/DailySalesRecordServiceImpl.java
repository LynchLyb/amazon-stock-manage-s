package com.stock.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.manage.entity.DailySalesRecordDO;
import com.stock.manage.mapper.DailySalesRecordMapper;
import com.stock.manage.service.DailySalesRecordService;
import org.springframework.stereotype.Service;

@Service
public class DailySalesRecordServiceImpl extends ServiceImpl<DailySalesRecordMapper, DailySalesRecordDO> implements DailySalesRecordService {
}