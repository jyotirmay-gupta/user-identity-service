package com.redashwood.useridentity.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnWebApplication
public class ExecutionTimeLogger {

    private static final Logger LOGGER = LogManager.getLogger(ExecutionTimeLogger.class);

    @Around("execution(public * com.redashwood.useridentity.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        String methodName = joinPoint.getSignature().toShortString();

        LOGGER.info("Method {} completed execution in {} ms", methodName, duration);
        return result;
    }
}
