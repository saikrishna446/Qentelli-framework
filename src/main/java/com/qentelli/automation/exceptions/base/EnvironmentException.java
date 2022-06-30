package com.qentelli.automation.exceptions.base;

public class EnvironmentException extends BaseException {
	private static final long serialVersionUID = 1L;

	public EnvironmentException(Throwable e1) {
		super(e1);

	}
	public EnvironmentException(String msg) {
		super(msg);

	}
	public EnvironmentException(String msg,Throwable cause) {
		super(msg,cause);

	}

	@Override
	public void addSymptoms() {
		label = "Environment Issue";
		type = ErrorType.Environment;

		myTypes.add("EnvironmentOutOfSyncException");
		myTypes.add("SauceConnectionException");
		myTypes.add("SauceException");
		myTypes.add("InfluxDBException");
		myTypes.add("InfluxDBIOException");

	}
}
