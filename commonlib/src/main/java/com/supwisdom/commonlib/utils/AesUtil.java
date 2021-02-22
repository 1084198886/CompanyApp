package com.supwisdom.commonlib.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by shuwei on 2018/12/3.
 * https://blog.csdn.net/skiof007/article/details/77993568
 */
public class AesUtil {
    private static byte[] iv = new byte[]{0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF};

    // 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "Crypto");
//        random.setSeed(sKey.getBytes());
//        kgen.init(192, random);
//        SecretKey secretKey = kgen.generateKey();
//        byte[] enCodeFormat = secretKey.getEncoded();
//        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
        SecretKeySpec keySpec = new SecretKeySpec(sKey.getBytes(), "AES");

//        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");//"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        String ecodeStr= JavaBase64.encode(encrypted);
        LogUtil.d("AesUtil","sSrc:"+sSrc+",ecodeStr:"+ecodeStr);
        return ecodeStr;
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        // 判断Key是否正确
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        //此处解决mac，linux报错
//        SecureRandom random;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            random = SecureRandom.getInstance("SHA1PRNG", "Crypto");
//        } else {
//            random = SecureRandom.getInstance("SHA1PRNG");
//        }
//        random.setSeed(sKey.getBytes());
//        kgen.init(192, random);
//        SecretKey secretKey = kgen.generateKey();
//        byte[] enCodeFormat = secretKey.getEncoded();
//        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
        SecretKeySpec keySpec = new SecretKeySpec(sKey.getBytes(), "AES");

//        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted1 = JavaBase64.decode(sSrc);//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
    }

  /*  public static void main(String[] args) {
        try {
            String rest = encrypt("5_10_touchorder1_100018", KEY);
            System.out.println(rest);
            System.out.println(decrypt(rest, KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
