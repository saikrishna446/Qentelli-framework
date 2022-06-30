package com.qentelli.automation.exceptions.base;

import org.openqa.selenium.By;

public class PerformanceIssueException extends BaseException {
    private static final long serialVersionUID = 1L;

    public PerformanceIssueException(String msg) {
        super(msg);

    }

    public PerformanceIssueException(Throwable e1) {
        super(e1);
    }
    public PerformanceIssueException(String msg, Throwable e1) {
        super(msg, e1);
    }

    public PerformanceIssueException(Throwable e1, By b) {
        super(e1);
        System.out.println("Hello world!");
    }

    @Override
    public void addSymptoms() {
        label = "Performance Issue";
        type = ErrorType.Performance;

        myTypes.add("ApplicationTooSlowException");

    }

}
