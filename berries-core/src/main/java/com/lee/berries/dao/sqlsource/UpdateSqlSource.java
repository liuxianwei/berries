package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.annotation.support.BerriesAnnotationSupport;
import com.lee.berries.dao.annotation.support.MethodMapper;

public class UpdateSqlSource extends BaseSqlSource {
	
	private final static String SQL = "update {tableName} set {setFields} where ";

	public UpdateSqlSource(Configuration configuration, Object object) {
		this.configuration = configuration;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		super.paramObject = object;
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			StringBuilder setFields = new StringBuilder(500);
			MethodMapper idMapper = idNameProvider.getIdName(object.getClass());
			String idFieldName = idMapper.getFieldName();
			for(MethodMapper methodMapper : BerriesAnnotationSupport.getInstance().getMethodMapper(object.getClass())) {
				if(!idFieldName.equals(methodMapper.getFieldName())) {
					Object value = methodMapper.getValue(object);
					if(value != null){
						String column = methodMapper.getColumnName();
						setFields.append(", ");
						setFields.append(column);
						setFields.append("=?");
						Builder builder = new Builder(configuration, methodMapper.getFieldName() , methodMapper.getMethod().getReturnType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			Builder builder = new Builder(configuration, idMapper.getFieldName() , idMapper.getMethod().getReturnType());
			ParameterMapping parameterMapping = builder.build();
			parameterMappings.add(parameterMapping);
			sql += idMapper.getColumnName() + "=?";
			sql = sql.replace("{setFields}", setFields.substring(1));
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, object);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
