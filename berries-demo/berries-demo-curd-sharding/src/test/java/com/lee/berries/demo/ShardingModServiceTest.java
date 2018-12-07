package com.lee.berries.demo;

import org.junit.Test;

import com.lee.berries.demo.po.ShardingMod;
import com.lee.berries.demo.service.ShardingModService;

public class ShardingModServiceTest extends BaseTest {

	@Test
	public void testSave() {
		ShardingModService shardingModService = getBean(ShardingModService.class);
		for(int i = 0; i < 100; i++) {
			Long id = Long.valueOf(i);
			ShardingMod shardingMod = new ShardingMod();
			shardingMod.setName("shardingmod" + id);
			shardingMod.setId(id);
			shardingModService.save(shardingMod);
		}
	}
	
	@Test
	public void testUpdate() {
		ShardingModService shardingModService = getBean(ShardingModService.class);
		ShardingMod shardingMod = new ShardingMod();
		shardingMod.setId(10L);
		shardingMod.setName("Hello Berries! I updated.");
		shardingModService.update(shardingMod);
	}
	
	
	@Test
	public void testGet() {
		ShardingModService shardingModService = getBean(ShardingModService.class);
		ShardingMod shardingMod = shardingModService.get(10L);
		System.out.println(shardingMod.getName());
	}
	
	@Test
	public void testDelete() {
		ShardingModService shardingModService = getBean(ShardingModService.class);
		Long []id = {0L,1L};
		shardingModService.delete(id);
	}
	
	
}
