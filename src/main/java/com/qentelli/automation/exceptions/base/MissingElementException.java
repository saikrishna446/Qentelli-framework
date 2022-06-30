package com.qentelli.automation.exceptions.base;

public class MissingElementException extends BaseException {
	private static final long serialVersionUID = 1L;

	public MissingElementException(Throwable e) {
		super(e);
	}

	public MissingElementException(String msg) {
		super(msg);
	}

	public MissingElementException(String msg,Throwable e) {
		super(msg,e);
	}

	@Override
	public void addSymptoms() {
		label = "Missing Element";
		type = ErrorType.MissingElement;

		myTypes.add("NoSuchElementException");

		myMessages.add("waiting for visibility of element");
		myMessages.add("waiting for presence of element");
		myMessages.add("waiting for element found by");
		myMessages.add("waiting for url to contain");
	}
}
