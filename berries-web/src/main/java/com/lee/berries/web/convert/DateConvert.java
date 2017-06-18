package com.lee.berries.web.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Title: 全局字符串转日期
 * Description: 描述
 * @author liuxianwei
 * @created 2016年12月16日 上午9:31:47
 */
public class DateConvert implements Converter<String, Date> {

    @Override
    public Date convert(String stringDate) {
    	if(stringDate==null || "".equals(stringDate))return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(stringDate.length()<13){
        	simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
