package com.propaganda.exception;

@SuppressWarnings("serial")
public class DataBaseException extends RuntimeException {
	@SuppressWarnings("unused")
	private Throwable nestedThrowable = null;
	public DataBaseException() {
		super("���ݿ����");
	}
	
	public DataBaseException(String e) {
		super(e);
	}
}
