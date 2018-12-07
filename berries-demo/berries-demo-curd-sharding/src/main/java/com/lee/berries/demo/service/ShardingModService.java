package com.lee.berries.demo.service;

import com.lee.berries.demo.po.ShardingMod;

public interface ShardingModService {

	void save(ShardingMod shardingMod);
	
	void update(ShardingMod shardingMod);
	
	void delete(Long []id);
	
	ShardingMod get(Long id);
}
