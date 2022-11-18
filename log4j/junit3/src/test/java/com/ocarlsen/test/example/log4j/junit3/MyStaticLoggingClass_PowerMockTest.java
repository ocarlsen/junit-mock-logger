package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyStaticLoggingClass;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.mockito.internal.verification.NoMoreInteractions;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit3.PowerMockSuite;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Example unit test for {@link MyStaticLoggingClass} with JUnit 3.
 * It uses PowerMock to mock out the {@link Logger}.
 */
@PrepareForTest(Logger.class)
@PowerMockIgnore({"jdk.internal.*"})
public class MyStaticLoggingClass_PowerMockTest extends MyStaticLoggingClassTestBase {

    /**
     * These hacks are necessary because {@link MyStaticLoggingClass#LOGGER} is static.
     * The {@link Logger#getLogger(Class)} method that we are mocking is only called once.
     */
    @SuppressWarnings("JavadocReference")
    private static Logger logger;
    private static boolean verifiedStatic = false;

    /**
     * Use this {@link TestSuite} to run with PowerMock in JUnit 3.
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static TestSuite suite() throws Exception {
        final Class<MyStaticLoggingClass_PowerMockTest> testCases = MyStaticLoggingClass_PowerMockTest.class;
        return new PowerMockSuite("Unit tests for MyStaticLoggingClass", testCases);
    }

    @Override
    protected void setUp() throws ClassNotFoundException {
        if (logger == null) {
            logger = mock(Logger.class);

            mockStatic(Logger.class);

            final Class<?> clazz = getClass().getClassLoader().loadClass(LOGGING_CLASS_NAME);
            when(Logger.getLogger(clazz)).thenReturn(logger);
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        // Only happens once, when class is loaded.
        if (!verifiedStatic) {
            verifiedStatic = true;

            verifyStaticInteractions();
        }
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
        // No-op because setUp method handles.
    }

    private static void verifyStaticInteractions() {

        // Also verify static mocking.
        // https://github.com/powermock/powermock/wiki/Mockito#how-to-verify-behavior
        verifyStatic(Logger.class, times(1));
        Logger.getLogger(MyStaticLoggingClass.class);

        verifyStatic(Logger.class, new NoMoreInteractions());
        Logger.getLogger(MyStaticLoggingClass.class);
    }
}
