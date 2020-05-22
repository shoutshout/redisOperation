package com.example.redis.cache.exception;

import com.example.redis.cache.enums.ResultEnum;

/**
 * <p>
 * QingDuoCheckException
 * <p>
 *
 * @author hucj
 * @date 2020-05-22 16:18:40
 **/
public class QingDuoCheckException extends RuntimeException{

    private Integer code;
    private String openid;

    public QingDuoCheckException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public QingDuoCheckException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public QingDuoCheckException(Integer code, String openid,String message) {
        super(message);
        this.code = code;
        this.openid = openid;
    }




    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
