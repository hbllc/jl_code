package com.core.fastdfs.client;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.fastdfs.DownloadCallback;

/**
 * 下载FastDfs文件writer
 * @author huangweiqi
 * 2015-6-26
 */
public class DownloadFastDfsFileWriter implements DownloadCallback {

	private static final Logger logger = LogManager.getLogger(DownloadFastDfsFileWriter.class);
	
	/**
	 * 文件输出流
	 */
	private FileOutputStream out;
	
	public DownloadFastDfsFileWriter(FileOutputStream out) {
		super();
		this.out = out;
	}


	@Override
	public int recv(long file_size, byte[] data, int bytes) {
		
		try {
			
			this.out.write(data, 0, bytes);
			
		} catch (IOException e) {
			logger.catching(e);
			return -1;
		}
		
		return 0;
	}
	
	

}
