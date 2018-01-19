package com.xgd.boss.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import com.xgd.boss.core.excel.ExcelType;

/** 
 * @Description: 本地文件工具类
 * @author tangchunmiao
 * @date 2016年9月20日 下午5:46:17 
 */
public class FileUtil {
	/**
	 * 获取系统临时文件目录
	 * @return
	 */
	public static String getTempPath(){
		return System.getProperty("java.io.tmpdir");
	}
	/**
	 * 
	* @Description: 获取txt临时目录文件名
	* @param fileName,可以是id或者某个名字  
	* @return String   
	 */
	public static String getTempTxtPathName(String fileName){
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("java.io.tmpdir"));
		sb.append(File.separator).append(fileName);
		sb.append(".txt");
		return sb.toString();
	}
	
	/**
	 * 
	* @Description: 获取excel临时目录文件名
	* @param fileName,可以是id或者某个名字  
	* @return String   
	 */
	public static String getTempExcelPathName(String fileName){
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("java.io.tmpdir"));
		sb.append(File.separator).append(fileName);
		sb.append(ExcelType.EXCEL_XLS.getKey());
		return sb.toString();
	}
	 /**
		 * 下载文件
		 * @param response HttpServletResponse对象
		 * @param filePath 文件物理路径
		 * @param isDel 下载完成后，是否删除被下载的文件
	     * @throws IOException 
		 */
		public static void downloadFile(HttpServletResponse response,
				String filePath, boolean isDel) throws IOException {
			OutputStream out = null;
			InputStream in = null;
			File file = null;
			try {
				file = new File(filePath);
				if (!file.exists()) {
					throw new FileNotFoundException("下载的文件不存在");
				}
				response.setContentType("application/force-download");
				String fileName = URLEncoder.encode(file.getName(), "UTF-8");
				response.setHeader("Location", fileName);
				response.setHeader("Content-Disposition", 
						"attachment;filename=" + fileName);
				out = response.getOutputStream();
				in = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int i = -1;
				while ((i = in.read(buffer)) != -1) {
					out.write(buffer, 0, i);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (file != null) {
					//删除下载文件
					if (isDel) {
						file.delete();
					}
				}
			}
		}
}
