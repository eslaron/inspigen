package org.sobiech.inspigen.core.services.exceptions;

public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = 8935072308280818055L;
	
	public UserExistsException(Throwable cause) {
		super(cause);
	}
	public UserExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	public UserExistsException(String message) {
		super(message);
	}
	public UserExistsException() {
	}
}