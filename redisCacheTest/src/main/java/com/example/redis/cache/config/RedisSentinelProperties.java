package com.example.redis.cache.config;

import lombok.Data;

/**
 *<P>
 *  RedisSentinelProperties 哨兵配置
 *<P>
 *
 *@author hucj
 *@date 2020-05-20
 **/
@Data
public class RedisSentinelProperties {

    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite;

    /**
     *
     */
    private int failMax;
}
