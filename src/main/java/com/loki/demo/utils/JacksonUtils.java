package com.loki.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 *
 * @author Loki
 */
public class JacksonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 将Object对象转换为JSON格式的字符串
     *
     * @param obj 要转换成JSON格式字符串的对象
     * @return 序列化成功，返回json格式的字符串；否则返回null
     */
    public static String write2JsonString(Object obj) {
        if (obj != null) {
            try {
                return mapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                logger.error("write2JsonString error", e);
            }
        }
        return null;
    }

    /**
     * 将JSON格式的字符串转换为期望类型的对象
     *
     * @param jsonString JSON格式的字符串
     * @param clazz      要转换的目标对象类型；当不存在具体的实体类时，可传入JsonNode.class，
     *                   将JSON字符串转换为一个JsonNode对象
     * @return 反序列化成功，返回T类型的对象；否则返回null
     */
    public static <T> T readJson2Entity(String jsonString, Class<T> clazz) {
        if (jsonString != null) {
            try {
                return mapper.readValue(jsonString, clazz);
            } catch (IOException e) {
                logger.error("readJson2Entity error jsonString={},class={}", jsonString, clazz.getName(), e);
            }
        }
        return null;
    }

    /**
     * 将JSON格式的字符串转换为期望类型的对象
     *
     * @param jsonString JSON格式的字符串
     * @param clazz      要转换的目标对象类型
     * @return 反序列化成功，返回T类型的对象列表；否则返回null
     */
    public static <T> List<T> readJson2EntityList(String jsonString, Class<T> clazz) {
        if (jsonString != null) {
            JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            try {
                return mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                logger.error("readJson2EntityList error jsonString={},class={}", jsonString, clazz.getName(), e);
            }
        }
        return null;
    }

    /**
     * 奖JSON格式的字符串转化为自定义类型的对象
     *
     * @param jsonString   JSON格式的字符串
     * @param valueTypeRef 要转换的目标对象类型
     * @param <T>          要转换的目标对象
     * @return 反序列化成功，返回T类型的对象列表；否则返回null
     */
    public static <T> T readJson2EntityByTypeReference(String jsonString, TypeReference<T> valueTypeRef) {
        if (jsonString != null) {
            try {
                return mapper.readValue(jsonString, valueTypeRef);
            } catch (IOException e) {
                logger.error("readJson2EntityByTypeReference error jsonString={},class={}", jsonString, valueTypeRef, e);
            }
        }
        return null;
    }

    public static <V> Map<String, V> toMap(Object object) {
        return mapper.convertValue(object, new TypeReference<Map<String, V>>() {
        });
    }
}
