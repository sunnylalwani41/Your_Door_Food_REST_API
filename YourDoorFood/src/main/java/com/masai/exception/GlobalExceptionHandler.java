package com.masai.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetail> notFoundExceptionHandler(NoHandlerFoundException nhfe, WebRequest req){
		MyErrorDetail myError= new MyErrorDetail();
		myError.setTimeStamp(LocalDateTime.now());
		myError.setMessage(nhfe.getMessage());
		myError.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetail>(myError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetail> allExceptionHandler(Exception e, WebRequest req){
		MyErrorDetail myError= new MyErrorDetail();
		myError.setTimeStamp(LocalDateTime.now());
		myError.setMessage(e.getMessage());
		myError.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetail>(myError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetail> validationExceptionHandler(MethodArgumentNotValidException manve){
		MyErrorDetail myError= new MyErrorDetail();
		myError.setTimeStamp(LocalDateTime.now());
		myError.setMessage(manve.getMessage());
		myError.setDetails(manve.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<MyErrorDetail>(myError, HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler(RestaurantException.class)
	public ResponseEntity<MyErrorDetail> myRouteException(RestaurantException re, WebRequest webReq){
		
		MyErrorDetail red = new MyErrorDetail();
		red.setTimeStamp(LocalDateTime.now());
		red.setMessage(re.getMessage());
		red.setDetails(webReq.getDescription(false));
		
		return new ResponseEntity<MyErrorDetail>(red, HttpStatus.BAD_REQUEST);
		
	}
	
}
