package com.supwisdom.commonlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jov on 2015/1/16.
 */
public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取系统版本名
     */
    public static String getSysVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Launcher应用的版本名
     */
    public static String getLauncherVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pkgList = packageManager.getInstalledPackages(0);
        for (PackageInfo pi : pkgList) {
            if (pi.packageName.equals("com.supwisdom.a903launcher")) {
                return pi.versionName;
            }
        }
        return null;
    }


    public static int dip2px(float dp) {
        Resources r = Resources.getSystem();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static int getScreenWith() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取系统版本code
     */
    public static int getSysVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取字符串真实的字节长度
     *
     * @param str
     * @return
     */
    public static int getRealLength(String str) {
        int m = 0;
        char arr[] = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            // 中文字符(根据Unicode范围判断),中文字符长度为2
            if ((c >= 0x0391 && c <= 0xFFE5)) {
                m = m + 2;
            } else if (c <= 0x00FF) // 英文字符
            {
                m = m + 1;
            }
        }
        return m;
    }

    public static boolean isIP(String str) {
        if (str.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String s[] = str.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            return true;
        }
        return false;
    }


    public static String encodingMD5(String val) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 should be unsupported", e);
        }
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }

    /**
     * 作用:读取该字节某一位的值是否为1
     * 为1返回true
     * 为0返回false
     *
     * @param data
     * @param bitOffset
     * @return
     */
    public static boolean checkBit(byte data, int bitOffset) {
        int result = 0;
        result = data & (1 << bitOffset);//00011000
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static int setBit(int data, int position) {
        int finalPos = 7 - position;
        int result = data;
        result |= (1 << finalPos);
        return result;
    }

    public static int clearBit(int data, int position) {
        int finalPos = 7 - position;
        int result = data;
        result &= ~(1 << finalPos);
        return result;
    }


    public static void doSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int value = resources.getDimensionPixelSize(resourceId);
        LogUtil.d(TAG, "状态栏高度:" + value);
        return value;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int value = resources.getDimensionPixelSize(resourceId);
        LogUtil.d(TAG, "导航栏高度:" + value);
        return value;
    }


    /**
     * 元->分
     *
     * @param yuan
     * @return
     */
    public static int YuanToFen(double yuan) {
        return (int) Math.round(yuan * 100);
    }


    /**
     * @return double精确到2位小数；
     */
    public static double formatDoubleNum(double number) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * long转double
     */
    public static String formatNumber(long number) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(number);
    }

    /**
     * @return Int类型；
     */
    public static int formatDouble(double number) {
        // new方式创建BigDecimal对象会丢失精度，参考https://blog.csdn.net/zongzhankui/article/details/79591294
//        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).
                multiply(new BigDecimal(100)).
                intValue();
    }

    /**
     * @return Int类型；
     */
    public static int formatDouble2(double number) {
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).
                intValue();
    }

    private static String hexStr = "0123456789ABCDEF";
    private static String hexLowStr = "0123456789abcdef";

    public static byte[] hexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < tmp.length / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    public static String getHexStringByBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < bytes.length; i++) {
            sb.append(hexStr.charAt((bytes[i] >> 4) & 0x0F)).append(hexStr.charAt((bytes[i] & 0x0F)));
        }
        return sb.toString();
    }

    public static String getHexLowerStrByBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < bytes.length; i++) {
            sb.append(hexLowStr.charAt((bytes[i] >> 4) & 0x0F)).append(hexLowStr.charAt((bytes[i] & 0x0F)));
        }
        return sb.toString();
    }

    /**
     * byte转换为char
     *
     * @param b
     * @return
     */
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

    /**
     * 加密
     *
     * @param content 待加密内容
     * @param key     加密的密钥
     * @return byte[] 加密内容
     */
    private static byte[] mIV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public static byte[] desEncrypt(String content, String key) {
        String algorithm;
        byte[] desKey = hexString2Bytes(key);
        byte[] theKey;
        if (desKey.length == 8) {
            algorithm = "DES";
            theKey = desKey;
        } else if (desKey.length == 16) {
            algorithm = "DESede";
            theKey = new byte[24];
            System.arraycopy(desKey, 0, theKey, 0, 16);
            System.arraycopy(desKey, 0, theKey, 16, 8);
        } else {
            throw new InvalidParameterException("Key length must be 8 or 16");
        }
        try {
            //8 倍加密数据
            byte[] tmp = content.getBytes();
            int size = tmp.length / 8;
            if (tmp.length % 8 != 0) {
                size += 1;
            }
            byte[] data = new byte[size * 8];
            System.arraycopy(tmp, 0, data, 0, tmp.length);

            SecretKeySpec enkey = new SecretKeySpec(theKey, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm + "/CBC/NoPadding");
            IvParameterSpec zeroIv = new IvParameterSpec(mIV);
            cipher.init(Cipher.ENCRYPT_MODE, enkey, zeroIv);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param encData 待解密内容
     * @param key     解密的密钥
     * @return byte[] 解密内容
     */
    public static byte[] desDecrypt(byte[] encData, String key) {
        String algorithm;
        byte[] theKey;
        byte[] desKey = hexString2Bytes(key);
        if (desKey.length == 8) {
            algorithm = "DES";
            theKey = desKey;
        } else if (desKey.length == 16) {
            algorithm = "DESede";
            theKey = new byte[24];
            System.arraycopy(desKey, 0, theKey, 0, 16);
            System.arraycopy(desKey, 0, theKey, 16, 8);
        } else {
            throw new InvalidParameterException("Key length must be 8 or 16");
        }

        try {
            SecretKeySpec enkey = new SecretKeySpec(theKey, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm + "/CBC/NoPadding");
            IvParameterSpec zeroIv = new IvParameterSpec(mIV);
            cipher.init(Cipher.DECRYPT_MODE, enkey, zeroIv);
            return cipher.doFinal(encData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(int spValue) {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, dm);
    }

    public static boolean isAPI23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    /**
     * 十进制转换成二进制String
     */
    public static String decimalToBinary(int decimalSource) {
        BigInteger bi = new BigInteger(String.valueOf(decimalSource));
        //参数2指定的是转化成X进制，默认10进制
        return bi.toString(2);
    }

    public static String bytesToStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        try {
            for (byte aByte : bytes) {
                sb.append(String.format("%c", aByte));
            }
        } catch (Exception ex) {
            LogUtil.e(TAG, "byte转char异常:" + ex.getMessage());
        }
        return sb.toString();
    }

    public static boolean checkPhone(String phone) {
        String phoneNumberReg = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        Pattern pattern = Pattern.compile(phoneNumberReg);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 统计汉字的个数
     *
     * @param zhStr
     * @return
     */
    public static int clcZhNumber(String zhStr) {
        int count = 0;
        String Reg = "^[\u4e00-\u9fa5]$";   //汉字的正规表达式
        for (int i = 0; i < zhStr.length(); i++) {
            String b = Character.toString(zhStr.charAt(i));
            if (b.matches(Reg)){
                count++;
            }
        }
        return count;
    }
}
