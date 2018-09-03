package com.lee.berries.dao.enums;

public enum DeleteFlagEnum {

	NORMAL("正常", false), 
	DELETED("删除", true);
	
	private String name;
	
	private boolean value;

	private DeleteFlagEnum(String name, boolean value) {
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
