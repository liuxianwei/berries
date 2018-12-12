package com.lee.berries.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.lee.berries.common.utils.ReflectUtils;
import com.lee.berries.dao.CommonDAO;
import com.lee.berries.dao.Page;
import com.lee.berries.dao.annotation.support.MethodMapper;
import com.lee.berries.dao.constants.StatementConstants;
import com.lee.berries.dao.id.IdWorkFactory;
import com.lee.berries.dao.params.DeleteByIdsParam;
import com.lee.berries.dao.params.DeleteParam;
import com.lee.berries.dao.params.SaveWithWhereParam;
import com.lee.berries.dao.params.UpdateByIdsParam;
import com.lee.berries.dao.params.UpdateWithOptimisticLockParam;
import com.lee.berries.dao.provider.IdNameProvider;
import com.lee.berries.dao.provider.ProviderFactory;
import com.lee.berries.dao.query.BaseQuery;

@Component
@Scope("prototype")
public class CommonDAOImpl implements CommonDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private IdNameProvider idNameProvider;
	
	public static final String DEFAULT_LOCK_NAME = "version"; //乐观锁默认锁变量名称
	
	@Override
	public <T> T get(Class<T> classzz, Serializable id) {
		T target = null;
		try{
			target = classzz.newInstance();
			BeanUtils.setProperty(target, idNameProvider.getIdMapper(classzz).getFieldName(), id);
		}
		catch(Exception e){
			//防御性容错
		}
		Map<String, Object> data = sqlSessionTemplate.selectOne(StatementConstants.STATEMENT_GETONE_ID, target);
		return ReflectUtils.parseMapToEntity(data, classzz, true);
	}

	@Override
	public <T> T getByExample(T object) {
		if(object == null){
			return null;
		}
		List<T> list = listByExample(object, 0, 1);//强制限制返回的记录数。防止一次性查询全表
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public <T> T get(String statement, Object parameter) {
		if(parameter == null) {
			return sqlSessionTemplate.selectOne(statement);
		}
		return sqlSessionTemplate.selectOne(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		if(parameter == null) {
			return sqlSessionTemplate.selectList(statement);
		}
		return sqlSessionTemplate.selectList(statement, parameter);
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
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Map<String,Object>> list = sqlSessionTemplate.selectList(StatementConstants.STATEMENT_LIST_ID, object, rowBounds);
		Class<T> classzz = (Class<T>) object.getClass();
		return ReflectUtils.parseMapToEntity(list, classzz, true);
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
	public <T> Page<T> findPage(String statementId, Object params, Page<T> page) {
		if(page == null){
			page = new Page<T>();
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
		int row = Integer.MAX_VALUE - 1;
		if(query.getEnableCount()) {
			row = queryRow(query);
		}
		if(row > 0){
			query.setOffset(page.getCurrentResult());
			query.setLimit(page.getPageSize());
			List<Map<String,Object>> list = sqlSessionTemplate.selectList(StatementConstants.STATEMENT_QUERY_ID, query, RowBounds.DEFAULT);
			page.setTotalResult(row);
			page.setResult(ReflectUtils.parseMapToEntity(list, query.getParamType(), true));
		}
		return page;
	}
	
	@Override
	public <T> Map<String, Object> statistics(BaseQuery<T> query){
		return sqlSessionTemplate.selectOne(StatementConstants.STATEMENT_QUERY_STATISTICS_ID, query);
	}
		
	@Override
	public List<Map<String, Object>> list(String statementId, Object object) {
		return sqlSessionTemplate.selectList(statementId, object);
	}

	@Override
	public <T> void save(T object) {
		try {
			MethodMapper idMapper = ProviderFactory.getIdNameProvider().getIdMapper(object.getClass());
			Object idValue = idMapper.getValue(object);
			if(idValue == null) {
				BeanUtils.setProperty(object, idMapper.getFieldName(), IdWorkFactory.get().nextId());
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			
		}
		sqlSessionTemplate.insert(StatementConstants.STATEMENT_SAVE_ID, object);
	}
	
	@Override
	public <T> int save(T object, String... whereFieldNames) {
		SaveWithWhereParam<T> saveWithWhereParam = new SaveWithWhereParam<T>(object, whereFieldNames);
		try {
			MethodMapper idMapper = ProviderFactory.getIdNameProvider().getIdMapper(object.getClass());
			Object idValue = idMapper.getValue(object);
			if(idValue == null) {
				BeanUtils.setProperty(object, idMapper.getFieldName(), IdWorkFactory.get().nextId());
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			
		}
		return sqlSessionTemplate.insert(StatementConstants.STATEMENT_SAVE_WITH_WHERE_ID, saveWithWhereParam);
	}
	
	@Override
	public <T> int update(T object) {
		return sqlSessionTemplate.update(StatementConstants.STATEMENT_UPDATE_ID, object);
	}

	@Override
	public <T> int updateWithOptimisticLock(T update, String ...lockFields) {
		UpdateWithOptimisticLockParam<T> object = null;
		if(lockFields == null || lockFields.length == 0){
			object = new UpdateWithOptimisticLockParam<T>(update, DEFAULT_LOCK_NAME);
		}
		else{
			object = new UpdateWithOptimisticLockParam<T>(update, lockFields);
		}
		return sqlSessionTemplate.update(StatementConstants.STATEMENT_UPDATEWITHOPTIMISTICLOCK_ID, object);
	}
	
	@Override
	public <T> int remove(T object) {
		DeleteParam<T> deleteParam = new DeleteParam<T>(object);
		return sqlSessionTemplate.delete(StatementConstants.STATEMENT_DELETE_ID, deleteParam);
	}
	
	@Override
	public <T> int remove(T object, String... fields) {
		DeleteParam<T> deleteParam = new DeleteParam<T>(object, fields);
		return sqlSessionTemplate.delete(StatementConstants.STATEMENT_DELETE_ID, deleteParam);
	}

	@Override
	public int update(String statement) {
		return sqlSessionTemplate.update(statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return sqlSessionTemplate.update(statement, parameter);
	}

	@Override
	public <T> int updateByIds(Class<T> classzz, String fieldName, Object updateValue, Serializable[] ids) {
		if(ids == null || ids.length == 0) {
			return 0;
		}
		UpdateByIdsParam<T> updateByIdsParam = new UpdateByIdsParam<>(classzz, fieldName, updateValue, ids);
		return sqlSessionTemplate.update(StatementConstants.STATEMENT_UPDATE_BY_IDS, updateByIdsParam);
	}

	@Override
	public <T> int deleteByIds(Class<T> classzz, Serializable[] ids) {
		if(ids == null || ids.length == 0) {
			return 0;
		}
		DeleteByIdsParam<T> deleteByIdsParam = new DeleteByIdsParam<>(classzz, ids);
		return sqlSessionTemplate.delete(StatementConstants.STATEMENT_DELETE_BY_IDS, deleteByIdsParam);
	}

}
