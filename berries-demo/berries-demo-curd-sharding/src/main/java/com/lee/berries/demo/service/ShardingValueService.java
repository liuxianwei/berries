package com.lee.berries.demo.service;

import com.lee.berries.demo.po.ShardingValue;

public interface ShardingValueService {

	void save(ShardingValue shardingValue);
	
	void update(ShardingValue shardingValue);
	
	void delete(ShardingValue shardingValue);
	
	ShardingValue get(Long id, Integer userId);
}
