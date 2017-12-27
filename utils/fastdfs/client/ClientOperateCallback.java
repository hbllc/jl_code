package com.core.fastdfs.client;

import java.io.IOException;
import java.util.Map;

import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;

import com.core.fastdfs.FastDfsException;

/**
 * 客户端操作回调
 * @author huangweiqi
 * 2015-6-25
 */
public interface ClientOperateCallback {

	/**
	 * 在客户端操作中的处理
	 * @param client StorageClient
	 * @return 任意对象
	 */
	public Map<String, Object> doInClientOperate(StorageClient client) 
			throws IOException, MyException, FastDfsException;
	
}
