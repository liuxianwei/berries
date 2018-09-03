package com.lee.berries.web.convert;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
public class JSONObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8480516581633749114L;
	
	public JSONObjectMapper() {
		SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, new JsonSerializer<Long>() {

			@Override
			public void serialize(Long value, JsonGenerator generator, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				generator.writeString(value.toString());
			}});
       super.registerModule(simpleModule);
       super.setSerializationInclusion(Include.NON_NULL);
	}
}
