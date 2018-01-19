package com.xgd.boss.core.mybatis;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
/**
 * 解决mybatis配置错误导致启动卡死的bug（捕捉异常并且抛出）
 * @author weiqingding
 *
 */
public class FixedSqlSessionFactory extends SqlSessionFactoryBean {
	private static final Log logger = LogFactory.getLog(FixedSqlSessionFactory.class);
	private static SqlSessionFactory sqlSessionFactory;
    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        try {
        	sqlSessionFactory =  super.buildSqlSessionFactory();
        }catch (Exception e){
            logger.error(e, e);
        }finally {
            ErrorContext.instance().reset();
        }
        return sqlSessionFactory;
    }
    
    public static void addInterceptor(Interceptor interceptor){
    	sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }
}
