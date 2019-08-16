package com.hzy

import javax.servlet.http.HttpServletRequest

/**
 * IP 地址相关工具类
 */
object IPUtil {

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    fun getIpAddress(request: HttpServletRequest): String {
        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        return if (ip!!.contains(",")) {
            ip.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            ip
        }
    }

}
