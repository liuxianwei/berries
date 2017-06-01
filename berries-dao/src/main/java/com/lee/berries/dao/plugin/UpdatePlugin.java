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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.constants.StatumentConstants;
import com.lee.berries.dao.sqlsource.DeleteSqlSource;
import com.lee.berries.dao.sqlsource.SaveSqlSource;
import com.lee.berries.dao.sqlsource.UpdateSqlSource;
import com.lee.berries.dao.sqlsource.UpdateWithOptimisticLockSqlSource;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class}) })
public class UpdatePlugin implements Interceptor {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		Object paramObject = invocation.getArgs()[1];
		if(StatumentConstants.STATEMENT_SAVE_ID.endsWith(statement.getId())){
			SqlSource sqlSource = new SaveSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
			BerriesUtils.setFieldValue(statement, "sqlSource", sqlSource);
		}
		if(StatumentConstants.STATEMENT_UPDATE_ID.endsWith(statement.getId())){
			SqlSource sqlSource = new UpdateSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
			BerriesUtils.setFieldValue(statement, "sqlSource", sqlSource);
		}
		if(StatumentConstants.STATEMENT_UPDATEWITHOPTIMISTICLOCK_ID.endsWith(statement.getId())){
			UpdateWithOptimisticLockSqlSource sqlSource = new UpdateWithOptimisticLockSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
			BerriesUtils.setFieldValue(statement, "sqlSource", sqlSource);
			invocation.getArgs()[1] = sqlSource.getParameterObject();
		}
		if(StatumentConstants.STATEMENT_DELETE_ID.endsWith(statement.getId())){
			SqlSource sqlSource = new DeleteSqlSource(statement.getConfiguration(), statement.getBoundSql(paramObject), paramObject);
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
