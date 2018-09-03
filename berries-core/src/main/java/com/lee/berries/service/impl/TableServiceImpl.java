package com.lee.berries.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lee.berries.service.TableService;

@Service
public class TableServiceImpl extends BaseServiceImpl implements TableService {

	@Override
	public List<String> getTableNames() {
		return selectList("TableMapper.getTables", null);
	}

	@Override
	public void createTableByTableName(String tableNameTemplate, String tableName) {
		String tableDDL = getTableDDL(tableNameTemplate);
		tableDDL = tableDDL.replace(tableNameTemplate, tableName);
		super.update("TableMapper.createTable", tableDDL);
	}

	@Override
	public String getTableDDL(String tableName) {
		Map<String, String> data = get("TableMapper.getTableDDL", tableName);
		return data.get("Create Table");
	}

}
