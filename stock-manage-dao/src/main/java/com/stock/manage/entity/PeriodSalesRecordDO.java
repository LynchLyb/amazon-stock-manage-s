package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.manage.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("amazon_stock_manager.period_sales_record")
public class PeriodSalesRecordDO extends BaseDO {

    @TableField("product_id")
    private Long productId;

    private Integer quantity;

    @TableField("start_date")
    private LocalDate startDate;

    @TableField("end_date")
    private LocalDate endDate;

    @TableField("is_complete_exchange")
    private Boolean isCompleteExchange;

    @TableField("data_source")
    private String dataSource;  // MANUAL / IMPORT
}