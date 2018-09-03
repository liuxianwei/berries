package com.lee.berries.router.model;

public enum DateRuleEnum {
	YEAR,
	MONTH,
	DAY;
	
	public static DateRuleEnum parse(String str) {
		if(str == null) {
			return null;
		}
		return valueOf(str.toUpperCase());
	}
}
