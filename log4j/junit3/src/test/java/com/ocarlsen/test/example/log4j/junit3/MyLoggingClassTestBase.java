package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyLoggingClass;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public abstract class MyLoggingClassTestBase extends TestCase {

    public void testLoggingMethod() throws Exception {

        // Prepare mocks
        final Logger logger = prepareLogger();

        // Given
        MyLoggingClass classToTest = new MyLoggingClass();

        // When
        classToTest.loggingMethod();

        // Then
        // (Asserts on other logic.)

        // Verify mocks
        verifyLogger(logger);
        verifyNoMoreInteractions(logger);
    }

    protected void verifyLogger(final Logger logger) {
        final InOrder inOrder = inOrder(logger);
        inOrder.verify(logger).trace("this is a trace message");
        inOrder.verify(logger).debug("this is a debug message");
        inOrder.verify(logger).info("this is an info message");
        inOrder.verify(logger).warn("this is a warn message");
        inOrder.verify(logger).error("this is an error message");
        inOrder.verifyNoMoreInteractions();
    }

    protected abstract Logger prepareLogger() throws Exception;

}
