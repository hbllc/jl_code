package com.xgd.mt.web.controller.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import sun.misc.BASE64Decoder;

import com.alibaba.druid.support.json.JSONUtils;
import com.core.date.DateUtil;
import com.core.fastdfs.FastDfsException;
import com.core.fastdfs.FastDfsUtil;
import com.core.fastdfs.vo.FastDfsUploadResult;
import com.core.fastdfs.vo.FastDfsUploadResult.FastDfsUploadResponseFile;
import com.core.web.controller.BaseController;
import com.xgd.mt.agent.common.consts.EnvProperties;
import com.xunlei.json.JSONUtil;

@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {

	/**
	 * 
	 * @param multipartRequest
	 * @return
	 */
	@ResponseBody
	@RequestMapping("baseImg")
	public String uploadImg(MultipartRequest multipartRequest) {
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("uploadStartTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
		try {
			returnMap.put("result", "0");// 2成功，0失败
			String serverHost = EnvProperties.getFastdfsPhpServerIp();
			int serverPort = EnvProperties.getFastdfsPhpServerPort();

			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				String fileName = iterator.next();
				MultipartFile file = multipartRequest.getFile(fileName);
				returnMap.put("fileSize",(file.getSize()/1024)+"");
				InputStream fileInputStream = file.getInputStream();

				// 上传图片
				Date fastDfsStartTime = new Date();
				returnMap.put("fastDfsStartTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
				FastDfsUploadResult uploadResult = FastDfsUtil.upload(
						serverHost, serverPort, fileInputStream, fileName);
				returnMap.put("fastDfsEndTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
				returnMap.put("fastdfsInterval",(new Date().getTime()-fastDfsStartTime.getTime())+"");
				
				if (!FastDfsUtil.UPLOAD_RESPONSE_RESULT_SUCCESS
						.equals(uploadResult.getResponse().getResult())) {
					throw new FastDfsException(" 上传失败!");
				} else {
					List<FastDfsUploadResponseFile> fileList = uploadResult
							.getResponse().getFileList();
					for (FastDfsUploadResponseFile responseFile : fileList) {
						returnMap.put("dllname", responseFile.getFileName());
						String url = responseFile.getFileID();
						returnMap.put("url", url);
						String src = FastDfsUtil.getDownloadURL(
								EnvProperties.getFastdfsPhpServerIp(),
								EnvProperties.getFastdfsPhpServerPort(), url);
						returnMap.put("src", src);
					}
				}
			}
			returnMap.put("uploadInterval","3");
			returnMap.put("result", "2");// 2成功，0失败
		} catch (Exception e) {
			logger.catching(e);
		}
		returnMap.put("uploadEndTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
		return JSONUtils.toJSONString(returnMap);
	}

	@ResponseBody
	@RequestMapping("compress")
	public String compress(String base64, String filename,HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("uploadStartTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
		try {
			returnMap.put("result", "0");// 2成功，0失败
			String serverHost = EnvProperties.getFastdfsPhpServerIp();
			int serverPort = EnvProperties.getFastdfsPhpServerPort();

			/*Iterator<String> iterator = base64.getFileNames();
			String fileName = iterator.next();
			MultipartFile file = base64.getFile(fileName);*/
			String filePath = request.getSession().getServletContext()
					.getRealPath("/temp")+File.separator;
			File pathF = new File(filePath);
			if(!pathF.exists()){
				pathF.mkdirs();
			}
			
			if(GenerateImage(base64,filePath+filename)){
				File file = new File(filePath,filename);
				returnMap.put("fileSize",(file.length()/1024)+"");
				InputStream fileInputStream = new FileInputStream(file);
				// 上传图片
				Date fastDfsStartTime = new Date();
				returnMap.put("fastDfsStartTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
				FastDfsUploadResult uploadResult = FastDfsUtil.upload(serverHost,
						serverPort, fileInputStream, filename);
				returnMap.put("fastDfsEndTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
				returnMap.put("fastdfsInterval",(new Date().getTime()-fastDfsStartTime.getTime())+"");
				
				if (!FastDfsUtil.UPLOAD_RESPONSE_RESULT_SUCCESS.equals(uploadResult
						.getResponse().getResult())) {
					throw new FastDfsException(" 上传失败!");
				} else {
					List<FastDfsUploadResponseFile> fileList = uploadResult
							.getResponse().getFileList();
					for (FastDfsUploadResponseFile responseFile : fileList) {
						returnMap.put("dllname", responseFile.getFileName());
						String url = responseFile.getFileID();
						returnMap.put("url", url);
						String src = FastDfsUtil.getDownloadURL(
								EnvProperties.getFastdfsPhpServerIp(),
								EnvProperties.getFastdfsPhpServerPort(), url);
						returnMap.put("src", src);
					}
				}
				file.delete();
				returnMap.put("result", "2");// 2成功，0失败
			}else{
				returnMap.put("result", "0");// 2成功，0失败
			}
		} catch (Exception e) {
			logger.catching(e);
		}
		returnMap.put("uploadInterval","3");
		returnMap.put("uploadEndTime",DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
		return JSONUtils.toJSONString(returnMap);
	}

	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		imgStr = imgStr.replace("data:image/jpeg;base64,", "");
		
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
