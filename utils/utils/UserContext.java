package com.xgd.boss.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xgd.boss.core.vo.LoginUser;

public class UserContext {
	/**
	 * 返回当前登录用户
	 * @return
	 */
	public static LoginUser getCurrentUser(){
		Map<String, Object> map =null;
		HttpSession session = getCurrsession();
		if(session!=null){
			map =(Map<String, Object>) session.getAttribute("currentUser");
		}
		if(map==null) return null;
		LoginUser user = new LoginUser();
		user.setCustomerId((String)map.get("customerId"));
		user.setLoginNo((String)map.get("loginNo"));
		user.setLoginEmail((String)map.get("loginEmail"));
		user.setLoginPhone((String)map.get("loginPhone"));
		user.setName((String)map.get("name"));
		user.setDepts(parseArray(map, "depts"));
		user.setRoles(parseArray(map, "roles"));
		user.setFirms(parseArray(map, "firms"));
		return user;
	}
	
	private static String[] parseArray(Map<String, Object> map,String key){
		try {
			Object obj = map.get(key);
			if(obj!=null&&!obj.equals("")){
				return (String[])obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取session
	 * @return
	 */
	public static HttpSession getCurrsession(){
		ServletRequestAttributes sra = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		if(sra==null) return null;
		HttpServletRequest request = sra.getRequest();
		return request!=null?request.getSession(false):null;
	}
	/**
	 * 获取当前用户部门ids
	 * @return
	 * @throws Exception 
	 */
	public static String getUserDepts() throws Exception {
		Map<String, Object> map =null;
		String[] ids = null;
		HttpSession session = getCurrsession();
		if(session!=null){
			map =(Map<String, Object>) session.getAttribute("currentUser");
		}else return null;
		try {
			ids = (String[]) map.get("depts");
		} catch (Exception e) {
		}
		if(ids==null || ids.length==0)
			return null;
		return StringUtils.join(ids, ",");
	}
	/**
	 * 获取当前用户机构ids
	 * @return
	 * @throws Exception 
	 */
	public static String getUserFirms() throws Exception {
		Map<String, Object> map =null;
		String[] ids = null;
		HttpSession session = getCurrsession();
		if(session!=null){
			map =(Map<String, Object>) session.getAttribute("currentUser");
		}else return null;
		try {
			ids = (String[]) map.get("firms");
		} catch (Exception e) {
		}
		if(ids==null || ids.length==0)
			return null;
		return StringUtils.join(ids, ",");
	}
	/**
	 * 获取当前用户角色ids
	 * @return
	 * @throws Exception 
	 */
	public static List<String> getUserRoles() throws Exception {
		Map<String, Object> map =null;
		HttpSession session = getCurrsession();
		if(session!=null){
			map =(Map<String, Object>) session.getAttribute("currentUser");
		}
		if(null!=map.get("roles") && !"".equals(map.get("roles"))){
			String[] ids = (String[]) map.get("roles");
			List<String> roleList = Arrays.asList(ids);
			return roleList;
		}else{
			return new ArrayList<>();
		}
		
	}
	/**
	 * 判断是否为超级管理员
	 * @return
	 * @throws Exception 
	 */
	public static boolean checkIsAdmin(){
		boolean flag =false;
		try {
			flag = getUserRoles().contains("sadmin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
