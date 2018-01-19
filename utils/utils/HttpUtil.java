package com.xgd.boss.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http工具类
 * @author weiqingding
 *
 */
public class HttpUtil {
	private static final Log logger = LogFactory.getLog(HttpUtil.class);
	private static String ENCOCE_UTF_8 = "UTF-8";
	private static CloseableHttpClient client = null;

	private HttpUtil() {
	}

	private static CloseableHttpClient getClientInstance() {
		if (client == null) {
			synchronized (HttpUtil.class) {
				if (client == null)
					client = HttpClientBuilder.create().setMaxConnPerRoute(20)// 设置每个路由的最大并发连接数为20以提高性能，默认为2
							.build();
			}
		}
		return client;
	}

	/**
	 * get请求
	 * 
	 * @param fullurl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpGet(String url) throws ClientProtocolException, IOException {
		// 创建httpget
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = getClientInstance().execute(httpGet);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}

	public static HttpEntity httpGetForEntity(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = getClientInstance().execute(httpGet);
		return response.getEntity();
	}
	
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public static  String getIpAddress(HttpServletRequest request) {  
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
        String ip = request.getHeader("X-Forwarded-For");  
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
            if(StringUtil.isNotEmpty(ip)){
            	ip = ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            }
        }else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
	
	/**
	 * 获取basepath
	 * @param req
	 * @return
	 */
	public static String getBashPath(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath();
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpPost(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		return httpPost(url, params, ENCOCE_UTF_8);
	}

	/**
	 * post请求
	 * 
	 * @param fullurl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpPost(String url, Map<String, String> params, String encode)
			throws ClientProtocolException, IOException {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);
		if (params != null) {
			// 创建参数队列
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, encode);
			httpPost.setEntity(uefEntity);
		}
		logger.debug("[httpclient->url:"+url+"]请求参数:"+ JsonUtil.toJson(params));
		HttpResponse response = getClientInstance().execute(httpPost);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
		logger.debug("[httpclient->url:"+url+"]返回:"+ responseStr);
		return responseStr;
	}

	/**
	 * 使用json body的方式发送post请求
	 * 
	 * @param url
	 * @param jsonBody
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpPostUseJsonBody(String url, String jsonBody) throws ClientProtocolException, IOException {
		return httpPostUseJsonBody(url, jsonBody, ENCOCE_UTF_8);
	}

	/**
	 * 使用json body的方式发送post请求
	 * 
	 * @param url
	 * @param jsonBody
	 * @param encode
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String httpPostUseJsonBody(String url, String jsonBody, String encode)
			throws ClientProtocolException, IOException {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(jsonBody, encode);
		stringEntity.setContentEncoding(encode);
		// 以json body的方式发送
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		HttpResponse response = getClientInstance().execute(httpPost);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}

}
