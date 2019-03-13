package com.hzy

object StringUtil {

    /**
     * 用自定义的分隔符拼接数据
     */
    fun <T> joinToString(
            collection: Collection<T>,
            separator: String = ", ",
            prefix: String = "",
            postfix: String = ""
    ): String {
        val sb = StringBuilder(prefix)
        for ((index, element) in collection.withIndex()) {
            if (index > 0) sb.append(separator)
            sb.append(element)
        }
        sb.append(postfix)
        return sb.toString()
    }

}