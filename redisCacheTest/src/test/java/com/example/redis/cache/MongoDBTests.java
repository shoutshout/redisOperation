package com.example.redis.cache;


import com.example.redis.cache.entity.TestColl;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoDBTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void addMongoDB(){
        List<TestColl> collects = new ArrayList<>();
        collects.add(new TestColl("123456","张三",11,new BigDecimal(12)));
        collects.add(new TestColl("1234456","李四",10,new BigDecimal(5)));
        collects.add(new TestColl("123458","呵呵",10,new BigDecimal(15)));

        mongoTemplate.insert(collects, TestColl.class);
        log.info("添加成功 !" + collects);

    }

    @Test
    public void findAllMongoDB(){
        List<TestColl> all = mongoTemplate.findAll(TestColl.class);
        log.info("查询集合：{}", all);
    }


    @Test
    public void findOneMongoDB(){

        TestColl coll = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("name").is("张三")), TestColl.class);
        log.info("指定查询集合：{}", coll);
    }

    @Test
    public void likeFindMongoDB(){
        Pattern pattern = Pattern.compile("^.*456$",Pattern.CASE_INSENSITIVE);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").regex(pattern));

        List<TestColl> collects = mongoTemplate.find(query, TestColl.class);
        log.info("模糊查询：{}", collects);
    }

    @Test
    public void updateMongoDB(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("呵呵"));
        Update update = Update.update("age", "16");
        UpdateResult upsert = mongoTemplate.upsert(query, update, TestColl.class);

        log.info("更新：{}", upsert);

    }

    @Test
    public void deleteMongoDB(){

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("李四"));

        DeleteResult remove = mongoTemplate.remove(query, TestColl.class);
        log.info("删除：{}", remove);
    }




}
