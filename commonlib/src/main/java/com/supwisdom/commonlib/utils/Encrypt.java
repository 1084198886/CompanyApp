package com.supwisdom.commonlib.utils;

import android.util.Log;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zzq
 * @date 2018/7/23
 * @desc algorithm
 */
public class Encrypt {
    public static String HASH256(String text) {
        return algorithm2(text, "SHA-256", true);
    }

    public static String HASH256(InputStream fis) {
        return algorithm(fis, "SHA-256");
    }

    public static String HASH256(InputStream fis, String extData) {
        return algorithm(fis, "SHA-256", extData);
    }

    public static String HASH512(String text) {
        return algorithm2(text, "SHA-512", true);
    }

    public static String MD5(String text) {
        return algorithm2(text, "MD5", false);
    }

    public static String HMACSHA1(String data, String key) {
        try {
            String algorithm = "HmacSHA1";
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), algorithm);
            mac.init(spec);
            byte[] byteHMAC = mac.doFinal(data.getBytes());
            return CommonUtil.getHexStringByBytes(byteHMAC);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignore) {
            ignore.printStackTrace();
        }
        return null;
    }

    private static String algorithm2(String text, String algorithm, boolean isUpperCase) {
        if (text != null && text.length() > 0) {
            try {
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance(algorithm);
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(text.getBytes("UTF-8"));
                //将得到的字节数组变成字符串返回
                String resultString = CommonUtil.getHexStringByBytes(results);
                if (isUpperCase) {
                    return resultString.toUpperCase();
                } else {
                    return resultString.toLowerCase();
                }
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static String algorithm(InputStream fis, String algorithm) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance(algorithm);

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] results = md.digest();
            return CommonUtil.getHexStringByBytes(results);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String algorithm(InputStream fis, String algorithm, String extData) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance(algorithm);

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            md.update(extData.getBytes("UTF-8"));

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] results = md.digest();
            return CommonUtil.getHexStringByBytes(results);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
