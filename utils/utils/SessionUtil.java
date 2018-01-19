package com.xgd.boss.core.utils;

import com.xgd.boss.core.vo.CommConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取sessionId
 * @author liangxiong
 * 2015-03-5
 *
 */
public class SessionUtil {
	
	private static Log loggger = LogFactory.getLog(SessionUtil.class);
	
	/**
	 * 基于当前请求和响应，获取sessionId
	 * 
	 * @param request http request
	 * @param response http response
	 * @return sessionId
	 */
	public static String getSessionId(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = "";
		// 首先从cookie中读取存入的sessionId
		Map<String, Cookie> cookieMap = formatCookieMap(request);
		if (cookieMap.containsKey(CommConst.MT_MERCHANT_SESSION_ID)) {
			Cookie cookie = cookieMap.get(CommConst.MT_MERCHANT_SESSION_ID);
			sessionId = cookie.getValue();
		}
		if (StringUtils.isNotBlank(sessionId)) {
			return sessionId;
		}

		// cookie中没有已存入的sessionId, 其次从response set-cookie头获取sessionId
		String sessionIdFromResponse = sessionIdFromResponseHeader(response);
		if (StringUtils.isNotEmpty(sessionIdFromResponse)) {
			sessionId = StringUtils.substring(sessionIdFromResponse, 0, 32);
			return sessionId;
		}

		// 最后向cookie写入http request sessionId，并返回该sessionId
		if (cookieMap.containsKey(CommConst.JSESSIONID)) {
			Cookie cookie = cookieMap.get(CommConst.JSESSIONID);
			sessionId = cookie.getValue();
			sessionId = StringUtils.substring(sessionId, 0, 32);
			cookie = new Cookie(CommConst.MT_MERCHANT_SESSION_ID, sessionId);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			sessionId = request.getSession().getId();
			sessionId = StringUtils.substring(sessionId, 0, 32);
			Cookie cookie = new Cookie(CommConst.MT_MERCHANT_SESSION_ID, sessionId);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return sessionId;
	}
	
	/**
	 * 将http请求中的cookie封装到Map中，key:cookie名字; value:cookie
	 * 
	 * @param request http request
	 * @return cookie map
	 */
	private static Map<String, Cookie> formatCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
	
	/**
	 * 从response响应头Set-Cookie中, 获取sessionId
	 * 
	 * @param response http response
	 * @return sessionId
	 */
	private static String sessionIdFromResponseHeader(HttpServletResponse response) {
		String sessionId = "";
		try {
			Collection<String> setCookiesCollection = response.getHeaders("Set-Cookie");
			if(null == setCookiesCollection || 0 == setCookiesCollection.size()) {
				return sessionId;
			}
			for (String setCookie : setCookiesCollection) {
				if (StringUtils.indexOf(setCookie, CommConst.MT_MERCHANT_SESSION_ID) > -1) {
					sessionId = StringUtils.substring(setCookie, CommConst.MT_MERCHANT_SESSION_ID.length() + 1, setCookie.length());
				}
			}
		} catch (Exception e) {
			loggger.warn("从HTTP RESPONSE Set-Cookie响应头获取sessionId异常", e);
		}
		return sessionId;
	}
}
