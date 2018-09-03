package com.lee.berries.router.sharing;

import com.lee.berries.router.model.BaseSharing;

/**
 * 表后缀名生成的提供者接口
 * @author lxw
 *
 */
public interface TableSharingProvider {

	public static final String SPLIT = "_"; //表名和后缀名之间分割字符
	
	String getTableName(BaseSharing sharing, Object value);
	
}
