package com.xgd.boss.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author liukun
 * @Description: 文件读取工具类
 * @date 2016年12月15日 10:50:18
 */
public class FileReaderUtil {
	
	//读取卡bin文件分割符
	private static final String CARD_BIN_SEPARATOR ="#CARD_BIN#";

    
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(InputStream ins) {
        BufferedReader reader = null;
        StringBuffer fileContent = new StringBuffer();
        try {
        	
        	InputStreamReader inputStream = new InputStreamReader(ins, "GBK");
            reader = new BufferedReader(inputStream);
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
               // System.out.println("line " + line + ": " + tempString);
                fileContent.append(tempString).append(CARD_BIN_SEPARATOR);
                line++;
            }
            System.out.println("line num:"+line);
//            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return fileContent.toString();
    }

}
