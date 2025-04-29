package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sales_record")
public class SalesRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String recordType;

    private Integer quantity;

    private Date startDate;

    private Date endDate;

    private String dataSource;

    @TableField(value = "is_deleted")
//    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}