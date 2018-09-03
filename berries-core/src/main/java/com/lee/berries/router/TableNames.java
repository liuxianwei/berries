package com.lee.berries.router;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableNames {

	private Set<String> tables;
	
	private Boolean init = false;
	
	private static TableNames instance;
	
	private TableNames() {
		tables = new HashSet<String>();
	}
	
	public void init(List<String> tableNames) {
		if(!init) {
			tables.addAll(tableNames);
			init = true;
		}
	}
	
	public void add(String tableName) {
		tables.add(tableName);
	}
	
	public void add(List<String> tableNames) {
		tables.addAll(tableNames);
	}
	
	public boolean exist(String tableNames) {
		return tables.contains(tableNames);
	}
	
	public String[] getTables(){
		String[] t = new String[tables.size()];
		tables.toArray(t);
		return t;
	}
	
	public Boolean getInit() {
		return init;
	}
	
	public static TableNames getInstance() {
		if(instance == null) {
			synchronized (TableNames.class) {
				if(instance == null) {
					instance = new TableNames();
				}
			}
		}
		return instance;
	}
	
	
}
