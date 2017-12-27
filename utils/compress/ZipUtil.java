package com.core.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.core.compress.vo.ZipOption;
import com.core.util.JsonUtil;

/**
 * ZIP文件压缩工具类
 * 注意必须多实例使用，不能同一个实例混用
 * @author huangweiqi 
 * 2015-2-13
 */
public class ZipUtil {
	
	private static final Logger logger = LogManager.getLogger(ZipUtil.class);
	
	/**系统默认编码*/
	public static final String DEFAULT_ENCODING = "UTF-8";
	/**GBK编码*/
	public static final String GBK_ENCODING = "GBK";
	
	/**
	 * 文件或文件夹的基路径
	 */
	private String fileBasePath;
	/**
	 * 顶级路径名称
	 */
	private String topPathName;
	/**
	 * 选项参数
	 */
	private ZipOption option;
	/**
	 * 是否已处理顶层目录
	 */
	private boolean hasHandlerTopDir = false;
	
	/**
	 * 压缩文件或文件夹
	 * @param file 文件或文件夹
	 * @param zipPkgPath zip包的存放位置
	 * @return 成功则返回true
	 * @throws IOException 
	 */
	public boolean compressZip(File file, String zipPkgPath, ZipOption option) throws IOException {
		if (file == null) {
			throw new IllegalStateException("file 为空 ");
		}
		
		if (!file.exists()) {
			throw new IllegalStateException("file不存在， " + file.getAbsolutePath());
		}
		
		logger.info("compressZipAll->file:{}", file.getAbsolutePath());
		logger.info("compressZipAll->zipPkgPath:{}", zipPkgPath);
		
		this.option = option;
		logger.info("compressZipAll->option:{}", JsonUtil.toJson(option));
		
		ZipArchiveOutputStream zaos = null;
		try {
			File zipFile = new File(zipPkgPath);
			//删除已存在的zip包
			if (zipFile.exists()) {
				throw new IllegalStateException("改zip包已存在");
			}
			zaos = new ZipArchiveOutputStream(
					zipFile);
			zaos.setEncoding(GBK_ENCODING);
			//设置文件或文件夹基路径
			fileBasePath = file.getAbsolutePath().substring(
					0, file.getAbsolutePath().indexOf(file.getName()));
			logger.info("compressZipAll->fileBasePath:{}", fileBasePath);
			
			topPathName = file.getName();
			if (!file.isDirectory()) {
				topPathName = "";
			}
			logger.info("compressZipAll->topPathName:{}", topPathName);
			
			compressZip(file, zaos);
		} finally {
			if (zaos != null) {
				zaos.closeArchiveEntry();
				zaos.finish();
				zaos.close();
			}
		}
		return true;
	}
	
	/**
	 * 压缩文件或文件夹
	 * @param file 
	 * @param zaos
	 * @return
	 * @throws IOException 
	 */
	private boolean compressZip(File file, ZipArchiveOutputStream zaos) throws IOException {
		//获取文件或文件夹在压缩包中的目录结构
		String entryName = getEntryName(file);
		logger.info("compressZip->filePath:{}, entryPath:{}", file.getAbsolutePath(), entryName);
		
		if (!option.getIsTopPath()) {
			
			if (!hasHandlerTopDir) { //处理顶层目录，无需创建zip entry
				hasHandlerTopDir = true;
				if (!file.isDirectory()) { //如果顶层是文件，则需要创建zip entry
					zaos.putArchiveEntry(new ZipArchiveEntry(entryName));
				} else {
					logger.info("compressZip->top path, no entry has been created");
				}
				
			} else {
				zaos.putArchiveEntry(new ZipArchiveEntry(entryName));
			}
			
		} else {
			zaos.putArchiveEntry(new ZipArchiveEntry(entryName));
		}
		
		//如果是文件夹，需要进行递归打包
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			File childFile = null;
			for (int i = 0, len = childFiles.length; i < len; i++) {
				childFile = childFiles[i];
				compressZip(childFile, zaos);
			}
		} else {  //如果是文件则进行直接打包
			if (!file.exists()) {
				logger.info("compressZip->file not exist:{}", file.getAbsolutePath());
				return false;
			}
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zaos);
			} finally {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
		}
		return true;
	}
	
	/**
	 * 获取文件或文件夹在压缩包中的目录结构
	 * @param file 文件夹或文件
	 */
	private String getEntryName(File file) {
		if (option.getIsTopPath()) {
			if (file.isDirectory()) {
				return file.getAbsolutePath().substring(fileBasePath.length()) + File.separator;
			} else {
				return file.getAbsolutePath().substring(fileBasePath.length());
			}
		} else {
			String baseEntryPath = file.getAbsolutePath().substring(fileBasePath.length() + topPathName.length());
			if (File.separator.equals(baseEntryPath)) {
				baseEntryPath = "";
			}
			if (baseEntryPath.startsWith(File.separator)) {
				baseEntryPath = baseEntryPath.substring(1);
			}
			if (file.isDirectory()) {
				return baseEntryPath + File.separator;
			} else {
				return baseEntryPath;
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
//		ZipUtil zipUtil = new ZipUtil();
//		long startTime = System.currentTimeMillis();
//		zipUtil.compressZip(new File("D:\\data\\mt_merchant_web_logs"), 
//				"D:\\data\\temp1227.zip");
//		long endTime = System.currentTimeMillis();
//		System.out.println("cost time:" + (endTime - startTime) );
	}
}
