package com.lee.berries.dao.sqlsource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.query.BaseQuery;

public class QuerySqlSource extends BaseSqlSource {

	private String statementId;
	
	public QuerySqlSource(String statementId, Configuration configuration, BoundSql boundSql, Object object) {
		this.statementId = statementId;
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL((BaseQuery<?>) object);
	}
	
	private String initSQL(BaseQuery<?> query){
		String sql = "";
		try{
			if(StatementConstants.STATEMENT_QUERY_ID.equals(statementId)){
				sql = query.getQueryBoundSQL();
			}
			if(StatementConstants.STATEMENT_QUERY_ROW_ID.equals(statementId)){
				sql = query.getQueryRowBoundSQL();
			}
			parameterMappings = query.getParameterMappings(configuration);
			BerriesUtils.setFieldValue(boundSql, "sql", sql);
			BerriesUtils.setFieldValue(boundSql, "parameterMappings", parameterMappings);
		}
		catch(Exception e){
			//防御性容错
		}
		return sql;
	}
}
