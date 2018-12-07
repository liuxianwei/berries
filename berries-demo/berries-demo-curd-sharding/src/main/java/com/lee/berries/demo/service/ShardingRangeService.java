package com.lee.berries.demo.service;

import com.lee.berries.demo.po.ShardingRange;

public interface ShardingRangeService {

	void save(ShardingRange shardingRange);
	
	void update(ShardingRange shardingRange);
	
	void delete(Long []id);
	
	ShardingRange get(Long id);
}
