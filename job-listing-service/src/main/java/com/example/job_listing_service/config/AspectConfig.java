package com.example.job_listing_service.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectConfig {

    /**
     * This method logs the entry and exit of the service methods
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.example.job_listing_service.service.impl.*.*(..))")
    public Object serviceLogInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Entering - " + proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.info("Completed the execution of- " + proceedingJoinPoint.getSignature().getName());
        return result;
    }

    /**
     * This method logs the entry and exit of the controller methods
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.example.job_listing_service.controller.*.*(..))")
    public Object controllerLogInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Entering - " + proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        log.info("Completed the execution of- " + proceedingJoinPoint.getSignature().getName());
        return result;
    }
}
