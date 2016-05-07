package com.guiguzi.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by kqy on 2015/10/19.
 */
public class Md5Utils {

        /**
         * 签名字符串
         * @param texts 需要签名的字符串
         * @return 签名结果
         */
        public static String sign(String ... texts) {
            StringBuffer stringBuffer = new StringBuffer();
            for(String text : texts){
                stringBuffer.append(text);
            }
            return DigestUtils.md5Hex(getContentBytes(stringBuffer.toString(), "utf-8"));
        }


        /**
         * @param content
         * @param charset
         * @return
         * @throws java.security.SignatureException
         * @throws java.io.UnsupportedEncodingException
         */
        private static byte[] getContentBytes(String content, String charset) {
            if (charset == null || "".equals(charset)) {
                return content.getBytes();
            }
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
        }


    public static void main(String[] args) {
        System.out.println(sign("111111","111111"));
    }
}
