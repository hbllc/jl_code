package com.core.fastdfs.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.core.fastdfs.FastDfsException;
import com.core.fastdfs.FastDfsUtil;
import com.core.fastdfs.vo.FastDfsDeleteResult;
import com.core.fastdfs.vo.FastDfsDownloadResult;
import com.core.fastdfs.vo.FastDfsUploadResult;
import com.core.fastdfs.vo.FastDfsUploadResult.FastDfsUploadResponse;
import com.core.fastdfs.vo.FastDfsUploadResult.FastDfsUploadResponseFile;
import com.core.util.JsonUtil;
import com.core.util.SpringPropertiesUtil;

/**
 * Fast DFS 客户端工具类
 * 直连，使用FastDfs官方提供的JAVA API
 * @author huangweiqi
 * 2015-6-25
 */
public abstract class FastDfsClientUtil {

	private static final Logger logger = LogManager.getLogger(FastDfsClientUtil.class);
	
	/**
	 * 是否已初始化
	 */
	private static volatile boolean isInit = false;
	
	/**
	 * 初始化
	 * @throws FastDfsException 
	 */
	public static void init() throws FastDfsException {
		if (isInit) {
			return;
		}
		isInit = true;
		String clientConfPath = SpringPropertiesUtil.getPropertiesValue("fastdfs.javaclient.conf");
		if (clientConfPath == null || clientConfPath.length() == 0) {
			throw new FastDfsException("没有配置fastdfs.javaclient.conf，请先在app.properties或env.properties中配置");
		}
		logger.info("init->clientConfPath:{}", clientConfPath);
		
		try {
			ClientGlobal.init(clientConfPath);
		} catch (IOException | MyException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 客户端操作
	 * @throws IOException 
	 * @throws FastDfsException 
	 * @author huangweiqi
	 */
	public static Map<String, Object> clientOperate(ClientOperateCallback clientOperateCallback) throws IOException, FastDfsException {
		//初始化
		init();
		
		Map<String, Object> map = null;
		
  		TrackerServer trackerServer = null;
  		try {
  			TrackerClient tracker = new TrackerClient();
  			trackerServer = tracker.getConnection();
  				
  	  		StorageClient client = new StorageClient(trackerServer, null);
  	  		try {
  	  			map = clientOperateCallback.doInClientOperate(client);
  			} catch (MyException e) {
  				throw new FastDfsException(e);
  			}
		} finally {
			if (trackerServer != null) {
				trackerServer.close();
			}
		}
  		return map;
	}
	
	/**
	 * 上传单个文件
	 * @param fastDfsFile fastdfs文件对象
	 * @return
	 * @throws IOException
	 * @throws FastDfsException
	 * @author huangweiqi
	 */
	public static FastDfsUploadResult upload(final FastDfsFile fastDfsFile) throws IOException, FastDfsException {
		List<FastDfsFile> fastDfsFileList = new ArrayList<>();
		fastDfsFileList.add(fastDfsFile);
		return upload(fastDfsFileList);
	}
	
	/**
	 * 批量上传文件
	 * @param fastDfsFileList fastdfs文件对象List
	 * @return
	 * @throws IOException
	 * @throws FastDfsException
	 * @author huangweiqi
	 */
	public static FastDfsUploadResult upload(final List<FastDfsFile> fastDfsFileList) 
			throws IOException, FastDfsException {
		if (fastDfsFileList == null || fastDfsFileList.size() == 0) {
			throw new FastDfsException("文件List为空");
		}
		
		logger.info("upload->fastDfsFileList:{}", JsonUtil.toJsonIgnore(fastDfsFileList, 
				Arrays.asList(new String[]{"inputStream"})));
		for (FastDfsFile fastDfsFile : fastDfsFileList) {
			if (fastDfsFile == null) {
				throw new FastDfsException("文件为空");
			}
			
			//校验
			fastDfsFile.validate();
		}
		
		Map<String, Object> map = null;
		try {
			map = clientOperate(new ClientOperateCallback() {
				
				@Override
				public Map<String, Object> doInClientOperate(StorageClient client) 
						throws IOException, MyException, FastDfsException {
					Map<String, Object> map = new HashMap<String, Object>();
					
					for (FastDfsFile fastDfsFile : fastDfsFileList) {
						
						byte[] bytes = IOUtils.toByteArray(fastDfsFile.getInputStream());
						if (bytes == null || bytes.length == 0) {
							throw new FastDfsException("文件大小为空，" + fastDfsFile.getFileName());
						}
						
						String[] results = client.upload_file(bytes, fastDfsFile.getFileExtName(), null);
						logger.info("upload->results:{}", JsonUtil.toJson(results));
						
						if (results == null) {
							throw new FastDfsException("上传后返回结果为空，上传失败，" + fastDfsFile.getFileName());
							
						} else if (results.length != 2) {
							throw new FastDfsException("上传后返回结果长度不等于2，上传失败，" + fastDfsFile.getFileName());
							
						}
						map.put(fastDfsFile.getFileName(), results[0] + "/" + results[1]);
						
					}
					
					return map;
				}
			});
			
			logger.info("upload->map:{}", JsonUtil.toJson(map));
			
		} finally {
			for (FastDfsFile fastDfsFile : fastDfsFileList) {
				InputStream inputStream = fastDfsFile.getInputStream();
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		
		if (map == null) {
			throw new FastDfsException("返回结果为空，上传失败");
		}
		
		List<FastDfsUploadResponseFile> responseFileList = new ArrayList<>();
		
		for (FastDfsFile fastDfsFile : fastDfsFileList) {
			FastDfsUploadResponseFile responseFile = new FastDfsUploadResponseFile();
			responseFile.setFileName(fastDfsFile.getFileName());
			
			Object remoteFilePathObj = map.get(fastDfsFile.getFileName());
			//FastDfs返回的路径
			String remoteFilePath = remoteFilePathObj == null ? "" : remoteFilePathObj.toString();
			if (remoteFilePath.length() == 0) {
				responseFile.setFileID("");
			} else {
				responseFile.setFileID(remoteFilePath);
			}
			
			
			responseFileList.add(responseFile);
		}
		
		FastDfsUploadResponse response = new FastDfsUploadResponse();
		response.setFileList(responseFileList);
		response.setResult(FastDfsUtil.UPLOAD_RESPONSE_RESULT_SUCCESS);
		
		FastDfsUploadResult  result = new FastDfsUploadResult();
		result.setResponse(response);
		
		logger.info("upload->result:{}", JsonUtil.toJson(result));
		return result;
		
	}
	
	/**
	 * 下载单个文件<br />
	 * 本地目录如果不存在会自动创建
	 * @param fastDfsPath FastDfs路径示例：group1/M00/05/83/rBEDm1WM_2SANUs-AAAfXzgg9w4432.jpg
	 * @param localPath 本地路径示例：/data/tmp/pic/apple.jpg
	 * @return
	 * @throws IOException
	 * @throws FastDfsException
	 */
	public static FastDfsDownloadResult download(String fastDfsPath, String localPath) 
			throws IOException, FastDfsException {
		Map<String, String> fastdfsPathAndlocalPathMap = new HashMap<>();
		fastdfsPathAndlocalPathMap.put(fastDfsPath, localPath);
		
		return download(fastdfsPathAndlocalPathMap);
	}
	
	/**
	 * 批量下载文件<br />
	 * 本地目录如果不存在会自动创建
	 * @param fastdfsPathAndlocalPathMap Map<FastDfs路径, 本地路径>    <br />
	 *					FastDfs路径示例：group1/M00/05/83/rBEDm1WM_2SANUs-AAAfXzgg9w4432.jpg   <br />
	 *					本地路径示例：/data/tmp/pic/apple.jpg
	 * @throws FastDfsException 
	 * @throws IOException 
	 * @return 
	 * @author huangweiqi
	 */
	public static FastDfsDownloadResult download(final Map<String, String> fastdfsPathAndlocalPathMap) 
			throws IOException, FastDfsException {
		//下载结果
		final FastDfsDownloadResult downloadResult = new FastDfsDownloadResult();
		
		if (fastdfsPathAndlocalPathMap == null || fastdfsPathAndlocalPathMap.size() == 0) {
			logger.info("download->fastdfsPathAndlocalPathMap is empty");
			return downloadResult;
		}
		logger.info("download->fastdfsPathAndlocalPathMap:{}", JsonUtil.toJson(fastdfsPathAndlocalPathMap));
		
		for (Entry<String, String> fastdfsPathAndlocalPathMapEntry : fastdfsPathAndlocalPathMap.entrySet()) {
			String fastDfsFilePath = fastdfsPathAndlocalPathMapEntry.getKey(); //FastDfs路径
			String localPath = fastdfsPathAndlocalPathMapEntry.getValue(); //本地路径
			
			logger.info("download->fastDfsFilePath:{}", fastDfsFilePath);
			logger.info("download->localPath:{}", localPath);
			
			if (localPath == null || localPath.length() == 0) {
				throw new FastDfsException("本地文件路径为空 ，下载失败");
			}
			
			if (localPath.lastIndexOf(File.separator) == -1) {
				throw new FastDfsException("本地文件路径不合法 ，下载失败，" + localPath);
			}
			
			String localFolder = localPath.substring(0, localPath.lastIndexOf(File.separator));
			File localFolderFile = new File(localFolder);
			if (!localFolderFile.exists()) {
				localFolderFile.mkdirs();
			}
			
			if (fastDfsFilePath == null || fastDfsFilePath.length() == 0) {
				throw new FastDfsException("远端文件路径为空，下载失败");
			}
			
			String[] fastDfsFilePathArr = fastDfsFilePath.split("/", 2);
			if (fastDfsFilePathArr == null) {
				throw new FastDfsException("远端文件路径不合法，下载失败 ，" + fastDfsFilePath + "，" + localPath);
			}
			
			if (fastDfsFilePathArr.length != 2) {
				throw new FastDfsException("远端文件路径不合法 ，下载失败，" + fastDfsFilePath + "，" + localPath);
			}
			
			String groupName = fastDfsFilePathArr[0];
			String remoteFileName = fastDfsFilePathArr[1];
			
			if (groupName == null || groupName.length() == 0) {
				throw new FastDfsException("groupName为空，下载失败 ，" + fastDfsFilePath + "，" + localPath);
			}
			
			if (remoteFileName == null || remoteFileName.length() == 0) {
				throw new FastDfsException("remoteFileName为空，下载失败 ，" + fastDfsFilePath + "，" + localPath);
			}
			
		}
		
		clientOperate(new ClientOperateCallback() {
			
			@Override
			public Map<String, Object> doInClientOperate(StorageClient client) 
					throws IOException, MyException, FastDfsException {
				
				//成功的FastDfs路径list
				List<String> successList = new ArrayList<>();
				downloadResult.setSuccessList(successList);
				//失败的FastDfs路径List
				List<String> errorList = new ArrayList<>();
				downloadResult.setErrorList(errorList);
				
				for (Entry<String, String> fastdfsPathAndlocalPathMapEntry : fastdfsPathAndlocalPathMap.entrySet()) {
					String fastDfsFilePath = fastdfsPathAndlocalPathMapEntry.getKey(); //FastDfs路径
					String localPath = fastdfsPathAndlocalPathMapEntry.getValue(); //本地路径
					
					logger.info("download opt->fastDfsFilePath:{}", fastDfsFilePath);
					logger.info("download opt->localPath:{}", localPath);
					
					String[] fastDfsFilePathArr = fastDfsFilePath.split("/", 2);
					
					String groupName = fastDfsFilePathArr[0];
					String remoteFileName = fastDfsFilePathArr[1];
					
					File localFile = new File(localPath);
					if (localFile.exists()) {
						localFile.delete();
					}
					
					int errno = 0; //下载错误码
					try (
							FileOutputStream out = new FileOutputStream(localFile);
						) {
						errno = client.download_file(groupName, remoteFileName, new DownloadFastDfsFileWriter(out));
						if (errno == 0) {
							successList.add(fastDfsFilePath);
						} 
					} finally {
						if (errno != 0) {
							logger.warn("download opt->下载失败，错误码:{}，{}, {}", errno, fastDfsFilePath, localPath);
							errorList.add(fastDfsFilePath);
							
							if (localFile.exists()) {
								localFile.delete();
							}
							
						}
					}
					
				}
				
				if (fastdfsPathAndlocalPathMap.size() == successList.size()) {
					downloadResult.setResult(FastDfsDownloadResult.DOWNLOAD_RESULT_ALL_SUCCESS);
				} else if (fastdfsPathAndlocalPathMap.size() > successList.size() && successList.size() != 0) {
					downloadResult.setResult(FastDfsDownloadResult.DOWNLOAD_RESULT_PARTIAL_SUCCESS);
				} else {
					downloadResult.setResult(FastDfsDownloadResult.DOWNLOAD_RESULT_ALL_ERROR);
				}
				
				return null;
			}
		});
		
		logger.info("download->downloadResult:{}", JsonUtil.toJson(downloadResult));
		return downloadResult;
		
	}
	
	/**
	 * 删除单个文件
	 * @param fastdfsPath fastDfs路径
	 * @return
	 * @throws IOException
	 * @throws FastDfsException
	 */
	public static FastDfsDeleteResult delete(String fastdfsPath)
			throws IOException, FastDfsException {
		List<String> fastdfsPathList = new ArrayList<>();
		fastdfsPathList.add(fastdfsPath);
		
		return delete(fastdfsPathList);
	}
	
	/**
	 * 批量删除文件
	 * @param fastdfsPathList fastDfs路径list
	 * @throws IOException
	 * @throws FastDfsException
	 */
	public static FastDfsDeleteResult delete(List<String> fastdfsPathList) 
			throws IOException, FastDfsException {
		final String prefix = "delete->";
		final FastDfsDeleteResult deleteResult = new FastDfsDeleteResult(); //删除结果
		
		if (fastdfsPathList == null || fastdfsPathList.size() == 0) {
			logger.info("{}fastdfsPathList is empty", prefix);
			return deleteResult;
		}
		logger.info("{}fastdfsPathList:{}", prefix, JsonUtil.toJson(fastdfsPathList));
		
		final Set<String> fastdfsPathSet = new HashSet<>(fastdfsPathList);
		logger.info("{}fastdfsPathSet:{}", prefix, JsonUtil.toJson(fastdfsPathSet));
		
		for (String fastDfsFilePath : fastdfsPathSet) {
			logger.info("{}fastDfsFilePath:{}", prefix, fastDfsFilePath);
			
			if (fastDfsFilePath == null || fastDfsFilePath.length() == 0) {
				throw new FastDfsException("远端文件路径为空，下载失败");
			}
			
			String[] fastDfsFilePathArr = fastDfsFilePath.split("/", 2);
			if (fastDfsFilePathArr == null) {
				throw new FastDfsException("远端文件路径不合法，下载失败 ，" + fastDfsFilePath);
			}
			
			if (fastDfsFilePathArr.length != 2) {
				throw new FastDfsException("远端文件路径不合法 ，下载失败，" + fastDfsFilePath);
			}
			
			String groupName = fastDfsFilePathArr[0];
			String remoteFileName = fastDfsFilePathArr[1];
			
			if (groupName == null || groupName.length() == 0) {
				throw new FastDfsException("groupName为空，下载失败 ，" + fastDfsFilePath);
			}
			
			if (remoteFileName == null || remoteFileName.length() == 0) {
				throw new FastDfsException("remoteFileName为空，下载失败 ，" + fastDfsFilePath);
			}
		}
		
		clientOperate(new ClientOperateCallback() {
			
			@Override
			public Map<String, Object> doInClientOperate(StorageClient client) 
					throws IOException, MyException, FastDfsException {
				
				//成功的FastDfs路径list
				List<String> successList = new ArrayList<>();
				deleteResult.setSuccessList(successList);
				//失败的FastDfs路径List
				List<String> errorList = new ArrayList<>();
				deleteResult.setErrorList(errorList);
				
				for (String fastDfsFilePath : fastdfsPathSet) {
					logger.info("delete opt->fastDfsFilePath:{}", fastDfsFilePath);
					
					String[] fastDfsFilePathArr = fastDfsFilePath.split("/", 2);
					
					String groupName = fastDfsFilePathArr[0];
					String remoteFileName = fastDfsFilePathArr[1];
					
					int errno = 0; //错误码
					try {
						errno = client.delete_file(groupName, remoteFileName);
						if (errno == 0) {
							successList.add(fastDfsFilePath);
						} 
					} finally {
						if (errno != 0) {
							logger.warn("delete opt->删除失败，错误码:{}，{}", errno, fastDfsFilePath);
							errorList.add(fastDfsFilePath);
						}
					}
					
				}
				
				if (fastdfsPathSet.size() == successList.size()) {
					deleteResult.setResult(FastDfsDeleteResult.DELETE_RESULT_ALL_SUCCESS);
				} else if (fastdfsPathSet.size() > successList.size() && successList.size() != 0) {
					deleteResult.setResult(FastDfsDeleteResult.DELETE_RESULT_PARTIAL_SUCCESS);
				} else {
					deleteResult.setResult(FastDfsDeleteResult.DELETE_RESULT_ALL_ERROR);
				}
				
				return null;
			}
		});
		
		logger.info("{}deleteResult:{}", prefix, JsonUtil.toJson(deleteResult));
		return deleteResult;
	}

	public static boolean isInit() {
		return isInit;
	}

	public static void setInit(boolean isInit) {
		FastDfsClientUtil.isInit = isInit;
	}
	
	
	
}
