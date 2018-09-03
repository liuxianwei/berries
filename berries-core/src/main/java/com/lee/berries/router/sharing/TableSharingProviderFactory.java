package com.lee.berries.router.sharing;

import java.util.HashMap;
import java.util.Map;

import com.lee.berries.router.model.SharingRuleEnum;

public class TableSharingProviderFactory {

	private static Map<SharingRuleEnum, TableSharingProvider> tableSuffiexProvider = new HashMap<>();
	
	static {
		tableSuffiexProvider.put(SharingRuleEnum.DATE, new DateTableSharingProvider());
		tableSuffiexProvider.put(SharingRuleEnum.VALUE, new ValueTableSharingProvider());
		tableSuffiexProvider.put(SharingRuleEnum.MOD, new ModTableSharingProvider());
		tableSuffiexProvider.put(SharingRuleEnum.RANGE, new RangeTableSharingProvider());
	}
	
	public static TableSharingProvider geTableSharingProvider(SharingRuleEnum sharingRuleEnum) {
		return tableSuffiexProvider.get(sharingRuleEnum);
	}
}
