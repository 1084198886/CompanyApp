package com.supwisdom.commonlib.utils;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
如果我们要使用3DES加密，需要以下几个步骤
①传入共同约定的密钥（keyBytes）以及算法（Algorithm），来构建SecretKey密钥对象
SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
②根据算法实例化Cipher对象。它负责加密/解密
Cipher c1 = Cipher.getInstance(Algorithm);
③传入加密/解密模式以及SecretKey密钥对象，实例化Cipher对象
c1.init(Cipher.ENCRYPT_MODE, deskey);
④传入字节数组，调用Cipher.doFinal()方法，实现加密/解密，并返回一个byte字节数组
c1.doFinal(src);
*/
public class ThreeDESUtil {
    // 定义加密算法
    private static final String TAG = "ThreeDESUtil";
    private static final String Algorithm = "DESede";


    // 加密 src为源数据的字节数组
    public static byte[] encryptMode(byte[] src, String secretkey) {

        try {// 生成密钥
            SecretKey deskey = new SecretKeySpec(build3Deskey(secretkey), Algorithm);
            // 实例化cipher
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密函数
    public static byte[] decryptMode(byte[] src, String secretkey) {
        SecretKey deskey;
        try {
           Log.i(TAG,"secretkey"+secretkey);
            deskey = new SecretKeySpec(build3Deskey(secretkey), Algorithm);
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 根据字符串生成密钥24位的字节数组
    public static byte[] build3Deskey(String keyStr) throws Exception {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("utf-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);

        } else {
            System.arraycopy(temp, 0, key, 0, key.length);

        }
        return key;
    }
}