package com.ocarlsen.test.example.log4j.junit5;

import com.ocarlsen.test.example.log4j.MyInstanceLoggingClass;
import org.apache.log4j.Logger;
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

    private MockedStatic<Logger> logManagerMockedStatic;
    private Logger logger;

    @BeforeEach
    public void init() throws ClassNotFoundException {
        logger = mock(Logger.class);

        logManagerMockedStatic = Mockito.mockStatic(Logger.class);

        final Class<?> clazz = getClass().getClassLoader().loadClass(LOGGING_CLASS_NAME);
        logManagerMockedStatic.when(() -> Logger.getLogger(clazz)).thenReturn(logger);
    }

    @AfterEach
    public void cleanup() {

        // Verify before closing.
        verifyStaticInteractions();

        logManagerMockedStatic.close();
    }

    @Override
    protected Logger getMockLogger() {
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
        // No-op because @BeforeEach method handles.
    }

    private void verifyStaticInteractions() {

        // Also verify static mocking.
        logManagerMockedStatic.verify(() -> Logger.getLogger(MyInstanceLoggingClass.class));

        logManagerMockedStatic.verifyNoMoreInteractions();
    }
}
