package com.lee.berries.soa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author 黄奕鹏(大鹏) 创建时间：2016/6/16
 */
public class AutoRegService implements BeanPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(AutoRegService.class);

	@Value(value = "${cache.enable:false}")
	private boolean isCacheEnable = false;

	@Autowired
	private SOAConfiguration soaConfig;

	public AutoRegService() {
	}

	@Override
	public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
		return o;
	}

	@Override
	public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

		Object newObj = o;

		/**
		 * 判断 enableSAO=true 是不是配置可用
		 */
		if (soaConfig.isEnableSOA() && newObj instanceof SOARemote) {
			SOARemote soaRemote = (SOARemote) newObj;
			soaConfig.exportRemoteService(soaRemote);
			logger.info("dove:soa:export:" + soaRemote.getClass().getName());
		}
		return newObj;
	}

}
