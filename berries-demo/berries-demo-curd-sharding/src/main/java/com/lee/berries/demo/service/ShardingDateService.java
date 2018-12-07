package com.lee.berries.demo.service;

import java.util.Date;

import com.lee.berries.demo.po.ShardingDate;

public interface ShardingDateService {

	void save(ShardingDate shardingDate);
	
	void update(ShardingDate shardingDate);
	
	void delete(Long id, Date createTime);
	
	ShardingDate get(Long id, Date createTime);
}
