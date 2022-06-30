package com.qentelli.automation.exceptions.base;

public class AutomationIssueException extends BaseException {
	private static final long serialVersionUID = 1L;

	public AutomationIssueException(Throwable e1) {
		super(e1);
	}
	public AutomationIssueException(String msg) {
		super(msg);
	}

	public AutomationIssueException(String msg, Throwable e1) {
		super(msg,e1);
	}

	@Override
	public void addSymptoms() {
		label = "Selenium Issue";
		type = ErrorType.Automation;

		myTypes.add("UnreachableBrowserException");
		myTypes.add("StaleElementReferenceException");

		myMessages.add("Error occurred trying to find number of elements that match");
	}

}