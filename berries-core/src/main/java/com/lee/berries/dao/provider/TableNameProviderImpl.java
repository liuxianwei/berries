package com.lee.berries.dao.provider;

import org.springframework.stereotype.Component;

import com.lee.berries.dao.annotation.Entity;

/**
 * 数据库表名与对象名之间的转换关系
 * 表名称采用全小写字母，单词之间用_分割，比如user_details
 * 对象名称采用骆驼峰形式，比如UserDetails
 * 本类就采用将UserDetails字段转换为user_details
 * @author Liuxianwei
 *
 */
@Component
public class TableNameProviderImpl implements TableNameProvider {
	
	@Override
	public <T> String getTableName(Class<T> classzz) {
		Entity entity = classzz.getAnnotation(Entity.class);
		if(entity != null) {
			return entity.tableName();
		}
		else {
			throw new java.lang.IllegalArgumentException("实体类没有找到Entity注解");
		}
	}

}
