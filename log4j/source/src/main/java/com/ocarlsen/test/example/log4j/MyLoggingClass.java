package com.ocarlsen.test.example.log4j;

import org.apache.log4j.Logger;

/**
 * A class that uses a Log4J {@link Logger}.
 */
public class MyLoggingClass {

    private static final Logger LOGGER = Logger.getLogger(MyLoggingClass.class);

    public static void main(String[] args) {
        new MyLoggingClass().loggingMethod();
    }

    public void loggingMethod() {
        LOGGER.trace("this is a trace message");
        LOGGER.debug("this is a debug message");
        LOGGER.info("this is an info message");
        LOGGER.warn("this is a warn message");
        LOGGER.error("this is an error message");
    }
}