package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("inventory")
public class Inventory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Integer sellableQty;

    private Integer reservedQty;

    private Integer inboundQty;

    private String dataSource;

    @TableField(value = "is_deleted")
//    @TableLogic
    private Integer isDeleted;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}