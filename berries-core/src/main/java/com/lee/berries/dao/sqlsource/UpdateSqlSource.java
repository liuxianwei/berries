package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;

public class UpdateSqlSource extends BaseSqlSource {
	
	private final String SQL = "update {tableName} set {setFields} where ";

	public UpdateSqlSource(Configuration configuration, BoundSql boundSql, Object object) {
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			String setFields = "";
			String idFieldName = idNameProvider.getIdName(object.getClass());
			for(Field field:object.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID") 
						&& !field.getName().equals(idFieldName)){
					field.setAccessible(true);
					Object value = field.get(object);
					if(value != null){
						String column = columnNameProvider.getColumnName(field.getName());
						setFields += ", " + column + "=?";
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			Field idField = object.getClass().getDeclaredField(idFieldName);
			Builder builder = new Builder(configuration, idFieldName , idField.getType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			sql += idFieldName + "=?";
			sql = sql.replace("{setFields}", setFields.substring(1));
			BerriesUtils.setFieldValue(boundSql, "sql", sql);
			BerriesUtils.setFieldValue(boundSql, "parameterMappings", parameterMappings);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
