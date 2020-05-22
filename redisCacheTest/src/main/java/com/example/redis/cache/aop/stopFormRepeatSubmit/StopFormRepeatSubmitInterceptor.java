package com.example.redis.cache.aop.stopFormRepeatSubmit;

import cn.hutool.core.util.StrUtil;
import com.example.redis.cache.annotation.StopFormRepeatSubmitAnnotation;
import com.example.redis.cache.enums.ResultEnum;
import com.example.redis.cache.util.HttpResultUtils;
import com.example.redis.cache.util.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * StopFormRepeatSubmitInterceptor
 * <p>
 *
 * @author hucj
 * @date 2020-05-21 15:46:24
 **/
@SuppressWarnings("unchecked")
@Slf4j
@Aspect
@AllArgsConstructor
public class StopFormRepeatSubmitInterceptor {

    private final RedisTemplate redisTemplate;

    @SneakyThrows
    @Around("@annotation(stopFormRepeatSubmitAnnotation)")
    public Object apiAopIdempotentInterceptor(ProceedingJoinPoint proceedingJoinPoint, StopFormRepeatSubmitAnnotation stopFormRepeatSubmitAnnotation) {


        //获取当前请求HttpServletRequest
        HttpServletRequest request = HttpUtil.getHttpServletRequest();

        //获取请求头
//        String header = request.getHeader(SecurityConstants.FROM);

        Object proceed = null;

        //判断是否是内部请求 满足条件将其放行
//        if (StrUtil.isNotEmpty(header) && StrUtil.equals(SecurityConstants.FROM_IN, header)) {
//            proceed = proceedingJoinPoint.proceed();
//            return proceed;
//        }


        //获取需要执行幂等注解方法参数、列如 @StopFormRepeatSubmitAnnotation("testExpApi")
        String extApi = stopFormRepeatSubmitAnnotation.value();
        String userId = getUserId();

        //判断获取用户ID是否为空
        if (StrUtil.isEmpty(userId)) {
            HttpServletResponse response = HttpUtil.getHttpServletResponse();
            HttpResultUtils.responseError(response, ResultEnum.TOKEN_LOSE_EFFICACY.getCode(), ResultEnum.TOKEN_LOSE_EFFICACY.getMessage());
            return null;
        }

        //组装幂等性唯一key
        String extApiKey = extApi + getUserId();

        //判断redis缓存中是否存在key、存在key需要抛出异常提示
        if (!findRepectKey(extApiKey)) {
            HttpServletResponse response = HttpUtil.getHttpServletResponse();
            HttpResultUtils.responseError(response, ResultEnum.REPECT_SUBMIT.getCode(), ResultEnum.REPECT_SUBMIT.getMessage());
            return null;
        }
        //满足条件将其放行
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            //将key从Redis中删除
            redisTemplate.delete(extApiKey);
            //调用异常工具类抛出异常
            e.printStackTrace();
        } finally {
            //将key从Redis中删除
            redisTemplate.delete(extApiKey);
        }
        return proceed;
    }

    /**
     * 1.在调用接口之前获取用户ID
     * 2.根据当前用户ID查询Redis缓存中是否有幂等性key、如果key不存在返回true，进入Controller方法执行api
     * 3.如果幂等性key在Redis缓存中，抛出异常提示客户不能重复提交
     *
     * @param extApiKey : 更新接口唯一key,由方法名+userId组成
     * @return 如果Redis中不存在该key返回true、否则返回false，为重复提交表单
     */
    private Boolean findRepectKey(String extApiKey) {
        final Object objectValueByKey = redisTemplate.opsForValue().get(extApiKey);
        if (Objects.isNull(objectValueByKey)) {
            //设置幂等性key、过期时间为30s
            redisTemplate.opsForValue().set(extApiKey, extApiKey, 20, TimeUnit.SECONDS);
            return Boolean.TRUE;
        }
        //保证每个接口对应的repectKey 只能访问一次，保证接口幂等性问题
        return Boolean.FALSE;
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
