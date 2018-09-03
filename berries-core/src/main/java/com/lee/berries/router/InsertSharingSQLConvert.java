package com.lee.berries.router;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.ParameterMapping;
import org.springframework.stereotype.Component;

import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.router.sharing.TableSharingProvider;
import com.lee.berries.router.sharing.TableSharingProviderFactory;
import com.lee.berries.service.SpringContextHolder;
import com.lee.berries.service.TableService;

@Component("insertSharingSQLConvert")
public class InsertSharingSQLConvert extends AbstractSharingSQLConvert implements SharingSQLConvert {
	
	@Resource
	TableSharings tableSharings;
	
	private static final String REXP = "insert\\s+into\\s+(.+?)\\s+?\\((.+?)\\)\\s+values\\s+?\\((.+?)\\)";
	
	private static final String REXP_WITH_SELECT = "insert\\s+into\\s+(.+?)\\s+?\\((.+?)\\)\\s+select(.+?)from";

	@Override
	public String convert(String sql, List<ParameterMapping> parameterMappings, Object parameter) {
		String tableName = getTableNameFromCache(sql);
		BaseSharing rule = tableSharings.get(tableName);
		if(rule != null) {
			String[][] where = getWhere(sql);
			Object value = getValue(rule, where[0], where[1], parameterMappings, parameter);
			TableSharingProvider tableSharingProvider = TableSharingProviderFactory.geTableSharingProvider(rule.getRule());
			String newTableName = tableSharingProvider.getTableName(rule, value);
			sql = sql.replace(tableName, newTableName);
			doNewTable(tableName, newTableName);
		}
		return sql;
	}
	
	private void doNewTable(String tableName, String newTableName) {
		TableService tableService = SpringContextHolder.getBean(TableService.class);
		TableNames tableNames = TableNames.getInstance();
		if(!tableNames.getInit()) {
			tableNames.init(tableService.getTableNames());
		}
		if(!tableNames.exist(newTableName)) {
			tableService.createTableByTableName(tableName, newTableName);
			tableNames.add(newTableName);
		}
	}

	@Override
	protected String getTableName(String sql) {
		String rexp = REXP;
		if(sql.indexOf("select") != -1) {
			rexp = REXP_WITH_SELECT;
		}
		Pattern pattern = Pattern.compile(rexp, PATTERN_FLAGS);
		Matcher  matcher = pattern.matcher(sql);
		if(matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	@Override
	protected String[][] getWhere(String sql){
		String[][] where = WHERECACHE.get(sql);
		if(where == null) {
			String rexp = REXP;
			if(sql.indexOf("select") != -1) {
				rexp = REXP_WITH_SELECT;
			}
			Pattern pattern = Pattern.compile(rexp, PATTERN_FLAGS);
			Matcher  matcher = pattern.matcher(sql);
			if(matcher.find()) {
				String fieldNames = matcher.group(2).replace(MYSQL_SPLIT, "");
				String values = matcher.group(3);
				where = new String[2][];
				where[0] = fieldNames.split(",");
				where[1] = values.split(",");
				if(sql.indexOf("select") == -1) { //如果包含where条件，说明是一个待条件的插入，不能缓存该sql
					WHERECACHE.put(sql, where);
				}
			}
		}
		return where;
	}
	
}
