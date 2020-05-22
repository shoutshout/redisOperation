package com.example.redis.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * ThreadPoolConfig 配置线程池
 * <p>
 *
 * @author hucj
 * @date 2020-05-21 16:43:58
 **/
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final String THREAD_POOL = "test-thread=pool";

    /**
     * 配置线程池
     *
     * @return Executor线程池
     */
    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最少数量
        executor.setCorePoolSize(10);
        //线程池维护线程的最大数量
        executor.setMaxPoolSize(50);
        //缓存队列
        executor.setQueueCapacity(99999);
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置线程名称
        executor.setThreadNamePrefix(THREAD_POOL);
        //允许的空闲时间
        executor.setKeepAliveSeconds(60);
        //执行初始化
        executor.initialize();
        return executor;
    }

}

