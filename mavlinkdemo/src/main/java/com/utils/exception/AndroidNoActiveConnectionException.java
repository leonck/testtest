package com.utils.exception;

@SuppressWarnings("serial")
public class AndroidNoActiveConnectionException extends Exception {
	
	public AndroidNoActiveConnectionException(){
		
	}
	public AndroidNoActiveConnectionException(String message){
		super(message);
	}

}
