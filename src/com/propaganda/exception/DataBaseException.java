package com.propaganda.exception;

@SuppressWarnings("serial")
public class DataBaseException extends RuntimeException {
	@SuppressWarnings("unused")
	private Throwable nestedThrowable = null;
	public DataBaseException() {
		super("Êý¾Ý¿â´íÎó");
	}
	
	public DataBaseException(String e) {
		super(e);
	}
}
