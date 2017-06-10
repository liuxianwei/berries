package com.lee.berries.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lee.berries.dao.search.BaseQuery;

/**
 * DAO层的接口
 * @author Liuxianwei
 *
 */
public interface CommonDAO {

	/**
	 * 根据主键ID获取一个对象，这里有唯一性判断，如果查询的结果不是唯一记录将会抛出异常
	 * @param classzz 数据库对象class
	 * @param id 主键
	 * @return
	 */
	public <T> T get(Class<T> classzz, Serializable id);
	
	/**
	 * 根据数据库映射对象样例获取一条记录，注：这里是从多条记录中获取第一条记录
	 * @param object 数据库对象样例
	 * @return
	 */
	public <T> T getByExample(T object);
	
	/**
	 * 根据数据库映射对象样例获取记录数
	 * @param object 数据库对象样例
	 * @return 记录条数
	 */
	public <T> int listByExampleRow(T object);
	
	/**
	 * 根据数据库映射对象样例获取记录，为了避免出现全表查询，这里强制做了100条数据限制
	 * @param object 数据库对象样例
	 * @return 返回记录
	 */
	public <T> List<T> listByExample(T object);
	
	/**
	 * 根据数据库映射对象样例获取记录，这里设置偏移量和返回记录数
	 * @param object 数据库对象样例
	 * @param offset 记录偏移量
	 * @param limit 返回记录数
	 * @return 返回记录
	 */
	public <T> List<T> listByExample(T object, int offset, int limit);
	
	/**
	 * 根据数据库映射对象样例进行分页查询
	 * @param object 数据库对象样例
	 * @param page 分页参数
	 * @return 记录结果包装
	 */
	public <T> Page<T> findPage(T object, Page<T> page);
	
	/**
	 * 使用query组件查询记录数
	 * @param query
	 * @return
	 */
	public <T> int queryRow(BaseQuery<T> query);
	
	/**
	 * 使用query组件进行查询
	 * @param query
	 * @param page
	 * @return
	 */
	public <T> Page<T> query(BaseQuery<T> query, Page<T> page);
	
	/**
	 * 跟Mapper中的配置进行查询
	 * @param statementId 查询id
	 * @param object 参数
	 * @return
	 */
	public List<Map<String, Object>> list(String statementId, Object object);
	
	/**
	 * 保存一个对象到数据库
	 * @param object
	 */
	public <T> void save(T object);
	
	/**
	 * 更新一个对象到数据库
	 * @param object 要更新的数据，为空的不更新
	 */
	public <T> void update(T object);
	
	/**
	 * 更新对象带乐观锁
	 * @param update 要更新的数据，为空的不更新
	 * @param lock 乐观锁中除去id条件之外的条件对象
	 * @return
	 */
	public <T> int updateWithOptimisticLock(T update, T lock);
	
	/**
	 * 从数据库中删除一个对象
	 * @param object id不能为空
	 */
	public <T> void remove(T object);
}
