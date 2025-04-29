package com.stock.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.manage.entity.SalesRecord;
import com.stock.manage.mapper.SalesRecordMapper;
import com.stock.manage.service.SalesRecordService;
import org.springframework.stereotype.Service;

@Service
public class SalesRecordServiceImpl extends ServiceImpl<SalesRecordMapper, SalesRecord> implements SalesRecordService {

}
