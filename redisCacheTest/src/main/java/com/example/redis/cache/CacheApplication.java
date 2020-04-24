package com.example.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huchangjian
 */
@SpringBootApplication
@Slf4j
public class CacheApplication {


    public static void main(String[] args) {

        log.info("开始启动。。。。。。。。。。。。。。。。");
        SpringApplication.run(CacheApplication.class, args);
        log.info("已经启动啦。。。。。。。。。。。。。。。");
    }

}
