package com.qentelli.automation.exceptions.custom;

public class ScriptSyncException extends Exception{

	public ScriptSyncException(String message, Throwable cause){
		super(message,cause);
	}

	public ScriptSyncException(String message) {
		super(message);
	}
}
