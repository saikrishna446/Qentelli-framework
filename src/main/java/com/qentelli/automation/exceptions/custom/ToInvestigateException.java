package com.qentelli.automation.exceptions.custom;

import com.qentelli.automation.exceptions.base.UnknownException;

public class ToInvestigateException extends UnknownException {

	public ToInvestigateException(String message, Throwable cause){
		super(message,cause);
	}

	public ToInvestigateException(String message) {
		super(message);
	}

	public ToInvestigateException(Throwable e) {
		super(e);
	}
}
