package com.lee.berries.demo;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.lee.berries.demo.po.ShardingDate;
import com.lee.berries.demo.service.ShardingDateService;

public class ShardingDateServiceTest extends BaseTest {

	@Test
	public void testSave() {
		ShardingDateService shardingDateService = getBean(ShardingDateService.class);
		for(int i = 0; i < 100; i++) {
			Long id = Long.valueOf(i);
			ShardingDate shardingDate = new ShardingDate();
			shardingDate.setName("shardingDate" + id);
			shardingDate.setId(id);
			shardingDate.setCreateTime(getDate(i));
			shardingDateService.save(shardingDate);
		}
	}
	
	@Test
	public void testUpdate() {
		ShardingDateService shardingDateService = getBean(ShardingDateService.class);
		ShardingDate shardingDate = new ShardingDate();
		shardingDate.setId(10L);
		shardingDate.setCreateTime(getDate(10));
		shardingDate.setName("Hello Berries! I updated.");
		shardingDateService.update(shardingDate);
	}
	
	
	@Test
	public void testGet() {
		ShardingDateService shardingDateService = getBean(ShardingDateService.class);
		ShardingDate shardingDate = shardingDateService.get(10L, getDate(10));
		System.out.println(shardingDate.getName());
	}
	
	@Test
	public void testDelete() {
		ShardingDateService shardingDateService = getBean(ShardingDateService.class);
		shardingDateService.delete(10L, getDate(10));
	}
	
	private Date getDate(int i) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, i);
		return calendar.getTime();
	}
	
}
