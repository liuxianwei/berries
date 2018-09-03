package com.lee.berries.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClientUtil {  
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * 以post方式发送JSON数据
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String httpPostWithJSON(String url, String encode, String json, int timeout){
		HttpPost httpPost = new HttpPost(url);
        try {
	        CloseableHttpClient client = HttpClients.createDefault();
	        String respContent = null;
	        //json方式
	        StringEntity entity = new StringEntity(json, encode);//解决中文乱码问题   
	        
	        entity.setContentEncoding(encode);    
	        entity.setContentType("application/json");    
	        httpPost.setEntity(entity);
	        RequestConfig requestConfig = RequestConfig.custom()  
	                .setSocketTimeout(timeout).setConnectTimeout(timeout).build();  
	        httpPost.setConfig(requestConfig);
	        HttpResponse resp = client.execute(httpPost);
	        if(resp.getStatusLine().getStatusCode() == 200) {
	            HttpEntity he = resp.getEntity();
	            respContent = EntityUtils.toString(he,"UTF-8");
	        }
	        return respContent;
		}
		catch(Exception e) {
			
		}
        finally {  
			httpPost.releaseConnection();  
        }  
		return null;
    }
	
	/**
	 * 以post方式发送表单数据
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String httpPostWithFORM(String url, String encode, Map<String, String> formData, int timeout) {
		HttpPost httpPost = new HttpPost(url);
	       try {
			logger.debug("以post方式发送表单数据, {}, 参数：{}, {}, {}", url.hashCode(), url, formData, timeout);
			 CloseableHttpClient client = HttpClients.createDefault();
	        String respContent = null;
	        //表单方式
	        List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>(); 
	        Iterator<Entry<String, String>> iter = formData.entrySet().iterator();
	        while(iter.hasNext()) {
	        	Entry<String, String> entry = iter.next();
	        	pairList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
	        }
	        RequestConfig requestConfig = RequestConfig.custom()  
	                .setSocketTimeout(timeout).setConnectTimeout(timeout).build();  
	        httpPost.setConfig(requestConfig);
	        httpPost.setEntity(new UrlEncodedFormEntity(pairList, encode));   
	        HttpResponse resp = client.execute(httpPost);
	        HttpEntity he = resp.getEntity();
	        respContent = EntityUtils.toString(he, encode);
	        logger.debug("以post方式发送表单数据, {}, 结果: {}, {}", url.hashCode(), resp.getStatusLine().getStatusCode(), respContent);
	        if(resp.getStatusLine().getStatusCode() == 200) {
	            return respContent;
	        }
		}
		catch(Exception e) {
			
		}
		finally {  
			httpPost.releaseConnection();  
        }  
        return null;
    }	
	
    /** 
     * 发送get请求 
     *  
     * @param url 
     *            路径 
     * @return 
     */  
    public static String httpGet(String url, String encode, int timeout) {  
        // get请求返回结果  
    	String result = null;
        CloseableHttpClient client = HttpClients.createDefault();  
        // 发送get请求  
        HttpGet request = new HttpGet(url);  
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(timeout).setConnectTimeout(timeout).build();  
        request.setConfig(requestConfig);  
        try {  
            CloseableHttpResponse response = client.execute(request);  
            //请求发送成功，并得到响应  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                //读取服务器返回过来的json字符串数据  
                HttpEntity entity = response.getEntity();  
                result = EntityUtils.toString(entity, encode);  
            } else {  
                logger.error("get请求提交失败:" + url);  
            }  
        } catch (IOException e) {  
            logger.error("get请求提交失败:" + url, e);  
        } finally {  
            request.releaseConnection();  
        }  
        return result;
    }
} 
