package com.lee.berries.dao.provider;

/**
 * 表明与对象名映射关系接口
 * @author Liuxianwei
 *
 */
public interface TableNameProvider {

	/**
	 * 根据对象获取表名称
	 * @param classzz
	 * @return
	 */
	<T> String getTableName(Class<T> classzz);
}
