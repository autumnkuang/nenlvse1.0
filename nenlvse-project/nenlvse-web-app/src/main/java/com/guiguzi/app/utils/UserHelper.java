package com.guiguzi.app.utils;

import com.guiguzi.common.dto.UserInfo;
import com.guiguzi.framework.web.exception.NoLoginException;
import com.guiguzi.framework.web.util.DefaultCurrentUserImpl;
import com.guiguzi.framework.web.util.WebUtils;

/**
 * Created by Administrator on 2015/4/19.
 */
public class UserHelper {

    //用户ID
    public static final String USER_ID = DefaultCurrentUserImpl.idKey;
    //用户信息
    public static final String USER_INFO = "userInfo";


    /**
     * 设置用户信息
     * @param userInfo
     */
    public static void setCurrentUserInfo(UserInfo userInfo){

        WebUtils.getSession().setAttribute(USER_ID,userInfo.getUserId());
        WebUtils.getSession().setAttribute(USER_INFO,userInfo);
        new DefaultCurrentUserImpl();
    }


    /**
     * 获取用户信息
     * @return
     */
    public static UserInfo getCurrentUserInfo(){
        if(hasLogin()){
           return (UserInfo)WebUtils.getSessionAttribute(USER_INFO);
        }

        throw new NoLoginException();
    }

    /**
     * 获取当前用户ID
     * @return
     */
    public static Long getCurrentUserId(){
        if(hasLogin()){
            return (Long)WebUtils.getSessionAttribute(USER_ID);
        }

        throw new NoLoginException();
    }

    /**
     * 判断登录状态
     * @return
     */
    public static boolean hasLogin(){
        return WebUtils.getSession().getAttribute(USER_ID) != null;
    }

}
