package com.stock.manage.framework.process.annotation;


import com.stock.manage.framework.process.enums.ProcessFlow;
import com.stock.manage.framework.process.enums.ProcessStep;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流程定义注解
 * 1.向Spring注入所有的的processor服务bean
 * 2.一个processService仅支持且必须声明一个processStep
 * 3.一个processService支持声明0个或多个processFlow
 * 4.processStep如果是abstract，那么应该囊括其具体的processFlow
 * @author Lynch
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ProcessDefine {
    /**
     * 处理流程中的某个步骤
     */
    ProcessStep processStep();

    /**
     * 步骤中的某种形式
     */
    ProcessFlow[] processFlows();
}
