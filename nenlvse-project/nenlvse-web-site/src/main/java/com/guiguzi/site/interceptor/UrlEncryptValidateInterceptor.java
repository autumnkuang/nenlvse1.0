/*
 * Copyright (C), 2012-2014, 上海好屋网信息技术有限公司
 * FileName: com.hoss.society.filter.CommunityRequestInterceptor
 */
package com.guiguzi.site.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.guiguzi.common.utils.CommonConstantUtil;
import com.guiguzi.common.utils.DigestAuthenticationUtil;
import com.guiguzi.site.vo.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Author: kuangqiuyong
 * Email: kuangqiuyong@haowu.com
 * Date: 2014/9/19 16:23
 * Description:
 * History:
 * <Author>      <Time>    <version>    <desc>
 * kuangqiuyong   16:23    1.0          Create
 */
public class UrlEncryptValidateInterceptor {
    private final Logger log = Logger.getLogger(UrlEncryptValidateInterceptor.class);


    /**
     * 校验URL请求
     *    1)必要参数是否存在
     *    2)是否为有效请求，相同的请求参数只能连续请求一次
     *    3)摘要校验是否正确
     * @param response
     * @param type
     */
//    @Override
//    protected void writeErrorResult(HttpServletResponse response, ResultType type) {
//        if(ResultType.PARAME_RROR.equals(type)){
//            this.writeJson(response,BaseResponse.paramError());
//
//        }else if(ResultType.INVALID.equals(type)){
//            this.writeJson(response,BaseResponse.newInstance(CommonConstantUtil.CommonCode.REQUEST_INVALID_CODE,
//                    CommonConstantUtil.CommonMessage.REQUEST_INVALID_MESSAGE));
//
//        }else if(ResultType.FORBIDDEN.equals(type)){
//            this.writeJson(response,BaseResponse.newInstance(CommonConstantUtil.CommonCode.REQUEST_FORBIDDEN_CODE,
//                    CommonConstantUtil.CommonMessage.REQUEST_FORBIDDEN_MESSAGE));
//        }
//    }

    /**
     * 验证摘要
     * @param paramKeys
     * @param request
     * @return
     */
//    @Override
//    protected boolean validateDigest(Set<String> paramKeys,HttpServletRequest request) throws UnsupportedEncodingException {
//
//        String digest = request.getParameter(this.getDigestKey());
//        Map<String,String> params = new TreeMap<String,String>();
//        Iterator<String> iterator = paramKeys.iterator();
//        while(iterator.hasNext()){
//            String key = iterator.next();
//            if(this.getDigestKey().equals(key) ){
//                continue;
//            }
//
//            String keyValue  = request.getParameter(key);
//            if(!StringUtils.hasText(keyValue)){
//                params.put(key,"");
//            }else{
//                params.put(key, Strings.nullToEmpty(keyValue));
//            }
//        }
//        return DigestAuthenticationUtil.validate(params,this.getSecret(), digest);
//    }


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
    /**
     * jsop 结果返回
     * @param response
     * @param obj
     */
    private void writeJsonp(HttpServletRequest request,HttpServletResponse response,Object obj){
        try{
            String json= JSON.toJSONString(obj);
            response.setContentType("application/javascript;charset=UTF-8");
            json = request.getParameter("callback") + "(" + json + ");";
            response.getWriter().write(json);
            response.setStatus(200);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
