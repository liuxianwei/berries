package com.lee.berries.router.model;

public class BaseSharing {

	protected SharingRuleEnum rule; //分片规则
	
	protected String tableName; //表名
	
	protected String columnName; //字段名称

	public SharingRuleEnum getRule() {
		return rule;
	}

	public void setRule(SharingRuleEnum rule) {
		this.rule = rule;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	
}
