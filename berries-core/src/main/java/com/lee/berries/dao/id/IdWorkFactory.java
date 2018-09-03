package com.lee.berries.dao.id;

import com.lee.berries.common.utils.WebToolUtils;

public class IdWorkFactory {

	private static IdWorker idWorker;
	
	public static IdWorker get() {
		if(idWorker == null) {
			int workerId = (int) (30 * Math.random());
			int datacenterId = (int) (30 * Math.random());
			try {
				String[] ip = WebToolUtils.getLocalIP().split("\\.");
				workerId = Integer.valueOf(ip[2]) % 31;
				datacenterId = Integer.valueOf(ip[3]) % 31;
			}
			catch (Exception e) {}
			idWorker = new IdWorker(workerId, datacenterId);
		}
		return idWorker;
	}
}
