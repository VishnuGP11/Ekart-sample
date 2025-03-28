package com.infy.user.exceptions;

public class ErrorMessage {
	private int errorCode;
	private String message;

	public ErrorMessage(int errorCode, String message2) {
		this.errorCode = errorCode;
		this.message = message2;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
