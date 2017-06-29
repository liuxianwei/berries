package com.lee.berries.dao.sqlsource;

import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.dao.provider.ColumnNameProvider;
import com.lee.berries.dao.provider.ColumnNameProviderImpl;
import com.lee.berries.dao.provider.IdNameProvider;
import com.lee.berries.dao.provider.IdNameProviderImpl;
import com.lee.berries.dao.provider.TableNameProvider;
import com.lee.berries.dao.provider.TableNameProviderImpl;

public abstract class BaseSqlSource implements SqlSource{

	protected Configuration configuration;
	protected BoundSql boundSql;
	protected List<ParameterMapping> parameterMappings;
	
	protected static TableNameProvider tableNameProvider = new TableNameProviderImpl();
	protected static ColumnNameProvider columnNameProvider = new ColumnNameProviderImpl();
	protected static IdNameProvider idNameProvider = new IdNameProviderImpl();
	
	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		return boundSql;
	}
}
