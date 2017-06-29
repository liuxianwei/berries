package com.lee.berries.dao;
import java.util.List;

/**
 * 分页类,默认每页15条
 */
public class Page<T> {
	
	private int pageSize; 			//每页显示记录数
	private int totalResult;		//总记录数
	private int currentResult;		//当前记录起始索引
	private int currentPage=0;
	/**
	 * 分页查询返回的数据
	 */
	private List<T> result;
	
	public Page(){
		this.pageSize = 15;
		setCurrentPage(1);
	}
	
	public Page(int currentResult , int pageSize){
		this.currentResult = currentResult;
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return  totalResult/pageSize +(totalResult%pageSize==0? 0:1);
	}
	
	public int getTotalResult() {
		return totalResult;
	}
	
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	
	public int getCurrentPage() {
		return currentResult/pageSize + 1;
	}
	
	public int getCurrentResult() {
		return currentResult;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize(){
		return this.pageSize;
	}
	
	/**
	 * 设置分页查询起始行数,对于mysql分页查询 limit 30,15
	 * 这里的30就是对应的currentResult
	 * @param currentResult
	 */
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.currentResult=(currentPage-1)*pageSize;
	}
	
	public int getNativeCurrentPage(){
		return this.currentPage;
	}
	
	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	
}
