package com.sky.aspect;


import com.sky.annocation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annocation.AutoFill)")
    public void autoFillCutPoint(){}

    @Before("autoFillCutPoint()")
    public void beforeFill(JoinPoint joinpoint){
        log.info("公共字段开始填充...");
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = autoFill.value();
        Object[] args = joinpoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];
        if (value == OperationType.INSERT) {
            try {
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class)
                        .invoke(entity, LocalDateTime.now());
                entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class)
                        .invoke(entity, BaseContext.getCurrentId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class)
                    .invoke(entity, LocalDateTime.now());
            entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class)
                    .invoke(entity, BaseContext.getCurrentId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
