package com.excilys.exception;

public class ComputerException extends Exception{

	private static final long serialVersionUID = -3279540662964756150L;
	private String exceptionMessage;

	public ComputerException(String message) {
		this.exceptionMessage = message;
	}

	public String getMessage() {
		return exceptionMessage;
	}
}
