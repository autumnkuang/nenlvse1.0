package com.guiguzi.common.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.guiguzi.common.enums.VerificationCodeType;

import java.util.Random;
import java.util.Set;

/*
 * Description: 验证码生成工具类
 */
public class VerificationCodeUtils {
    public static long timeout = 90 * 1000;

    private static final char [] number={'0','1','2','3','4','5','6','7','8','9'};
    private static final char [] letter=
            {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final char [] blend=
            {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    //验证码长度
    public static final Integer DEFAULT_CODE_LENGTH= 6;

    public static String generateCodeByStrategy(VerificationCodeType strategy){

        return generateCodeByStrategy(strategy,DEFAULT_CODE_LENGTH);
    }

    public static String generateCodeByStrategy(VerificationCodeType strategy,int length){
        //每次使用新种子
        Random random = new Random();
        char [] repertory = number;
        switch ( strategy ){
            case number:{ repertory = number;}break;
            case letter:{ repertory = letter; }break;
            case blend:{ repertory = blend; }break;
        }
        char [] code = new char[length];
        for(int i=0;i<length;i++){
            code[i] = repertory[random.nextInt(repertory.length)];
        }
        return new String(code);
    }

    public static void main(String ... args){
        int size=100000;
        Set<String> set= Sets.newHashSet();
        while(set.size()<=size){
            set.add( VerificationCodeUtils.generateCodeByStrategy(VerificationCodeType.blend, 6));
        }
        System.err.println(Joiner.on(",").join(set.toArray()));
    }
}
