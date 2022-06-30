package com.qentelli.automation.exceptions.custom;

import com.qentelli.automation.exceptions.base.EnvironmentException;

public class EnvironmentOutOfSyncException extends EnvironmentException {
    public EnvironmentOutOfSyncException(String message, Throwable cause){
        super("This exception occurred because this functionality differs in both the environments (UAT and QA3). This step will fail until both the environments are in sync.\n"
                + message,cause);
    }
    public EnvironmentOutOfSyncException(String message) {
        super("This exception occurred because this functionality differs in both the environments (UAT and QA3). This step will fail until both the environments are in sync.\n"
                + message);
    }

    public EnvironmentOutOfSyncException(Throwable e) {
        super(e);
    }

    @Override
    public void addSymptoms() {
        label = "Environment Issue";
        type = ErrorType.Environment;
    }
}