package com.stock.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.stock.manage.entity.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 补货建议记录表（根据断货风险与海运周期倒推）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("replenishment_suggestion")
public class ReplenishmentSuggestionDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;

    private String productStage;

    private Date salesReferenceStartDate;

    private Date salesReferenceEndDate;

    /**
     * 注意 salesSnapshot 是 JSON类型
     * 可以用 String 来接，也可以用 List<Integer> 如果你想做自动转换的话，需要配置 typeHandler
     */
    private String salesSnapshot;

    private Date expectedOosDate;

    private Integer suggestedQuantity;

    private Date suggestedShipDate;

    private Date expectedArrivalDate;

    private String transportMode;

    private Integer bufferDays;

    private String status;

    private String source;

    private String adjustmentNote;
}