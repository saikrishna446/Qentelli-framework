package com.qentelli.automation.exceptions.base;

public class UnknownException extends BaseException {
	private static final long serialVersionUID = -3428581656910893900L;
	public static final String label = "Unknown";
	public static final ErrorType type = ErrorType.Unknown;

	String reason = "Base Reason";

	public UnknownException(String msg) {
		super(msg);
	}

	public UnknownException(Throwable e1) {
		super(e1);
	}

	public UnknownException(String msg,Throwable e1) {
		super(msg, e1);
	}

	@Override
	public Boolean isMyException() {
		return true;
	}

	@Override
	public String getLabel() {
		return null;
	}

	@Override
	public ErrorType getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
