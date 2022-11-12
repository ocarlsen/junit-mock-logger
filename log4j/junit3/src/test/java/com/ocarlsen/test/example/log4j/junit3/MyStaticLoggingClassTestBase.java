package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyStaticLoggingClass;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public abstract class MyStaticLoggingClassTestBase extends TestCase {

    public static final String LOGGER_FIELD_NAME = "LOGGER";

    public void testLoggingMethod() throws Exception {

        // Prepare mocks
        final Logger logger = prepareLogger(LOGGER_FIELD_NAME);

        // Given
        MyStaticLoggingClass testInstance = new MyStaticLoggingClass();

        // When
        testInstance.loggingMethod();

        // Then
        // (Asserts on other logic.)

        // Verify mocks
        verifyLogger(logger);
        verifyNoMoreInteractions(logger);
    }

    public void testLoggingMethodWithException() throws Exception {

        // Prepare mocks
        final Logger logger = prepareLogger(LOGGER_FIELD_NAME);

        // Given
        MyStaticLoggingClass testInstance = new MyStaticLoggingClass();
        final Exception ex = new Exception("fake it");

        // When
        testInstance.loggingMethodWithException(ex);

        // Then
        // (Asserts on other logic.)

        // Verify mocks
        verifyLogger(logger, ex);
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

    protected void verifyLogger(final Logger logger, final Exception ex) {
        final InOrder inOrder = inOrder(logger);
        inOrder.verify(logger).trace("this is a trace message", ex);
        inOrder.verify(logger).debug("this is a debug message", ex);
        inOrder.verify(logger).info("this is an info message", ex);
        inOrder.verify(logger).warn("this is a warn message", ex);
        inOrder.verify(logger).error("this is an error message", ex);
        inOrder.verifyNoMoreInteractions();
    }

    protected abstract Logger prepareLogger(final String loggerFieldName) throws Exception;
}
