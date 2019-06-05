package com.hzy

/**
 * 字符串工具类
 */
object StringUtil {

    /**
     * 判断字符串是否为空
     */
    fun isEmpty(str: String?) = null == str || "" == str

    /**
     * 用自定义的分隔符拼接数据
     */
    fun <T> Collection<T>.joinToString(
            separator: String = ",",
            prefix: String = "",
            postfix: String = ""
    ): String {
        val sb = StringBuilder(prefix)
        for ((index, element) in this.withIndex()) {
            if (index > 0) sb.append(separator)
            sb.append(element)
        }
        sb.append(postfix)
        return sb.toString()
    }

}