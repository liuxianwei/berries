package com.lee.berries.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lee.berries.common.utils.ReflectUtils;
import com.lee.berries.dao.CommonDAO;
import com.lee.berries.dao.Page;
import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.params.UpdateWithOptimisticLockParam;
import com.lee.berries.dao.provider.IdNameProvider;
import com.lee.berries.dao.query.BaseQuery;

@Component
public class CommonDAOImpl implements CommonDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private IdNameProvider idNameProvider;
	
	@Override
	public <T> T get(Class<T> classzz, Serializable id) {
		T target = null;
		try{
			target = classzz.newInstance();
			BeanUtils.setProperty(target, idNameProvider.getIdName(classzz), id);
		}
		catch(Exception e){
			//防御性容错
		}
		Map<String, Object> data = sqlSessionTemplate.selectOne(StatementConstants.STATEMENT_GETONE_ID, target);
		return ReflectUtils.parseMapToEntity(data, classzz);
	}

	@Override
	public <T> T getByExample(T object) {
		if(object == null){
			return null;
		}
		List<T> list = listByExample(object, 0, 1);//强制限制返回的记录数。防止一次性查询全表
		if(list != null && list.size() > 0){
			list.get(0);
		}
		return null;
	}
	
	@Override
	public <T> int listByExampleRow(T object){
		int row = 0;
		if(object != null){
			List<Map<String,Object>> list = sqlSessionTemplate.selectList(StatementConstants.STATEMENT_ROW_ID, object);
			if(list != null && list.size() >0){
				Map<String, Object> data = list.get(0);
				if(data.containsKey("c")){
					row = ((Long)data.get("c")).intValue();
				}
			}
		}
		return row;
	}

	@Override
	public <T> List<T> listByExample(T object) {
		return listByExample(object, 0, 100);//最多查100条记录防止全表扫描
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> listByExample(T object, int offset, int limit) {
		RowBounds row = new RowBounds(offset, limit);//强制限制返回的记录数。防止一次性查询全表
		List<Map<String,Object>> list = sqlSessionTemplate.selectList(StatementConstants.STATEMENT_LIST_ID, object, row);
		Class<T> classzz = (Class<T>) object.getClass();
		return ReflectUtils.parseMapToEntity(list, classzz);
	}
	
	@Override
	public <T> Page<T> findPage(T object, Page<T> page){
		if(page == null){
			page = new Page<T>();
		}
		int row = this.listByExampleRow(object);
		if(row > 0){
			page.setTotalResult(row);
			page.setResult(this.listByExample(object, page.getCurrentResult(), page.getPageSize()));
		}
		return page;
	}
	
	@Override
	public <T> int queryRow(BaseQuery<T> query){
		if(query == null){
			return 0;
		}
		int row = 0;
		if(query != null){
			List<Map<String,Object>> list = sqlSessionTemplate.selectList(StatementConstants.STATEMENT_QUERY_ROW_ID, query);
			if(list != null && list.size() >0){
				Map<String, Object> data = list.get(0);
				if(data.containsKey("c")){
					row = ((Long)data.get("c")).intValue();
				}
			}
		}
		return row;
	}
	
	@Override
	public <T> Page<T> query(BaseQuery<T> query, Page<T> page){
		if(page == null){
			page = new Page<T>();
		}
		if(query == null){
			return null;
		}
		int row = queryRow(query);
		if(row > 0){
			RowBounds rowBounds = new RowBounds(page.getCurrentResult(), page.getPageSize());//强制限制返回的记录数。防止一次性查询全表
			List<Map<String,Object>> list = sqlSessionTemplate.selectList(StatementConstants.STATEMENT_QUERY_ID, query, rowBounds);
			page.setTotalResult(row);
			page.setResult(ReflectUtils.parseMapToEntity(list, query.getParamType()));
		}
		return page;
	}
	
	
	@Override
	public List<Map<String, Object>> list(String statementId, Object object) {
		return sqlSessionTemplate.selectList(statementId, object);
	}

	@Override
	public <T> void save(T object) {
		sqlSessionTemplate.insert(StatementConstants.STATEMENT_SAVE_ID, object);
	}
	
	@Override
	public <T> void update(T object) {
		sqlSessionTemplate.update(StatementConstants.STATEMENT_UPDATE_ID, object);
	}

	@Override
	public <T> int updateWithOptimisticLock(T update, T lock) {
		UpdateWithOptimisticLockParam<T> object = new UpdateWithOptimisticLockParam<T>(update, lock);
		return sqlSessionTemplate.update(StatementConstants.STATEMENT_UPDATEWITHOPTIMISTICLOCK_ID, object);
	}
	
	@Override
	public <T> void remove(T object) {
		sqlSessionTemplate.delete(StatementConstants.STATEMENT_DELETE_ID, object);
	}
	
	

	

}
