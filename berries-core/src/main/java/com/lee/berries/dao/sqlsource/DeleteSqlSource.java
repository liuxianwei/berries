package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.annotation.support.MethodMapper;
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
			MethodMapper idMapper = idNameProvider.getIdMapper(paramObject.getClass());
			Builder builder = new Builder(configuration, idMapper.getFieldName() , idMapper.getMethod().getReturnType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			StringBuilder where = new StringBuilder(200);
			if(deleteParam.getFields() != null && deleteParam.getFields().length > 0) {
				for(String f:deleteParam.getFields()) {
					MethodMapper mapper = columnNameProvider.getColumnMapper(paramObject.getClass(), f);
					where.append("and ")
						.append(mapper.getColumnName())
						.append("=? ");
					Builder fieldbuilder = new Builder(configuration, f , mapper.getMethod().getReturnType());
					ParameterMapping fieldParameterMapping = fieldbuilder.build();
					parameterMappings.add(fieldParameterMapping);
				}
			}
			sql = sql.replace("{idFieldName}", idMapper.getColumnName());
			sql = sql.replace("{where}", where.toString());
			
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, paramObject);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
	
	
}
