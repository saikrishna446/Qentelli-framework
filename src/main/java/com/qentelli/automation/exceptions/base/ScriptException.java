/// ---- please use AutomationIssueException instead 
//package com.qentelli.automation.exceptions.base;
//
//public class ScriptException extends BaseException {
//
//	public ScriptException(String message, Throwable cause){
//		super(message,cause);
//	}
//
//	public ScriptException(String message) {
//		super(message);
//	}
//
//	public ScriptException(Throwable e) {
//		super(e);
//	}
//
//
//	@Override
//	public void addSymptoms() {
//		label = "Script Issue";
//		type = ErrorType.Script;
//
//		myTypes.add("NullPointerException");
//		myTypes.add("MissingResourceException");
//		myTypes.add("ArrayIndexOutOfBoundsException");
//		myTypes.add("IndexOutOfBoundsException");
//	}
//}
