package com.qentelli.automation.exceptions.custom;


import com.qentelli.automation.exceptions.base.EnvironmentException;


public class UnknownHostException extends EnvironmentException {
	private static final long serialVersionUID = 1L;

	public UnknownHostException(String message) {
		super("the api isn't avaible:"+message);
	}

	@Override
	public void addSymptoms() {
		label = "UnknownHostException";
		type = ErrorType.Environment;
	}
}
