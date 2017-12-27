package com.core.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author 颜福华
 * @version 创建时间：2015年11月11日 上午11:43:07
 */
public class HttpClientHelper {
	protected final static Logger logger = LogManager.getLogger(HttpClientHelper.class);

	private static CloseableHttpClient client = null;

	private HttpClientHelper() {
	}

	private static CloseableHttpClient getClientInstance() {
		if (client == null) {
			synchronized (HttpClientHelper.class) {
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
	 * @param host
	 * @param url
	 * @param proxyIp
	 * @param proxyPort
	 * @return
	 */
	public static String httpProxyGet(String url, String proxyIp, int proxyPort) throws ClientProtocolException, IOException {
		// 创建httpget
		HttpGet httpGet = new HttpGet(url);
		HttpHost proxy = new HttpHost(proxyIp, proxyPort);
		HttpClient httpClient = getClientInstance();
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
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
//		logger.info("[httpclient->url:{}]请求参数:{}", url, JsonUtil.toJson(params));
		HttpResponse response = getClientInstance().execute(httpPost);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
//		logger.info("[httpclient->url:{}]返回:{}", url, responseStr);
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

	/**
	 * http delete
	 * 
	 * @param deleteUrl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HttpResponse httpDelete(String deleteUrl, String userName, String password)
			throws ClientProtocolException, IOException {
		URI uri = URI.create(deleteUrl);
		HttpHost host = URIUtils.extractHost(uri);
		// 用户名密码
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()),
				new UsernamePasswordCredentials(userName, password));
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		// 创建默认的httpClient实例.
		CloseableHttpClient client = HttpClientBuilder.create().build();
		// 创建httppost
		HttpDelete httpDelete = new HttpDelete(deleteUrl);
		HttpResponse response = getClientInstance().execute(httpDelete, context);
		return response;
	}

	public static void main(String[] args) {
		String fullurl = "http://172.20.11.100:10087/topay";
		String paramStr = "{\"organize_code\":\"001522742505200\",\"request_id\":\"2016081014124536\",\"request_org_id\":\"2016081014124538\",\"version\":\"1.0.0\",\"query_time\":\"20160810141845\",\"sign\":\"uf+cC1g0+P36m/U5ECJYfZnkHJyp3XKDu8DwstWYls7gI1YNF355KqMVKPOpudJ+Se0sg22/2YAjOKbW9Iw+i6SCcAL5gHgoauk0ImKNt/mmH95pwGA2Z8wdHpeFQhxrUJmT98XZToSP04nCh/GKKAvgHmwX+mdvOAEZdRpeNnxtygu5T9dBLQ4Kp23TL40n3hmBqkqbeHkPVS8KJCeiryGVfViPjCeOhk9JOkDRV99SmVR/6Ve0edyD9xHJp2GeADkhqVakRt6jefBU8UXC/EAxcjQoezX32ABWnVZoXVCF4YxTfAJ1/uHpBDMTsXwccWGz6Q0Jjfrq7RJth9UElA==\"}";
		Map<String, String> param = new HashMap<>();
		param.put("query", paramStr);
		try {
			String str = HttpClientHelper.httpPost(fullurl, param, ENCOCE_UTF_8);
			System.out.println(str);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String ENCOCE_UTF_8 = "UTF-8";
}
