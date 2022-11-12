package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyLoggingClass;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.mockito.InOrder;
import org.mockito.internal.verification.NoMoreInteractions;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit3.PowerMockSuite;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Example unit test for {@link MyLoggingClass} with JUnit 3.
 * It uses PowerMock to mock out the {@link Logger}.
 */
@PrepareForTest(Logger.class)
@PowerMockIgnore({"jdk.internal.*"})
public class MyLoggingClass_PowerMockTest extends MyLoggingClassTestBase {

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

        // Also verify static mocking.
        // https://github.com/powermock/powermock/wiki/Mockito#how-to-verify-behavior
        verifyStatic(Logger.class, times(1));
        Logger.getLogger(MyLoggingClass.class);

        verifyStatic(Logger.class, new NoMoreInteractions());
        Logger.getLogger(Class.class);

    }

    @Override
    protected Logger prepareLogger() {
        final Logger logger = mock(Logger.class);

        mockStatic(Logger.class);
        when(Logger.getLogger(MyLoggingClass.class)).thenReturn(logger);

        return logger;
    }
}
