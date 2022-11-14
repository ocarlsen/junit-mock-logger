package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyInstanceLoggingClass;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.mockito.internal.verification.NoMoreInteractions;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit3.PowerMockSuite;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Example unit test for {@link MyInstanceLoggingClass} with JUnit 3.
 * It uses PowerMock 1.x to mock out the {@link Logger}.
 */
@PrepareForTest(Logger.class)
@PowerMockIgnore({"jdk.internal.*"})
public class MyInstanceLoggingClass_PowerMockTest extends MyInstanceLoggingClassTestBase {

    /**
     * Use this {@link TestSuite} to run with PowerMock in JUnit 3.
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static TestSuite suite() throws Exception {
        final Class<MyInstanceLoggingClass_PowerMockTest> testCases = MyInstanceLoggingClass_PowerMockTest.class;
        return new PowerMockSuite("Unit tests for " + MyInstanceLoggingClass.class.getSimpleName(), testCases);
    }

    @Override
    protected Logger getMockLogger() {
        return mock(Logger.class);
    }

    @Override
    protected void verifyLogger(final Logger logger) {

        // Usual steps.
        super.verifyLogger(logger);

        verifyStaticInteractions();
    }

    @Override
    protected void verifyLogger(final Logger logger, final Exception ex) {

        // Usual steps.
        super.verifyLogger(logger, ex);

        verifyStaticInteractions();
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
        mockStatic(Logger.class);

        final Class<?> clazz = getClass().getClassLoader().loadClass(loggingClassName);
        when(Logger.getLogger(clazz)).thenReturn(logger);
    }

    private void verifyStaticInteractions() {

        // Also verify static mocking.
        // https://github.com/powermock/powermock/wiki/Mockito#how-to-verify-behavior
        verifyStatic(Logger.class, times(1));
        Logger.getLogger(MyInstanceLoggingClass.class);

        verifyStatic(Logger.class, new NoMoreInteractions());
        Logger.getLogger(Class.class);
    }
}
