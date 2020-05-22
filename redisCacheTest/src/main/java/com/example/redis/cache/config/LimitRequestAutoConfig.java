package com.example.redis.cache.config;

import com.example.redis.cache.aop.LimitRequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * <p>
 * LimitRequestAutoConfig
 * <p>
 *
 * @author hucj
 * @date 2020-05-22 15:56:46
 **/
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LimitRequestAutoConfig {

    @Bean
    public LimitRequestInterceptor limitInterceptor() {
        return new LimitRequestInterceptor();
    }

    @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
