package com.lee.berries.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CommonDAO {

	public <T> T get(Class<T> classzz, Serializable id);
	
	public <T> T getByExample(T object);
	
	public <T> int listByExampleRow(T object);
	
	public <T> List<T> listByExample(T object);
	
	public <T> List<T> listByExample(T object, int offset, int limit);
	
	public <T> Page<T> findPage(T object, Page<T> page);
	
	public List<Map<String, Object>> list(String statementId, Object object);
	
	public <T> void save(T object);
	
	public <T> void update(T object);
	
	public <T> int updateWithOptimisticLock(T update, T lock);
	
	public <T> void remove(T object);
}
