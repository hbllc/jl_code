package com.core.fastdfs.vo;

import java.io.Serializable;
import java.util.List;

/**
 * FastDfs上传结果对象
 * @author huangweiqi
 * 2015-2-5
 */
public class FastDfsUploadResult implements Serializable {

	private static final long serialVersionUID = 2024449073884272987L;

	private FastDfsUploadResponse response;
	
	
	public static class FastDfsUploadResponse implements Serializable {

		private static final long serialVersionUID = 4047922840238689505L;
		//0 成功
		private String result;
		
		private List<FastDfsUploadResponseFile> fileList;

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public List<FastDfsUploadResponseFile> getFileList() {
			return fileList;
		}

		public void setFileList(List<FastDfsUploadResponseFile> fileList) {
			this.fileList = fileList;
		}
		
	}
	
	public static class FastDfsUploadResponseFile implements Serializable {

		private static final long serialVersionUID = -566232158497514417L;
		//返回文件名
		private String fileName;
		//返回URL
		private String fileID;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileID() {
			return fileID;
		}

		public void setFileID(String fileID) {
			this.fileID = fileID;
		}
		
	}

	public FastDfsUploadResponse getResponse() {
		return response;
	}

	public void setResponse(FastDfsUploadResponse response) {
		this.response = response;
	}
	
	
	
}
