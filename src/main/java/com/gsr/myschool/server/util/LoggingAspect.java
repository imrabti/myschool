package com.gsr.myschool.server.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private Log log = LogFactory.getLog(this.getClass());

    @Pointcut("execution(* com.gsr.myschool.server.service.*.*(..))")
    protected void loggingOperation() {}

    @Before("loggingOperation()")
    @Order(1)
    public void logJoinPoint(JoinPoint joinPoint) {
        log.info("Join point kind : " + joinPoint.getKind());
        log.info("Signature declaring type : "+ joinPoint.getSignature().getDeclaringTypeName());
        log.info("Signature name : " + joinPoint.getSignature().getName());
        log.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        log.info("Target class : "+ joinPoint.getTarget().getClass().getName());
        log.info("This class : " + joinPoint.getThis().getClass().getName());
    }

    @AfterReturning(pointcut="loggingOperation()", returning = "result")
    @Order(2)
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("Exiting from Method :"+joinPoint.getSignature().getName());
        log.info("Return value :"+result);
    }

    @AfterThrowing(pointcut="loggingOperation()", throwing = "e")
    @Order(3)
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("An exception has been thrown in "+ joinPoint.getSignature().getName() + "()");
        log.error("Cause :"+e.getCause());
    }
}
