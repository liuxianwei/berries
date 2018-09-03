package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

public class RowSqlSource extends BaseSqlSource {

	private final static String SQL = "select count(*) as c from {tableName} where 1=1 ";

	public RowSqlSource(Configuration configuration, Object object) {
		this.configuration = configuration;
		this.paramObject = object;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			StringBuilder where = new StringBuilder(500);
			for(Field field:object.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID")){
					field.setAccessible(true);
					Object value = field.get(object);
					if(value != null){
						where.append(" and ");
						where.append(columnNameProvider.getColumnName(field.getName()));
						where.append("=?");
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			this.boundSql = new BoundSql(configuration, sql + where.toString(), parameterMappings, object);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
