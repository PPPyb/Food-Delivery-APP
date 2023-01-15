package com.xzr.glassfishDemo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class GenerateToken {
    /*
     *
     */
    private GenerateToken()
    {
    }

    private static final GenerateToken instance = new GenerateToken();

    /**
     * 返回类的对象
     *
     * @return
     */
    public static GenerateToken getInstance()
    {
        return instance;
    }

    /**
     * 生成Token Token：Nv6RRuGEVvmGjB+jimI/gw==
     *
     * @return
     */
    public String makeToken()
    { // checkException
        // 7346734837483 834u938493493849384 43434384
        String token = UUID.randomUUID().toString().replaceAll("-", "") + "";
        //
        try
        {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());

            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
