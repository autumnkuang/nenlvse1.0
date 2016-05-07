/*
 * Copyright (C), 2012-2015, 上海好屋网信息技术有限公司
 * FileName: com.guiguzi.site.filter.LoginValidateInterceptor
 */
package com.guiguzi.site.interceptor;

import com.alibaba.fastjson.JSON;
import com.guiguzi.common.utils.CommonConstantUtil;
import com.guiguzi.site.utils.UserHelper;
import com.guiguzi.site.vo.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: kuangqiuyong
 * Email: kuangqiuyong@haowu.com
 * Date: 2015/4/30 9:13
 * Description:
 * History:
 * <Author>      <Time>    <version>    <desc>
 * kuangqiuyong   9:13    1.0          Create
 */
public class LoginValidateInterceptor implements HandlerInterceptor {
    private static final Logger log = Logger.getLogger(LoginValidateInterceptor.class);
    private String excludeUris;  //排除不需要拦截的URI

    public void setExcludeUris(String excludeUris) {
        this.excludeUris = excludeUris;
    }

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(StringUtils.hasText(excludeUris)){
            String uri = request.getRequestURI();
            if(uri.contains("/")){
                uri = uri.substring(uri.lastIndexOf("/")+1);
            }

            if(excludeUris.contains(uri)) return  true;
        }

        /**
         * 判断用户是否登录
         */
        if(UserHelper.hasLogin()){
            return true;
        }

        this.writeJson(response, BaseResponse.newInstance(CommonConstantUtil.CommonCode.NO_LOGIN_CODE,
                                                          CommonConstantUtil.CommonMessage.NO_LOGIN_MESSAGE));
        return false;
    }

    /**
     * 在业务处理器处理请求执行完成后,
     * 生成视图之前执行的动作
     *
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     *  在DispatcherServlet完全处理完请求后被调用
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * json 结果返回
     * @param response
     * @param obj
     */
    private void writeJson(HttpServletResponse response,Object obj){
        try{
            String json= JSON.toJSONString(obj);
            response.getWriter().write(json);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
