package com.lee.berries.exception;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BizException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6417613796687468770L;
	
	protected static final Logger logger = LoggerFactory.getLogger(BizException.class);

	private static final String PROPERTIES_FILE = "exception/exception.properties";
	
	private static Properties PROPERTIES;
	
	static {
		PROPERTIES = new Properties();
		try {
			logger.info("load exception.properties!");
			PROPERTIES.load(BizException.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			logger.error("load exception error!");
		}
	}
	
	public BizException(String code, Object ...args) {
		super(getProperty(code, args));
	}
	
	public BizException(String code, Throwable e, Object ...args) {
		super(getProperty(code, args), e);
	}
	
	public BizException(String code, Throwable e) {
		this(code, e, new Object[] {});
	}
	
	public static String getProperty(String code, Object ...args) {
		String value = PROPERTIES.getProperty(code);
		if(value == null) {
			value = code;
		}
		if(args != null) {
			value = String.format(value, args);
		}
		return value;
	}
}
