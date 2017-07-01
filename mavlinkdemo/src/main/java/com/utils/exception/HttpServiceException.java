package com.utils.exception;

public class HttpServiceException extends Exception {
	
	private static final long serialVersionUID = -8591404630102202513L;
	
	public HttpServiceException(){
		
	}

	public HttpServiceException(String message){
		super(message);
	}
}
