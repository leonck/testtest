package com.utils.exception;

@SuppressWarnings("serial")
public class SDCardFreeNotAvailableException extends Exception {
	
	public SDCardFreeNotAvailableException(){
		
	}
	public SDCardFreeNotAvailableException(String message){
		super(message);
	}

}
