package com.lee.berries.demo;

import org.junit.Test;

import com.lee.berries.demo.po.ShardingRange;
import com.lee.berries.demo.service.ShardingRangeService;

public class ShardingRangeServiceTest extends BaseTest {

	@Test
	public void testSave() {
		ShardingRangeService shardingRangeService = getBean(ShardingRangeService.class);
		for(int i = 0; i < 1000; i++) {
			Long id = Long.valueOf(i);
			ShardingRange shardingRange = new ShardingRange();
			shardingRange.setName("shardingrange" + id);
			shardingRange.setId(id);
			shardingRangeService.save(shardingRange);
		}
	}
	
	@Test
	public void testUpdate() {
		ShardingRangeService shardingRangeService = getBean(ShardingRangeService.class);
		ShardingRange shardingRange = shardingRangeService.get(10L);
		shardingRange.setName("Hello Berries! I updated.");
		shardingRangeService.update(shardingRange);
	}
	
	
	@Test
	public void testGet() {
		ShardingRangeService shardingRangeService = getBean(ShardingRangeService.class);
		ShardingRange shardingRange = shardingRangeService.get(10L);
		System.out.println(shardingRange.getName());
	}
	
	@Test
	public void testDelete() {
		ShardingRangeService shardingRangeService = getBean(ShardingRangeService.class);
		
		shardingRangeService.delete(new Long[]{10L, 11L});
	}
	
	
}
