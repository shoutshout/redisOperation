package com.example.redis.cache.aop;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.example.redis.cache.annotation.LimitRequestAnnotation;
import com.example.redis.cache.constant.SecurityConstants;
import com.example.redis.cache.enums.LimitType;
import com.example.redis.cache.enums.ResultEnum;
import com.example.redis.cache.exception.QingDuoCheckException;
import com.example.redis.cache.util.HttpResultUtils;
import com.example.redis.cache.util.HttpUtil;
import com.example.redis.cache.util.LuaScriptUtils;
import com.example.redis.cache.util.RequestIpUtils;
import com.google.common.collect.ImmutableList;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>
 * LimitRequestInterceptor
 * <p>
 *
 * @author hucj
 * @date 2020-05-22 15:57:57
 **/
@Aspect
@Slf4j
public class LimitRequestInterceptor {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @SneakyThrows
    @Around("@annotation(limitRequestAnnotation)")
    public Object limitInterceptor(ProceedingJoinPoint proceedingJoinPoint, LimitRequestAnnotation limitRequestAnnotation) {

        //获取当前请求HttpServletRequest
        HttpServletRequest request = HttpUtil.getHttpServletRequest();

        Object proceed = null;

        //获取请求头
        String header = request.getHeader(SecurityConstants.FROM);

        //判断是否是内部请求 满足条件将其放行
        if (StrUtil.isNotEmpty(header) && StrUtil.equals(SecurityConstants.FROM_IN, header)) {
            proceed = proceedingJoinPoint.proceed();
            return proceed;
        }

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        // 如果annotation为空 直接放行执行程序
        if (Objects.isNull(limitRequestAnnotation)) {
            try {
                proceed = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return proceed;
        }

        //获取当前请求用户ID
        String userId = getUserId();

        //获取接口注解限流类型、限制时间、限制次数
        LimitType limitType = limitRequestAnnotation.limitType();
        String name = limitRequestAnnotation.name();
        String key;
        int limitPeriod = limitRequestAnnotation.period();
        int limitCount = limitRequestAnnotation.count();


        switch (limitType) {
            //IP限流
            case IP:
                key = "limit:" + RequestIpUtils.getIpAddr(request);
                break;
            //客户自定义限流
            case CUSTOMER:
                String value = limitRequestAnnotation.value();
                key = "limit:" + value + userId;
                break;
            default:
                key = "limit:" + StringUtils.upperCase(method.getName());
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitRequestAnnotation.prefix(), key));
        try {
            String luaScript = LuaScriptUtils.buildLuaScript();
            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            log.debug("Access try count is {} for name={} and key = {}", count, name, key);
            if (Objects.nonNull(count) && count.intValue() <= limitCount) {
                return proceedingJoinPoint.proceed();
            } else {
                HttpServletResponse response = HttpUtil.getHttpServletResponse();
                HttpResultUtils.responseError(response, ResultEnum.REQUEST_LIMIT.getCode(), limitRequestAnnotation.exceptionMsg());
                return null;
            }
        } catch (Throwable e) {
            //调用异常工具类抛出异常
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户ID
     *
     * @return 返回当前userId
     */
    private String getUserId() {

        return "123456";
    }

}
