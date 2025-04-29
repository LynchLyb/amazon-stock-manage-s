package com.stock.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.manage.entity.SalesRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售记录表 Mapper 接口
 */
@Mapper
public interface SalesRecordMapper extends BaseMapper<SalesRecord> {
}