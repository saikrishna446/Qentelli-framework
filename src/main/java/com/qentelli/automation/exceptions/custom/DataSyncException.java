package com.qentelli.automation.exceptions.custom;

public class DataSyncException extends Exception{

	public DataSyncException(String message, Throwable cause){
		super(message,cause);
	}

	public DataSyncException(String message) {
		super(message);
	}
}
