package com.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.ContextLoader;

/**
 * 文件操作工具类
 * @author Neo
 * 2014-7-31
 */
public class FileUtil {

	private static final Logger logger = LogManager.getLogger(FileUtil.class);
	
	/**
	 * 获取web根目录下的绝对路径
	 * @return web根目录下的绝对路径
	 */
    public static String getWebContextRealPath() {
    	String rootPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
    	if (!rootPath.endsWith("/") && !rootPath.endsWith("\\")) {
    		rootPath = rootPath + File.separator;
		} 
		logger.info("getWebContextRealPath->rootPath:{}", rootPath);
		return rootPath;
    }

    /**
     * 通过资源路径获取资源 调用Spring的Resource接口获取资源，path的格式支持spring的路径规范，例如：
     *                classpath:/com/abc/efg/xxx/xxx 
     *                file:/etc/abc/efg/db.properties
     *                http://www.baidu.com/ 
     *                ftp://ftp.baidu.com/tmp/abc/xxxx/yyy/sss
     *                com/abc/efg/xxx/xxx 根据ApplicationContext具体实现类，则采用对应的Resource
     * @param path 资源路径
     * @return 资源
     */
    public static Resource getResourceByPath(String path) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResource(path);
    }

    /**
     * 通过资源路径获取资源（获取多个文件），path的格式支持spring的路径规范，例如：
     * 					classpath:/com/abc/efg/xxx/xxx 
     *                	file:/etc/abc/efg/db.properties
     *                	http://www.baidu.com/ 
     *                	ftp://ftp.baidu.com/tmp/abc/xxxx/yyy/sss
     *                	com/abc/efg/xxx/xxx 根据ApplicationContext具体实现类，则采用对应的Resource
     * @param path 资源路径
     * @return 资源数组
     * @throws IOException
     */
    public static Resource[] getResourcesByPath(String path) throws IOException  {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources(path);
    }
    
    
    public static String readFile(String path){
		StringBuffer content = new StringBuffer("");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path),"utf-8"));
			String line=null;
			while((line=reader.readLine())!=null){
				content.append(line);
			}
		}catch (Exception e) {
			//log.error("read file failure.path->"+path);
		}finally{
			try {
				if(reader!=null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}
    
    public static void writeFile(String path,List<Map<String,Object>> listMap, List<String> sortKeys){
    	if(listMap==null|| listMap.isEmpty()) return;
    	OutputStreamWriter write = null;
    	BufferedWriter writer = null;
		try{
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			write = new OutputStreamWriter(new FileOutputStream(file), "GBK");
			writer= new BufferedWriter(write);
			//列
			StringBuffer header = new StringBuffer("");
			for(String key : sortKeys){
				header.append(key).append("	");
			}			
			writer.write(header.toString()+"\n");
			//行			
			for(Map<String,Object> map : listMap){
				StringBuffer content = new StringBuffer("");
				for(String key : sortKeys){
					content.append(map.get(key)).append("	");
				}
				String line= content.toString()+"\n";
				writer.write(line);
			}
			
		}catch (Exception e) {
			//log.error("read file failure.path->"+path);
		}finally{
			try {
				if(writer!=null)
					writer.close();
				if(write!=null)
					write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
