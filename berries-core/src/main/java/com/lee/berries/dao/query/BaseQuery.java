package com.lee.berries.dao.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMapping.Builder;
import org.apache.ibatis.session.Configuration;

/**
 * 搜索组件模型基础类
 * @author Liuxianwei
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseQuery<T> {

	protected static final String SPLIT = "_";
	protected static final String MIN_PREFIX = "min_";
	protected static final String MAX_PREFIX = "max_";
	protected static final String IN_PREFIX = "in_";
	protected static final String ORDER_PREFIX = "order_";
	protected static final String ASC = "asc";
	public    static final String DESC = "desc";
	public    static final String DEFAULT_ORDER_TYPE = ASC;
	
	protected Boolean enableLike = false;
	
	private List<ParameterMapping> parameterMappings;
	
	protected abstract String getQuerySQL();
	
	protected String getRowSQL(){
		String sql = getQuerySQL();
		sql = sql.replaceFirst("select.+?from", "select count(*) as c from ");
		return sql;
	}

	protected String getOrder(){
		String order = "";
		for(Field field:this.getClass().getDeclaredFields()){
			String fieldName = field.getName();
			if(fieldName.startsWith(ORDER_PREFIX)){
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + field.getName().substring(1);
				try{
					Method getMethod = this.getClass().getMethod(getMethodName);
					Object value = getMethod.invoke(this);
					String orderType = DEFAULT_ORDER_TYPE;
					if(value != null && value.toString().length() > 0){
						orderType = value.toString();
					}
					String orderName = fieldName.replace(ORDER_PREFIX, "");
					orderName = orderName.replace(SPLIT, ".");
					order += ", " + orderName + " " + orderType;
					
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		if(order != null && order.length() > 0){
			order = order.replaceFirst(",", "");
			order = " order by " + order;
		}
		return order;
	}
	
	/**
	 * 根据变量值得到查询条件，
	 * 如果是字符型的，字符长度大于0才算有效，
	 * 其余以是否为空作为判断，不为空为有效
	 * @return
	 */
	protected String getWhere(){
		String where = "";
		for(Field field:this.getClass().getDeclaredFields()){
			String fieldName = field.getName();
			if(!fieldName.startsWith(ORDER_PREFIX)){
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + field.getName().substring(1);
				try{
					Method getMethod = this.getClass().getMethod(getMethodName);
					Object value = getMethod.invoke(this);
					if(value instanceof String && value.equals("")){
						value = null;
					}
					if(value != null){
						String itemWhere = getItemWhere(fieldName,value);
						where += itemWhere;
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		if(where != null && where.length() > 0){
			where = where.replaceFirst("and", "");
			where = " where " + where;
		}
		return where;
	}
	
	private String getItemWhere(String fieldName,Object value){
		
		String propertyName = fieldName;
		fieldName = fieldName.replace(SPLIT, ".");
		
		String where = " and " + fieldName + " = ?";
		
		if(propertyName.startsWith(MIN_PREFIX)){
			String min = propertyName;
			String newName = min.replace(MIN_PREFIX, "").replace(SPLIT,".");
			where = " and " + newName + " >= ?"; 
		}
		
		if(propertyName.startsWith(MAX_PREFIX)){
			String max = propertyName;
			String newName = max.replace(MAX_PREFIX, "").replace(SPLIT,".");
			where = " and " + newName + " <= ?"; 
		}
		
		if(propertyName.startsWith(IN_PREFIX)){
			List<Serializable> list = (List<Serializable>) value;
			if(list != null && list.size() > 0){
				String in = propertyName;
				String newName = in.replace(IN_PREFIX, "").replace(SPLIT,".");
				String inValue = "";
				for(int i = 0; i < list.size(); i++){
					inValue += ",?";
				}
				where = " and " + newName + " in (" + inValue.substring(1) + ")"; 
			}
			else{
				where = "";
			}
		}
		
		if(value instanceof String && value.toString().length() > 0){
			if(enableLike){
				where = " and " + fieldName + " like ?";
			}
			else{
				where = " and " + fieldName + " = ?";
			}
		}
		
		return where;
	}
	
	public String getQueryBoundSQL(){
		String sql = getQuerySQL() + getWhere() + getOrder();
		return sql;
	}
	
	public String getQueryRowBoundSQL(){
		String sql = getRowSQL() + getWhere();
		return sql;
	}
	
	public List<ParameterMapping> getParameterMappings(Configuration configuration){
		parameterMappings = new ArrayList<ParameterMapping>();
		for(Field field:this.getClass().getDeclaredFields()){
			String fieldName = field.getName();
			if(!fieldName.startsWith(ORDER_PREFIX)){
				try{
					field.setAccessible(true);
					Object value = field.get(this);
					if(value instanceof String && value.equals("")){
						value = null;
					}
					if(value != null){
						if(fieldName.startsWith(IN_PREFIX)){
							List<Serializable> list = (List<Serializable>) value;
							if(list != null && list.size() > 0){
								for(int i = 0; i < list.size(); i++){
									Builder builder = new Builder(configuration, field.getName() , field.getType());
									ParameterMapping parameterMapping = builder.build();
									parameterMappings.add(parameterMapping);
								}
							}
						}
						else{
							Builder builder = new Builder(configuration, field.getName() , field.getType());
							ParameterMapping parameterMapping = builder.build();
							parameterMappings.add(parameterMapping);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return parameterMappings;
	}
	
	/**
	 * 获取泛型的类型
	 * @return
	 */
	public Class<T> getParamType(){
		Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
        return entityClass;
	}
	
	public Boolean getEnableLike() {
		return enableLike;
	}

	public void setEnableLike(Boolean enableLike) {
		this.enableLike = enableLike;
	}
}
