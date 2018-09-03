package com.lee.berries.datasource.aop;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.datasource.BerriesDataSourceHolder;

/**
 * 在执行方法之前拦截
 * @author lxw
 *
 */
public class DataSourceAspect {
	
	private List<String> writeMethod = new ArrayList<>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public void before(JoinPoint point) {
		String methodName = point.getSignature().getName();
		String key = BerriesDataSourceHolder.get();
		if(key == null) {
			key = BerriesDataSourceHolder.markRead();
		}
		if(BerriesDataSourceHolder.READ_KEY.equals(key) 
				&& needMarkWrite(methodName)) {
			key = BerriesDataSourceHolder.markWrite();
		}
		logger.debug("mark datasource is {}", key);
	}
	
	public void after(JoinPoint point) {
		BerriesDataSourceHolder.set(null);
		logger.debug("clean datasource");
	}
	
	private boolean needMarkWrite(String methodName) {
		boolean flag = false;
		for(String m:writeMethod) {
			if(methodName.startsWith(m)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public void setNeedWriteMethod(String needWriteMethod) {
		if(needWriteMethod != null) {
			for(String m:needWriteMethod.split(",")) {
				writeMethod.add(m);
			}
		}
	}

}
