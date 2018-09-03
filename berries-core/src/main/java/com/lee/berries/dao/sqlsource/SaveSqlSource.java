package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

public class SaveSqlSource extends BaseSqlSource {

	private final static String SQL = "insert into {tableName} ({fields}) values ({values}) ";

	public SaveSqlSource(Configuration configuration, Object object) {
		this.configuration = configuration;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		super.paramObject = object;
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			StringBuilder fields = new StringBuilder(500);
			StringBuilder values = new StringBuilder(500);
			for(Field field:object.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID")){
					field.setAccessible(true);
					Object value = field.get(object);
					if(value != null){
						String column = columnNameProvider.getColumnName(field.getName());
						fields.append(", ");
						fields.append(column);
						
						values.append(",?");
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			sql = sql.replace("{fields}", fields.substring(1));
			sql = sql.replace("{values}", values.substring(1));
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, object);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
