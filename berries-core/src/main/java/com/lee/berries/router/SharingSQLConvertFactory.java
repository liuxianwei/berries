package com.lee.berries.router;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.stereotype.Component;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.sqlsource.BaseSqlSource;
import com.lee.berries.dao.sqlsource.BerriesMappedStatementUtils;

@Component
public class SharingSQLConvertFactory {

	@Resource
	DeleteSharingSQLConvert deleteSharingSQLConvert;
	
	@Resource
	QuerySharingSQLConvert querySharingSQLConvert;
	
	@Resource
	InsertSharingSQLConvert insertSharingSQLConvert;
	
	@Resource
	UpdateSharingSQLConvert updateSharingSQLConvert;
	
	public void convert(Object[] invokeArgs) {
		boolean createStatment = true;
		MappedStatement mappedStatement = (MappedStatement) invokeArgs[0];
		Object paramObject = invokeArgs[1];
		if(mappedStatement.getSqlSource() instanceof BaseSqlSource) {
			createStatment = false;
		}
		BoundSql boundSql = mappedStatement.getBoundSql(paramObject);
		String oldSql = boundSql.getSql().replace("\r", " ").replace("\n", " ");
		String sql = oldSql;
		List<ParameterMapping> parameterMappings =  boundSql.getParameterMappings();
		
		Object parameter = boundSql.getParameterObject();
		String newSQL = sql.toLowerCase().trim();
		if(newSQL.startsWith("select")) {
			sql = querySharingSQLConvert.convert(sql, parameterMappings, parameter);
		}
		else if(newSQL.startsWith("update")){
			sql = updateSharingSQLConvert.convert(sql, parameterMappings, parameter);
		}
		else if(newSQL.startsWith("insert")){
			sql = insertSharingSQLConvert.convert(sql, parameterMappings, parameter);
		}
		else if(newSQL.startsWith("delete")){
			sql = deleteSharingSQLConvert.convert(sql, parameterMappings, parameter);
		}
		if(sql.length() != oldSql.length()) { //两个sql的长度发生变化，说明进行了分表处理。
			if(createStatment) {
				BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, parameterMappings, paramObject);
				SqlSource sqlSource = (object) -> newBoundSql;
				MappedStatement newMappedStatement = BerriesMappedStatementUtils.getMappedStatement(mappedStatement, sqlSource, "_sharing");
				invokeArgs[0] = newMappedStatement;
			}
			else {
				BerriesUtils.setFieldValue(boundSql, "sql", sql);
			}
			
		}
		
	}
	
}
