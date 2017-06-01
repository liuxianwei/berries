package com.lee.berries.common.utils;

import java.lang.reflect.Field;

/**
 * 一些公共使用的方法
 * @author Liuxianwei
 *
 */
public class BerriesUtils {

	/**
	 * 将大骆驼峰转换为下划线
	 * UserDetails转换为user_details
	 * @param name
	 * @return
	 */
	public static String camelCaseToUnderline(String name){
		char[] array = name.toCharArray();
		for(int i = 0; i < array.length; i++){
			char c = array[i];
			char newChar = c;
			if(c >= 'A' && c <= 'Z'){
				newChar = (char)(c+32);
				if(i == 0){
					name = name.replaceFirst(c + "", newChar + "");
				}
				else{
					name = name.replaceFirst(c + "", "_" + newChar);
				}
			}
			
		}
		return name;
	}
	
	/**
	 * 将下划线命名转换为骆驼峰命名
	 * @param name
	 * @return
	 */
	public static String underlineToUpperCamelCase(String name){
		String[] words = name.split("_");
		String newName = "";
		if(words.length > 1){
			for(String word:words){
				if(word.length() > 1){
					newName += word.substring(0, 1).toUpperCase() + word.substring(1);
				}
			}
		}
		return newName;
	}
	
	/**
	 * 将下划线命名转换为小骆驼峰命名 第一个单词首字母小写
	 * user_details=userDetails;
	 * @param name
	 * @return
	 */
	public static String underlineToLowerCamelCase(String name){
		name = underlineToUpperCamelCase(name);
		String newName = name.substring(0, 1).toLowerCase() + name.substring(1);
		return newName;
	}
	
	public static void setFieldValue(Object obj, String fieldName, Object value){
		try{
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj,  value);
		}
		catch(Exception e){
			
		}
	}
}
