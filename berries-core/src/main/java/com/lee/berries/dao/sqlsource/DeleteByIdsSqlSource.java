package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.params.DeleteByIdsParam;

public class DeleteByIdsSqlSource extends BaseSqlSource {
	
	private Map<String, Object> parameterObject;
	
	private final static String SQL = "delete from  {tableName} where ${idFieldName} in (${ids})";

	public DeleteByIdsSqlSource(Configuration configuration, Object object) {
		this.configuration = configuration;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterObject = new HashMap<>();
		DeleteByIdsParam<?> deleteByIdsParam = (DeleteByIdsParam<?>) object;
		Class<?> classzz = deleteByIdsParam.getClasszz();
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		try{
			sql = sql.replace("{tableName}", tableNameProvider.getTableName(classzz));
			sql = sql.replace("${idFieldName}", idNameProvider.getIdName(classzz).getColumnName());
			
			StringBuffer in = new StringBuffer(500);
			for(int i = 0; i < deleteByIdsParam.getIds().length; i++) {
				Object id = deleteByIdsParam.getIds()[i];
				Builder idsBuilder = new Builder(configuration, "key_" + i , id.getClass());
				parameterMappings.add(idsBuilder.build());
				parameterObject.put("key_" + i, id);
				in.append(",?");
			}
			sql = sql.replace("${ids}", in.toString().substring(1));
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
