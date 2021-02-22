package com.sj.app.utils

import java.lang.Exception
import java.util.regex.Pattern

/**
 * @author zzq
 * @date 2018/3/20.
 * @version 1.0.1
 * @desc  TODO
 */
fun ByteArray.encodeHex(): String {
    val res = StringBuilder()
    forEach {
        res.append("%02X".format(it.toInt() and 0xFF))
    }
    return res.toString()
}

fun String.decodeHex(): ByteArray {
    if (this.isEmpty() || this.length % 2 != 0) {
        throw Exception("不合法的数据")
    }
    return (0 until this.length step 2).map {
        val v = this.substring(it, it + 2).toInt(16)
        (v and 0xFF).toByte()
    }.toByteArray()
}

fun String.encodeBCD(): ByteArray {
    if (this.isEmpty() || this.length % 2 != 0) {
        throw Exception("不合法的数据")
    }
    return (0 until this.length step 2).map {
        val d = this.substring(it, it + 2)
        val b1 = d[0].toInt() - 0x30
        val b2 = d[1].toInt() - 0x30
        if (b1 !in 0..9 || b2 !in 0..9) {
            throw Exception("不合法的BCD格式数据<$this>")
        }
        ((b1.shl(4) or b2) and 0xFF).toByte()
    }.toByteArray()
}

fun ByteArray.decodeBCD(): String {
    return joinToString(separator = "") {
        val b1 = it.unsignedToInt().shr(4)
        val b2 = it.unsignedToInt() and 0x0F
        if (b1 !in 0..9 || b2 !in 0..9) {
            throw Exception("不合法的数据")
        }
        "%01d%01d".format(b1, b2)
    }
}

fun byteArrayTohex(data: ByteArray, startPos: Int, byteLength: Int): String {
    val res = StringBuilder()
    data.forEachIndexed { index, s ->
        if (index >= startPos && index < startPos + byteLength) {
            res.append("%02X".format(s.unsignedToInt() and 0xFF))
        }
    }
    return res.toString()
}

fun Byte.unsignedToInt(): Int {
    return this.toInt() and 0xFF
}

fun Byte.unsignedToLong(): Long {
    return this.toLong() and 0xFF
}

fun byteArrayToIntLe(data: ByteArray, startPos: Int, byteLength: Int): Int {
    var res = 0
    data.forEachIndexed { index, s ->
        if (index >= startPos && index < startPos + byteLength) {
            res = res or (s.unsignedToInt() shl ((index - startPos) shl 3))
        }
    }
    return res
}

fun byteArrayToIntBe(data: ByteArray, startPos: Int, byteLength: Int): Int {
    var res = 0
    data.forEachIndexed { index, s ->
        if (index >= startPos && index < startPos + byteLength) {
            res = (res shl 8) or s.unsignedToInt()
        }
    }
    return res
}

fun byteArrayToLongBe(data: ByteArray, startPos: Int, byteLength: Int): Long {
    var res: Long = 0
    data.forEachIndexed { index, s ->
        if (index >= startPos && index < startPos + byteLength) {
            res = (res shl 8) or s.toLong()
        }
    }
    return res
}

fun byteArrayToLongLe(data: ByteArray): Long {
    var res: Long = 0
    data.forEachIndexed { index, s ->
        res = res or ((s.toLong() and 0xFF) shl (index shl 3))
    }
    return res
}


fun intBeToByteArray(value: Int, byteLen: Int = 4): ByteArray {
    if (byteLen !in 1..4) {
        throw Exception("数据长度错误")
    }
    val res = "%08X".format(value).decodeHex()
    return res.copyOfRange(4 - byteLen, 4)
}

fun intBeToByteArray(value: Int, data: ByteArray, startPos: Int, byteLen: Int) {
    if (byteLen !in 1..4) {
        throw Exception("数据长度错误")
    }
    val res = "%08X".format(value).decodeHex()
    (startPos until startPos + byteLen).forEach {
        data[it] = res[4 - byteLen + it - startPos]
    }

}

fun longBeToByteArray(value: Long, byteLen: Int = 4): ByteArray {
    if (byteLen !in 1..8) {
        throw Exception("数据长度错误")
    }
    val res = ByteArray(8) { 0x00 }
    (0..7).forEach {
        res[it] = ((value shr ((7 - it) shl 3)) and 0xFF).toByte()
    }
    return res.copyOfRange(8 - byteLen, 8)
}

fun intLeToByteArray(value: Int, byteLen: Int = 4): ByteArray {
    if (byteLen !in 1..4) {
        throw Exception("数据长度错误")
    }
    val res = ByteArray(4) { 0x00 }
    (0..3).forEach {
        res[it] = ((value shr (it shl 3)) and 0xFF).toByte()
    }
    return res.copyOfRange(0, byteLen)
}

fun intLeToByteArray(value: Int, data: ByteArray, startPos: Int, byteLen: Int) {
    if (byteLen !in 1..4) {
        throw Exception("数据长度错误")
    }
    (0..3).forEach {
        data[it + startPos] = ((value shr (it shl 3)) and 0xFF).toByte()
    }
}

/**验证IP格式*/
fun verifyIpFormat(ip: String): Boolean {
    val pattern = Pattern.compile("")
    return pattern.matcher(ip).matches()
}
