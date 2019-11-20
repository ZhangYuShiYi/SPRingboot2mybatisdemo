package com.winterchen.easemob.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JacksonUtil {

	private static ObjectMapper defaultMapper;
	private static ObjectMapper formatedMapper;

	static {
		// 默认的ObjectMapper
		defaultMapper = new ObjectMapper();
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		defaultMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		formatedMapper = new ObjectMapper();
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		formatedMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 所有日期格式都统一为固定格式
		formatedMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		formatedMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	/**
	 * 将对象转化为json数据
	 *
	 * @param obj the obj
	 *
	 * @return string string
	 *
	 * @throws IOException the io exception
	 */
	public static String toJson(Object obj) throws IOException {
		Preconditions.checkArgument(obj != null, "this argument is required; it must not be null");
		return defaultMapper.writeValueAsString(obj);
	}

	/**
	 * json数据转化为对象(Class)
	 * User u = JacksonUtil.parseJson(jsonValue, User.class);
	 * User[] arr = JacksonUtil.parseJson(jsonValue, User[].class);
	 *
	 * @param <T>       the type parameter
	 * @param jsonValue the json value
	 * @param valueType the value type
	 *
	 * @return t t
	 *
	 * @throws IOException the io exception
	 */
	public static <T> T parseJson(String jsonValue, Class<T> valueType) throws IOException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "this argument is required; it must not be null");
		return defaultMapper.readValue(jsonValue, valueType);
	}

	/**
	 * json数据转化为对象(JavaType)
	 *
	 * @param <T>       the type parameter
	 * @param jsonValue the json value
	 * @param valueType the value type
	 *
	 * @return t t
	 *
	 * @throws IOException the io exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseJson(String jsonValue, JavaType valueType) throws IOException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "this argument is required; it must not be null");
		return (T) defaultMapper.readValue(jsonValue, valueType);
	}

	/**
	 * json数据转化为对象(TypeReference)
	 *
	 * @param <T>          the type parameter
	 * @param jsonValue    the json value
	 * @param valueTypeRef the value type ref
	 *
	 * @return t t
	 *
	 * @throws IOException the io exception
	 */
	public static <T> T parseJson(String jsonValue, TypeReference<T> valueTypeRef) throws IOException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "this argument is required; it must not be null");
		return (T) defaultMapper.readValue(jsonValue, valueTypeRef);
	}

	/**
	 * 将对象转化为json数据(时间转换格式： "yyyy-MM-dd HH:mm:ss")
	 *
	 * @param obj the obj
	 *
	 * @return string string
	 *
	 * @throws IOException the io exception
	 */
	public static String toJsonWithFormat(Object obj) throws IOException {
		Preconditions.checkArgument(obj != null, "this argument is required; it must not be null");
		return formatedMapper.writeValueAsString(obj);
	}

	/**
	 * json数据转化为对象(时间转换格式： "yyyy-MM-dd HH:mm:ss")
	 * User u = JacksonUtil.parseJsonWithFormat(jsonValue, User.class);
	 * User[] arr = JacksonUtil.parseJsonWithFormat(jsonValue, User[].class);
	 *
	 * @param <T>       the type parameter
	 * @param jsonValue the json value
	 * @param valueType the value type
	 *
	 * @return t t
	 *
	 * @throws IOException the io exception
	 */
	public static <T> T parseJsonWithFormat(String jsonValue, Class<T> valueType) throws IOException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "this argument is required; it must not be null");
		return formatedMapper.readValue(jsonValue, valueType);
	}

	/**
	 * json数据转化为对象(JavaType)
	 *
	 * @param <T>       the type parameter
	 * @param jsonValue the json value
	 * @param valueType the value type
	 *
	 * @return t t
	 *
	 * @throws IOException the io exception
	 */
	public static <T> T parseJsonWithFormat(String jsonValue, JavaType valueType) throws IOException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "this argument is required; it must not be null");
		return (T) formatedMapper.readValue(jsonValue, valueType);
	}

	/**
	 * json数据转化为对象(TypeReference)
	 *
	 * @param <T>          the type parameter
	 * @param jsonValue    the json value
	 * @param valueTypeRef the value type ref
	 *
	 * @return t t
	 *
	 * @throws IOException the io exception
	 */
	public static <T> T parseJsonWithFormat(String jsonValue, TypeReference<T> valueTypeRef) throws IOException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(jsonValue), "jsonValue is not null");
		return (T) formatedMapper.readValue(jsonValue, valueTypeRef);
	}
	
	public static <T> T serializable(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return defaultMapper.readValue(json, clazz);
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static <T> T serializable(String json, TypeReference<T> reference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return defaultMapper.readValue(json, reference);
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static String deserializer(Object json) {
        if (json == null) {
            return null;
        } else {
            try {
                return defaultMapper.writeValueAsString(json);
            } catch (IOException e) {
                return null;
            }
        }
    }
	
	public static <T> List<T> deserializer(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException
	{
		JavaType javaType = getCollectionType(ArrayList.class, clazz); 
		return (List<T>)defaultMapper.readValue(json, javaType);
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return defaultMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
	
	/**
	 * 设置时间转换格式
	 * @param pattern
	 */
	public static void setDataFormat(String pattern)
	{
		defaultMapper.setDateFormat(new SimpleDateFormat(pattern));
	}

}
