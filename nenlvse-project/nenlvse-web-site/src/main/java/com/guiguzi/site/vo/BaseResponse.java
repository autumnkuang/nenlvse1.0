/*
 * Copyright (C), 2012-2015, 上海好屋网信息技术有限公司
 * FileName: com.guiguzi.vo.BaseResponse
 */
package com.guiguzi.site.vo;


import com.guiguzi.common.utils.CommonConstantUtil;
import com.guiguzi.framework.web.session.ShareSession;
import com.guiguzi.framework.web.util.WebUtils;

import javax.servlet.http.HttpSession;

/**
 * Author: kuangqiuyong
 * Email: kuangqiuyong@haowu.com
 * Date: 2015/4/15 9:24
 * Description:
 * History:
 * <Author>      <Time>    <version>    <desc>O
 * kuangqiuyong   9:24    1.0          Create
 */
public class BaseResponse {
    private String status;
    private Object data;
    private String detail;
    private String token;

    public BaseResponse() {
        HttpSession session = WebUtils.getSession();
        this.token = session == null ? "" : session.getId();
    }

    public BaseResponse(String status) {
        this();
        this.status = status;
    }

    public BaseResponse(String status, Object data) {
        this();
        this.status = status;
        this.setData(data);
    }

    private BaseResponse(String status, Object data, String detail) {
        this();
        this.status = status;
        this.detail = detail;
        this.setData(data);
    }

    public static BaseResponse success() {
        return newInstance(CommonConstantUtil.CommonCode.SUCCESS_CODE, "", CommonConstantUtil.CommonMessage.SUCCESS_MESSAGE);
    }

    public static BaseResponse success(Object data) {
        return newInstance(CommonConstantUtil.CommonCode.SUCCESS_CODE, data, CommonConstantUtil.CommonMessage.SUCCESS_MESSAGE);
    }

    public static BaseResponse error() {
        return newInstance(CommonConstantUtil.CommonCode.ERROR_CODE, "", CommonConstantUtil.CommonMessage.ERROR_MESSAGE);
    }

    public static BaseResponse error(String msg) {
        return newInstance(CommonConstantUtil.CommonCode.ERROR_CODE, "", msg);
    }

    public static BaseResponse paramError() {
        return newInstance(CommonConstantUtil.CommonCode.PARAM_ERROR_CODE, "", CommonConstantUtil.CommonMessage.PARAM_ERROR_MESSAGE);
    }

    public static BaseResponse paramError(String msg) {
        return newInstance(CommonConstantUtil.CommonCode.PARAM_ERROR_CODE, "", msg);
    }


    public static BaseResponse bussinessError(String msg) {
        return newInstance(CommonConstantUtil.CommonCode.BUSSINESS_ERROR_CODE, "", msg);
    }

    public static BaseResponse bussinessError(Object data, String msg) {
        return newInstance(CommonConstantUtil.CommonCode.BUSSINESS_ERROR_CODE, data, msg);
    }

    public static BaseResponse newInstance(String status) {
        return new BaseResponse(status);
    }

    public static BaseResponse newInstance(String status,String detail) {
        return new BaseResponse(status,"",detail);
    }

    public static BaseResponse newInstance(String status, Object data) {
        return new BaseResponse(status, data);
    }

    public static BaseResponse newInstance(String status, Object data, String detail) {
        return new BaseResponse(status, data, detail);
    }


    public String getStatus() {
        return status;
    }

    public BaseResponse setStatus(String status) {
        if (null == status)
            this.status = "";
        else
            this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    /**
     * 设置 响应数据
     * 列表类型数据(list)会自动转换为分页类型的数据
     *
     * @param data
     * @return
     */
    public BaseResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public BaseResponse setDetail(String message) {
        if (null == message)
            this.detail = "";
        else
            this.detail = message;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
