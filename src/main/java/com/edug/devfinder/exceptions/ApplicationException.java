package com.edug.devfinder.exceptions;

import java.io.Serial;

public class ApplicationException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message) {
		super(message);
	}

}

