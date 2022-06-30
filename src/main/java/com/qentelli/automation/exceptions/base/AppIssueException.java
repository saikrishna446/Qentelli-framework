package com.qentelli.automation.exceptions.base;

import org.openqa.selenium.By;

public class AppIssueException extends BaseException {
	private static final long serialVersionUID = -731231456376111563L;

	public AppIssueException(String msg) {
		super(msg);

	}

	public AppIssueException(Throwable e1) {
		super(e1);
	}
	public AppIssueException(String msg, Throwable e1) {
		super(msg, e1);
	}

	public AppIssueException(Throwable e1, By b) {
		super(e1);
		System.out.println("Hello world!");
	}

	@Override
	public void addSymptoms() {
		label = "Application Issue";
		type = ErrorType.Application;

		myTypes.add("AssertionError");
		myTypes.add("Assert");
		myTypes.add("Assertion");
		myTypes.add("ComparisonFailure");
		myTypes.add("UnhandledAlertException");
		myTypes.add("InvalidElementStateException");

		myMessages.add("Assert");
		myMessages.add("not displayed");
		myMessages.add("waiting for element to be clickable");



	}

}
