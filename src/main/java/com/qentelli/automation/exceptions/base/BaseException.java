package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.qentelli.automation.annotations.IException;

import java.util.*;

public abstract class BaseException extends RuntimeException implements IException {
	private static final long serialVersionUID = 5029371255785944491L;
	Logger Log = LogManager.getLogger(BaseException.class);

	public Throwable e;

	int a;
	String port;
	String host;
	String page;
	String testrail;
	String msg;
	String exceptionType;
	WebDriver driver;
	String stackTrace;

	public ErrorType type = ErrorType.Unknown;
	public String label = "";

	public List<String> myTypes = new ArrayList<String>();
	public List<String> myMessages = new ArrayList<String>();

	public BaseException(String msg) {
		super(msg);
		addSymptoms();
		appendMessage(msg);
	}

	public BaseException(Throwable e1) {
		super(e1);
		e = e1;
		addSymptoms();
		appendMessage(e.getMessage());
		Log.info("Exception:\t" + e.getMessage());

	}

	public BaseException(String message, Throwable cause) {
		super(appendMessage(message,cause), cause);
		e = cause;
		addSymptoms();
		appendMessage(e.getMessage());
		Log.info("Exception:\t" + e.getMessage());
	}

	private void appendMessage(String message){
		if(this.msg == null){
			this.msg = message;
		}
		else if(!this.msg.contains(message)){
			this.msg = this.msg + "\n " + message;
		}
	}

	private static String appendMessage(String message, Throwable cause){
		String outcome = message;
		if(!outcome.contains(cause.getMessage())){
			outcome = outcome + "\n " + cause.getMessage();
		}

		try {
			if (!outcome.contains(((BaseException) cause).getReason())) {
				outcome = outcome + "\n " + ((BaseException) cause).getReason();
			}
		}catch (Exception ignored){}
		return outcome;
	}

	public static String getErrorType(String comment) {

		if (comment.equals("none")) {
			return ErrorType.Validation.toString();
		}
		ErrorType et = ErrorType.Unknown;
		if (comment.contains("Actual object") || comment.contains("AssertionError") || comment.contains("AppIssue")) {
			et = ErrorType.Validation;
		} else if (comment.contains("failed to get data")) {
			et = ErrorType.Environment;
		} else if (comment.contains("MissingResourceException") || comment.contains("NullPointerException")) {
			et = ErrorType.Automation;
		} else if (comment.contains("Missing") || comment.contains("presense") || comment.contains("presense")) {
			et = ErrorType.MissingElement;
		} else {
			et = ErrorType.Unknown;
		}
		if (comment.contains("presence")) {
			et = ErrorType.MissingElement;
		}
		if (comment.contains("grow my business header not displayed")) {
			et = ErrorType.Automation;
		}
		return et.toString();
	}

	@Override
	public BaseException getException() {
		return this;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getReason() {
		return msg;
	}

	@Override
	public ErrorType getType() {
		return type;
	}

	public void addSymptoms(){

	}

	public Boolean isMyException(){

		for(String ex : myTypes){
			if (e.getClass().getSimpleName().equalsIgnoreCase(ex)) {
				return true;
			}
		}

		if(msg != null) {
			for (String m : myMessages) {
				if (msg.contains(m)) {
					return true;
				}
			}
		}
		return false;
	}

}
