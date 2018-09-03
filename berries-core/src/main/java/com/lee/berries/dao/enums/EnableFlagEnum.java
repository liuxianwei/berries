package com.lee.berries.dao.enums;

public enum EnableFlagEnum {

	ENABLE("启用", true), 
	DISABLE("禁用", false);
	
	private String name;
	
	private boolean value;

	private EnableFlagEnum(String name, boolean value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public boolean getValue() {
		return value;
	}


	
}
