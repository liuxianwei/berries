package com.lee.berries.demo;

import org.junit.Test;

import com.lee.berries.demo.po.ShardingValue;
import com.lee.berries.demo.service.ShardingValueService;

public class ShardingValueServiceTest extends BaseTest {

	@Test
	public void testSave() {
		ShardingValueService shardingValueService = getBean(ShardingValueService.class);
		for(int i = 0; i < 100; i++) {
			Long id = Long.valueOf(i);
			ShardingValue shardingValue = new ShardingValue();
			shardingValue.setName("shardingvalue" + id);
			shardingValue.setUserId(i % 5);
			shardingValue.setId(id);
			shardingValueService.save(shardingValue);
		}
	}
	
	@Test
	public void testUpdate() {
		ShardingValueService shardingValueService = getBean(ShardingValueService.class);
		ShardingValue shardingValue = shardingValueService.get(10L, 10%5);
		shardingValue.setName("Hello Berries! I updated.");
		shardingValueService.update(shardingValue);
	}
	
	
	@Test
	public void testGet() {
		ShardingValueService shardingValueService = getBean(ShardingValueService.class);
		ShardingValue shardingValue = shardingValueService.get(10L, 10%5);
		System.out.println(shardingValue.getName());
	}
	
	@Test
	public void testDelete() {
		ShardingValueService shardingValueService = getBean(ShardingValueService.class);
		ShardingValue shardingValue = shardingValueService.get(10L, 10%5);
		
		shardingValueService.delete(shardingValue);
	}
	
	
}
