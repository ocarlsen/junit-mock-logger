package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyLoggingClass;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.mock;

/**
 * Example unit test for {@link com.ocarlsen.test.example.log4j.MyLoggingClass} with JUnit 3.
 * It uses reflection to mock out the {@link Logger}.
 */
@SuppressWarnings("NewClassNamingConvention")
public class MyLoggingClass_ReflectionTest extends MyLoggingClassTestBase {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected Logger prepareLogger() throws Exception {

        // Field is static
        Class<MyLoggingClass> clazz = MyLoggingClass.class;
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
