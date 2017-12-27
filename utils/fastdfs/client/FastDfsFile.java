package com.core.fastdfs.client;

import java.io.InputStream;
import java.io.Serializable;

import com.core.fastdfs.FastDfsException;

/**
 * FastDfs文件
 * @author huangweiqi
 * 2015-6-25
 */
public class FastDfsFile implements Serializable {

	private static final long serialVersionUID = -3825145856899569910L;

	/**
	 * 输入流
	 */
	private InputStream inputStream;
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件后缀名（无需传，自动从fileName获取）
	 */
	private String fileExtName;
	
	/**
	 * 校验
	 * @throws FastDfsException
	 */
	public void validate() throws FastDfsException {
		fileName = fileName == null ? "" : fileName.trim();
		
		if (fileName.length() == 0) {
			throw new FastDfsException("文件名为空");
		}
		
		if (fileName.lastIndexOf(".") == -1) {
			throw new FastDfsException("文件名请带上后缀名");
		}
		
		String fileExtName = getFileExtName();
		if (fileExtName == null || fileExtName.length() == 0) {
			throw new FastDfsException("文件名请带上后缀名。");
		}
		
		if (fileExtName.length() > 6) {
			throw new FastDfsException("文件后缀名长度不超过6");
		}
		
		if (inputStream == null) {
			throw new FastDfsException("输入流为空，" + fileName);
		}
	}
	
	/**
	 * 获取文件后缀名
	 * @return
	 */
	public String getFileExtName() {
		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		this.fileExtName = fileExtName;
		return this.fileExtName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
