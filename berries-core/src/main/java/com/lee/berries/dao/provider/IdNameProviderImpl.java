package com.lee.berries.dao.provider;

import org.springframework.stereotype.Component;

/**
 * 根据类对象获取id字段名称，写死为id
 * @author Liuxianwei
 *
 */
@Component
public class IdNameProviderImpl implements IdNameProvider {

	@Override
	public <T> String getIdName(Class<T> classzz) {
		return "id";
	}

}
