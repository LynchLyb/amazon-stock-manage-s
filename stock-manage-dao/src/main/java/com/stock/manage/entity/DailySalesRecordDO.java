package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.manage.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("amazon_stock_manager.daily_sales_record")
public class DailySalesRecordDO extends BaseDO {

    @TableField("product_id")
    private Long productId;

    private Integer quantity;

    @TableField("record_date")
    private LocalDate recordDate;

    @TableField("data_source")
    private String dataSource;  // MANUAL / IMPORT / TASK
}