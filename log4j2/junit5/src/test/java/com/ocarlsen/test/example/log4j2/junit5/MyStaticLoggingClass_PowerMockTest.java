package com.ocarlsen.test.example.log4j2.junit5;

import com.ocarlsen.test.example.log4j2.MyStaticLoggingClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * Example unit test for {@link MyStaticLoggingClass} with JUnit 5.
 * It uses {@link Mockito#mockStatic(Class)} to mock out the {@link Logger}.
 */
@SuppressWarnings("NewClassNamingConvention")
public class MyStaticLoggingClass_PowerMockTest extends MyStaticLoggingClassTestBase {

    private static MockedStatic<LogManager> logManagerMockedStatic;

    /**
     * These hacks are necessary because {@link MyStaticLoggingClass#LOGGER} is static.
     * The {@link Logger#getLogger(Class)} method that we are mocking is only called once.
     */
    @SuppressWarnings("JavadocReference")
    private static Logger logger;
    private static boolean verifiedStatic = false;

    @BeforeAll
    public static void setupStatic() throws ClassNotFoundException {
        logger = mock(Logger.class);

        logManagerMockedStatic = Mockito.mockStatic(LogManager.class);

        final Class<?> clazz = MyStaticLoggingClass_PowerMockTest.class.getClassLoader().loadClass(LOGGING_CLASS_NAME);
        logManagerMockedStatic.when(() -> LogManager.getLogger(clazz)).thenReturn(logger);
    }

    @AfterAll
    public static void cleanup() {
        verifyStaticInteractions();
        logManagerMockedStatic.close();
    }

    @Override
    protected Logger getMockLogger() {
        reset(logger);

        return logger;
    }

    @Override
    protected void prepareInstance(final Logger logger,
                                   final Object testInstance,
                                   final String loggerFieldName) {
        // No-op because logger factory is mocked.
    }

    @Override
    protected void prepareClass(final Logger logger,
                                final String loggingClassName,  // Need to provide as String so it does not get loaded before we can mock it.
                                final String loggerFieldName) {
        // No-op because @BeforeAll method handles.
    }

    private static void verifyStaticInteractions() {

        // Also verify static mocking.
        logManagerMockedStatic.verify(() -> LogManager.getLogger(MyStaticLoggingClass.class));

        logManagerMockedStatic.verifyNoMoreInteractions();
    }
}
