package com.lee.berries.common.utils;

import org.apache.commons.beanutils.BeanUtils;

public class ValidateUtil {

	public static <T> void isNull(T target, String ...fields) {
		if(target == null) {
			throw new ParamException("请求体不能为null!");
		}
		for(String filed:fields) {
			Object value = null;
			try {
				value = BeanUtils.getProperty(target, filed);
			} catch (Exception e) {} 
			if(value == null) {
				throw new ParamException("字段" + filed + "不能为null！");
			}
		}
	}
}
