package com.lee.berries.dao.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.sqlsource.BaseSqlSource;
import com.lee.berries.dao.sqlsource.BerriesMappedStatementUtils;
import com.lee.berries.dao.sqlsource.DeleteByIdsSqlSource;
import com.lee.berries.dao.sqlsource.DeleteSqlSource;
import com.lee.berries.dao.sqlsource.SaveSqlSource;
import com.lee.berries.dao.sqlsource.SaveWithWhereSqlSource;
import com.lee.berries.dao.sqlsource.UpdateByIdsSqlSource;
import com.lee.berries.dao.sqlsource.UpdateSqlSource;
import com.lee.berries.dao.sqlsource.UpdateWithOptimisticLockSqlSource;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class}) })
public class UpdatePlugin implements Interceptor {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		Object paramObject = invocation.getArgs()[1];
		if(StatementConstants.STATEMENT_SAVE_ID.equals(statement.getId())){
			BaseSqlSource sqlSource = new SaveSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}else if(StatementConstants.STATEMENT_SAVE_WITH_WHERE_ID.equals(statement.getId())){
			BaseSqlSource sqlSource = new SaveWithWhereSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}else if(StatementConstants.STATEMENT_UPDATE_ID.equals(statement.getId())){
			BaseSqlSource sqlSource = new UpdateSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}else if(StatementConstants.STATEMENT_UPDATEWITHOPTIMISTICLOCK_ID.equals(statement.getId())){
			UpdateWithOptimisticLockSqlSource sqlSource = new UpdateWithOptimisticLockSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}else if(StatementConstants.STATEMENT_DELETE_ID.equals(statement.getId())){
			BaseSqlSource sqlSource = new DeleteSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}else if(StatementConstants.STATEMENT_DELETE_BY_IDS.equals(statement.getId())){
			DeleteByIdsSqlSource sqlSource = new DeleteByIdsSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}else if(StatementConstants.STATEMENT_UPDATE_BY_IDS.equals(statement.getId())){
			UpdateByIdsSqlSource sqlSource = new UpdateByIdsSqlSource(statement.getConfiguration(), paramObject);
			BerriesMappedStatementUtils.processMappedStatement(statement, sqlSource, invocation.getArgs());
		}
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
