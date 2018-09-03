package com.lee.berries.router.sharing;

import com.lee.berries.router.exception.UnsupportedTypeException;
import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.router.model.RangeSharing;

public class RangeTableSharingProvider implements TableSharingProvider {

	@Override
	public String getTableName(BaseSharing sharing, Object value) {
		RangeSharing rangeSharing = (RangeSharing) sharing;
		int count = 0;
		int rangeCount = rangeSharing.getRangeCount();
		if(value instanceof Long) {
			count = (int)Math.floor((Long)value/rangeCount);
		}
		else if(value instanceof Integer) {
			count = (int)Math.floor((Integer)value/rangeCount);
		}
		else if(value instanceof Short) {
			count = (int)Math.floor((Short)value/rangeCount);
		}
		else if(value instanceof Byte) {
			count = (int)Math.floor((Byte)value/rangeCount);
		}
		else if(value instanceof String) {
			count = (int)Math.floor(Long.valueOf((String) value)/rangeCount);
		}
		else{
			throw new UnsupportedTypeException(value.getClass());
		}
		StringBuilder tableName = new StringBuilder(100);
		tableName.append(sharing.getTableName())
			.append(TableSharingProvider.SPLIT)
			.append(count);
		return tableName.toString();
	}

}
