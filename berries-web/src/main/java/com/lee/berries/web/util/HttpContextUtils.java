package com.lee.berries.web.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取request，response 等静态类
 * @author lxw
 *
 */
public class HttpContextUtils {

	public static HttpServletRequest getServletRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

	public static ServletContext getServletContext(){
        HttpServletRequest request = getServletRequest();
        if (request != null){
            return request.getSession().getServletContext();
        }

        return null;
    }
	
	public static HttpServletResponse getServletResponse(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
