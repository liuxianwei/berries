package com.lee.berries.dao.context;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	protected final static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);
	 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
		logger.debug("set applicationContext!");
	}

	public static ApplicationContext getApplicationContext() {
        return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		return (T) applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> classzz) {
		return applicationContext.getBean(classzz);
	}
	
	public static <T> Map<String, T> getBeans(Class<T> classzz) {
		return applicationContext.getBeansOfType(classzz);
	}
}
