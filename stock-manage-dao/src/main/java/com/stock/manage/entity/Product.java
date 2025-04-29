package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("product")
public class Product implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("sku_code")
    private String skuCode;

    @TableField("sku_fn_code")
    private String skuFnCode;

    @TableField("sku_name")
    private String skuName;

    @TableField("product_stage")
    private String productStage;

    @TableField("memo")
    private String memo;

    @TableField("extend_info")
    private String extendInfo;

    @TableField(value = "is_deleted")
//    @TableLogic
    private Integer isDeleted;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}