package com.lee.berries.dao.sqlsource;

import java.util.ArrayList;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.lee.berries.dao.annotation.support.BerriesAnnotationSupport;
import com.lee.berries.dao.annotation.support.MethodMapper;

public class SelectSqlSource extends BaseSqlSource {
	
	private final static String SQL = "select {fields} from {tableName} t where 1=1 ";
	
	private RowBounds rowBounds;

	public SelectSqlSource(Configuration configuration, Object object, RowBounds rowBounds) {
		this.configuration = configuration;
		this.rowBounds = rowBounds;
		this.paramObject = object;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		StringBuilder sqlBuffer = new StringBuilder(500);
		sqlBuffer.append(SQL);
		try{
			StringBuilder fields = new StringBuilder(500);
			
			for(MethodMapper mapper : BerriesAnnotationSupport.getInstance().getMethodMapper(object.getClass())){
				String column = mapper.getColumnName();
				fields.append(",t.");
				fields.append(column);
				fields.append(" as ");
				fields.append(mapper.getFieldName());
				Object value = mapper.getValue(object);
				if(value != null){
					sqlBuffer.append(" and t.");
					sqlBuffer.append(column);
					sqlBuffer.append("=?");
					Builder builder = new Builder(configuration, mapper.getFieldName(), mapper.getMethod().getReturnType());
					ParameterMapping parameterMapping = builder.build();
					parameterMappings.add(parameterMapping);
				}
			}
			/*if(rowBounds != null && !rowBounds.equals(RowBounds.DEFAULT)) {
				sqlBuffer.append(" limit ");
				sqlBuffer.append(rowBounds.getOffset());
				sqlBuffer.append(", ");
				sqlBuffer.append(rowBounds.getLimit());
			}*/
			String sql = sqlBuffer.toString();
			sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
			sql = sql.replace("{fields}", fields.substring(1));
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, object);
		}
		catch(Exception e){
			logger.error("error", e);
			//防御性容错
		}
		return "";
	}
}
