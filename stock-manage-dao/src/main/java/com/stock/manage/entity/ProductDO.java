package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.stock.manage.entity.base.BaseDO;
import com.stock.manage.constant.enums.ProductStageEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("product")
public class ProductDO extends BaseDO implements Serializable {

    @TableField("sku_code")
    private String skuCode;

    @TableField("sku_fn_code")
    private String skuFnCode;

    @TableField("sku_name")
    private String skuName;

    @TableField("product_stage")
    private ProductStageEnums productStage;

    @TableField("memo")
    private String memo;

    @TableField("extend_info")
    private String extendInfo;
}