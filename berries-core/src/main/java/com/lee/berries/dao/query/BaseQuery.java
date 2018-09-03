package com.lee.berries.dao.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
	protected static final String KEY_PREFIX = "key_";
	
	protected static final String ORDER_PREFIX = "order_";
	protected static final String ASC = "asc";
	public    static final String DESC = "desc";
	public    static final String DEFAULT_ORDER_TYPE = ASC;
	
	protected Boolean enableLike = false;
	
	protected Boolean enableCount = true;
	
	private String queryBoundSQL;
	
	private String queryRowBoundSQL;
	
	private String queryStatisticsBoundSQL;
	
	private int offset = 0;
	
	private int limit = Integer.MAX_VALUE;
	
	private List<ParameterMapping> parameterMappings;
	
	protected abstract String getQuerySQL();
	
	protected String getStatisticsSQL() {
		return null;
	}
	
	protected String getRowSQL(){
		String sql = "select count(1) as c from ( " + getQuerySQL() + getWhere() + " ) t";
		return sql;
	}

	protected String getOrder(){
		StringBuilder order = new StringBuilder();
		for(Field field:this.getClass().getDeclaredFields()){
			String fieldName = field.getName();
			if(Modifier.isStatic(field.getModifiers()) || fieldName.equals("serialVersionUID")) {
				continue;
			}
			if(fieldName.startsWith(ORDER_PREFIX)){
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + field.getName().substring(1);
				try{
					Method getMethod = this.getClass().getMethod(getMethodName);
					Object value = getMethod.invoke(this);
					if(value != null && value.toString().trim().length() > 0){
						String orderType = (String) value;
						String orderName = fieldName.replaceFirst(ORDER_PREFIX, "");
						orderName = orderName.replaceFirst(SPLIT, ".");
						order.append(", ")
							.append(orderName)
							.append(" ")
							.append(orderType);
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
		if(order.length() > 0){
			order.deleteCharAt(0);
			order.insert(0, " order by ");
			return order.toString();
		}
		return "";
	}
	
	/**
	 * 根据变量值得到查询条件，
	 * 如果是字符型的，字符长度大于0才算有效，
	 * 其余以是否为空作为判断，不为空为有效
	 * @return
	 */
	protected String getWhere(){
		StringBuilder where = new StringBuilder();
		for(Field field:this.getClass().getDeclaredFields()){
			String fieldName = field.getName();
			if(Modifier.isStatic(field.getModifiers()) || fieldName.equals("serialVersionUID")) {
				continue;
			}
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
						where.append(itemWhere);
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
			where.delete(0, 4);
			where.insert(0, " where ");
			return where.toString();
		}
		return "";
	}
	
	private String getItemWhere(String fieldName,Object value){
		
		String propertyName = fieldName;
		fieldName = fieldName.replaceFirst(SPLIT, ".");
		
		StringBuilder where = new StringBuilder(100);
		
		if(propertyName.startsWith(MIN_PREFIX)){
			String min = propertyName;
			String newName = min.replace(MIN_PREFIX, "").replaceFirst(SPLIT,".");
			where.append(" and ").append(newName).append(" >= ?");
		}
		else if(propertyName.startsWith(MAX_PREFIX)){
			String max = propertyName;
			String newName = max.replace(MAX_PREFIX, "").replaceFirst(SPLIT,".");
			where.append(" and ").append(newName).append(" <= ?");
		}
		else if(propertyName.startsWith(IN_PREFIX)){
			List<Serializable> list = (List<Serializable>) value;
			if(list != null && list.size() > 0){
				String in = propertyName;
				String newName = in.replace(IN_PREFIX, "").replaceFirst(SPLIT,".");
				where.append(" and ").append(newName).append(" in (");
				for(int i = 0; i < list.size(); i++){
					where.append(",?");
				}
				where.append(")");
			}
		}
		else if(value instanceof String && value.toString().length() > 0){
			if(enableLike){
				where.append(" and ").append(fieldName).append(" like ?");
			}
			else{
				where.append(" and ").append(fieldName).append(" = ?");
			}
		}
		if(where.length() == 0) {
			where.append(" and ").append(fieldName).append(" = ?");
		}
		return where.toString();
	}
	
	public String getQueryBoundSQL(){
		if(queryBoundSQL == null) {
			queryBoundSQL = getQuerySQL() + getWhere() + getOrder();
		}
		return queryBoundSQL;
	}
	
	public String getQueryRowBoundSQL(){
		if(queryRowBoundSQL == null) {
			queryRowBoundSQL = getRowSQL();
		}
		return queryRowBoundSQL;
	}
	
	public String getQueryStatisticsBoundSQL(){
		if(queryStatisticsBoundSQL == null) {
			queryStatisticsBoundSQL = getStatisticsSQL() + getWhere();
		}
		return queryStatisticsBoundSQL;
	}
	
	public List<ParameterMapping> getParameterMappings(Configuration configuration){
		if(parameterMappings == null) {
			
			parameterMappings = new ArrayList<ParameterMapping>();
			for(Field field:this.getClass().getDeclaredFields()){
				String fieldName = field.getName();
				if(Modifier.isStatic(field.getModifiers()) || fieldName.equals("serialVersionUID")) {
					continue;
				}
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
								if(this.enableLike && value instanceof String) {
									String valueStr = (String) value;
									valueStr = valueStr.trim();
									valueStr = escape(valueStr);
									value = "%" + valueStr + "%";
									field.set(this, value);
								}
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
		}
		return parameterMappings;
	}
	
	private String escape(String value) {
		value = value.replace("%", "\\%");
		value = value.replace("_", "\\_");
		return value;
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
	
	public Boolean getEnableCount() {
		return enableCount;
	}

	public void setEnableCount(Boolean enableCount) {
		this.enableCount = enableCount;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	
}
