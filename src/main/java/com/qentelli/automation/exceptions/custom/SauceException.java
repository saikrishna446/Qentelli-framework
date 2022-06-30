package com.qentelli.automation.exceptions.custom;

import com.qentelli.automation.exceptions.base.EnvironmentException;

public class SauceException extends EnvironmentException {

    public SauceException(String message, Throwable cause){
        super("Sauce Exception occurred\n" + message,cause);
    }

    public SauceException(String message) {
        super("Sauce Exception occurred\n" + message);
    }

    public SauceException(Throwable e) {
        super(e);
    }

    @Override
    public void addSymptoms() {
        label = "Sauce Issue";
        type = ErrorType.Environment;

        myMessages.add("504 Gateway Time-out");
        myMessages.add("Sauce Driver failed");
        myMessages.add("Unable to determine type from: E. Last 1 characters read: E");
    }
}
