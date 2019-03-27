package com.excilys.exception;

public class TimestampException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exceptionMessage;
	
	public TimestampException(String message) {
        this.exceptionMessage = message;
    }
	
	public String getMessage() {
		return exceptionMessage;
	}
}
