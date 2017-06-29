package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;

public class DeleteSqlSource extends BaseSqlSource {
	
	private final String SQL = "delete from {tableName} where ";

	public DeleteSqlSource(Configuration configuration, BoundSql boundSql, Object object) {
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			String idFieldName = idNameProvider.getIdName(object.getClass());
			Field idField = object.getClass().getDeclaredField(idFieldName);
			Builder builder = new Builder(configuration, idFieldName , idField.getType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			sql += idFieldName + "=?";
			BerriesUtils.setFieldValue(boundSql, "sql", sql);
			BerriesUtils.setFieldValue(boundSql, "parameterMappings", parameterMappings);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
