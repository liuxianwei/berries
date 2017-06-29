package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;

public class SaveSqlSource extends BaseSqlSource {

	private final String SQL = "insert into {tableName} ({fields}) values ({values}) ";

	public SaveSqlSource(Configuration configuration, BoundSql boundSql, Object object) {
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			String fields = "";
			String values = "";
			for(Field field:object.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID")){
					field.setAccessible(true);
					Object value = field.get(object);
					if(value != null){
						String column = columnNameProvider.getColumnName(field.getName());
						fields += ", " + column;
						values += ",?";
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			sql = sql.replace("{fields}", fields.substring(1));
			sql = sql.replace("{values}", values.substring(1));
			BerriesUtils.setFieldValue(boundSql, "sql", sql);
			BerriesUtils.setFieldValue(boundSql, "parameterMappings", parameterMappings);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
