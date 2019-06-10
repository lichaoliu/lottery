package com.lottery.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationException;

import java.nio.charset.Charset;


public class JacksonJsonSerializer<T> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 
    private final JavaType javaType;
 
    private ObjectMapper objectMapper = new ObjectMapper();
 
    public JacksonJsonSerializer(Class<T> type) {
        this.javaType = objectMapper.getTypeFactory().uncheckedSimpleType(type);
    }
 
    @SuppressWarnings("unchecked")
     
    public T deserialize(byte[] bytes) throws SerializationException {
    	
        if (bytes==null||bytes.length==0) {
            return null;
        }
        try {
            return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }
 
     
    public byte[] serialize(Object t) throws SerializationException {
        if (t == null) {
            return null;
        }
        try {
            return this.objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }
 
   
}
