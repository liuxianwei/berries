package com.lee.berries.soa.util;

/**
 * 
 * @author 黄奕鹏
 * 参数错误异常
 */
public class ParamException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type = 0;
	public ParamException(String msg)
	{
		super(msg);
	}
	
	public  ParamException(String msg,int type)
	{
		super(msg);
		this.type = type;
	}
	
	public int getType()
	{
		return type;
	}
}
