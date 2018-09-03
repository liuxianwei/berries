package com.lee.berries.router;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.mapping.ParameterMapping;

import com.lee.berries.router.exception.FindSharingParamException;
import com.lee.berries.router.model.BaseSharing;

public abstract class AbstractSharingSQLConvert implements SharingSQLConvert{
	
	protected static Map<String, String[][]> WHERECACHE = new HashMap<>();
	
	protected static Map<String, String> TABLENAMECACHE = new HashMap<>();
	
	protected static final String MYSQL_SPLIT = "`";
	
	protected static final int PATTERN_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE;
	
	protected static final String WHERE_REXP = "[\\s|,]([0-1a-zA-Z_-|\\.]+?)\\s{0,}(=|<=|>=|<>|like|>)\\s{0,}([\\?])\\s?";

	protected Object getValue(List<ParameterMapping> parameterMappings, Object parameter, int parameterIndex) {
		Object value = null;
		String propertyName = parameterMappings.get(parameterIndex).getProperty();
		try {
			Field field = parameter.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			value = field.get(parameter);
		} catch (Exception e) {
			
		}
		return value;
	}
	
	protected Object getValue(BaseSharing rule, String[] propertyNames, String[] values, List<ParameterMapping> parameterMappings, Object parameter) {
		int parameterIndex = -1;
		String value = null;
		for(int i = 0; i < propertyNames.length; i++) {
			if(propertyNames[i].trim().equals(rule.getColumnName())) {
				parameterIndex = i;
				break;
			}
		}
		if(parameterIndex > -1) {
			if("?".equals(values[parameterIndex].trim())) {
				return getValue(parameterMappings, parameter, parameterIndex);
			}
			else {
				value = values[parameterIndex];
				value = value.trim();
				if(value.startsWith("'") && value.endsWith("'")) {
					value = value.substring(1, value.length() - 1);
				}
				return value;
			}
		}
		else {
			throw new FindSharingParamException(rule);
		}
	}
	
	protected String getTableNameFromCache(String sql) {
		String tableName = TABLENAMECACHE.get(sql);
		if(tableName == null) {
			tableName = getTableName(sql);
			if(tableName != null) {
				tableName = tableName.trim();
				TABLENAMECACHE.put(sql, tableName);
			}
		}
		return tableName;
	}
	
	abstract protected String getTableName(String sql);
	
	protected String[][] getWhere(String sql){
		String[][] where = WHERECACHE.get(sql);
		if(where == null) {
			where = new String[2][];
			Pattern pattern = Pattern.compile(WHERE_REXP, PATTERN_FLAGS);
			Matcher  matcher = pattern.matcher(sql);
			List<String> property = new ArrayList<>();
			List<String> values = new ArrayList<>();
			while(matcher.find()) {  
				property.add(matcher.group(1));
				values.add(matcher.group(3));
			}
			String[] propertyArray = new String[property.size()];
			String[] valuesArray = new String[values.size()];
			property.toArray(propertyArray);
			values.toArray(valuesArray);
			where[0] = propertyArray;
			where[1] = valuesArray;
			WHERECACHE.put(sql, where);
		}
		return where;
	}
}
