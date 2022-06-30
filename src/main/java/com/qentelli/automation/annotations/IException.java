package com.qentelli.automation.annotations;

public interface IException {
	public enum ErrorType {
		Application, MissingElement, Validation, Environment, Automation, Performance, Unknown
	}

	public ErrorType type = ErrorType.Unknown;
	public String label = "";

	Exception getException();

	String getLabel();

	String getReason();

	ErrorType getType();

	Boolean isMyException();
}
