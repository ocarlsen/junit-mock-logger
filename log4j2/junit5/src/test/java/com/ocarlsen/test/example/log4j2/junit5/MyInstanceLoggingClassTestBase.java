package com.ocarlsen.test.example.log4j2.junit5;

import com.ocarlsen.test.example.log4j2.MyInstanceLoggingClass;

public abstract class MyInstanceLoggingClassTestBase extends MyLoggingClassTestBase<MyInstanceLoggingClass> {

    static final String LOGGING_CLASS_NAME = "com.ocarlsen.test.example.log4j2.MyInstanceLoggingClass";
    static final String LOGGER_FIELD_NAME = "logger";

    @Override
    protected String getLoggingClassName() {
        return LOGGING_CLASS_NAME;
    }

    @Override
    protected String getLoggerFieldName() {
        return LOGGER_FIELD_NAME;
    }

    @Override
    protected MyInstanceLoggingClass newLoggingInstance() {
        return new MyInstanceLoggingClass();
    }
}
