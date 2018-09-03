package com.lee.berries.service;

import java.util.List;

public interface TableService {

	/**
	 * 获取数据库所有的表
	 * @return
	 */
	List<String> getTableNames();
	
	/**
	 * 根据数据库中的表创建新的空表
	 * @param tableNameTemplate 表接口模板
	 * @param tableName 新表名称
	 */
	void createTableByTableName(String tableNameTemplate, String tableName);
	
	/**
	 * 获取表的DDL
	 * @param tableName
	 * @return
	 */
	String getTableDDL(String tableName);
}
