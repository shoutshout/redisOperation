package com.example.redis.cache.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.redis.cache.entity.HttpResult;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * HttpUtil
 * <p>
 *
 * @author hucj
 * @date 2020-05-21 16:21:46
 **/
@UtilityClass
public class HttpUtil {

    /**
     * 获取HttpServletRequest对象
     *
     * @return
     */
    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpServletResponse对象
     *
     * @return
     */
    public HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }


    /**
     * 输出信息到浏览器
     *
     * @param response : 响应结果
     * @param data     : 输出响应数据
     * @throws IOException
     */
    public void writeSuccess(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpResult httpResult = HttpResultUtils.success(data);
        JSON resultJSONString = JSONUtil.parse(httpResult);
        response.getWriter().print(resultJSONString);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 输出信息到浏览器
     *
     * @param response : 响应结果
     * @param errorMsg : 输出响应数据
     * @throws IOException
     */
    public void writeError(HttpServletResponse response, String errorMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpResult httpResult = HttpResultUtils.error(errorMsg);
        JSON resultJSONString = JSONUtil.parse(httpResult);
        response.getWriter().print(resultJSONString);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
