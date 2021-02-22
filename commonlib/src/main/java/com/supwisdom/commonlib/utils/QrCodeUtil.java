package com.supwisdom.commonlib.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QrCodeUtil {

//    public static String checkQrcodeType(String qrcode) {
//        if (StringUtils.isEmpty(qrcode))
//            return null;
//
//        if (NumberUtils.isDigits(qrcode)) {
//            //纯数字
//            if (checkWechatAuthcode(qrcode)) {
//                //微信二维码
//                return ApiConstants.QRCODETYPE_WECHAT;
//
//            } else if (checkAlipayAuthcode(qrcode)) {
//                //支付宝二维码
//                return ApiConstants.QRCODETYPE_ALIPAY;
//
//            } else if (checkUnionpayAuthcode(qrcode)) {
//                //银联二维码
//                return ApiConstants.QRCODETYPE_UNIONPAY;
//
//            } else {
//                //暂认为是悦校APP二维码
//                return ApiConstants.QRCODETYPE_YUEXIAO;
//            }
//        } else {
//            //非纯数字
//            if (qrcode.startsWith("SWH5_")) {
//                return ApiConstants.QRCODETYPE_H5;
//            }
//
//            if (qrcode.startsWith("SWO5_")) {
//                return ApiConstants.QRCODETYPE_O5;
//            }
//
//            if (qrcode.startsWith("shu")) {
//                return ApiConstants.QRCODETYPE_SHU; //上大自定义二维码
//            }
//
//            // 暂认为是完美校园二维码
//            return ApiConstants.QRCODETYPE_WMXY;
//        }
//    }

    public static boolean checkWechatAuthcode(String qrcode) {
        //初步判断微信二维码
        //用户刷卡条形码规则：18位纯数字，以10、11、12、13、14、15开头
        if (!NumberUtils.isDigits(qrcode) || qrcode.trim().length() != 18) {
            return false;
        }

        List<Integer> prefixs = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 13, 14, 15));
        int prefix = Integer.valueOf(qrcode.trim().substring(0, 2));
        if (!prefixs.contains(prefix)) {
            return false;
        }
        return true;
    }

    public static boolean checkAlipayAuthcode(String qrcode) {
        //支付宝: 支付授权码，25~30开头的长度为16~24位的数字
        if (NumberUtils.isDigits(qrcode)) {
            //纯数字
            if (qrcode.trim().length() >= 16 && qrcode.trim().length() <= 24) {
                //16~24位
                int prefix = Integer.valueOf(qrcode.trim().substring(0, 2));
                if (prefix >= 25 && prefix <= 30) {
                    //25~30开头
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkUnionpayAuthcode(String qrcode) {
        //初步判断银联二维码
        //用户刷卡条形码规则：19位纯数字，以62开头
        if (!NumberUtils.isDigits(qrcode) || qrcode.trim().length() > 19 || qrcode.trim().length() < 13) {
            return false;
        }

        List<Integer> prefixs = new ArrayList<Integer>(Arrays.asList(62, 29));
        int prefix = Integer.valueOf(qrcode.trim().substring(0, 2));
        if (!prefixs.contains(prefix)) {
            return false;
        }

        return true;
    }

}
