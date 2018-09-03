package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.params.DeleteParam;

public class DeleteSqlSource extends BaseSqlSource {
	
	private final static String SQL = "delete from {tableName} where {idFieldName}=? {where}";

	public DeleteSqlSource(Configuration configuration,Object object) {
		this.configuration = configuration;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		DeleteParam<?> deleteParam = (DeleteParam<?>) object;
		super.paramObject = deleteParam.getDelete();
		
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(paramObject.getClass()));
		try{
			String idFieldName = idNameProvider.getIdName(paramObject.getClass());
			Field idField = paramObject.getClass().getDeclaredField(idFieldName);
			Builder builder = new Builder(configuration, idFieldName , idField.getType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			StringBuilder where = new StringBuilder(200);
			if(deleteParam.getFields() != null && deleteParam.getFields().length > 0) {
				for(String f:deleteParam.getFields()) {
					Field field = paramObject.getClass().getDeclaredField(f);
					String fieldName = columnNameProvider.getColumnName(f);
					where.append("and ")
						.append(fieldName)
						.append("=? ");
					Builder fieldbuilder = new Builder(configuration, f , field.getType());
					ParameterMapping fieldParameterMapping = fieldbuilder.build();
					parameterMappings.add(fieldParameterMapping);
				}
			}
			sql = sql.replace("{idFieldName}", idFieldName);
			sql = sql.replace("{where}", where.toString());
			
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, paramObject);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
	
	
}
