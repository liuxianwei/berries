package com.lee.berries.datasource;

public class BerriesDataSourceHolder {

	public static final String WRITE_KEY = "write"; //写数据的key
	
	public static final String READ_KEY = "read"; //读数据的Key
	
	
	private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();

	public static String get() {
		return dataSourceKey.get();
	}
	
	public static String set(String value) {
		dataSourceKey.set(value);
		return value;
	}
	
	public static String markRead() {
		return set(READ_KEY);
	}
	
	public static String markWrite() {
		return set(WRITE_KEY);
	}
	
}
