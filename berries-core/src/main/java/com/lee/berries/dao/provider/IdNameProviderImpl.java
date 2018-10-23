package com.lee.berries.dao.provider;

import org.springframework.stereotype.Component;

import com.lee.berries.dao.annotation.support.BerriesAnnotationSupport;
import com.lee.berries.dao.annotation.support.MethodMapper;

/**
 * 根据类对象获取id字段名称，写死为id
 * @author Liuxianwei
 *
 */
@Component
public class IdNameProviderImpl implements IdNameProvider {

	@Override
	public <T> MethodMapper getIdName(Class<T> classzz) {
		return BerriesAnnotationSupport.getInstance().getIdMapper(classzz);
	}

}
