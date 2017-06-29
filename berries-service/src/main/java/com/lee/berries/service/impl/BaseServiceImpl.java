package com.lee.berries.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lee.berries.dao.CommonDAO;
import com.lee.berries.dao.Page;
import com.lee.berries.dao.query.BaseQuery;
import com.lee.berries.service.IBaseService;

public abstract class BaseServiceImpl implements IBaseService {

	@Autowired
	protected CommonDAO commonDao;
	
	@Override
	public <T> T get(Class<T> classzz, Serializable id) {
		return commonDao.get(classzz, id);
	}

	@Override
	public <T> T getByExample(T object) {
		return commonDao.getByExample(object);
	}

	@Override
	public <T> int listByExampleRow(T object) {
		return commonDao.listByExampleRow(object);
	}

	@Override
	public <T> List<T> listByExample(T object) {
		return commonDao.listByExample(object);
	}

	@Override
	public <T> List<T> listByExample(T object, int offset, int limit) {
		
		return commonDao.listByExample(object, offset, limit);
	}

	@Override
	public <T> Page<T> findPage(T object, Page<T> page) {
		
		return commonDao.findPage(object, page);
	}

	@Override
	public <T> int queryRow(BaseQuery<T> query) {
		
		return commonDao.queryRow(query);
	}

	@Override
	public <T> Page<T> query(BaseQuery<T> query, Page<T> page) {
		
		return commonDao.query(query, page);
	}

	@Override
	public List<Map<String, Object>> list(String statementId, Object object) {
		
		return commonDao.list(statementId, object);
	}

	@Override
	public <T> void save(T object) {
		commonDao.save(object);
	}

	@Override
	public <T> void update(T object) {
		commonDao.update(object);
	}

	@Override
	public <T> int updateWithOptimisticLock(T update, String ...lockFields) {
		return commonDao.updateWithOptimisticLock(update, lockFields);
	}

	@Override
	public <T> void remove(T object) {
		commonDao.remove(object);
	}

}
