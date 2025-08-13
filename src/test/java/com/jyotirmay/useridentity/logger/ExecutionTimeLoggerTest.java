package com.jyotirmay.useridentity.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ExecutionTimeLoggerTest {

    @Test
    @DisplayName("Should log execution time and return the result of the join point")
    void givenJoinPoint_whenLogExecutionTime_thenReturnsResultAndLogsExecutionTime() throws Throwable {

        ExecutionTimeLogger executionTimeLogger = new ExecutionTimeLogger();

        ProceedingJoinPoint mockJoinPoint = Mockito.mock(ProceedingJoinPoint.class);
        Signature mockSignature = Mockito.mock(Signature.class);

        Mockito.when(mockJoinPoint.getSignature()).thenReturn(mockSignature);
        Mockito.when(mockSignature.toShortString()).thenReturn("MockedClass.mockedMethod()");
        Mockito.when(mockJoinPoint.proceed()).thenAnswer(invocation -> {
            Thread.sleep(50); // simulate execution time
            return "ExpectedResult";
        });

        Object result = executionTimeLogger.logExecutionTime(mockJoinPoint);

        Assertions.assertEquals("ExpectedResult", result);

    }
}
