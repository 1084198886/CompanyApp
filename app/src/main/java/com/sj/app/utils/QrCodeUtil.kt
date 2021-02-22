package com.supwisdom.dcpos.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel


/**
 * @author zzq
 * @date 2018/5/8.
 * @version 1.0.1
 * @desc  字符串转二维码
 */
object QrCodeUtil {
    private var QR_WIDTH = 1000
    private var QR_HEIGHT = 1000
    private var IMAGE_HALFWIDTH = 800
    /**
     * 生成带logo的二维码，logo默认为二维码的1/5
     *
     * @param text 需要生成二维码的文字、网址等
     * @param size 需要生成二维码的大小（）
     * @param mBitmap logo文件
     * @return bitmap
     */
    @Throws(WriterException::class)
    fun createTwoDCodeWithLogo(text: String, size: Int, logoBitmap: Bitmap): Bitmap? {
     IMAGE_HALFWIDTH = size / 10
        val hints = HashMap<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "utf-8"
        /*
         * 设置容错级别，默认为ErrorCorrectionLevel.L
         * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
         */
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        val bitMatrix = QRCodeWriter().encode(text,
                BarcodeFormat.QR_CODE, size, size, hints)

        val width = bitMatrix.width//矩阵高度
        val height = bitMatrix.height//矩阵宽度
        val halfW = width / 2
        val halfH = height / 2

        val m = Matrix()
        val sx = 2.toFloat() * IMAGE_HALFWIDTH / logoBitmap.width
        val sy = 2.toFloat() * IMAGE_HALFWIDTH / logoBitmap.height
        m.setScale(sx, sy)
        //设置缩放信息
        //将logo图片按martix设置的信息缩放
        var fixLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0,
                logoBitmap.width, logoBitmap.height, m, false)

        val pixels = IntArray(size * size)
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    //该位置用于存放图片信息
                    //记录图片每个像素信息
                    pixels[y * width + x] = fixLogoBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH)
                } else {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = -0x1000000
                    } else {
                        pixels[y * size + x] = -0x1
                    }
                }
            }
        }
        val bitmap = Bitmap.createBitmap(size, size,
                Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
        return bitmap
    }

    /**
     * 将指定的内容生成成二维码
     *
     * @param content 将要生成二维码的内容
     * @return 返回生成好的二维码事件
     * @throws com.google.zxing.WriterException WriterException异常
     * @size 二维码的长宽（等距）
     */
    @Throws(WriterException::class)
    fun createTwoDCode(content: String, size: Int): Bitmap {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        val matrix = MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, size, size)
        val width = matrix.width
        val height = matrix.height
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = -0x1000000
                }
            }
        }

        val bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    /**
     * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
     *
     * @param content   将要生成一维码的内容
     * @param widthOne  将要生成一维码的长度
     * @param heightOne 将要生成一维码的宽度
     * @return 返回生成好的一维码bitmap
     * @throws WriterException WriterException异常
     */
    @Throws(WriterException::class)
    fun createOneDCode(content: String, widthOne: Int, heightOne: Int): Bitmap {
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        val matrix = MultiFormatWriter().encode(content,
                BarcodeFormat.CODE_128, widthOne, heightOne)
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = -0x1000000
                }
            }
        }

        val bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
}