package com.example.redis.cache.util;

import lombok.experimental.UtilityClass;

/**
 * <p>
 * LuaScriptUtils
 * <p>
 *
 * @author hucj
 * @date 2020-05-22 16:04:30
 **/
@UtilityClass
public class LuaScriptUtils {

    /**
     * 构建lua脚本
     *
     * @return
     */
    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }
}
