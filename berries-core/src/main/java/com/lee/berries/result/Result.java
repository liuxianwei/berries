package com.lee.berries.result;

/**
 * 包装controller返回的结果数据
 * 
 * @author yzw
 *
 */
public class Result<T> {
	/**
	 * 返回状态码
	 */
	private int code;
	/**
	 * 返回消息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private T data;
	
	public static <T> Result<T> bulder(ResultCodeEnum codeEnum, T data) {
		return new Result<T>(codeEnum, data);
	}
	
	public static <T> Result<T> bulder(ResultCodeEnum codeEnum, String msg, T data) {
		return new Result<T>(codeEnum, msg, data);
	}
	
	public static <T> Result<T> successed() {
		return successed(null);
	}
	
	public static <T> Result<T> successed(T data) {
		return bulder(ResultCodeEnum.SUCCESS, data);
	}
	
	public static <T> Result<T> failed() {
		return failed(null);
	}
	
	public static <T> Result<T> failed(T data) {
		return bulder(ResultCodeEnum.FAIL, data);
	}
	
	public Result(ResultCodeEnum codeEnum, T data) {
		this.code = codeEnum.getCode();
		this.msg  = codeEnum.getMessage();
		this.data = data;
	}
	
	public Result(ResultCodeEnum codeEnum, String msg, T data) {
		this.code = codeEnum.getCode();
		this.msg  = msg;
		this.data = data;
	}
	
	public Result(ResultCodeEnum code) {
		this(code, null);
	}
	
	public int getCode() {
		return this.code;
	}
	public void setCode(ResultCodeEnum codeEnum) {
		this.code = codeEnum.getCode();
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
