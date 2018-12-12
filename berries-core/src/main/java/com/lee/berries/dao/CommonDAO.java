package com.lee.berries.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lee.berries.dao.query.BaseQuery;

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
	 <T> T get(Class<T> classzz, Serializable id);
	
	/**
	 * 根据数据库映射对象样例获取一条记录，注：这里是从多条记录中获取第一条记录
	 * @param object 数据库对象样例
	 * @return
	 */
	 <T> T getByExample(T object);
	
	/**
	 * 根据statement 唯一返回结果
	 * @param statement
	 * @param parameter
	 * @return
	 */
	<T> T get(String statement, Object parameter);
	
	/**
	 * 根据statement返回查询list
	 * @param statement
	 * @param parameter
	 * @return
	 */
	<E> List<E> selectList(String statement, Object parameter);
	
	/**
	 * 根据数据库映射对象样例获取记录数
	 * @param object 数据库对象样例
	 * @return 记录条数
	 */
	 <T> int listByExampleRow(T object);
	
	/**
	 * 根据数据库映射对象样例获取记录，为了避免出现全表查询，这里强制做了100条数据限制
	 * @param object 数据库对象样例
	 * @return 返回记录
	 */
	 <T> List<T> listByExample(T object);
	
	/**
	 * 根据数据库映射对象样例获取记录，这里设置偏移量和返回记录数
	 * @param object 数据库对象样例
	 * @param offset 记录偏移量
	 * @param limit 返回记录数
	 * @return 返回记录
	 */
	 <T> List<T> listByExample(T object, int offset, int limit);
	
	/**
	 * 根据数据库映射对象样例进行分页查询
	 * @param object 数据库对象样例
	 * @param page 分页参数
	 * @return 记录结果包装
	 */
	 <T> Page<T> findPage(T object, Page<T> page);
	 
	 /**
	  * 分页查询
	  * @param statementId
	  * @param params
	  * @param page
	  * @return
	  */
	 <T> Page<T> findPage(String statementId, Object params, Page<T> page);
	
	/**
	 * 使用query组件查询记录数
	 * @param query
	 * @return
	 */
	 <T> int queryRow(BaseQuery<T> query);
	
	/**
	 * 使用query组件进行查询
	 * @param query
	 * @param page
	 * @return
	 */
	 <T> Page<T> query(BaseQuery<T> query, Page<T> page);
	 
	 /**
	  * 统计
	  * @param query
	  * @return
	  */
	 <T> Map<String, Object> statistics(BaseQuery<T> query);
	
	/**
	 * 跟Mapper中的配置进行查询
	 * @param statementId 查询id
	 * @param object 参数
	 * @return
	 */
	 List<Map<String, Object>> list(String statementId, Object object);
	
	/**
	 * 保存一个对象到数据库
	 * @param object
	 */
	 <T> void save(T object);
	 
	 /**
	 * 带条件保存一个对象到数据库，
	 * 当数据库中不存在规定的条件值的时候，保存，反之没有任何操作
	 * 返回0 代表没有保存，数据库中已经有了，1 表示保存成功，
	 * 多个条件用的是AND连接，注意
	 * @param object
	 * @param whereFieldNames
	 */
	 <T> int save(T object, String... whereFieldNames);
	
	/**
	 * 更新一个对象到数据库
	 * @param object 要更新的数据，为空的不更新
	 */
	 <T> int update(T object);
	
	/**
	 * 更新对象带乐观锁
	 * @param update 要更新的数据，为空的不更新
	 * @param lockFields 锁变量对应的字段名称,如果为null的话，默认使用version作为锁变量
	 * @return
	 */
	 <T> int updateWithOptimisticLock(T update, String ...lockFields);
	
	/**
	 * 从数据库中删除一个对象
	 * @param object id不能为空
	 */
	 <T> int remove(T object);
	 
	 /**
	 * 从数据库中删除一个对象
	 * @param object id不能为空
	 * @param fields 除去id以外参与条件的字段名称
	 */
	 <T> int remove(T object, String... fields);
	 
	 /**
	  * 使用SQL语句更新
	  * @param statement
	  * @return
	  */
	 int update(String statement);
	 
	 /**
	  * 使用SQL语句更新
	  * @param statement
	  * @return
	  */
	 int update(String statement, Object parameter);
	 
	 /**
	  * 批量更新某一个classzz对应表的feildName字段值为updateValue
	  * @param classzz
	  * @param fieldName
	  * @param updateValue
	  * @param ids 逗号分割多个id
	  * @return
	  */
	 <T> int updateByIds(Class<T> classzz, String fieldName, Object updateValue, Serializable[] ids);
	 
	 /**
	  * 批量删除主键=ids的所有字段，id用,分割
	  * @param classzz
	  * @param ids
	  * @return 逗号分割多个id
	  */
	 <T> int deleteByIds(Class<T> classzz, Serializable[] ids);
}
