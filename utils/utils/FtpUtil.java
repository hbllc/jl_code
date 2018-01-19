package com.xgd.boss.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 
 * @author chenkai
 * @desc ftp utils
 */
public class FtpUtil {
	
	private static final Log logger = LogFactory.getLog(FtpUtil.class);
	
	public FTPClient ftpClient;
	private String ip = "192.168.0.142"; // 服务器IP地址
	private String userName = "zxb"; // 用户名
	private String userPwd = "1"; // 密码
	private int port = 21; // 端口号
	private static String path = "/upload/"; // 读取文件的存放目录

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		FtpUtil.path = path;
	}

	/**
	 * init ftp servere
	 */
	public FtpUtil() {
		// this.reSet();
	}
	/**
	 * 以当前系统时间生成文件名
	 * 
	 * @return
	 */
	private String getFileName() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		String str = "";
		try {
			str = sdFormat.format(new Date());
		} catch (Exception e) {
			return "";
		}
		if (str.equals("1900-01-01")) {
			str = "";
		}

		return str;
	}

	/**
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 * @throws SocketException
	 * @throws IOException
	 *             function:连接到服务器
	 */
	public void connectServer(String ip, int port, String userName,
			String userPwd, String path) {
		ftpClient = new FTPClient();
		try {
			ftpClient.setActivePortRange(29833,29833);
			// 下面三行代码必须要，而且不能改变编码格式
			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 连接
			ftpClient.connect(ip, port);

			// 登录
			ftpClient.login(userName, userPwd);
			if (path != null && path.length() > 0) {
				// 跳转到指定目录
				ftpClient.changeWorkingDirectory(path);
			}

		} catch (SocketException e) {
			logger.info("ftp服务器无法连接！");
		} catch (IOException e) {
			logger.info("ftp服务器登陆失败！");
		}
	}

	/**
	 * @throws IOException
	 *             function:关闭连接
	 */
	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param path
	 * @return function:读取指定目录下的文件名
	 * @throws IOException
	 */
	public List<String> getFileList(String path) {
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	/**
	 * @param fileName
	 * @param sourceFile
	 * @return
	 * @throws IOException
	 *             function:下载文件
	 */
	public boolean unloadFile(String fileName, String sourceFile) {
		boolean flag = false;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			flag = ftpClient.retrieveFile(sourceFile, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 返回一个文件流
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFile(String fileName) {
		String result = "";
		InputStream ins = null;
		try {
			ins = ftpClient.retrieveFileStream(fileName);

			// byte []b = new byte[ins.available()];
			// ins.read(b);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins, "UTF-8"));
			String inLine = reader.readLine();
			while (inLine != null) {
				result += (inLine + System.getProperty("line.separator"));
				inLine = reader.readLine();
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}

			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 在ftp服务器上 创建xml文件
	 * 
	 * @param xml
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public String uploadFile(String text, String fileName) throws IOException {

		OutputStreamWriter out = new OutputStreamWriter(ftpClient.appendFileStream(fileName + ".txt"),"UTF-8");
		BufferedWriter pw = new BufferedWriter(out);
		pw.write(text);
		pw.flush();
		pw.close();
		ftpClient.completePendingCommand();
		return fileName;
	}
	
	/**
	 * @param fileName
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public String storeFile(String fileName,String text) throws IOException {
		InputStream local = new ByteArrayInputStream(text.getBytes());
		boolean res = ftpClient.storeFile(fileName + ".txt", local);
		local.close();
		if(res){
			return fileName + ".txt";
		}else{
			logger.info("upload ftp file error");
			return null;
		}
	}

	/**
	 * @param fileName
	 *            function:删除文件
	 */
	public void deleteFile(String fileName) {
		try {
			ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回一个文件流
	 * 
	 * @param fileName
	 * @return
	 */
	public InputStream readFiles(String fileName) {
		InputStream ins = null;
		try {
			ins = ftpClient.retrieveFileStream(fileName);

			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ins;
	}

	/**
	 * 把FTP上当前工作目录下的文件(file.getName())，移动到与当前目录平级的另一目录BACKUP/L0082下。
	 * 如果你只是想更改名称，那就写成fc.rename(file.getName(), "newName");
	 * 移动文件只是在新文件名前加上路径，我设置了当前工作目录，所以这里用的是相对路径。
	 * pathFile "../BACKUP/LO080/"
	 * @param oldFileName
	 * @throws IOException
	 */
	public void remove(String oldFileName,String pathFile,FTPClient ftpClient) throws IOException {
		ftpClient.rename(oldFileName,  pathFile+ oldFileName);
	}

	/**
	 * 注销连接
	 * 
	 * @throws IOException
	 */
	public void disconnect(FTPClient ftpClient) throws IOException {
		int reply;
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
	        ftpClient.logout(); 
			ftpClient.disconnect();
			System.err.println("FTP server refused connection.");
		}

	}

	/** */
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public boolean CreateDirecroty(String remote, FTPClient ftpClient)
			throws IOException {
		ftpClient.changeWorkingDirectory(".");
		//ftpClient.changeToParentDirectory();
		//ftpClient.changeToParentDirectory();
		System.out.println(ftpClient.printWorkingDirectory());
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						return false;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return true;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws Exception {
		FtpUtil ftp = new FtpUtil();
		//ftp.connectServer("172.20.5.53", 21, "ftp_user", "ftp_user", "test/");
		ftp.connectServer("v0.ftp.upyun.com", 21, "jlpaytms/tms-jlpay", "JLPAY@upyun%2017", ".");
		List<String> list = ftp.getFileList(".");
		ftp.CreateDirecroty("yy/uu/", ftp.ftpClient);
		ftp.ftpClient.storeFile("222.txt", new ByteArrayInputStream(new String("测试").getBytes()));
		ftp.ftpClient.setActivePortRange(29833, 29833);
		ftp.uploadFile("<a>1212</a>", "test1");
//		ftp.readCustomersFile(sb);
//		if (sb != null && sb.length() > 0) {
//			String[] str = sb.toString()
//					.substring(0, sb.toString().length() - 1).split(";");
//			for (String fileName : str) {
//				ftp.remove(fileName);
//			}
//		}
		ftp.ftpClient.disconnect();
	}
}
