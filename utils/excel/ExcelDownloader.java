package com.xgd.boss.core.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xgd.boss.core.vo.Response;

/** 
 * @Description: excel文件下载
 * @author tangchunmiao
 * @date 2016年10月21日 上午10:29:23 
 */
public class ExcelDownloader {
	private static final Log logger = LogFactory.getLog(ExcelDownloader.class);
	private static final String FILE_FORMAT = "yyyy_MM_dd_HH_mm_ss";
	
	/**
	 * 
	* @Description: 对导入文件的基本检测以及读取
	* @param stream 文件流
	* @param filename  导入的文件名
	* @return Response<String>   
	 */
	public static Response<String> saveExcelfile(InputStream stream, String filename){ 
		Response<String> response = new Response<String>();
		response.setSuccess(true);
		//得到保存文件名
         String tempFileName = getTempExcelFileName(filename);  
         String pathName = System.getProperty("java.io.tmpdir")
        		 + File.separator + tempFileName;
         logger.info("****download excel pathName :" + pathName);
         
        File importFile = new File(pathName);
		try {
			//下载保存
			saveFile(stream, pathName);
			//返回文件路径
			response.setData(pathName);
		} catch (Exception e) {
			logger.error(filename + "文件导入异常:" + e.getMessage());
			//异常，删除文件
			if (importFile.exists()){
				importFile.delete();
			}
			return response.getErrorResponse("导入失败,原因: " + e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * 保存文件到服务器本地
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static void saveFile(InputStream stream, String pathName) throws Exception {
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(pathName);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
		} catch (Exception e) {
			logger.error(pathName + "文件下载异常:" + e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fs);
			IOUtils.closeQuietly(stream);
		}
		logger.info(pathName + "文件保存成功！");
	}
	
	
	/**
	 * 
	* @Description: 获取临时文件名(防止短时间内重复导入文件覆盖的问题)
	* @param fileName 
	* @return String   
	* @throws
	 */
	private static String getTempExcelFileName(String fileName) {
		SimpleDateFormat sdf = new SimpleDateFormat(FILE_FORMAT);
		java.util.Date date = new java.util.Date();
		String dirname = sdf.format(date);
		return fileName.endsWith(".xls") ? dirname + ".xls" : dirname + ".xlsx";
	}
}
