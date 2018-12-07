package com.lee.berries.router.sharing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lee.berries.router.exception.UnsupportedTypeException;
import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.router.model.DateRuleEnum;
import com.lee.berries.router.model.DateSharing;

public class DateTableSharingProvider implements TableSharingProvider {

	private static final String YEAR_FORMART = "yyyy";
	
	private static final String MONTH_FORMART = "yyyyMM";
	
	private static final String DATE_FORMART = "yyyyMMdd";
	
	private static final String FORMART = "yyyy-MM-dd HH:mm:ss";
	
	private static final SimpleDateFormat DATE_PARSE = new SimpleDateFormat(FORMART);
	
	@Override
	public String getTableName(BaseSharing sharing, Object value) {
		Date date = null;
		if(value instanceof String) {
			try {
				date = DATE_PARSE.parse((String) value);
			} catch (ParseException e) {}
		}
		else if(value instanceof Date){
			date = (Date) value;
		}
		else {
			throw new UnsupportedTypeException(value.getClass());
		}
		DateSharing dateSharing = (DateSharing) sharing;
		String dateFormart = "";
		if(DateRuleEnum.YEAR == dateSharing.getDateRule()) {
			dateFormart = YEAR_FORMART;
		}
		else if (DateRuleEnum.MONTH == dateSharing.getDateRule()){
			dateFormart = MONTH_FORMART;
		}
		if (DateRuleEnum.DAY == dateSharing.getDateRule()){
			dateFormart = DATE_FORMART;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormart);
		StringBuilder tableName = new StringBuilder(100);
		tableName.append(sharing.getTableName())
			.append(TableSharingProvider.SPLIT)
			.append(simpleDateFormat.format(date));
		return tableName.toString();
	}

}
