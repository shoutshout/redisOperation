package com.example.redis.cache.collecter;

import com.example.redis.cache.annotation.LimitRequestAnnotation;
import com.example.redis.cache.annotation.StopFormRepeatSubmitAnnotation;
import com.example.redis.cache.enums.LimitType;
import com.example.redis.cache.util.RedissLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

/**
 *<P>
 *  TestCollecter
 *<P>
 *
 *@author hucj
 *@date 2020-05-20
 **/
@RequestMapping("/redisson")
@Controller
@Slf4j
public class TestCollecter {

    @GetMapping("/LimitRequest")
    @LimitRequestAnnotation(value = "redisson:limit", exceptionMsg = "你的操作过于频繁,请休息一下吧!"
            , key = "test", period = 10, count = 3, name = "resource"
            , prefix = "limit", limitType = LimitType.CUSTOMER)
    public void limit(){
        log.info("开始调用接口");

        log.info("调用接口完成 ！");
    }

    @GetMapping("/stopFormRequestSubmit")
    @StopFormRepeatSubmitAnnotation("redisson:stopFormRequestSubmit")
    public void stop(){
        log.info("开始调用接口");
        try {
            Thread.sleep(5000);
        }catch (Exception e){
//            e.printStackTrace();
           log.error("表单不能重复提交 !");
        }
        log.info("调用接口完成 ！");
    }

    @GetMapping("/test")
    public void redissonTest() {
        String key = "redisson_key";
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.err.println("=============线程开启============" + Thread.currentThread().getName());
                        /*
                         * distributedLocker.lock(key,10L); //直接加锁，获取不到锁则一直等待获取锁
                         * Thread.sleep(100); //获得锁之后可以进行相应的处理
                         * System.err.println("======获得锁后进行相应的操作======"+Thread.
                         * currentThread().getName());
                         * distributedLocker.unlock(key); //解锁
                         * System.err.println("============================="+
                         * Thread.currentThread().getName());
                         */
                        // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        boolean isGetLock = RedissLockUtil.tryLock(key, TimeUnit.SECONDS, 5, 10);
                        if (isGetLock) {
                            System.out.println("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                            Thread.sleep(100); // 获得锁之后可以进行相应的处理
                            System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
                            RedissLockUtil.unlock(key);
                            System.err.println("=============================" + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }
}
