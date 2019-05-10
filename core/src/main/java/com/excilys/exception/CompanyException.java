package com.excilys.exception;

public class CompanyException extends Exception{

	private static final long serialVersionUID = -3279540662964756150L;
	private final String exceptionMessage;

	public CompanyException(String message) {
		this.exceptionMessage = message;
	}

	@Override
	public String getMessage() {
		return exceptionMessage;
	}
}
