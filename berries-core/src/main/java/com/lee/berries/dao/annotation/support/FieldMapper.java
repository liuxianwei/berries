package com.lee.berries.dao.annotation.support;

import com.alibaba.druid.sql.ast.SQLStructDataType.Field;

public class FieldMapper {

	private String fieldName;
	private String columnName;
	private Field field;
	private Object value;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
