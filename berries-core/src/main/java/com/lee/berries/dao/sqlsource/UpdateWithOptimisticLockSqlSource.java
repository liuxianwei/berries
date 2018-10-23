package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.annotation.support.BerriesAnnotationSupport;
import com.lee.berries.dao.annotation.support.MethodMapper;
import com.lee.berries.dao.params.UpdateWithOptimisticLockParam;

public class UpdateWithOptimisticLockSqlSource extends BaseSqlSource {

	private Map<String, Object> parameterObject;
	
	private final static String SQL = "update {tableName} set {setFields} where {idName}=?";

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
			MethodMapper idMapper = idNameProvider.getIdMapper(update.getClass());
			
			for(MethodMapper mapper : BerriesAnnotationSupport.getInstance().getMethodMapper(update.getClass())) {
				if(!idMapper.getFieldName().equals(mapper.getFieldName())) {
					Object value = mapper.getValue(update);
					if(value != null){
						String column = mapper.getColumnName();
						setFields.append(", ");
						setFields.append(column);
						setFields.append("=?");
						Builder builder = new Builder(configuration, mapper.getFieldName() , mapper.getMethod().getReturnType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
						parameterObject.put(mapper.getFieldName(), value);
					}
				}
			}
			sql = sql.replace("{setFields}", setFields.substring(1));
			
			Object idValue = idMapper.getValue(update.getClass());
			Builder builder = new Builder(configuration, idMapper.getFieldName() , idMapper.getMethod().getReturnType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			parameterObject.put(idMapper.getFieldName(), idValue);
			sql = sql.replace("{idName}", idMapper.getColumnName());
			
			for(String fieldName:lockFields){
				MethodMapper mapper = columnNameProvider.getColumnMapper(update.getClass(), fieldName);
				Object value = mapper.getValue(update);
				if(value != null){
					String column = mapper.getColumnName();
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
