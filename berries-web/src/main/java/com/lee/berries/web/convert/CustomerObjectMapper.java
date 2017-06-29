package com.lee.berries.web.convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class CustomerObjectMapper extends ObjectMapper {

    public CustomerObjectMapper() {
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
        factory.addGenericMapping(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator jsonGenerator,
                    SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                String str = new java.text.DecimalFormat("0.00").format(value);
                jsonGenerator.writeString(str);
            }
        });
        this.setSerializerFactory(factory);
        
    }
}
