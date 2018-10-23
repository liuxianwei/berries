package com.lee.berries.dao.annotation.support;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodMapper {

	private String fieldName;
	private String methodName;
	private String columnName;
	private Method method;
	
	protected static final Logger logger = LoggerFactory.getLogger(MethodMapper.class);
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Object getValue(Object target) {
		try {
			return method.invoke(target);
		} catch (Exception e) {
			logger.error("error", e);
		}
		return null;
	}
}
