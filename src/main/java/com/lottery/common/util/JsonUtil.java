package com.lottery.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lottery.common.exception.LotteryException;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * JSON转换工具类
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		//objectMapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		//objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
	}

	
	public static ObjectNode createObjectNode() {
		return objectMapper.createObjectNode();
	}
	
	
	public static ArrayNode createArrayNode() {
		return objectMapper.createArrayNode();
	}
	
	
	/**
	 * json字符串转换为Map<String,Object>格式
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> transferJson2Map(String json) {
		if (StringUtils.isBlank(json)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> result = new JSONDeserializer<Map<String, Object>>().use(null,
				new HashMap<String, Object>().getClass()).deserialize(json);
		return result;
	}

	public static String toJson(Object obj) {
		try {

			
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new LotteryException("OrderRequest.toJson error", e);
		}
	}

	public static <T> T fromJsonToObject(String body, Class<T> clazz) {
		try {
			objectMapper.configure(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS, false);
			
			return objectMapper.readValue(body, clazz);
		} catch (Exception e) {
			throw new LotteryException("JsonUtil fromJsonToObject error,body="+body, e);
		}
	}

    /**
     * object to class
     * @param  object
     * @param  clazz
     * */
    public static <T> T objectToClass(Object object,Class<T> clazz){
        return  objectMapper.convertValue(object,clazz);
    }

	/**
	 * json转换为arrayList 
	 * @param  jsonStr json字符串
	 * @param clazz 转换的实体类
	 * 
	 * */
	public static <T> List<T> fromJsonToAarryList(String jsonStr,Class<T> clazz)throws  Exception{
        try{
            JavaType javaType=objectMapper.getTypeFactory().constructParametrizedType(ArrayList.class,ArrayList.class,clazz);
            return objectMapper.readValue(jsonStr, javaType);
        }catch(Exception e){
            throw new LotteryException("Json to List error,jsonStr="+jsonStr,e);
        }

	}
    /**
     * object to list
     * @param  object
     * @param  clazz
     * */
    public static <T> List<T> objectToList(Object object,Class<T> clazz){
        JavaType javaType=objectMapper.getTypeFactory().constructParametrizedType(List.class,List.class, clazz);
        return objectMapper.convertValue(object, javaType);
    }

    public static <T> Collection<T> objectToCollection(Object object,Class<T> clazz){

        //mapper.readValue(jsonInput, new TypeReference<List<ConsultantDto>>(){});
        JavaType javaType=objectMapper.getTypeFactory().constructParametrizedType(Collection.class,Collection.class, clazz);

        return objectMapper.convertValue(object, javaType);
    }

    public static <T> JavaType getCollectionType(Class<T> collectionClass, Class<T>  elementClasses) {
        return objectMapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass,elementClasses);
    }


    /**
	 * 以下用flexjson 进行序列化，反序列化
	 * @param <T>
	 * */
	
	public static <T> String flextoJson(T t){
		return  new JSONSerializer().exclude("*.class").serialize(t);
	}
	public static <T> T flexToObject(Class<T> c,String json){
		
		return new JSONDeserializer<T>().use(null, c).deserialize(json);
	}
	
	public static <T> String flextoJsonArray(Collection<T> collection) {
	        return new JSONSerializer().exclude("*.class").serialize(collection);
	    }
	   public static <T> Collection<T> flexfromJsonArrayToObject(Class<T> c,String json) {
	        return new JSONDeserializer<Collection<T>>().use(null, ArrayList.class).use("values", c).deserialize(json);
	    }
	
	
}
