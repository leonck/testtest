package com.utils.exception;

@SuppressWarnings("serial")
public class SDCardNotAvailableException extends Exception {
	
	public SDCardNotAvailableException(){
		
	}
	
	public SDCardNotAvailableException(String message){
		super(message);
	}

}
