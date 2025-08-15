package com.stock.manage.vo;

import lombok.Data;

@Data
public class ProductVO {
    private Long id;
    private String skuCode;
    private String skuFnCode;
    private String skuName;
    private String productStage;
    private String memo;
    private String extendInfo;
} 