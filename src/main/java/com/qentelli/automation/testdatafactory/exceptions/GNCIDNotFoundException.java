package com.qentelli.automation.testdatafactory.exceptions;

import com.qentelli.automation.exceptions.base.AutomationIssueException;

public class GNCIDNotFoundException extends AutomationIssueException {
    public GNCIDNotFoundException(String message) {
        super(message);
    }
}
