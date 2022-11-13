package com.ocarlsen.test.example.log4j2.junit4;

import com.ocarlsen.test.example.log4j2.MyStaticLoggingClass;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.mock;

/**
 * Example unit test for {@link MyStaticLoggingClass} with JUnit 4.
 * It uses reflection to mock out the {@link Logger}.
 */
@SuppressWarnings("NewClassNamingConvention")
public class MyStaticLoggingClass_ReflectionTest extends MyStaticLoggingClassTestBase {

    @Override
    protected Logger getMockLogger() {
        return mock(Logger.class);
    }

    @Override
    protected void prepareAfterInstance(final Logger logger,
                                        final Object testInstance,
                                        final String loggerFieldName) {
        // No-op because logger field is Class scope.
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void prepareBeforeInstance(final Logger logger,
                                         final String loggingClassName,  // Need to provide as String so it does not get loaded before we can mock it.
                                         final String loggerFieldName) throws Exception {

        Class<?> clazz = getClass().getClassLoader().loadClass(loggingClassName);
        final Field loggerField = clazz.getDeclaredField(loggerFieldName);

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

        // Set mock logger on Class field
        loggerField.set(null, logger);
    }
}
