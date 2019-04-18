package com.isbing.authority.common.util;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 统一返回结果bean
 * Created by songbing
 * Created time 2019/3/16 下午6:42
 */
public class JsonUtil {

	//jackson默认方式序列化
	private static final ObjectMapper jacksonMapper = new ObjectMapper();  //生成json

	static {
		jacksonMapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//手动设置时区
		jacksonMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		//null 和原始类型 为默认值的 不参与序列化
		jacksonMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
	}

	public static final String toJson(Object ob) {
		try {
			return jacksonMapper.writeValueAsString(ob);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String toJsonWithObject(Map<String, Object> map) {
		try {
			return jacksonMapper.writeValueAsString(map);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final Map<String, Object> toMap(String json) {
		try {
			return jacksonMapper.readValue(json, HashMap.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final ArrayList<Object> toList(String json) {
		try {
			return jacksonMapper.readValue(json, ArrayList.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final Object toObject(String json) {
		try {
			return jacksonMapper.readValue(json, Object.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final <T> T toObject(String json, TypeReference<T> type) {
		try {
			return jacksonMapper.readValue(json, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final <T> T convertObject(Object bean, TypeReference<T> type) throws Exception {
		try {
			return jacksonMapper.convertValue(bean, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final <T> List<T> toObjectList(String json, TypeReference<? extends Collection<T>> type) {
		try {
			return jacksonMapper.readValue(json, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final <T> List<T> toObjectList(String json, Class<?> collectionClass, Class<T> valueClass) {
		try {
			JavaType javaType = jacksonMapper.getTypeFactory().constructParametricType(collectionClass, valueClass);
			return jacksonMapper.readValue(json, javaType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final <K, V> Map<K, V> toObjectMap(String json, TypeReference<? extends Map<K, V>> type) {
		try {
			return jacksonMapper.readValue(json, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final <T> T toObject(String json, Class<T> tclass) {
		try {
			return jacksonMapper.readValue(json, tclass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String toJson(List<String> list) {
		try {
			return jacksonMapper.writeValueAsString(list);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static JsonNode toJsonNode(String value) {
		try {
			ObjectMapper om = new ObjectMapper();
			return om.readTree(value);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
