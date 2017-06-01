package com.lee.berries.dao.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.constants.StatementConstants;
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
		if(StatementConstants.STATEMENT_GETONE_ID.endsWith(statement.getId())){ //查询单个，如果结果出现多个将报错！
			SqlSource sqlSource = new SelectSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
			BerriesUtils.setFieldValue(statement, "sqlSource", sqlSource);
		}
		if(StatementConstants.STATEMENT_LIST_ID.endsWith(statement.getId())){ //查询多个记录！
			SqlSource sqlSource = new SelectSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
			BerriesUtils.setFieldValue(statement, "sqlSource", sqlSource);
		}
		if(StatementConstants.STATEMENT_ROW_ID.endsWith(statement.getId())){ //查询记录数！
			SqlSource sqlSource = new RowSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
			BerriesUtils.setFieldValue(statement, "sqlSource", sqlSource);
		}
		logger.info("statement:" + statement.getBoundSql(paramObject).getSql());
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
