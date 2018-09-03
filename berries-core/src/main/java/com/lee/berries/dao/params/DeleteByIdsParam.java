package com.lee.berries.dao.params;

public class DeleteByIdsParam<T> {

	private Class<T> classzz;
	private Object[] ids;
	
	public DeleteByIdsParam() {
		super();
	}
	
	public DeleteByIdsParam(Class<T> classzz, Object[] ids) {
		super();
		this.classzz = classzz;
		this.ids = ids;
	}
	
	public Class<T> getClasszz() {
		return classzz;
	}
	public void setClasszz(Class<T> classzz) {
		this.classzz = classzz;
	}
	public Object[] getIds() {
		return ids;
	}
	public void setIds(Object[] ids) {
		this.ids = ids;
	}
}
