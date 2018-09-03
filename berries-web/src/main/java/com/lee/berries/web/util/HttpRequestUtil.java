package com.lee.berries.web.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求工具类
 * 
 * @author 
 *
 */
public class HttpRequestUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
	
    /** 
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
     *  
     * @param request 
     * @return 
     * @throws IOException 
     */  
    public final static String getIpAddress(HttpServletRequest request) {  
    	try {
	        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
	        String ip = request.getHeader("X-Forwarded-For");  
	        if (logger.isInfoEnabled()) {  
	            logger.debug("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);  
	        }  
	  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("Proxy-Client-IP");  
	                if (logger.isInfoEnabled()) {  
	                    logger.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("WL-Proxy-Client-IP");  
	                if (logger.isInfoEnabled()) {  
	                    logger.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("HTTP_CLIENT_IP");  
	                if (logger.isInfoEnabled()) {  
	                    logger.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	                if (logger.isInfoEnabled()) {  
	                    logger.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getRemoteAddr();  
	                if (logger.isInfoEnabled()) {  
	                    logger.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);  
	                }  
	            }  
	        } else if (ip.length() > 15) {  
	            String[] ips = ip.split(",");  
	            for (int index = 0; index < ips.length; index++) {  
	                String strIp = ips[index];  
	                if (!("unknown".equalsIgnoreCase(strIp))) {  
	                    ip = strIp;  
	                    break;  
	                }  
	            }  
	        }  
	        return ip;  
    	}catch (Exception e) {
    		logger.error("获取客户端IP地址出错", e);
    	}
		return null;
    }  
    
	public static  Map<String, String> getFormData(HttpServletRequest request){
		Map<String, String> data = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		if(map != null) {
			for(Entry<String, String[]> entry:map.entrySet()) {
				String value = "";
				for(String v:entry.getValue()) {
					value += v + ",";
				}
				data.put(entry.getKey(), value);
			}
		}
		return data;
	}
    
}
