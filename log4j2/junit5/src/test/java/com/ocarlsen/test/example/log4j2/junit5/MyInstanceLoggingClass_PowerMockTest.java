package com.ocarlsen.test.example.log4j2.junit5;

import com.ocarlsen.test.example.log4j2.MyInstanceLoggingClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * Example unit test for {@link MyInstanceLoggingClass} with JUnit 5.
 * It uses {@link Mockito#mockStatic(Class)} to mock out the {@link Logger}.
 */
@SuppressWarnings("NewClassNamingConvention")
public class MyInstanceLoggingClass_PowerMockTest extends MyInstanceLoggingClassTestBase {

    /**
     * This mocks static, but must be kept in instance scope.
     */
    private MockedStatic<LogManager> logManagerMockedStatic;

    @BeforeEach
    public void init() {
        logManagerMockedStatic = Mockito.mockStatic(LogManager.class);
    }

    @AfterEach
    public void cleanup() {
        verifyStaticInteractions();
        logManagerMockedStatic.close();
    }

    @Override
    protected Logger getMockLogger() {
        return mock(Logger.class);
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
                                final String loggerFieldName) throws Exception {

        // TODO: Figure out how to do in init method.
        final Class<?> clazz = getClass().getClassLoader().loadClass(loggingClassName);
        logManagerMockedStatic.when(() -> LogManager.getLogger(clazz)).thenReturn(logger);
    }

    private void verifyStaticInteractions() {

        // Also verify static mocking.
        logManagerMockedStatic.verify(() -> LogManager.getLogger(MyInstanceLoggingClass.class));

        logManagerMockedStatic.verifyNoMoreInteractions();
    }
}
