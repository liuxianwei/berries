package com.lee.berries.dao.plugin;

import java.util.Properties;

import javax.annotation.Resource;

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

import com.lee.berries.router.SharingSQLConvertFactory;
import com.lee.berries.router.TableSharings;

@Intercepts({ 
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class}),
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class })})
public class ReplicationPlugin implements Interceptor{

	@Resource
	TableSharings tableSharings;
	
	@Resource
	SharingSQLConvertFactory sharingSQLConvertFactory;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if(tableSharings.isEnableSharing()) {
			sharingSQLConvertFactory.convert(invocation.getArgs());
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		
	}

}
