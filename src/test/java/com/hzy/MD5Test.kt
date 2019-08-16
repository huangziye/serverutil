package com.hzy

import com.hzy.security.MD5Util

/**
 * @author: ziye_huang
 * @date: 2019/7/23
 */
object MD5Test {
    @JvmStatic
    fun main(args: Array<String>) {
        println(MD5Util.getMD5("sdf"))
    }
}
