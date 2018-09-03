package com.lee.berries.router.sharing;

import com.lee.berries.router.exception.UnsupportedTypeException;
import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.router.model.ModSharing;

public class ModTableSharingProvider implements TableSharingProvider {
	
	@Override
	public String getTableName(BaseSharing sharing, Object value) {
		int mod = 0;
		ModSharing modSharing = (ModSharing) sharing;
		if(value instanceof Long) {
			mod = (int) Math.floorMod((Long)value, modSharing.getModCount());
		}
		else if(value instanceof Integer) {
			mod = Math.floorMod((Integer)value, modSharing.getModCount());
		}
		else if(value instanceof Short) {
			mod = Math.floorMod((Integer)value, modSharing.getModCount());
		}
		else if(value instanceof Byte) {
			mod = Math.floorMod((Integer)value, modSharing.getModCount());
		}
		else if(value instanceof String){
			try {
				mod = (int) Math.floorMod(Long.valueOf((String)value), modSharing.getModCount());
			}
			catch (Exception e) {
				mod = value.hashCode() % modSharing.getModCount();
			}
			
		}
		else {
			throw new UnsupportedTypeException(value.getClass());
		}
		StringBuilder tableName = new StringBuilder(100);
		tableName.append(sharing.getTableName())
			.append(TableSharingProvider.SPLIT)
			.append(mod);
		return tableName.toString();
	}

}
