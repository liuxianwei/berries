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

@Component("deleteSharingSQLConvert")
public class DeleteSharingSQLConvert extends AbstractSharingSQLConvert implements SharingSQLConvert {

	@Resource
	TableSharings tableSharings;
	
	private static final String REXP = "delete\\s+from\\s+(.+)\\s+where";
	
	@Override
	public String convert(String sql, List<ParameterMapping> parameterMappings, Object parameter) {
		String tableName = getTableNameFromCache(sql);
		BaseSharing rule = tableSharings.get(tableName);
		if(rule != null) {
			String[][] where = getWhere(sql.replace(MYSQL_SPLIT, ""));
			Object value = getValue(rule, where[0], where[1], parameterMappings, parameter);
			TableSharingProvider tableSharingProvider = TableSharingProviderFactory.geTableSharingProvider(rule.getRule());
			String newTableName = tableSharingProvider.getTableName(rule, value);
			sql = sql.replace(tableName, newTableName);
		}
		
		return sql;
	}

	@Override
	protected String getTableName(String sql) {
		Pattern pattern = Pattern.compile(REXP, Pattern.CASE_INSENSITIVE);
		Matcher  matcher = pattern.matcher(sql);
		if(matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
