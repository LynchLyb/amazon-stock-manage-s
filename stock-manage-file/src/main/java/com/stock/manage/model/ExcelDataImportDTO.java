package com.stock.manage.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExcelDataImportDTO {

    /**
     * 商品身份码
     */
    @ExcelProperty("Asin")
    private String skuCode;

    /**
     * 货品编码
     */
    @ExcelProperty("FNSKU")
    private String skuFnCode;


    @ExcelProperty("产品")
    private String skuName;

    @ExcelProperty("产品时期")
    private String productStage;

    /**
     * 销量
     */
    @ExcelProperty("月销")
    private Integer quantity;

    @ExcelProperty("可售")
    private Integer sellableQty;

    @ExcelProperty("预留")
    private Integer reservedQty;

    @ExcelProperty("系统（入库）")
    private Integer inboundQty;

    /**
     * 起始日期
     */
    private Date startDate;

    /**
     * 结束日期（可选，周/月记录）
     */
    private Date endDate;
}