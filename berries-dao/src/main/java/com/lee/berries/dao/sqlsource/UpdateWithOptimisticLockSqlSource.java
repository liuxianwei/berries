package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.params.UpdateWithOptimisticLockParam;

public class UpdateWithOptimisticLockSqlSource extends BaseSqlSource {

	private Map<String, Object> parameterObject;
	
	private final String SQL = "update {tableName} set {setFields} where id=?";

	public UpdateWithOptimisticLockSqlSource(Configuration configuration, BoundSql boundSql, Object object) {
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		UpdateWithOptimisticLockParam<?> updateObject = (UpdateWithOptimisticLockParam<?>) object;
		Object update = updateObject.getUpdate();
		Object lock = updateObject.getLock();
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		parameterObject = new HashMap<String,Object>();
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(update.getClass()));
		try{
			String setFields = "";
			String idFieldName = idNameProvider.getIdName(update.getClass());
			for(Field field:update.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID") 
						&& !field.getName().equals(idFieldName)){
					field.setAccessible(true);
					Object value = field.get(update);
					if(value != null){
						String column = columnNameProvider.getColumnName(field.getName());
						setFields += ", " + column + "=?";
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
						parameterObject.put(field.getName(), value);
					}
				}
			}
			sql = sql.replace("{setFields}", setFields.substring(1));
			
			Field idField = update.getClass().getDeclaredField(idFieldName);
			idField.setAccessible(true);
			Object idValue = idField.get(update);
			Builder builder = new Builder(configuration, idFieldName , idField.getType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			parameterObject.put(idFieldName, idValue);
			
			for(Field field:lock.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID") 
						&& !field.getName().equals(idFieldName)){
					field.setAccessible(true);
					Object value = field.get(lock);
					if(value != null){
						String column = columnNameProvider.getColumnName(field.getName());
						String key = "optimistickLock_" + field.getName();
						sql += " and " + column + "=?";
						Builder builderLock = new Builder(configuration, key , field.getType());
						ParameterMapping parameterMappingLock = builderLock.build();
						parameterMappings.add(parameterMappingLock);
						parameterObject.put(key, value);
					}
				}
			}
			
			BerriesUtils.setFieldValue(boundSql, "sql", sql);
			BerriesUtils.setFieldValue(boundSql, "parameterMappings", parameterMappings);
			BerriesUtils.setFieldValue(boundSql, "parameterObject", parameterObject);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
	
	public Map<String, Object> getParameterObject() {
		return parameterObject;
	}
}
