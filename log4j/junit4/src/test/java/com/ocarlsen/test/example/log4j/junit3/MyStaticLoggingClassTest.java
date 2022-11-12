package com.ocarlsen.test.example.log4j.junit3;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.InOrder;

import com.ocarlsen.test.example.log4j.MyStaticLoggingClass;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * Example unit test for {@link MyStaticLoggingClass} with JUnit 4.
 */
public class MyStaticLoggingClassTest {

    @Test
    public void loggingMethod() throws Exception {

        // Prepare mocks
        final Logger logger = mockLogger();

        // Given
        MyStaticLoggingClass classToTest = new MyStaticLoggingClass();

        // When
        classToTest.loggingMethod();

        // Then
        // (Asserts on other logic.)

        // Verify mocks
        final InOrder inOrder = inOrder(logger);
        inOrder.verify(logger).trace("this is a trace message");
        inOrder.verify(logger).debug("this is a debug message");
        inOrder.verify(logger).info("this is an info message");
        inOrder.verify(logger).warn("this is a warn message");
        inOrder.verify(logger).error("this is an error message");
        inOrder.verifyNoMoreInteractions();
    }

    @SuppressWarnings("ConstantConditions")
    private static Logger mockLogger() throws Exception {

        // Field is static
        Class<MyStaticLoggingClass> clazz = MyStaticLoggingClass.class;
        final Field loggerField = clazz.getDeclaredField("LOGGER");

        // Field is private
        loggerField.setAccessible(true);

        // Field is final
        Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
        getDeclaredFields0.setAccessible(true);
        Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
        Field modifiersField = null;
        for (Field each : fields) {
            if ("modifiers".equals(each.getName())) {
                modifiersField = each;
                break;
            }
        }
        modifiersField.setAccessible(true);
        modifiersField.setInt(loggerField, loggerField.getModifiers() & ~Modifier.FINAL);

        // Set mock logger on field
        final Logger logger = mock(Logger.class);
        loggerField.set(logger, logger);

        return logger;
    }
}
