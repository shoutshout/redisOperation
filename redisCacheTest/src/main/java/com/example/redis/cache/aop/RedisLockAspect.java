package com.example.redis.cache.aop;

import com.example.redis.cache.redisson.RedisLock;
import com.example.redis.cache.util.RedissLockUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *<P>
 *  RedisLockAspect
 *<P>
 *
 *@author hucj
 *@date 2020-05-20
 **/
@Aspect
@Component
public class RedisLockAspect {

    /**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     */
    private static Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁

    //Service层切点     用于记录错误日志
//    @Pointcut("@annotation(com.hsshy.beam.aop.RedisServicelock)")
//    public void lockAspect() {
//
//    }

    @Around("@annotation(redisLock)")
    public  Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        boolean res=false;

        Object obj = null;
        String seckillId = joinPoint.getArgs()[0].toString();
        try {
            res = RedissLockUtil.tryLock(seckillId, TimeUnit.SECONDS, 3, 20);

            if(res){
                obj = joinPoint.proceed();
            }


        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally{
            if(res){//释放锁
                RedissLockUtil.unlock(seckillId+"");
            }
        }
        return obj;
    }

}
