package com.lee.berries.router.exception;

public class UnsupportedTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6107256975792583847L;

	public UnsupportedTypeException() {
        super();
    }
	
	public UnsupportedTypeException(String message) {
        super(message);
    }
	
	public UnsupportedTypeException(Class<?> classzz) {
        super("Unsupported type: " + classzz.getName());
    }
}
