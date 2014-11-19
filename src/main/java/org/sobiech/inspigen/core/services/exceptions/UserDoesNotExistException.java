package org.sobiech.inspigen.core.services.exceptions;

public class UserDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 8935072308280818055L;
	
	public UserDoesNotExistException(Throwable cause) {
		super(cause);
	}
	public UserDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
	public UserDoesNotExistException(String message) {
		super(message);
	}
	public UserDoesNotExistException() {
	}
}