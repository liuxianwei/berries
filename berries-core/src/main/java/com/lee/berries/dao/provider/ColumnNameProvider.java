package com.lee.berries.dao.provider;

import java.util.List;

import com.lee.berries.dao.annotation.support.MethodMapper;

/**
 * 表明与对象名映射关系接口
 * @author Liuxianwei
 *
 */
public interface ColumnNameProvider {

	/**
	 * 根据对象获取表名称
	 * @param classzz
	 * @return
	 */
	<T> List<MethodMapper> getColumnMapper(Class<T> classzz);
	
	/**
	 * 根据对象获取表名称
	 * @param classzz
	 * @return
	 */
	<T> MethodMapper getColumnName(Class<T> classzz, String fieldName);
}
