package com.ocarlsen.test.example.log4j;

import org.apache.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;

/**
 * A class that uses a Log4J {@link Logger}.
 */
public class MyLoggingClass {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @VisibleForTesting
    void loggingMethod() {
        logger.trace("this is a trace message");
        logger.debug("this is a debug message");
        logger.info("this is an info message");
        logger.warn("this is a warn message");
        logger.error("this is an error message");
    }

    public static void main(String[] args) {
        new MyLoggingClass().loggingMethod();
    }
}