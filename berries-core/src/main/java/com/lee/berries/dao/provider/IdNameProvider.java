package com.lee.berries.dao.provider;

/**
 * 获取类对象的id名称
 * @author Liuxianwei
 *
 */
public interface IdNameProvider {

	/**
	 * 根据对象获取表名称
	 * @param classzz
	 * @return
	 */
	<T> String getIdName(Class<T> classzz);
}
