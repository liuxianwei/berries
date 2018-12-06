package com.lee.berries.dao.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.sqlsource.BaseSqlSource;
import com.lee.berries.dao.sqlsource.BerriesMappedStatementUtils;
import com.lee.berries.dao.sqlsource.QuerySqlSource;
import com.lee.berries.dao.sqlsource.RowSqlSource;
import com.lee.berries.dao.sqlsource.SelectSqlSource;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class QueryPlugin implements Interceptor {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		Object paramObject = invocation.getArgs()[1];
		RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
		
		if(StatementConstants.STATEMENT_GETONE_ID.equals(statement.getId())){ //查询单个，如果结果出现多个将报错！
			rowBounds = new RowBounds(0, 1);
			BaseSqlSource sqlSource = new SelectSqlSource(statement.getConfiguration(), paramObject, rowBounds);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
		else if(StatementConstants.STATEMENT_LIST_ID.equals(statement.getId())){ //查询多个记录！
			BaseSqlSource sqlSource = new SelectSqlSource(statement.getConfiguration(), paramObject, rowBounds);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
		else if(StatementConstants.STATEMENT_ROW_ID.equals(statement.getId())){ //查询记录数！
			BaseSqlSource sqlSource = new RowSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
		else if(StatementConstants.STATEMENT_QUERY_ID.equals(statement.getId())){ //查询记录数！
			BaseSqlSource sqlSource = new QuerySqlSource(statement.getId(), statement.getConfiguration(), paramObject, rowBounds);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
		else if(StatementConstants.STATEMENT_QUERY_ROW_ID.equals(statement.getId())){ //查询记录数！
			invocation.getArgs()[2] = RowBounds.DEFAULT;
			BaseSqlSource sqlSource = new QuerySqlSource(statement.getId(), statement.getConfiguration(), paramObject, rowBounds);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
		else if(StatementConstants.STATEMENT_QUERY_STATISTICS_ID.equals(statement.getId())){ //查询记录数！
			invocation.getArgs()[2] = RowBounds.DEFAULT;
			BaseSqlSource sqlSource = new QuerySqlSource(statement.getId(), statement.getConfiguration(), paramObject, rowBounds);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
		Object object = invocation.proceed();
		return object;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
