package com.supwisdom.orderlib.utils

import java.security.MessageDigest

/**
 * @desc  md5
 */
internal object MD5 {
    //十六进制下数字到字符的映射数组
    private val hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    /**
     * 把inputString加密
     */
    @Throws(Exception::class)
    fun generatePassword(inputString: String): String? {
        return encodeByMD5(inputString)
    }

    /**
     * 验证输入的密码是否正确
     *
     * @param password    加密后的密码
     * @param inputString 输入的字符串
     * @return 验证结果，TRUE:正确 FALSE:错误
     */
    @Throws(Exception::class)
    fun validatePassword(password: String, inputString: String): Boolean {
        return password == encodeByMD5(inputString)
    }

    /**
     * 对字符串进行MD5加密
     */
    @Throws(Exception::class)
    fun encodeByMD5(originString: String?): String? {
        if (originString != null) {
            try {
                //创建具有指定算法名称的信息摘要
                val md = MessageDigest.getInstance("MD5")
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                val results = md.digest(originString.toByteArray(charset("UTF-8")))
                //将得到的字节数组变成字符串返回
                val resultString = byteArrayToHexString(results)
                return resultString.toUpperCase()
            } catch (ex: Exception) {
                throw ex
            }

        }
        return null
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param 字节数组
     * @return 十六进制字符串
     */
    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (i in b.indices) {
            resultSb.append(byteToHexString(b[i]))
        }
        return resultSb.toString()
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     */
    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0)
            n += 256
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }
}