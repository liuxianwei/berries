package com.lee.berries.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lee.berries.demo.po.ShardingDate;
import com.lee.berries.demo.service.ShardingDateService;
import com.lee.berries.service.impl.BaseServiceImpl;

@Service
public class ShardingDateServiceImpl extends BaseServiceImpl implements ShardingDateService {

	@Override
	public void save(ShardingDate shardingDate) {
		super.save(shardingDate);
	}

	@Override
	public void update(ShardingDate shardingDate) {
		super.update(shardingDate);
	}

	@Override
	public void delete(Long id, Date createTime) {
		ShardingDate shardingDate = new ShardingDate();
		shardingDate.setCreateTime(createTime);
		shardingDate.setId(id);
		super.remove(shardingDate);

	}

	@Override
	public ShardingDate get(Long id, Date createTime) {
		ShardingDate shardingDate = new ShardingDate();
		shardingDate.setCreateTime(createTime);
		shardingDate.setId(10L);
		return getByExample(shardingDate);
	}

}
