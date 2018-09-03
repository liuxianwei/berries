package com.lee.berries.test;

import com.lee.berries.dao.id.IdWorker;

public class IdFactory {

	private IdWorker idWorker;
	
	private static IdFactory instance;
	
	private IdFactory() {
		idWorker = new IdWorker(0L, 0L);
	}
	
	public static IdFactory getInstance() {
		if(null == instance) {
			synchronized (IdFactory.class) {
				if(null == instance) {
					System.out.println("生成IDFactory");
					instance = new IdFactory();
				}
			}
		}
		return instance;
	}

	public IdWorker getIdWorker() {
		return idWorker;
	}
	
}
