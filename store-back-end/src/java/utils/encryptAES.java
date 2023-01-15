package com.xzr.glassfishDemo.utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.lang.model.element.NestingKind;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

/**
 * AES
 */
public class encryptAES {
    private static final String default_key = "U0CgGQFpJt2ZpGuR";
    private static final String default_iv = "gzAbpbVaSRA0zLZV";
    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();
    static final String charset = "utf-8";
    static final String AES = "AES";



    public static String encodeBase64(String content){
        String encode = encrypt(content);//AES
        if(encode == null) return null;
        try {
            String s = encoder.encodeToString(encode.getBytes(charset));
            return s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeBase64(String content){
        try {
            String s = new String(decoder.decode(content), charset);
            String decode = decrypt(s);
            return decode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String a = "Jane";
        String b = "222";
        String s1 = encodeBase64(a);
        String s2 = encodeBase64(b);
        System.out.println(a+":"+s1);
//        String s1 = decodeBase64(s);
        System.out.println(b+":"+s2);
        System.out.println(decodeBase64(s1));
        System.out.println(decodeBase64(s2));
        System.out.println(decodeBase64("eGViRHZYUzZwSkNWeVJsNXNuWHE2dz09"));
    }

    //AE
    public static String encrypt(String data)  {

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(default_key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(default_iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new sun.misc.BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String decrypt(String data)  {
        try {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            SecretKeySpec keyspec = new SecretKeySpec(default_key.getBytes("UTF-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(default_iv.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            originalString = originalString.replaceAll("\\u0000", "");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

