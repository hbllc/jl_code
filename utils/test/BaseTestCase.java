package com.core.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/conf/**/applicationContext-*.xml"})
public abstract class BaseTestCase {

	@BeforeClass
	public static void beforeClass() {
	}
	
	@Before
	public void init() {
		System.out.println("\n\nstart test --> ");
	}

	@After
	public void destory() {
		System.out.println("end test --> \n\n");
	}
	
}
