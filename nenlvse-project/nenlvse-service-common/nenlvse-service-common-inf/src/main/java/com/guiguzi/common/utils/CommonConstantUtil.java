/*
 * Copyright (C), 2012-2015, 上海好屋网信息技术有限公司
 * FileName: com.guiguzi.utils.CommonConstantUtil
 */
package com.guiguzi.common.utils;

/**
 * Author: kuangqiuyong
 * Email: kuangqiuyong@haowu.com
 * Date: 2015/4/15 9:27
 * Description:
 * History:
 * <Author>      <Time>    <version>    <desc>
 * kuangqiuyong   9:27    1.0          Create
 */
public class CommonConstantUtil {

    //通用的消息
    public abstract class CommonMessage {
        public static final String PARAM_ERROR_MESSAGE = "请求参数传递错误!";//参数传递错误
        public static final String ERROR_MESSAGE = "请求数据出错!";//获取数据出错!
        public static final String SUCCESS_MESSAGE = "请求数据成功!";//获取数据失败
        public static final String NO_LOGIN_MESSAGE = "未登录!";    //未登录
        public static final String BUSSINESS_ERROR_MESSAGE = "服务器内部错误!"; //业务异常

        public static final String REQUEST_FORBIDDEN_MESSAGE = "非法请求";  //不允许请求
        public static final String REQUEST_INVALID_MESSAGE = "无效请求";  //无效请求
    }

    //通用的状态码
    public abstract class CommonCode {
        public static final String PARAM_ERROR_CODE = "-1";//请求参数传递错误状态码
        public static final String ERROR_CODE = "0"; //请求服务器数据出错状态码
        public static final String SUCCESS_CODE = "1";//请求服务器数据成功状态码
        public static final String NO_LOGIN_CODE = "-99";//未登录状态码
        public static final String BUSSINESS_ERROR_CODE = "-101"; //业务异常

        public static final String REQUEST_INVALID_CODE = "401";  //无效请求
        public static final String REQUEST_FORBIDDEN_CODE = "403";  //不允许请求
    }

}
