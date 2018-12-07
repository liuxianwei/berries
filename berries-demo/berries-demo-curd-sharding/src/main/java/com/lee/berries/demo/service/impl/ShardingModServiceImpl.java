package com.lee.berries.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lee.berries.demo.po.ShardingMod;
import com.lee.berries.demo.service.ShardingModService;
import com.lee.berries.service.impl.BaseServiceImpl;

@Service
public class ShardingModServiceImpl extends BaseServiceImpl implements ShardingModService {

	@Override
	public void save(ShardingMod shardingMod) {
		shardingMod.setCreateTime(new Date());
		super.save(shardingMod);
	}

	@Override
	public void update(ShardingMod shardingMod) {
		super.update(shardingMod);
	}

	@Override
	public void delete(Long[] id) {
		super.deleteByIds(ShardingMod.class, id);

	}

	@Override
	public ShardingMod get(Long id) {
		return get(ShardingMod.class, id);
	}

}
