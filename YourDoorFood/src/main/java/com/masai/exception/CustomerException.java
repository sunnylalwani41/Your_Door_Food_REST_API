package com.masai.exception;

<<<<<<< HEAD
public class CustomerException extends RuntimeException{
=======
@SuppressWarnings("serial")
public class CustomerException extends Exception{
>>>>>>> branch 'main' of https://github.com/sunnylalwani41/tasty-hour-5423.git
	public CustomerException() {
		// TODO Auto-generated constructor stub
	}
	public CustomerException(String massege) {
		super(massege);
	}
}
