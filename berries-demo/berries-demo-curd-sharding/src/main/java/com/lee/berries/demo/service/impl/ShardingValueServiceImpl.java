package com.lee.berries.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lee.berries.demo.po.ShardingValue;
import com.lee.berries.demo.service.ShardingValueService;
import com.lee.berries.service.impl.BaseServiceImpl;

@Service
public class ShardingValueServiceImpl extends BaseServiceImpl implements ShardingValueService {

	@Override
	public void save(ShardingValue shardingValue) {
		shardingValue.setCreateTime(new Date());
		super.save(shardingValue);
	}

	@Override
	public void update(ShardingValue shardingValue) {
		super.update(shardingValue);
	}

	@Override
	public void delete(ShardingValue shardingValue) {
		super.remove(shardingValue);

	}

	@Override
	public ShardingValue get(Long id, Integer userId) {
		ShardingValue shardingValue = new ShardingValue();
		shardingValue.setId(id);
		shardingValue.setUserId(userId);
		return getByExample(shardingValue);
	}

}
