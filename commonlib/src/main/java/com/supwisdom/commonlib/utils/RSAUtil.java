package com.supwisdom.commonlib.utils;

import android.util.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Encoder;

/**
 * Created by shuwei on 2018/8/16.
 */
public class RSAUtil {
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String SIGN_TYPE = "RSA";
    private static final String CHARSET = "UTF-8";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String TAG = RSAUtil.class.getSimpleName();

    /**
     * 获得公钥字符串
     */
    public static String getPublicKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 编码返回字符串
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 获得私钥字符串
     */
    public static String getPrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化公私钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(SIGN_TYPE);

        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);

        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;

    }


    /**
     * @param srcStr     待签名字符串
     * @param privateKey 私钥
     * @return 密串
     * @title 签名方法
     * @desc
     * @author W.jw
     * @date 2018年5月3日 下午6:14:35
     */
    public static String sign(String srcStr, String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE);
            byte[] decodeKey = JavaBase64.decode(privateKey);
            PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(decodeKey);
            PrivateKey priKey = keyFactory.generatePrivate(peks);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(srcStr.getBytes(CHARSET));
            byte[] signes = signature.sign();
            return JavaBase64.encode(signes);
        } catch (Exception e) {
            LogUtil.e(TAG, "RSA加密异常:" + e.getMessage());
        }
        return null;
    }

    /**
     * @param srcStr    待验签参数
     * @param tarStr    待验签密串
     * @param publicKey 公钥
     * @return 是否验签通过
     * @title 验签方法
     * @desc
     * @author W.jw
     * @date 2018年5月3日 下午6:15:23
     */
    public static boolean verify(String srcStr, String tarStr, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE);
            byte[] decodeKey = Base64.decode(publicKey, Base64.NO_WRAP);
            X509EncodedKeySpec xeks = new X509EncodedKeySpec(decodeKey);
            PublicKey pubKey = keyFactory.generatePublic(xeks);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(srcStr.getBytes(CHARSET));
            boolean verify = signature.verify(Base64.decode(tarStr, Base64.NO_WRAP));
            return verify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap;
        byte[] cipherText;
        String input = "Hello World!";
        try {
            keyMap = initKey();
            String publicKey = getPublicKeyStr(keyMap);
            System.out.println("公钥------------------");
            System.out.println(publicKey);
            String privateKey = getPrivateKeyStr(keyMap);
            System.out.println("私钥------------------");
            System.out.println(privateKey);

            System.out.println("测试可行性-------------------");
            System.out.println("明文=======" + input);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
