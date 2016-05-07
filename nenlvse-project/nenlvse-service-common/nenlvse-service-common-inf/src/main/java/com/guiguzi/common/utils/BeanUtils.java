package com.guiguzi.common.utils;

/**
 * Created by kqy on 2015/5/31.
 */
public class BeanUtils {

    public static <T> T copyProperties(Object source,Class<T> targetClazz){
        if(source == null) return null;

        try {

            T targetObj = targetClazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, targetObj);

            return targetObj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }



}
