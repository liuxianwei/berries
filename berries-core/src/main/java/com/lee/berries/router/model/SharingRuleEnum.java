package com.lee.berries.router.model;

public enum SharingRuleEnum {
	VALUE,
	MOD,
	DATE,
	RANGE;
	
	public static SharingRuleEnum parse(String str) {
		if(str == null) {
			return null;
		}
		return valueOf(str.toUpperCase());
	}
}
