package com.propaganda.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
	@SuppressWarnings("unused")
	private Throwable nestedThrowable = null;
	public UserNotFoundException() {
		super();
	}
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
