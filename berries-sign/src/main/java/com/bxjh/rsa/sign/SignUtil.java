package com.bxjh.rsa.sign;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import com.bxjh.rsa.RSA;

/**
 * 接口签名使用的通用工具
 * 
 * @author Liuxianwei
 *
 */
public class SignUtil {

	public final static String SIGN_FLIED_NAME = "sign"; //签名串的字段名称
	
	public final static String SIGNTYPE_FLIED_NAME = "signType"; //签名加密算法字段名称
	
	public final static String INPUTCHART_FLIED_NAME = "inputCharSet"; //字符编码字段名称
	
	/**
	 * 对一个对象进行签名
	 * @param target
	 */
	public static <T> void sign(T target, String privateKey){
		sign(target, privateKey, SIGN_FLIED_NAME, SIGNTYPE_FLIED_NAME, INPUTCHART_FLIED_NAME);
	}
	
	/**
	 * 对一个对象进行签名
	 * @param target
	 * @param privateKey
	 * @param signFieldName 
	 * @param signTypeFieldName
	 * @param inputCharSetFieldName
	 */
	public static <T> void sign(T target, String privateKey, String signFieldName, 
			String signTypeFieldName, String inputCharSetFieldName){
		String queryString = getQueryString(target, SIGN_FLIED_NAME, SIGNTYPE_FLIED_NAME, INPUTCHART_FLIED_NAME);
		String sign = RSA.sign(queryString, privateKey, (String) getValue(target, inputCharSetFieldName));
		setValue(target, signFieldName, sign);
	}
	
	/**
	 * 对象的签名验证
	 * @param target
	 * @return
	 */
	public static <T> boolean vertify(T target, String publicKey){
		return vertify(target, publicKey, SIGN_FLIED_NAME, SIGNTYPE_FLIED_NAME, INPUTCHART_FLIED_NAME);
	}
	
	/**
	 * 
	 * @param target
	 * @param signFieldName
	 * @param signTypeFieldName
	 * @param inputCharSetFieldName
	 * @return
	 */
	public static <T> boolean vertify(T target, String publicKey, String signFieldName, 
			String signTypeFieldName, String inputCharSetFieldName){
		String queryString = getQueryString(target, SIGN_FLIED_NAME, SIGNTYPE_FLIED_NAME, INPUTCHART_FLIED_NAME);
		String sign = (String) getValue(target, signFieldName);
		String inputCharSet = (String) getValue(target, inputCharSetFieldName);
		return RSA.verify(queryString, sign, publicKey, inputCharSet);
	}
	
	/**
	 * 获取对象除过签名字段的所有字段UrlEcoder后的串
	 * @param target
	 * @param inputCharSetFieldName
	 * @return
	 */
	public static <T> String getQueryString(T target, String signFieldName, 
			String signTypeFieldName, String inputCharSetFieldName){
		String queryString = "";
		Field[] fields = target.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for(int i = 0; i < fieldNames.length; i++){
			fieldNames[i] = fields[i].getName();
		}
		Arrays.sort(fieldNames);//按照字典进行排序
		try{
			for(String fieldName:fieldNames){
				Field field = target.getClass().getDeclaredField(fieldName);
				if(fieldName.equals(signFieldName)
						|| fieldName.equals(signTypeFieldName)
						|| fieldName.equals(inputCharSetFieldName)){
					continue;
				}
				field.setAccessible(true);
				Object value = field.get(target);
				if(value != null && !StringUtils.isEmpty(value.toString())){
					//queryString += "&" + fieldName + "=" + URLEncoder.encode(value.toString(), inputCharSet);
					queryString += "&" + fieldName + "=" + value;
				}
			}
			if(queryString.length() > 0){
				queryString = queryString.substring(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return queryString;
	}
	
	private static <T> Object getValue(T target, String name){
		try {
			String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
			Method method = null;
			try {
				method = target.getClass().getDeclaredMethod(methodName);
			}
			catch (Exception e) {
				method = target.getClass().getMethod(methodName);
			}
			Object value = method.invoke(target);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static <T> void setValue(T target, String name, Object value){
		try {
			BeanUtils.setProperty(target, name, value);
		} catch (Exception e) {
			
		}
	}
}
