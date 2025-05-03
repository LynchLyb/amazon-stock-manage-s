package com.stock.manage.framework.process.entrance;

import com.stock.manage.framework.process.ProcessService;
import com.stock.manage.framework.process.annotation.ProcessDefine;
import com.stock.manage.framework.process.enums.ProcessFlow;
import com.stock.manage.framework.process.enums.ProcessStep;
import com.stock.manage.framework.process.model.AbstractProcessInfo;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public abstract class BaseProcess {

    private static final Map<Pair<ProcessStep, ProcessFlow>, ProcessService> PROCESS_SERVICE_MAP = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        List<ProcessService> processServiceList = new ArrayList<>(applicationContext.getBeansOfType(ProcessService.class)
                .values());

        processServiceList.forEach(processService -> {
                    ProcessDefine processDefine = processService.getClass().getAnnotation(ProcessDefine.class);
                    ProcessStep processStep = processDefine.processStep();
                    ProcessFlow[] processFlows = processDefine.processFlows();
                    if (processFlows == null || processFlows.length == 0) {
                        PROCESS_SERVICE_MAP.put(Pair.of(processStep, null), processService);
                    }else {
                        for (ProcessFlow processFlow : processFlows) {
                            PROCESS_SERVICE_MAP.put(Pair.of(processStep, processFlow), processService);
                        }
                    }
                });

    }


    public void process(AbstractProcessInfo processInfo, ProcessStep processName) {
        ProcessService processService = getProcessService(processName, processInfo.getFlow());

    }

    private ProcessService getProcessService(ProcessStep processName, ProcessFlow flow) {
        return PROCESS_SERVICE_MAP.get(Pair.of(processName, flow));
    }
}
