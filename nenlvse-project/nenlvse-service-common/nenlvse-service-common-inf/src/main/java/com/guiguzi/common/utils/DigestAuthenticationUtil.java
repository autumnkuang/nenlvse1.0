package com.guiguzi.common.utils;

import com.google.common.base.Joiner;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by kqy
 */
public class DigestAuthenticationUtil {
    private DigestAuthenticationUtil() {
    }

    /**
     * 基于Java服务端参数摘要校验
     *
     * @param params
     * @param digest
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static boolean validate(Map<String,String> params,String secret, String digest) {

        try {
            boolean digestIsOk = getDigest(params,secret).equals(digest);
            return digestIsOk;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 基于Java服务端响应摘要生成
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static String getDigest(Map<String,String> params,String secret) throws Exception {
        StringBuffer sb = new StringBuffer();

        Set<String> treeSet = new TreeSet<String>(params.keySet());
        treeSet.addAll(params.values());
        Joiner.on("").appendTo(sb,treeSet);

        return getDigest(sb.toString(),secret);
    }

    /**
     * 基于Java服务端响应摘要生成
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String getDigest(String content,String secret) throws Exception {
        content += secret;

        String md5Str = encodeHexString(getMD5(string2Base64(content)));
        return md5Str;
    }

    public static String encodeHexString(final byte[] data) {
        return encodeHex(data);
    }




    /**
     * 小写字母变大写  数字加1
     * @param str
     * @return
     */
    public static String exChange(String str){
        StringBuffer sb = new StringBuffer();
        if(str!=null){
            for(int i=0;i<str.length();i++){
                char c = str.charAt(i);
                if(Character.isUpperCase(c)){
                    sb.append(Character.toLowerCase(c));
                }else if(Character.isLowerCase(c)){
                    sb.append(Character.toUpperCase(c));
                }else{
                    try {
                        int ints=Integer.parseInt(c+"");
                        ints=ints+1;
                        sb.append(ints);
                    }catch (Exception e){
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }



    public static String string2Base64(String s) throws UnsupportedEncodingException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] b = s.getBytes("utf8");
        return base64Encoder.encode(b);
    }

    public static String byte2Base64(byte[] bytes) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }

    public static byte[] base642Byte(String base64) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return base64Decoder.decodeBuffer(base64);
    }

    static String reverStr(String s){
        int low = 0;
        int high = s.length()-1;
        char[] temp = new char[s.length()];
        while(low <= high){
            temp[low] = s.charAt(high);
            temp[high] = s.charAt(low);
            low++;
            high--;
        }
        return String.valueOf(temp);
    }
    /**
     *
     * @param s 要移位的字符串
     * @param digits  向左移动的位数
     */
    static String moveLeft(String s,int digits){
        if(digits==0){
            return s;
        }else if(digits>0){
            digits = digits % s.length();
            String left = reverStr(s.substring(0, digits));
            String right = reverStr(s.substring(digits));
            String result = reverStr(left+right);
            return result;
        }else{
            //此时变为向右移digits位,即向左移动s.length() - digits位
            digits = -digits;
            digits = digits % s.length();
            return moveLeft(s,s.length()-digits);
        }
    }

    public static byte[] getMD5(String content) throws UnsupportedEncodingException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(content.getBytes("utf8"));
            return bytes;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getSHA1(String content) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = md.digest(content.getBytes("utf8"));
        return bytes;
    }

    /**
     * byte数组转hex字符串<br/>
     * 一个byte转为2个hex字符
     *
     * @param src
     * @return
     */
    protected static String encodeHex(final byte[] src) {
        final int l = src.length;
        final char[] out = new char[l << 1];
        final char[] toDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & src[i]) >>> 4];
            out[j++] = toDigits[0x0F & src[i]];
        }
        return new String(out);
    }

    /**
     * hex字符串转byte数组<br/>
     * 2个hex转为一个byte
     *
     * @param src
     * @return
     */
    public static byte[] hex2Bytes(String src) {
        byte[] res = new byte[src.length() / 2];
        char[] chs = src.toCharArray();
        for (int i = 0, c = 0; i < chs.length; i += 2, c++) {
            res[c] = (byte) (Integer.parseInt(new String(chs, i, 2), 16));
        }

        return res;
    }

    public static void main(String[] args) throws Exception {

        String secret = "999a7a5593324cdb889d9d679d1c5745";

        Map<String,String> map = new TreeMap<String, String>();

        map.put("deviceId","1234567890");          //设备ID, 必须
        map.put("appKey","huihebus");              //APP标识，固定为: huihebus , 必须
        map.put("version","V1.0.0");               //接口版本号,现为:V1.0.0 , 必须
        map.put("timestamp",String.valueOf(System.currentTimeMillis())); //随机时间戳,必须
        map.put("nonce",String.valueOf(new Random().nextInt(1000000))); //随机数,必须


        /****业务参数 开始*****/
        map.put("token","8C8791A8D0713040D966BB8D19CE152A");
        map.put("userName","kqy");
        map.put("passwd","123456");
        /****业务参数 结束*****/

        //签名
        String digest = DigestAuthenticationUtil.getDigest(map,secret);
        map.put("digest",digest);

        Set<String> set  = new HashSet<String>();
        Set<String> keyset = map.keySet();
        Iterator<String> iterator = keyset.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            set.add(key+"="+map.get(key));
        }

        String paramStr = Joiner.on("&").join(set);

        //生成的签名请求参数
        System.out.println("?"+paramStr);

        //校验签名
        map.remove("digest");
        System.out.println(DigestAuthenticationUtil.validate(map,secret,digest));
    }

}
