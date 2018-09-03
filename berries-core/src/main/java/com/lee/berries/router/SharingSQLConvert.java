package com.lee.berries.router;

import java.util.List;

import org.apache.ibatis.mapping.ParameterMapping;

public interface SharingSQLConvert {

	String convert(String sql, List<ParameterMapping> parameterMappings, Object parameter);
	
}
