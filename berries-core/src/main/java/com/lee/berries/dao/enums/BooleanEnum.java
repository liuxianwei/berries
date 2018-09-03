package com.lee.berries.dao.enums;

public enum BooleanEnum {

	TRUE("true", true), 
	FALSE("false", false);
	
	private String name;
	
	private boolean value;

	private BooleanEnum(String name, boolean value) {
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
