package com.lee.berries.dao.sqlsource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.provider.ColumnNameProvider;
import com.lee.berries.dao.provider.ColumnNameProviderImpl;
import com.lee.berries.dao.provider.IdNameProvider;
import com.lee.berries.dao.provider.IdNameProviderImpl;
import com.lee.berries.dao.provider.TableNameProvider;
import com.lee.berries.dao.provider.TableNameProviderImpl;

public class SelectSqlSource implements SqlSource {

	private final Configuration configuration;
	private final BoundSql boundSql;
	private List<ParameterMapping> parameterMappings;
	
	protected static TableNameProvider tableNameProvider = new TableNameProviderImpl();
	protected static ColumnNameProvider columnNameProvider = new ColumnNameProviderImpl();
	protected static IdNameProvider idNameProvider = new IdNameProviderImpl();
	
	private final String SQL = "select {fields} from {tableName} where 1=1 ";

	public SelectSqlSource(Configuration configuration, BoundSql boundSql, Object object) {
		this.configuration = configuration;
		this.boundSql = boundSql;
		initSQL(object);
	}
	
	private String initSQL(Object object){
		parameterMappings = new ArrayList<ParameterMapping>();
		String sql = SQL;
		sql = sql.replace("{tableName}", tableNameProvider.getTableName(object.getClass()));
		try{
			String fields = "";
			for(Field field:object.getClass().getDeclaredFields()){
				if(!field.getName().equals("serialVersionUID")){
					String column = columnNameProvider.getColumnName(field.getName());
					fields += "," + column + " as " + field.getName();
					field.setAccessible(true);
					Object value = field.get(object);
					if(value != null){
						sql += " and " + columnNameProvider.getColumnName(field.getName()) + "=?";
						Builder builder = new Builder(configuration, field.getName() , field.getType());
						ParameterMapping parameterMapping = builder.build();
						parameterMappings.add(parameterMapping);
					}
				}
			}
			sql = sql.replace("{fields}", fields.substring(1));
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
