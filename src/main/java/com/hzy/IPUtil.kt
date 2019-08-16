package com.hzy

import java.net.InetAddress
import java.net.UnknownHostException
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
    fun getIpAddress(request: HttpServletRequest): String? {
        var ip: String? = request.getHeader("x-forwarded-for")
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
            if (ip == "127.0.0.1" || ip == "0:0:0:0:0:0:0:1") {
                //根据网卡取本机配置的IP
                var inetAddress: InetAddress? = null
                try {
                    inetAddress = InetAddress.getLocalHost()
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                    return ip
                }
                ip = inetAddress!!.hostAddress
            }
        }
        return if (ip!!.contains(",")) {
            ip.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            ip
        }
    }

}
