package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.annotation.support.BerriesAnnotationSupport;
import com.lee.berries.dao.annotation.support.MethodMapper;

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
			
			for(MethodMapper mapper : BerriesAnnotationSupport.getInstance().getMethodMapper(object.getClass())){
				Object value = mapper.getValue(object);
				if(value != null){
					where.append(" and ");
					where.append(mapper.getColumnName());
					where.append("=?");
					Builder builder = new Builder(configuration, mapper.getFieldName() , mapper.getMethod().getReturnType());
					ParameterMapping parameterMapping = builder.build();
					parameterMappings.add(parameterMapping);
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
