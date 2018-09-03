package com.lee.berries.common.utils;

/**
 * 类型检查异常
 * @author lxw
 *
 */
public class ParamException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamException(){
		super();
	}
	
	public ParamException(String message) {
		super(message);
	}
	
	public ParamException(String message, Throwable cause) {
		super(message, cause);
	}
}
