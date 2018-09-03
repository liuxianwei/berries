package com.lee.berries.dao.params;

public class SaveWithWhereParam<T> {

	private T target;
	
	private String[] whereFields;
	
	public SaveWithWhereParam() {
	}

	public SaveWithWhereParam(T target, String[] whereFields) {
		super();
		this.target = target;
		this.whereFields = whereFields;
	}
	
	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public String[] getWhereFields() {
		return whereFields;
	}

	public void setWhereFields(String[] whereFields) {
		this.whereFields = whereFields;
	}

	
}
