package com.stock.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.stock.manage.entity.ReplenishmentSuggestionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 补货建议 Mapper 接口
 */
@Mapper
public interface ReplenishmentSuggestionMapper extends BaseMapper<ReplenishmentSuggestionDO> {
}