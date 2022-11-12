package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyLoggingClass;
import junit.framework.TestCase;
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
public class MyLoggingClass_PowerMockTest extends TestCase {

    /**
     * Use this {@link TestSuite} as a stand-in for JUnit 4 RunWith(PowerMockRunner).
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public static TestSuite suite() throws Exception {
        final Class<MyLoggingClass_PowerMockTest> testCases = MyLoggingClass_PowerMockTest.class;
        return new PowerMockSuite("Unit tests for " + MyLoggingClass.class.getSimpleName(), testCases);
    }

    public void testLoggingMethod() {

        // Prepare mocks
        final Logger logger = mockLogger();

        // Given
        MyLoggingClass classToTest = new MyLoggingClass();

        // When
        classToTest.loggingMethod();

        // Then
        // (Asserts on other logic.)

        // Verify mocks
        final InOrder inOrder = inOrder(logger);
        inOrder.verify(logger).trace("this is a trace message");
        inOrder.verify(logger).debug("this is a debug message");
        inOrder.verify(logger).info("this is an info message");
        inOrder.verify(logger).warn("this is a warn message");
        inOrder.verify(logger).error("this is an error message");
        inOrder.verifyNoMoreInteractions();

        // Also verify static mocking.
        // https://github.com/powermock/powermock/wiki/Mockito#how-to-verify-behavior
        verifyStatic(Logger.class, times(1));
        Logger.getLogger(MyLoggingClass.class);

        verifyStatic(Logger.class, new NoMoreInteractions());
        Logger.getLogger(Class.class);

    }

    private static Logger mockLogger() {
        final Logger logger = mock(Logger.class);

        mockStatic(Logger.class);
        when(Logger.getLogger(MyLoggingClass.class)).thenReturn(logger);

        return logger;
    }
}
