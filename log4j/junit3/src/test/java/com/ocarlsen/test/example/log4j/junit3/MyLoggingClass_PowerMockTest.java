package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyLoggingClass;
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
 * Example unit test for {@link MyLoggingClass} with JUnit 3.
 * It uses PowerMock 1.x to mock out the {@link Logger}.
 */
@PrepareForTest(Logger.class)
@PowerMockIgnore({"jdk.internal.*"})
public class MyLoggingClass_PowerMockTest extends MyLoggingClassTestBase {

    /**
     * These hacks are necessary because {@link MyLoggingClass#LOGGER} is static.
     * The {@link Logger#getLogger(Class)} method that we are mocking is only called once.
     * TODO: Make {@link MyLoggingClass#LOGGER} non static
     */
    @SuppressWarnings("JavadocReference")
    private static Logger logger;
    private static boolean verifiedStatic = false;

    /**
     * Use this {@link TestSuite} to run with PowerMock in JUnit 3.
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static TestSuite suite() throws Exception {
        final Class<MyLoggingClass_PowerMockTest> testCases = MyLoggingClass_PowerMockTest.class;
        return new PowerMockSuite("Unit tests for " + MyLoggingClass.class.getSimpleName(), testCases);
    }

    @Override
    protected void verifyLogger(final Logger logger) {

        // Usual steps.
        super.verifyLogger(logger);

        // Only happens once, when class is loaded.
        if (!verifiedStatic) {
            verifiedStatic = true;

            verifyStaticInteractions();
        }
    }

    @Override
    protected void verifyLogger(final Logger logger, final Exception ex) {

        // Usual steps.
        super.verifyLogger(logger, ex);

        // Only happens once, when class is loaded.
        if (!verifiedStatic) {
            verifiedStatic = true;

            verifyStaticInteractions();
        }

    }

    @Override
    protected Logger prepareLogger() {
        if (logger == null) {
            logger = mock(Logger.class);

            mockStatic(Logger.class);
            when(Logger.getLogger(MyLoggingClass.class)).thenReturn(logger);
        } else {
            reset(logger);
        }

        return logger;
    }

    private void verifyStaticInteractions() {

        // Also verify static mocking.
        // https://github.com/powermock/powermock/wiki/Mockito#how-to-verify-behavior
        verifyStatic(Logger.class, times(1));
        Logger.getLogger(MyLoggingClass.class);

        verifyStatic(Logger.class, new NoMoreInteractions());
        Logger.getLogger(Class.class);
    }
}
