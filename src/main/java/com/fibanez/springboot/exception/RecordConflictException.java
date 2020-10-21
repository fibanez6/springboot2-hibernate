package com.fibanez.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RecordConflictException extends Exception {

	private static final long serialVersionUID = 1L;

	public RecordConflictException(String message) {
		super(message);
	}

	public RecordConflictException(String message, Throwable t) {
		super(message, t);
	}
}
