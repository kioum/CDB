package com.excilys.exception;

public class TimestampException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String exceptionMessage;

	public TimestampException(String message) {
		this.exceptionMessage = message;
	}

	@Override
	public String getMessage() {
		return exceptionMessage;
	}
}
