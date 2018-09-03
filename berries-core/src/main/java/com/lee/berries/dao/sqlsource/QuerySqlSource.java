package com.lee.berries.dao.sqlsource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.query.BaseQuery;

public class QuerySqlSource extends BaseSqlSource {

	private String statementId;
	private RowBounds rowBounds;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public QuerySqlSource(String statementId, Configuration configuration, Object object, RowBounds rowBounds) {
		this.statementId = statementId;
		this.configuration = configuration;
		this.rowBounds = rowBounds;
		this.paramObject = object;
		initSQL((BaseQuery<?>) object);
	}
	
	private String initSQL(BaseQuery<?> query){
		StringBuilder sqlBuffer = new StringBuilder(500);
		String sql = "";
		try{
			parameterMappings = query.getParameterMappings(configuration);
			if(StatementConstants.STATEMENT_QUERY_ID.equals(statementId)){
				sqlBuffer.append(query.getQueryBoundSQL());
				if(rowBounds != null) {
					sqlBuffer.append(" limit ?,?");
					parameterMappings.add(new Builder(configuration, "offset" , Integer.class).build());
					parameterMappings.add(new Builder(configuration, "limit" , Integer.class).build());
				}
				sql = sqlBuffer.toString();
			}
			if(StatementConstants.STATEMENT_QUERY_ROW_ID.equals(statementId)){
				sql = query.getQueryRowBoundSQL();
			}
			if(StatementConstants.STATEMENT_QUERY_STATISTICS_ID.equals(statementId)){
				sql = query.getQueryStatisticsBoundSQL();
			}
			this.boundSql = new BoundSql(configuration, sql, parameterMappings, query);
			
		}
		catch(Exception e){
			//防御性容错
		}
		return "";
	}
}
