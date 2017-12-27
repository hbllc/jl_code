package com.core.test;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;  
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  

import com.core.constant.CommonConstant;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({CommonConstant.SPRING_CONFIG_PATH, "classpath:/conf/spring-mvc.xml"})
public abstract class BaseSpringMvcTestCase {

	@Resource
	protected WebApplicationContext webApplicationContext;
	
	protected MockMvc mockMvc;
	/**
	 * Http Request
	 */
	protected MockHttpServletRequest request = new MockHttpServletRequest();
	/**
	 * Http Response
	 */
	protected MockHttpServletResponse response = new MockHttpServletResponse();
	
	@BeforeClass
	public static void beforeClass() {
		try {
			Class.forName("javax.servlet.AsyncContext");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void init() {
		System.out.println("\n\nstart test --> ");
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void destory() {
		System.out.println("end test --> \n\n");
	}
	
	/**
	 * 发送请求
	 * @param requestBuilder request构建器
	 */
	protected String sendRequest(MockHttpServletRequestBuilder requestBuilder) {
		try {
			ResultActions resultActions = mockMvc.perform(requestBuilder);
			resultActions.andDo(MockMvcResultHandlers.print());
			MvcResult mvcResult = resultActions.andReturn();
			return mvcResult.getResponse().getContentAsString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected void sendRequest(String url,Map<String,Object> paramMap,Map<String,Object> sessionMap){
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
		if(paramMap!=null){
			for(Entry<String,Object> entry: paramMap.entrySet()){
				requestBuilder.param(entry.getKey(), entry.getValue()+"");
			}
		}
		if(sessionMap!=null){
			for(Entry<String,Object> entry: sessionMap.entrySet()){
				requestBuilder.sessionAttr(entry.getKey(), entry.getValue()+"");
			}
		}
		sendRequest(requestBuilder);
	}
	
}
