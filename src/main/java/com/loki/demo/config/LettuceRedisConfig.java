package com.loki.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.Serializable;
import java.time.Duration;

/**
 * redis配置类
 *
 * @author Loki
 */
@Configuration
@EnableCaching
public class LettuceRedisConfig {

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public GenericToStringSerializer<String> genericToStringSerializer() {
        return new GenericToStringSerializer<>(String.class);
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJsonRedisSerializer() {
        ObjectMapper redisJsonMapper = Jackson2ObjectMapperBuilder.json().serializationInclusion(JsonInclude.Include.NON_NULL)
                .failOnUnknownProperties(false).featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
        // 把类名作为属性进行存储
        redisJsonMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 剔除类上的所有注解
        redisJsonMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        redisJsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 枚举类使用toString方法进行反序列化
        redisJsonMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        // 枚举类使用toString方法进行反序列化
        redisJsonMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        return new GenericJackson2JsonRedisSerializer(redisJsonMapper);
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory,
                                     GenericToStringSerializer<String> genericToStringSerializer,
                                     GenericJackson2JsonRedisSerializer genericJsonRedisSerializer) {

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(3600))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(genericToStringSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJsonRedisSerializer));

        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }
}
