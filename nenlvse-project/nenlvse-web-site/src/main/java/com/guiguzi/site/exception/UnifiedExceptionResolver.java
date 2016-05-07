package com.guiguzi.site.exception;

import com.alibaba.fastjson.JSON;
import com.guiguzi.common.exception.BizException;
import com.guiguzi.site.utils.ResponseCode;
import com.guiguzi.site.vo.BaseResponse;
import com.guiguzi.framework.web.exception.NoLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 */
@Component
public class UnifiedExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(UnifiedExceptionResolver.class);
    private static final String callbackKey = "callback";
    private boolean returnJson = true;
    private String defaultMessage = "服务器繁忙，请稍后再试!";

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {

        if (returnJson) {//json返回

            String callback = request.getParameter(callbackKey);
            if(ex instanceof NoLoginException){
                logger.warn(ex.getMessage(), ex);
                writeJson(response, callback,
                        BaseResponse.newInstance(String.valueOf(ResponseCode.NO_LOGIN.getCode()),
                                ResponseCode.NO_LOGIN.getMsg()));

            }else if (ex instanceof BizException) {
                logger.warn(ex.getMessage(), ex);
                writeJson(response, callback,
                        BaseResponse.newInstance(String.valueOf(ResponseCode.FAILURE.getCode()),
                                ex.getMessage()));

            }else if (ex instanceof MaxUploadSizeExceededException) {
                writeScript(response, "alert('上传文件过大')");
                response.setStatus(413);

            }else{
                logger.error(ex.getMessage(), ex);
                writeJson(response, callback,
                        BaseResponse.newInstance(String.valueOf(ResponseCode.EXCEPTION.getCode()),
                                ResponseCode.EXCEPTION.getMsg()));
            }


            return new ModelAndView();
        } else {//普通返回

            return super.doResolveException(request, response, handler, ex);
        }

    }

    /**
     * json 结果返回
     *
     * @param response
     * @param callback
     * @param obj
     */
    private void writeJson(HttpServletResponse response, String callback, BaseResponse obj) {
        try {
            String json = JSON.toJSONString(obj);
            if (callback == null) {
                response.getWriter().write(json);
            } else {
                response.getWriter().write(callback + "(" + json + ")");
            }
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
        } catch (Exception e) {
           logger.warn(e.getMessage());
        }
    }

    /**
     * 脚本信息
     *
     * @param response
     */
    private void writeScript(HttpServletResponse response, String msg) {
        try {
            response.getWriter().write("<script>" + msg + "</script>");
            response.setContentType("text/javascript;charset=UTF-8");
            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文本信息
     *
     * @param response
     */
    private void writeString(HttpServletResponse response, String msg) {
        try {
            response.getWriter().write(msg);
            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReturnJson() {
        return returnJson;
    }

    public void setReturnJson(boolean returnJson) {
        this.returnJson = returnJson;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
