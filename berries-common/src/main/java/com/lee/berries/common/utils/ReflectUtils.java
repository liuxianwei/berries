package com.lee.berries.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

public class ReflectUtils {
	
	static {  
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空  
        ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);  
        ConvertUtils.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);  
    }  
	/**
	 * 将map对象转换为实体对象
	 * @param map
	 * @param classzz
	 * @param camelPeak 是否开启下划线转骆驼峰
	 * @return
	 */
	public static <T> T parseMapToEntity(Map<String, Object> map, Class<T> classzz, boolean camelPeak){
		if(map == null){
			return null;
		}
		T target = null;
		try {
			target = classzz.newInstance();
			for(String key:map.keySet()){
				Object value = map.get(key);
				if(value != null){
					if(value instanceof Timestamp) {
						value = ((Date) value);
					}
					if(camelPeak) {
						key = BerriesUtils.underlineToLowerCamelCase(key);
					}
					BeanUtils.setProperty(target, key, value);
				}
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return target;
	}
	
	/**
	 * 将list map对象转换为实体对象
	 * @param map
	 * @param classzz
	 * @param camelPeak 是否开启下划线转骆驼峰
	 * @return
	 */
	public static <T> List<T> parseMapToEntity(List<Map<String, Object>> list, Class<T> classzz, boolean camelPeak){ 
		if(list == null){
			return null;
		}
		List<T> entityList = new ArrayList<T>();
		for(Map<String, Object> map:list){
			entityList.add(parseMapToEntity(map, classzz, camelPeak));
		}
		return entityList;
	}
	
}
