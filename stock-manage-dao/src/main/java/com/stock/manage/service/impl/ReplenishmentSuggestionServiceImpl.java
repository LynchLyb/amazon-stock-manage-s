package com.stock.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.manage.entity.ReplenishmentSuggestionDO;
import com.stock.manage.mapper.ReplenishmentSuggestionMapper;

import com.stock.manage.service.ReplenishmentSuggestionService;
import org.springframework.stereotype.Service;

/**
 * 补货建议 Service 实现
 */
@Service
public class ReplenishmentSuggestionServiceImpl extends ServiceImpl<ReplenishmentSuggestionMapper, ReplenishmentSuggestionDO> implements ReplenishmentSuggestionService {
}