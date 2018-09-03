package com.lee.berries.dao.params;

public class UpdateByIdsParam<T> {

	private Class<T> classzz;
	private String fieldName;
	private Object updateValue;
	private Object[] ids;
	
	public UpdateByIdsParam() {
		super();
	}
	
	public UpdateByIdsParam(Class<T> classzz, String fieldName, Object updateValue, Object[] ids) {
		super();
		this.classzz = classzz;
		this.fieldName = fieldName;
		this.updateValue = updateValue;
		this.ids = ids;
	}
	
	public Class<T> getClasszz() {
		return classzz;
	}
	public void setClasszz(Class<T> classzz) {
		this.classzz = classzz;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getUpdateValue() {
		return updateValue;
	}
	public void setUpdateValue(Object updateValue) {
		this.updateValue = updateValue;
	}
	public Object[] getIds() {
		return ids;
	}
	public void setIds(Object[] ids) {
		this.ids = ids;
	}
}
