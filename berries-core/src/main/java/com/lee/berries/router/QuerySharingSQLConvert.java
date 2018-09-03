package com.lee.berries.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.mapping.ParameterMapping;
import org.springframework.stereotype.Component;

import com.lee.berries.router.exception.FindSharingParamException;
import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.router.sharing.TableSharingProvider;
import com.lee.berries.router.sharing.TableSharingProviderFactory;

@Component("querySharingSQLConvert")
public class QuerySharingSQLConvert extends AbstractSharingSQLConvert implements SharingSQLConvert {

	protected static Map<String, List<QuerySharing>> TABLENAMES = new HashMap<>();
	
	@Resource
	TableSharings tableSharings;
	
	private static final String TABLE_NAME_REXP = "[from|join]\\s+([a-zA-Z0-9_-]+)\\s+([a-zA-Z0-9_-]+)\\s+";
	
	@Override
	public String convert(String sql, List<ParameterMapping> parameterMappings, Object parameter) {
		List<QuerySharing> tables = getTableNames(sql);
		String[][] where = null;
		for(QuerySharing table:tables) {
			BaseSharing rule = tableSharings.get(table.tableName);
			if(rule != null) {
				if(where == null) {
					where = getWhere(sql);
				}
				int paramterIndex = getParamterIndex(rule, table, where);
				Object value = getValue(parameterMappings, parameter, paramterIndex);
				TableSharingProvider tableSharingProvider = TableSharingProviderFactory.geTableSharingProvider(rule.getRule());
				String newTableName = tableSharingProvider.getTableName(rule, value);
				sql = sql.replace(table.tableName, newTableName);
			}
		}
		
		return sql;
	}
	
	private int getParamterIndex(BaseSharing rule, QuerySharing table, String[][] where) {
		int paramterIndex = -1;
		String propertyName = table.tableAlisa + "." + rule.getColumnName();
		for(int i = 0; i < where[0].length; i++) {
			String w = where[0][i];
			if(propertyName.equals(w)) {
				paramterIndex = i;
				break;
			}
		}
		if(paramterIndex == -1) {
			throw new FindSharingParamException(rule);
		}
		return paramterIndex;
	}
	
	private List<QuerySharing> getTableNames(String sql){
		List<QuerySharing> list = TABLENAMES.get(sql);
		if(list == null) {
			list = new ArrayList<>();
			Pattern pattern = Pattern.compile(TABLE_NAME_REXP, Pattern.CASE_INSENSITIVE);
			Matcher  matcher = pattern.matcher(sql);
			while(matcher.find()) {
				QuerySharing querySharing = new QuerySharing();
				querySharing.tableName = matcher.group(1);
				querySharing.tableAlisa = matcher.group(2);
				if(querySharing.tableName != null) {
					querySharing.tableName = querySharing.tableName.trim();
				}
				if(querySharing.tableAlisa != null) {
					querySharing.tableAlisa = querySharing.tableAlisa.trim();
				}
				list.add(querySharing);
			}
			TABLENAMES.put(sql, list);
		}
		return list;
	}

	class QuerySharing{
		String tableName;
		String tableAlisa;
	}

	@Override
	protected String getTableName(String sql) {
		return "";
	}
}
