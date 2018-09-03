package com.lee.berries.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring 上下文和获取Bean的方式
 * @author Liuxianwei
 *
 */
@Service
public class SpringContextHolder implements ApplicationContextAware {

	//Spring 上下文
	private static ApplicationContext applicationContext;  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class); 
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
		LOGGER.info("set applicationContext to SpringContextHolder");
	}

	public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
	
    public static Object getBean(String beanName) {  
        return applicationContext.getBean(beanName);  
    }  
      
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);  
    }
    
    public static <T> Map<String, T> getBeanOfType(Class<T> clazz){
    	return applicationContext.getBeansOfType(clazz);
    }
}
