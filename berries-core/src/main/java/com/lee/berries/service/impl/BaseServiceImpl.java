
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
	
	public final static String DELETE_FLAG_FIELD_NAME = "deleteFlag";
	
	public final static String ENABLE_FLAG_FIELD_NAME = "enableFlag";
	
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
	public <T> T get(String statement, Object parameter) {
		return commonDao.get(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return commonDao.selectList(statement, parameter);
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
	public <T> Page<T> findPage(String statementId, Object params, Page<T> page) {
		return commonDao.findPage(statementId, params, page);
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
	public <T> Map<String, Object> statistics(BaseQuery<T> query){
		return commonDao.statistics(query);
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
	public <T> int save(T object, String... whereFieldNames) {
		return commonDao.save(object, whereFieldNames);
	}

	@Override
	public <T> int update(T object) {
		return commonDao.update(object);
	}

	@Override
	public <T> int updateWithOptimisticLock(T update, String ...lockFields) {
		return commonDao.updateWithOptimisticLock(update, lockFields);
	}

	@Override
	public <T> int remove(T object) {
		return commonDao.remove(object);
	}
	
	@Override
	public <T> int remove(T object, String... fields) {
		return commonDao.remove(object, fields);
	}

	@Override
	public int update(String statement) {
		return commonDao.update(statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return commonDao.update(statement, parameter);
	}
	
	@Override
	public <T> int updateByIds(Class<T> classzz, String fieldName, 
			Object updateValue, Serializable[] ids) {
		return commonDao.updateByIds(classzz, fieldName, updateValue, ids);
	}
	
	@Override
	public <T> int deleteByIds(Class<T> classzz, Serializable[] ids) {
		return commonDao.deleteByIds(classzz, ids);
	}
}
