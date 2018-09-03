package com.lee.berries.dao.provider;

import com.lee.berries.dao.context.SpringContextUtil;

/**
 * 工厂方法
 * @author lxw
 *
 */
public final class ProviderFactory {


	public static ColumnNameProvider getColumnNameProvider() {
		return SpringContextUtil.getBean(ColumnNameProvider.class);
	}
	
	public static IdNameProvider getIdNameProvider() {
		return SpringContextUtil.getBean(IdNameProvider.class);
	}
	
	public static TableNameProvider getTableNameProvider() {
		return SpringContextUtil.getBean(TableNameProvider.class);
	}
}
