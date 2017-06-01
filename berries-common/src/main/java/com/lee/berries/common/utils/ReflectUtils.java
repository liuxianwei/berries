package com.lee.berries.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class ReflectUtils {
	
	/**
	 * 将map对象转换为实体对象
	 * @param map
	 * @param classzz
	 * @return
	 */
	public static <T> T parseMapToEntity(Map<String, Object> map, Class<T> classzz){
		if(map == null){
			return null;
		}
		T target = null;
		try {
			target = classzz.newInstance();
			for(String key:map.keySet()){
				Object value = map.get(key);
				if(value != null){
					BeanUtils.setProperty(target, key, value);
				}
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return target;
	}
	
	public static <T> List<T> parseMapToEntity(List<Map<String, Object>> list, Class<T> classzz){ 
		if(list == null){
			return null;
		}
		List<T> entityList = new ArrayList<T>();
		for(Map<String, Object> map:list){
			entityList.add(parseMapToEntity(map, classzz));
		}
		return entityList;
	}
	
}
