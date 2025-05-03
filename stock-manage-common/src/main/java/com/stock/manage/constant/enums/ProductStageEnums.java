package com.stock.manage.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ProductStageEnums {

    TESTING("TESTING", "测品期"),
    PROMOTION("PROMOTION", "推广期"),
    STABLE("STABLE", "稳定期"),
    CLEARANCE("CLEARANCE", "清货期");

    @Getter
    private final String code;
    @Getter
    private final String description;

    public static ProductStageEnums fromCode(String code) {
        for (ProductStageEnums stage : values()) {
            if (stage.getCode().equalsIgnoreCase(code)) {
                return stage;
            }
        }
        return null;
    }

    public static ProductStageEnums getCodeByDescription(String code) {
        for (ProductStageEnums stage : values()) {
            if (stage.getDescription().equalsIgnoreCase(code)) {
                return stage;
            }
        }
        return null;
    }

}
