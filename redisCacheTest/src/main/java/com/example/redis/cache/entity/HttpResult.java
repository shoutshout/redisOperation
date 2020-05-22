package com.example.redis.cache.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * HttpResult
 * <p>
 *
 * @author hucj
 * @date 2020-05-21 16:24:38
 **/
@Data
public class HttpResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回结果报文 状态码 : 返回标记：成功标记=0，失败标记非0
     */
//    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记非0", name = "code", notes = "返回标记：成功标记=0，失败标记非0", example = "0", required = true, hidden = false)
    private Integer code;

    /**
     * 返回结果信息 : code为0的时候为: 请求成功、code为非0的时候为: 请求失败
     */
//    @ApiModelProperty(value = "code为0的时候请求成功、code为非0的时候请求失败", name = "msg", notes = "code为0的时候请求成功、code为非0的时候请求失败", example = "请求成功", required = true, hidden = false)
    private String msg;

    /**
     * 返回结果数据
     */
//    @ApiModelProperty(value = "返回结果数据", name = "data", notes = "返回结果数据", example = "true", required = true, hidden = false)
    private Object data;

}
