package com.lee.berries.dao.annotation.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lee.berries.dao.annotation.Column;
import com.lee.berries.dao.annotation.Id;

public class BerriesAnnotationSupport {
	
	private Map<String, MethodMapper> idMappers;
	
	private Map<String, List<MethodMapper>> methodMappers;
	
	private static BerriesAnnotationSupport instance = new BerriesAnnotationSupport();
	
	private BerriesAnnotationSupport() {
		idMappers = new HashMap<>();
		methodMappers = new HashMap<>();
	}

	public <T> List<MethodMapper> getMethodMapper(Class<T> classzz) {
		String className = classzz.getName();
		List<MethodMapper> mapper = methodMappers.get(className);
		if(mapper == null) {
			mapper = new ArrayList<>();
			for(Method method : classzz.getMethods()) {
				String methodName = method.getName();
				if(!methodName.startsWith("get")) {
					continue;
				}
				Column column = method.getAnnotation(Column.class);
				String columnName = null;
				if(column != null) {
					columnName = column.name();
				}
				else {
					Id id = method.getAnnotation(Id.class);
					if(id != null) {
						columnName = id.name();
					}
				}
				if(columnName != null) {
					MethodMapper methodMapper = new MethodMapper();
					methodMapper.setColumnName(columnName);
					methodMapper.setFieldName(getFieldName(methodName));
					methodMapper.setMethod(method);
					methodMapper.setMethodName(methodName);
					mapper.add(methodMapper);
				}
			}
			methodMappers.put(className, mapper);
		}	
		return mapper;
	}
	
	public <T> MethodMapper getColumnName(Class<T> classzz, String fieldName) {
		List<MethodMapper> mapper = getMethodMapper(classzz);
		for(MethodMapper methodMapper : mapper) {
			if(methodMapper.getFieldName().equals(fieldName)) {
				return methodMapper;
			}
		}
		return null;
	}
	
	public <T> MethodMapper getIdMapper(Class<T> classzz) {
		String className = classzz.getName();
		MethodMapper mapper = idMappers.get(className);
		if(mapper == null) {
			for(Method method : classzz.getMethods()) {
				String methodName = method.getName();
				if(!methodName.startsWith("get")) {
					continue;
				}
				String columnName = null;
				Id id = method.getAnnotation(Id.class);
				if(id != null) {
					columnName = id.name();
					mapper = new MethodMapper();
					mapper.setColumnName(columnName);
					mapper.setFieldName(getFieldName(methodName));
					mapper.setMethod(method);
					mapper.setMethodName(methodName);
					idMappers.put(className, mapper);
					break;
				}
			}
		}	
		return mapper;
	}
	
	private String getFieldName(String getMethodName) {
		String fieldName = getMethodName.substring(3);
		return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1); 
	}
	
	public static BerriesAnnotationSupport getInstance() {
		return instance;
	}
}
