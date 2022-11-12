package com.ocarlsen.test.example.log4j.junit3;

import com.ocarlsen.test.example.log4j.MyStaticLoggingClass;

public abstract class MyStaticLoggingClassTestBase extends MyLoggingClassTestBase {

    private static final String LOGGING_CLASS_NAME = "com.ocarlsen.test.example.log4j.MyStaticLoggingClass";
    private static final String LOGGER_FIELD_NAME = "LOGGER";

    @Override
    protected String getLoggingClassName() {
        return LOGGING_CLASS_NAME;
    }

    @Override
    protected String getLoggerFieldName() {
        return LOGGER_FIELD_NAME;
    }

    @Override
    protected MyStaticLoggingClass newLoggingInstance() {
        return new MyStaticLoggingClass();
    }
}
