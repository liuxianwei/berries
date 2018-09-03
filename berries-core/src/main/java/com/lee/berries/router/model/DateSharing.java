package com.lee.berries.router.model;
//日期分片
public class DateSharing extends BaseSharing{

	private DateRuleEnum dateRule; //日期分片规则

	public DateRuleEnum getDateRule() {
		return dateRule;
	}

	public void setDateRule(DateRuleEnum dateRule) {
		this.dateRule = dateRule;
	}
}
