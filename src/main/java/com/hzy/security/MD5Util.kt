package com.hzy.security

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.MessageDigest
import kotlin.experimental.and


/**
 * MD5 工具类
 */
object MD5Util {
    private val hexCode = "0123456789ABCDEF".toCharArray()
    /**
     * 获取一个文件的md5值(可处理大文件)
     * @param file
     * @return 返回文件的md5字符串，如果计算过程中任务的状态变为取消或暂停，返回null， 如果有其他异常，返回空字符串
     */
    fun getMD5(file: File): String? {
        var fis: FileInputStream? = null
        try {
            val md5 = MessageDigest.getInstance("MD5")
            fis = FileInputStream(file)
            val buffer = ByteArray(1024 * 4)
            var len: Int = fis.read(buffer)
            while (len != -1) {
                md5.update(buffer, 0, len)
                len = fis.read(buffer)
            }
            return String(Hex.encodeHex(md5.digest()))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 求一个字符串的md5值
     * @param text
     * @return 返回text的md5字符串
     */
    fun getMD5(text: String): String {
        return DigestUtils.md5Hex(text)
    }

    /**
     * 将字节数组转为十六进制的字符串
     */
    fun toHexString(data: ByteArray): String {
        val sb = StringBuilder(data.size * 2)
        for (b in data) {
            sb.append(hexCode[(b.toInt() shr 4) and 0xF])
            sb.append(hexCode[(b and 0xF).toInt()])
        }
        return sb.toString()
    }
}
