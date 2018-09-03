package com.lee.berries.dao.sqlsource;

import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.berries.dao.provider.ColumnNameProvider;
import com.lee.berries.dao.provider.IdNameProvider;
import com.lee.berries.dao.provider.ProviderFactory;
import com.lee.berries.dao.provider.TableNameProvider;

public abstract class BaseSqlSource implements SqlSource{

	protected Configuration configuration;
	protected BoundSql boundSql;
	protected List<ParameterMapping> parameterMappings;
	
	protected TableNameProvider tableNameProvider;
	protected ColumnNameProvider columnNameProvider;
	protected IdNameProvider idNameProvider;
	protected Object paramObject;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public BaseSqlSource() {
		tableNameProvider = ProviderFactory.getTableNameProvider();
		columnNameProvider = ProviderFactory.getColumnNameProvider();
		idNameProvider = ProviderFactory.getIdNameProvider();
	}
	
	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		return boundSql;
	}
	
	public Object getParamObject() {
		return paramObject;
	}
}
