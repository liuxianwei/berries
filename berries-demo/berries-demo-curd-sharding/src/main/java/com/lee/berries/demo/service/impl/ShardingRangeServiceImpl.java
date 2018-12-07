package com.lee.berries.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lee.berries.demo.po.ShardingRange;
import com.lee.berries.demo.service.ShardingRangeService;
import com.lee.berries.service.impl.BaseServiceImpl;

@Service
public class ShardingRangeServiceImpl extends BaseServiceImpl implements ShardingRangeService {

	@Override
	public void save(ShardingRange shardingRange) {
		shardingRange.setCreateTime(new Date());
		super.save(shardingRange);
	}

	@Override
	public void update(ShardingRange shardingRange) {
		super.update(shardingRange);
	}

	@Override
	public void delete(Long[] id) {
		super.deleteByIds(ShardingRange.class, id);

	}

	@Override
	public ShardingRange get(Long id) {
		return get(ShardingRange.class, id);
	}

}
