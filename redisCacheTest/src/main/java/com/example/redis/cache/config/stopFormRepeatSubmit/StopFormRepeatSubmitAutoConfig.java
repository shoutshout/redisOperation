package com.example.redis.cache.config.stopFormRepeatSubmit;
import com.example.redis.cache.aop.stopFormRepeatSubmit.StopFormRepeatSubmitInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *<P>
 *  StopFormRepeatSubmitAutoConfig
 *  Api接口幂等性(防止表单重复提交)Bean自动注入到spring容器中。其他模块使用
 *<P>
 *
 *@author hucj
 *@date 2020-05-21
 **/
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class StopFormRepeatSubmitAutoConfig {


    @Bean
    public StopFormRepeatSubmitInterceptor extApiAopIdempotent(final RedisTemplate redisTemplate) {
        return new StopFormRepeatSubmitInterceptor(redisTemplate);
    }

}
