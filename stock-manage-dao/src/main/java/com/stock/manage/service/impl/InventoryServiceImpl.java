package com.stock.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.manage.entity.InventoryDO;
import com.stock.manage.mapper.InventoryMapper;
import com.stock.manage.service.InventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, InventoryDO> implements InventoryService {
}
