package com.ocarlsen.test.example.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class that uses a private final {@link Logger}.
 */
public class MyInstanceLoggingClass extends AbstractLoggingClass {

    public static void main(String[] args) {
        new MyInstanceLoggingClass().loggingMethod();
        new MyInstanceLoggingClass().loggingMethodWithException(new RuntimeException("yay"));
    }

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * <p>This method tests a few {@link Logger} methods.</p>
     */
    public void loggingMethod() {
        logger.trace("this is a trace message");
        logger.debug("this is a debug message");
        logger.info("this is an info message");
        logger.warn("this is a warn message");
        logger.error("this is an error message");
    }

    /**
     * <p>This method tests a few {@link Logger} methods with an Exception argument.</p>
     */
    public void loggingMethodWithException(Exception ex) {
        logger.trace("this is a trace message", ex);
        logger.debug("this is a debug message", ex);
        logger.info("this is an info message", ex);
        logger.warn("this is a warn message", ex);
        logger.error("this is an error message", ex);
    }
}
