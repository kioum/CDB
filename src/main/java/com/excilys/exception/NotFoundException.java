package com.excilys.exception;

public class NotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3279540662964756150L;
	public String exceptionMessage;
	
	public NotFoundException(String message) {
        this.exceptionMessage = message;
    }
}
