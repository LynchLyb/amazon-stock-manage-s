package com.stock.manage.framework.process.model;

import com.stock.manage.framework.process.enums.ProcessFlow;
import lombok.Data;

@Data
public class AbstractProcessInfo {

    private ProcessFlow flow;

    private ProcessContext processContext;
}
