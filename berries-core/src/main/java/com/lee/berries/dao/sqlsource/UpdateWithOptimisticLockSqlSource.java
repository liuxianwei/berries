package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.params.UpdateWithOptimisticLockParam;

public class UpdateWithOptimisticLockSqlSource extends BaseSqlSource {

	private Map<String, Object> parameterObject;
	
	private final static String SQL = "update {tableName} set {setFields} where id=?";

	public UpdateWithOptimisticLockSqlSource(Configuration configuration, Object object) {
		this.configuration = configuration;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		UpdateWithOptimisticLockParam<?> updateObject = (UpdateWithOptimisticLockParam<?>) object;
		Object update = updateObject.getUpdate();
		String[] lockFields = updateObject.getLockFields();
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		parameterObject = new HashMap<String,Object>();
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(update.getClass()));
		try{
			StringBuilder setFields = new StringBuilder(500);
			String idFieldName = idNameProvider.getIdName(update.getClass());
			for(Field field:update.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID") 
						&& !field.getName().equals(idFieldName)){
					field.setAccessible(true);
					Object value = field.get(update);
					if(value != null){
						String column = columnNameProvider.getColumnName(field.getName());
						setFields.append(", ");
						setFields.append(column);
						setFields.append("=?");
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
			
			for(String fieldName:lockFields){
				Object value = BeanUtils.getProperty(update, fieldName);
				if(value != null){
					String column = columnNameProvider.getColumnName(fieldName);
					String key = "optimistickLock_" + fieldName;
					sql += " and " + column + "<?";
					Builder builderLock = new Builder(configuration, key , value.getClass());
					ParameterMapping parameterMappingLock = builderLock.build();
					parameterMappings.add(parameterMappingLock);
					parameterObject.put(key, value);
				}
			}
			
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, parameterObject);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
	
	@Override
	public Object getParamObject() {
		return parameterObject;
	}
}
