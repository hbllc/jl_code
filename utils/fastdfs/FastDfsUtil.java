package com.core.fastdfs;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.core.fastdfs.vo.FastDfsUploadResult;
import com.core.util.JsonUtil;

/**
 * FastDfs工具类(For PHP)
 * @author huangweiqi
 * 2015-2-5
 */
public class FastDfsUtil {

	private static final Logger logger = LogManager.getLogger(FastDfsUtil.class);
	
	/**MD5前缀*/
	public static final String MD5_PREFIX = "JIALIANXGD0E6E5991";
	
	/**上传URL的模式*/
	public static final String UPLOAD_URL_PATTERN = "http://%s/uploadMerchantPhoto.php";
	/**上传URL的模式*/
	public static final String UPLOAD_TOTAL_URL_PATTERN = "http://%s/uploadFile.php";
	
	/**上传响应结果成功*/
	public static final String UPLOAD_RESPONSE_RESULT_SUCCESS = "0";
	
	/**
	 * 上传
	 * @param serverHost 服务器Host
	 * @param serverPort 服务器端口
	 * @param inputStream 输入流
	 * @param fileName 文件名
	 * @return
	 * @exception FastDfsException
	 */
	public static FastDfsUploadResult upload(String serverHost, Integer serverPort, 
			InputStream inputStream, String fileName) throws FastDfsException  {
		final String prefix = "upload->";
		logger.info("{}开始上传文件 ~~~~~ file = {}", prefix,fileName);
		
		//获取host + 端口号
		String hostAndPort = getHostAndPort(serverHost, serverPort);
		// 上传的url
		String uploadUrl = String.format(UPLOAD_URL_PATTERN, hostAndPort);
		logger.info("{}uploadUrl:{}", prefix, uploadUrl);
		
		// 换行符
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String timestampGet = FastDfsHelper.getTimeStamp();
		String tokenGet = FastDfsHelper.MD5(MD5_PREFIX + timestampGet);
		String json = "";
		
		HttpURLConnection httpURLConnection = null;
		DataOutputStream dataOutputStream = null;
		try {
			URL url = new URL(uploadUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			
			String[] uploadFiles = { "ts", "token",""};
			
			String s;
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0, len = uploadFiles.length; i < len; i++) {
				
				s = twoHyphens + boundary + end;
				dataOutputStream.writeBytes(s);
				sb.append(s);
				
				if (i == 0) {
					s = "Content-Disposition: form-data; name=\"ts\"" + end + end + timestampGet + end;
					dataOutputStream.writeBytes(s);
					sb.append(s);
				} else if (i == 1) {
					s = "Content-Disposition: form-data; name=\"token\"" + end + end + tokenGet + end;
					dataOutputStream.writeBytes(s);
					sb.append(s);
				} else {
					s = "Content-Disposition: form-data; name=\"file\"; filename="+fileName+ end + end;
					dataOutputStream.writeBytes(s);
					sb.append(s);
					
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					while ((length = inputStream.read(buffer)) != -1) {
						dataOutputStream.write(buffer, 0, length);
					}
					dataOutputStream.writeBytes(end);
					sb.append(end);
				}
				
			}
			
			s = twoHyphens + boundary + twoHyphens + end;
			dataOutputStream.writeBytes(s);
			dataOutputStream.flush();
			sb.append(s);

			logger.info( "{}sb =\n {}", prefix, sb.toString());
			
			// 定义BufferedReader输入流来读取URL的响应
			InputStream is = httpURLConnection.getInputStream();
			int ch;
			StringBuffer stringBuffer = new StringBuffer();
			while ((ch = is.read()) != -1) {
				stringBuffer.append((char) ch);
			}
			json = stringBuffer.toString();
			
		} catch (MalformedURLException e) {
			throw new IllegalStateException("url格式不对");
		} catch (ProtocolException e) {
			throw new IllegalStateException("HTTP请求方法不对");
		} catch (IOException e) {
			throw new FastDfsException("上传文件IO异常", e);
		}  finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.catching(e);
				}
			}
			
			if (dataOutputStream != null) {
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					logger.catching(e);
				}
			}
			
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		
		logger.info( "{}文件服务器返回的json:{}", prefix, json);
		
		FastDfsUploadResult result = JsonUtil.fromJson(json, FastDfsUploadResult.class);
		return result;
	}
	
	/**
	 * 上传
	 * @param serverHost 服务器Host
	 * @param serverPort 服务器端口
	 * @param inputStream 输入流
	 * @param fileName 文件名
	 * @return
	 * @exception FastDfsException
	 */
	public static FastDfsUploadResult uploadTotalFile(String serverHost, Integer serverPort, 
			InputStream inputStream, String fileName) throws FastDfsException  {
		final String prefix = "upload->";
		logger.info("{}开始上传文件 ~~~~~ file = {}", prefix,fileName);
		
		//获取host + 端口号
		String hostAndPort = getHostAndPort(serverHost, serverPort);
		// 上传的url
		String uploadUrl = String.format(UPLOAD_TOTAL_URL_PATTERN, hostAndPort);
		logger.info("{}uploadUrl:{}", prefix, uploadUrl);
		
		// 换行符
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String timestampGet = FastDfsHelper.getTimeStamp();
		String tokenGet = FastDfsHelper.MD5(MD5_PREFIX + timestampGet);
		String json = "";
		
		HttpURLConnection httpURLConnection = null;
		DataOutputStream dataOutputStream = null;
		try {
			URL url = new URL(uploadUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			
			String[] uploadFiles = { "ts", "token",""};
			
			String s;
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0, len = uploadFiles.length; i < len; i++) {
				
				s = twoHyphens + boundary + end;
				dataOutputStream.writeBytes(s);
				sb.append(s);
				
				if (i == 0) {
					s = "Content-Disposition: form-data; name=\"ts\"" + end + end + timestampGet + end;
					dataOutputStream.writeBytes(s);
					sb.append(s);
				} else if (i == 1) {
					s = "Content-Disposition: form-data; name=\"token\"" + end + end + tokenGet + end;
					dataOutputStream.writeBytes(s);
					sb.append(s);
				} else {
					s = "Content-Disposition: form-data; name=\"file\"; filename="+fileName+ end + end;
					dataOutputStream.writeBytes(s);
					sb.append(s);
					
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					while ((length = inputStream.read(buffer)) != -1) {
						dataOutputStream.write(buffer, 0, length);
					}
					dataOutputStream.writeBytes(end);
					sb.append(end);
				}
				
			}
			
			s = twoHyphens + boundary + twoHyphens + end;
			dataOutputStream.writeBytes(s);
			dataOutputStream.flush();
			sb.append(s);

			logger.info( "{}sb =\n {}", prefix, sb.toString());
			
			// 定义BufferedReader输入流来读取URL的响应
			InputStream is = httpURLConnection.getInputStream();
			int ch;
			StringBuffer stringBuffer = new StringBuffer();
			while ((ch = is.read()) != -1) {
				stringBuffer.append((char) ch);
			}
			json = stringBuffer.toString();
			
		} catch (MalformedURLException e) {
			throw new IllegalStateException("url格式不对");
		} catch (ProtocolException e) {
			throw new IllegalStateException("HTTP请求方法不对");
		} catch (IOException e) {
			throw new FastDfsException("上传文件IO异常", e);
		}  finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.catching(e);
				}
			}
			
			if (dataOutputStream != null) {
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					logger.catching(e);
				}
			}
			
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		
		logger.info( "{}文件服务器返回的json:{}", prefix, json);
		
		FastDfsUploadResult result = JsonUtil.fromJson(json, FastDfsUploadResult.class);
		return result;
	}
	
	/**
	 * 下载
	 * @param downloadPath 下载路径
	 * @param localDirectory 本地文件夹地址
	 * @param fileName 文件名
	 * @return
	 */
	public static void downloadFile(String downloadPath, 
			String localDirectory, String fileName) throws FastDfsException {
		final String prefix = "downloadFile->";
		logger.info("{}localDirectory:{}", prefix, localDirectory);
		logger.info("{}fileName:{}", prefix, fileName);
		
		File dir = new File(localDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		HttpURLConnection httpURLConnection = null;
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			URL url = new URL(downloadPath);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			inputStream = httpURLConnection.getInputStream();
			
			os = new FileOutputStream(localDirectory + fileName);
			
			byte[] buff = new byte[1024];
			int hasRead = 0;
			while ((hasRead = inputStream.read(buff)) > 0) {
				os.write(buff, 0, hasRead);
			}
			
		} catch (MalformedURLException e) {
			throw new IllegalStateException("url格式不对");
		} catch (IOException e) {
			throw new FastDfsException("下载文件IO异常", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.catching(e);
				}
			}
			
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.catching(e);
				}
			}
			
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		
	}
	
	/**
	 * 获取下载的url
	 * @param serverHost 服务器Host
	 * @param serverPort 服务器端口
	 * @param fastDfsFilePath fastdfs文件路径
	 * @return
	 */
	public static String getDownloadURL(String serverHost, Integer serverPort, 
			String fastDfsFilePath) {
		if (fastDfsFilePath == null || fastDfsFilePath.trim().length() == 0) {
			throw new IllegalStateException("fastDfsFilePath 不能为空");
		}
		
		String ts = FastDfsHelper.getTimeStamp();
		int M00_index = fastDfsFilePath.indexOf("M00");
		if (M00_index < 0) {
			throw new IllegalStateException("M00 does not exist in fastDfsFilePath");
		}
		String MOO = fastDfsFilePath.substring(M00_index);
		String token_s = MOO + MD5_PREFIX + ts;
		String token = FastDfsHelper.MD5(token_s);
		
		// 把图片获取回来，保存在这个路径下
		String downloadURL = String.format("http://%s/%s?ts=%s&token=%s", getHostAndPort(serverHost, serverPort), 
				fastDfsFilePath, ts, token);
		logger.info("getDownloadURL->downloadURL:{}", downloadURL);
		return downloadURL;
	}
	
	/**
	 * 获取host + 端口号
	 * @param serverHost 服务器Host
	 * @param serverPort 服务器端口
	 * @return
	 */
	public static String getHostAndPort(String serverHost, Integer serverPort) {
		// host + 端口号
		String hostAndPort = serverHost;
		if (serverPort != null) {
			hostAndPort = serverHost
					+ (serverPort == 80 ? "" : ":" + serverPort);
		}
		return hostAndPort;
	}
	
}
