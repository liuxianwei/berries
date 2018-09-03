package com.lee.berries.dao.provider;

import org.springframework.stereotype.Component;

import com.lee.berries.common.utils.BerriesUtils;

/**
 * 数据库表名与对象名之间的转换关系
 * 表名称采用全小写字母，单词之间用_分割，比如user_details
 * 对象名称采用骆驼峰形式，比如UserDetails
 * 本类就采用将UserDetails字段转换为user_details
 * @author Liuxianwei
 *
 */
@Component
public class ColumnNameProviderImpl implements ColumnNameProvider {

	@Override
	public <T> String getColumnName(String fieldName) {
		return BerriesUtils.camelCaseToUnderline(fieldName);
	}

}
