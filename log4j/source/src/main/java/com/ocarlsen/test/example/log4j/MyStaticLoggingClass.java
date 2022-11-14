package com.ocarlsen.test.example.log4j;

import org.apache.log4j.Logger;

/**
 * A class that uses a private <b>static</b> final {@link Logger}.
 */
public class MyStaticLoggingClass extends AbstractLoggingClass {

    public static void main(String[] args) {
        new MyStaticLoggingClass().loggingMethod();
    }

    private static final Logger LOGGER = Logger.getLogger(MyStaticLoggingClass.class);

    /**
     * <p>This method tests a few {@link Logger} methods.</p>
     */
    public void loggingMethod() {
        LOGGER.trace("this is a trace message");
        LOGGER.debug("this is a debug message");
        LOGGER.info("this is an info message");
        LOGGER.warn("this is a warn message");
        LOGGER.error("this is an error message");
    }

    public void loggingMethodWithException(Exception ex) {
        LOGGER.trace("this is a trace message", ex);
        LOGGER.debug("this is a debug message", ex);
        LOGGER.info("this is an info message", ex);
        LOGGER.warn("this is a warn message", ex);
        LOGGER.error("this is an error message", ex);
    }
}
