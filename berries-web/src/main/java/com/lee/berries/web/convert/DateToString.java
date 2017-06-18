package com.lee.berries.web.convert;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Title: 解决返回JSON中Date展示位时间挫的问题，让Date类型自动返回为yyyy-MM-dd HH:mm:ss
 * Description: 描述
 * @author liuxianwei
 * @created 2016年12月16日 下午2:09:35
 */
public class DateToString extends ObjectMapper {

	public DateToString() {
		CustomSerializerFactory factory = new CustomSerializerFactory();
		factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String str = sdf.format(value);
				if(str.length() > 0){
					str = str.replace(" 00:00:00", "");
				}
				jsonGenerator.writeString(str);
			}
		});
		this.setSerializerFactory(factory);
		
	}
}
