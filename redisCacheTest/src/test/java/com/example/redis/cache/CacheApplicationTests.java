package com.example.redis.cache;

import com.alibaba.fastjson.JSON;
import com.example.redis.cache.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class CacheApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

        log.info("测试、、、、、、、");
    }

    @Test
    public void redisValueCache(){
        String key = "hu:cache:value";
        redisTemplate.opsForValue().set(key,"value",5, TimeUnit.MINUTES);
        Object o = redisTemplate.opsForValue().get(key);
        log.info("查询缓存key:"+ key +"的值：" + o);
    }


    @Test
    public void redisListCache(){
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("小明");
        String key = "hu:cache:list";
        redisTemplate.opsForList().leftPushAll(key, list,1,TimeUnit.MINUTES);
        Object o = redisTemplate.opsForList().range(key,0,-1);
        log.info("查询缓存key:"+ key +"的值：" + o);
    }


    @Test
    public void redisHashCache(){
        User user = new User();
        user.setId("123456");
        user.setName("张三");
        user.setPassWord("111111");
        String key = "hu:cache:hash";
        String hashKey = "user";

        Object cacheUser = redisTemplate.opsForHash().get(key, hashKey);

        if (cacheUser == null) {
            redisTemplate.opsForHash().put(key, hashKey,user);
        }

        log.info("查询缓存key:"+ key +"的值：" + cacheUser);
    }


    @Test
    public void redisSetCache(){
        HashSet<String> set = new HashSet<>();
        set.add("张三");
        set.add("李四");
        String key = "hu:cache:set";
        redisTemplate.opsForSet().add(key, set);
        Object pop = redisTemplate.opsForSet().pop(key);
        log.info("查询缓存key:"+ key +"弹出的值：" + pop);

        Object members = redisTemplate.opsForSet().members(key);
        log.info("查询缓存key:"+ key +"剩余的值：" + members);
    }


}
