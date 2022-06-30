package com.qentelli.automation.exceptions.custom;

import com.qentelli.automation.exceptions.base.PerformanceIssueException;
import org.apache.commons.lang.StringUtils;
import org.testng.Reporter;

public class ApplicationTooSlowException extends PerformanceIssueException {
	private static final long serialVersionUID = 1L;

	private static String getEnv() {
		String environment = System.getProperty("environment");
		if (StringUtils.isBlank(environment)) {
			environment = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("environment");
		}
		return environment;
	}

	public ApplicationTooSlowException(Throwable e) {
		super(e);
	}

	public ApplicationTooSlowException(String message, Throwable cause) {
		super("The application is too slow on the '" + getEnv() + "' environment.\n" + message,cause);
	}

	public ApplicationTooSlowException(String message) {
		super("The application is too slow on the '" + getEnv() + "' environment.\n" + message);
	}

	@Override
	public void addSymptoms() {
		label = "Performance";
		type = ErrorType.Application;
	}
}
