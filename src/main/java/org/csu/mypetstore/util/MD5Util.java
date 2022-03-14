package org.csu.mypetstore.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



/**
 * @Description
 * @Date 2022/3/14 7:49 下午
 * @Author RessMatthew
 * @Version 1.0
 **/

@Component
public class MD5Util {

    //设置salt
    private static final String salt="1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * @Description //TODO 第一次MD5加密，前端到服务器前
     **/
    public static String inputPassToFromPass(String inputPass){
        String str = salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4); //相当于盐为12c3
        return md5(str);
    }

    /**
     * @Description //TODO 第二次MD5加密，后端到数据库前
     **/
    public static String formPassToDBPass(String formPass,String salt){
        String str = salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);   //第一次MD5加密
        String dbPass = formPassToDBPass(fromPass,salt);    //第二次MD5加密
        return dbPass;
    }

    /**
     * @Description //TODO 测试MD5加密
     **/
  /*  public static void main(String[] args) {

        System.out.println(inputPassToFromPass("123456"));
        //inputPass：123456 到 FromPass：ce21b747de5af71ab5c2e20ff0a60eea
        System.out.println(formPassToDBPass("ce21b747de5af71ab5c2e20ff0a60eea","1a2b3c4d"));
        //FromPass：ce21b747de5af71ab5c2e20ff0a60eea 到 DBPass 0687f9701bca74827fcefcd7e743d179


        System.out.println(inputPassToDBPass("123456","1a2b3c4d")); //等价于执行了上两行
    }*/
}
