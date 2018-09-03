package com.lee.berries.router.sharing;

import com.lee.berries.router.model.BaseSharing;

public class ValueTableSharingProvider implements TableSharingProvider {

	@Override
	public String getTableName(BaseSharing sharing, Object value) {
		StringBuilder tableName = new StringBuilder(100);
		tableName.append(sharing.getTableName())
			.append(TableSharingProvider.SPLIT)
			.append(value);
		return tableName.toString();
	}

}
