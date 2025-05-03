package com.stock.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.manage.entity.InventoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存表 Mapper 接口
 */
@Mapper
public interface InventoryMapper extends BaseMapper<InventoryDO> {
}