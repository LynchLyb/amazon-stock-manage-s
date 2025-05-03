package com.stock.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.manage.entity.PeriodSalesRecordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeriodSalesRecordMapper extends BaseMapper<PeriodSalesRecordDO> {
    // 如果你有自定义 SQL 查询，也可以在这里加 @Select 等注解方法
}