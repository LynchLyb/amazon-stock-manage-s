package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.stock.manage.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inventory")
public class InventoryDO extends BaseDO implements Serializable {

    private Long productId;

    private Integer sellableQty;

    private Integer reservedQty;

    private Integer inboundQty;

    private String dataSource;
}