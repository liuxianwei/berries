package com.lee.berries.soa.util;


/**
 * @author 黄奕鹏(大鹏)
 * 参数检查等参数相关操作 
 */
public class ParamUtil {
	/**
	 * 不满足条件，抛出错误信息
	 * @param condition
	 * @param errMsg
     */
	public static void check(boolean condition,String errMsg)
	{
		if(!condition){
			throw new ParamException(errMsg);
		}
	}

	/**
	 * 如果参数为空或null，抛出错误信息
	 * @param str
	 * @param errMsg
     */
	public static void checkNotNullOrBlank(String str,String errMsg)
	{
		if(str == null || str.trim().length() ==0){
			throw new ParamException(errMsg);
		}
	}

	/**
	 * 整数格式不满足要求，抛出错误信息
	 * @param value
	 * @param errMsg
	 * @return
     */
	public static int parseInt(String value,String errMsg)
	{
		try {
			return Integer.parseInt(value);
		}catch(NumberFormatException nfe){
			throw new ParamException(errMsg);
		}
	}

	/**
	 * 解析布尔值的输入,不满足格式抛出错误信息
	 * @param value
	 * @param errMsg
     * @return
     */
	public static boolean parseBoolean(String value,String errMsg)
	{
		String temp = value.toLowerCase();
		if(temp.equals("true")){
			return true;
		}else if(temp.equals("false")){
			return false;
		}else {
			throw new ParamException(errMsg);
		}
	}
}
