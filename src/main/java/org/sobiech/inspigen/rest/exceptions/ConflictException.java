package org.sobiech.inspigen.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = -758609869886243764L;

	public ConflictException() {}
	
	public ConflictException(Throwable cause) {
		super(cause);
	}
}