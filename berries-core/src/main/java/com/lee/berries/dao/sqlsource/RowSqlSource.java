package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;

public class RowSqlSource extends BaseSqlSource {

	private final String SQL = "select count(*) as c from {tableName} where 1=1 ";

	public RowSqlSource(Configuration configuration, BoundSql boundSql, Object object) {
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			for(Field field:object.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID")){
					field.setAccessible(true);
					Object value = field.get(object);
					if(value != null){
						sql += " and " + columnNameProvider.getColumnName(field.getName()) + "=?";
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			BerriesUtils.setFieldValue(boundSql, "sql", sql);
			BerriesUtils.setFieldValue(boundSql, "parameterMappings", parameterMappings);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
