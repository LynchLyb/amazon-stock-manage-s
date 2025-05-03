package com.stock.manage.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CommonDataSourceEnums {

    IMPORT("IMPORT", 0),
    MANUAL("MANUAL", 1),
    TASK("TASK", 2),
    ;

    @Getter
    private final String code;

    @Getter
    private final Integer priority;
}
