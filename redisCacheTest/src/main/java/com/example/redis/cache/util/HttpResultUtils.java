package com.example.redis.cache.util;

import cn.hutool.json.JSONUtil;
import com.example.redis.cache.entity.HttpResult;
import com.example.redis.cache.enums.ResultEnum;
import lombok.experimental.UtilityClass;
import org.springframework.beans.MethodInvocationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * <p>
 * HttpResultUtils
 * <p>
 *
 * @author hucj
 * @date 2020-05-21 16:26:22
 **/
@UtilityClass
public class HttpResultUtils {


    /**
     * 返回成功
     *
     * @param object: 返回对象
     * @return HttpResult
     */
    public HttpResult success(Object object) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(ResultEnum.SUCCESS_CODE.getCode());
        HttpResult.setMsg(ResultEnum.SUCCESS_CODE.getMessage());
        HttpResult.setData(object);
        return HttpResult;
    }

    /**
     * 返回成功
     *
     * @param code   : 返回成功状态码
     * @param object : 返回对象
     * @return
     */
    public HttpResult success(Integer code, Object object) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(code);
        HttpResult.setMsg(ResultEnum.SUCCESS_CODE.getMessage());
        HttpResult.setData(object);
        return HttpResult;
    }

    /**
     * 返回成功
     *
     * @param code: 返回状态吗
     * @param msg
     * @return
     */
    public HttpResult success(Integer code, String msg) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(code);
        HttpResult.setMsg(msg);
        HttpResult.setData(null);
        return HttpResult;
    }

    /**
     * 返回成功
     *
     * @param code:  返回状态吗
     * @param msg
     * @param object
     * @return
     */
    public HttpResult success(Integer code, String msg, Object object) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(code);
        HttpResult.setMsg(msg);
        HttpResult.setData(object);
        return HttpResult;
    }

    /**
     * 返回成功
     *
     * @return HttpResult
     */
    public HttpResult success() {
        return success(null);
    }

    /**
     * 返回错误
     *
     * @param code: 错误码
     * @param msg:  错误信息
     * @return
     */
    public HttpResult error(Integer code, String msg) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(code);
        HttpResult.setMsg(msg);
        return HttpResult;
    }

    /**
     * 异常拦截器返回错误
     *
     * @param msg: 错误信息
     * @return
     */
    public HttpResult error(String msg) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(ResultEnum.ERROR_CODE.getCode());
        HttpResult.setMsg(msg);
        return HttpResult;
    }

    /**
     * 封装response返回异常json格式
     *
     * @param response : response返回
     * @throws IOException
     */
    public void responseError(HttpServletResponse response, Integer code, String msg) throws IOException {
        if (Objects.isNull(response)) {
            return;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        HttpResult httpResult = HttpResultUtils.error(code, msg);
        String jsonStr = JSONUtil.toJsonStr(httpResult);
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }

}

