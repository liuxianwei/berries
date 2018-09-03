package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.params.UpdateByIdsParam;

public class UpdateByIdsSqlSource extends BaseSqlSource {
	
	private Map<String, Object> parameterObject;
			
	private final static String SQL = "update {tableName} set {setFields} where ${idFieldName} in (${ids})";

	public UpdateByIdsSqlSource(Configuration configuration, Object object) {
		this.configuration = configuration;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterObject = new HashMap<>();
		UpdateByIdsParam<?> updateByIdsParam = (UpdateByIdsParam<?>) object;
		Class<?> classzz = updateByIdsParam.getClasszz();
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(classzz));
		try{
			Builder valueBuilder = new Builder(configuration, "updateValue" , updateByIdsParam.getUpdateValue().getClass());
			parameterMappings.add(valueBuilder.build());
			parameterObject.put("updateValue", updateByIdsParam.getUpdateValue());
			
			StringBuilder in = new StringBuilder(100);
			for(int i = 0; i < updateByIdsParam.getIds().length; i++) {
				Object id = updateByIdsParam.getIds()[i];
				Builder idsBuilder = new Builder(configuration, "key_" + i , id.getClass());
				parameterMappings.add(idsBuilder.build());
				parameterObject.put("key_" + i, id);
				in.append(",?");
			}
			sql = sql.replace("${ids}", in.toString().substring(1));
			
			String setFields = BerriesUtils.camelCaseToUnderline(updateByIdsParam.getFieldName()) + "=?";
			String idFieldName = idNameProvider.getIdName(classzz);
			sql = sql.replace("{setFields}", setFields);
			sql = sql.replace("${idFieldName}", idFieldName);
			
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
