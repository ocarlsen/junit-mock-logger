package com.ocarlsen.test.example.log4j2.junit40_43;

import com.ocarlsen.test.example.log4j2.MyInstanceLoggingClass;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.mock;

/**
 * Example unit test for {@link MyInstanceLoggingClass} with JUnit 3.
 * It uses reflection to mock out the {@link Logger}.
 */
@SuppressWarnings("NewClassNamingConvention")
public class MyInstanceLoggingClass_ReflectionTest extends MyInstanceLoggingClassTestBase {

    @Override
    protected Logger getMockLogger() {
        return mock(Logger.class);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void prepareInstance(final Logger logger,
                                   final Object loggingInstance,   // Need to provide as Object so it does not get loaded before we can mock it.
                                   final String loggingFieldName) throws Exception {

        Class<?> clazz = loggingInstance.getClass();
        final Field loggerField = clazz.getDeclaredField(loggingFieldName);

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

        // Set mock logger on Instance field
        loggerField.set(loggingInstance, logger);
    }

    @Override
    protected void prepareClass(final Logger logger,
                                final String loggingClassName,  // Need to provide as String so it does not get loaded before we can mock it.
                                final String loggerFieldName) {
        // No-op because logger field is Instance scope.
    }
}
