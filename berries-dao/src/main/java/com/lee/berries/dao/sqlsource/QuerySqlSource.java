package com.lee.berries.dao.sqlsource;

import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.provider.ColumnNameProvider;
import com.lee.berries.dao.provider.ColumnNameProviderImpl;
import com.lee.berries.dao.provider.IdNameProvider;
import com.lee.berries.dao.provider.IdNameProviderImpl;
import com.lee.berries.dao.provider.TableNameProvider;
import com.lee.berries.dao.provider.TableNameProviderImpl;
import com.lee.berries.dao.query.BaseQuery;

public class QuerySqlSource implements SqlSource {

	private Configuration configuration;
	private BoundSql boundSql;
	private String statementId;
	private List<ParameterMapping> parameterMappings;
	
	protected static TableNameProvider tableNameProvider = new TableNameProviderImpl();
	protected static ColumnNameProvider columnNameProvider = new ColumnNameProviderImpl();
	protected static IdNameProvider idNameProvider = new IdNameProviderImpl();
	
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

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		return boundSql;
	}
}
