package com.masai.exception;

@SuppressWarnings("serial")
public class LoginException extends RuntimeException{

	public LoginException() {
		// TODO Auto-generated constructor stub
	}
	
	public LoginException(String msg) {
		
		super(msg);
	
	}
}
