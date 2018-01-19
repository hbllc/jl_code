package com.xgd.boss.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @Description: excel工具类
 * @date 2016年8月16日 下午3:59:18
 */
public class ExcelReader {
	private static final Log logger = LogFactory.getLog(ExcelReader.class);
	
	public static interface IRowProcessor{
		void process(List<String> row) throws Exception;
	}

	
	/**
	* 正则表达式替换tab键、换行键,前后去空格,以及一个诡异的16进制编码c2a0,肉眼看起来和空格一样
	* @param str
	* @return
	*/
	public static String getStringNoBlank(String str) {   
		  str = StringUtils.trimToEmpty(str);
		  if (StringUtils.isEmpty(str)){
			  return str;
		  }
		 
		  //*************start************
		  //不可见的特殊字符,16进制编码为c2a0,请勿修改或删除!并不是普通的空格
		  //please don't modify,it's not blank symbol!!
		  String speciStr = " ";
		  str = str.replaceAll(speciStr, "");
		  //*************end*************
		  
		  //去掉头尾空白符，中间有多个连续空白符，只保留一个
		  return StringUtils.normalizeSpace(str);
	}

	
	/**
	 * 
	 * @Description:获取单元格内容
	 * @param cell
	 * @return
	 * @return String
	 * @throws
	 */
	private static String getValue(Cell cell) {
		Object value = "";
		DecimalFormat df = new DecimalFormat("0");// 格式化 number String
		DecimalFormat nf = new DecimalFormat("0");// 格式化数字
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");// 格式化日期字符串
		if (null != cell) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:// 字符串——String type
				value = cell.getStringCellValue();
				//去掉空格，tab等特殊字符,否则拿去查询数据库可能导致查不到，请勿删除
				value = getStringNoBlank(value.toString());
				break;
			case Cell.CELL_TYPE_NUMERIC:// 数字——Number type
				if ("@".equals(cell.getCellStyle().getDataFormatString())) {
					value = df.format(cell.getNumericCellValue());
				} else if ("General".equals(cell.getCellStyle()
						.getDataFormatString())) {
					value = nf.format(cell.getNumericCellValue());
				} else if ("h:mm:ss".equals(cell.getCellStyle()
						.getDataFormatString())) {
					value = sdf2.format(cell.getDateCellValue());
				} else {
					value = sdf.format(HSSFDateUtil.getJavaDate(cell
							.getNumericCellValue()));
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:// boolean——Boolean type
				value = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:// 空白——Blank type
				value = "";
				break;
			default:
				value = cell.toString();
			}
		}
		return value==null?"":value.toString();
	}

	
	/**
	 * 读取excel首行列名
	 * 
	 * @param fileName
	 * @return  List<List<String>>
	 * @throws Exception 
	 * @throws IOException
	 */
	public static List<String> readExcelFirstLine(String fileName) throws Exception {
		File file = validate(fileName);
		InputStream is = null;
		List<String> Contentlist = new ArrayList<String>();
		try {
			is = new FileInputStream(file);
			Workbook wb = fileName.endsWith(ExcelType.EXCEL_XLS.getKey()) ? new HSSFWorkbook(
					is) : new XSSFWorkbook(is);
			Contentlist = readFirstCell(wb);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return Contentlist;
	}
	
	
	/**
	 * 读取excel
	 * 
	 * @param fileName
	 * @return  List<List<String>>
	 * @throws Exception 
	 * @throws IOException
	 */
	public static List<List<String>> readExcel(String fileName, boolean readFirstLine) throws Exception {
		File file = validate(fileName);
		InputStream is = null;
		List<List<String>> Contentlist = new ArrayList<List<String>>();
		try {
			is = new FileInputStream(file);
			Workbook wb = fileName.endsWith(ExcelType.EXCEL_XLS.getKey()) ? new HSSFWorkbook(
					is) : new XSSFWorkbook(is);
			Contentlist = readCell(wb, readFirstLine, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return Contentlist;
	}
	
	/**
	 * 以回调方式读取Excel
	 * @param fileName
	 * @param readFirstLine
	 * @param rowProcessor
	 * @throws Exception
	 */
	public static void readExcel(String fileName,boolean readFirstLine,IRowProcessor rowProcessor)throws Exception{
		if(rowProcessor==null)
			throw new IllegalArgumentException("RowProcessor is null!");
		File file = validate(fileName);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			Workbook wb = fileName.endsWith(ExcelType.EXCEL_XLS.getKey()) ? new HSSFWorkbook(
					is) : new XSSFWorkbook(is);
			readCell(wb, readFirstLine, rowProcessor);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}		
	}

	private static File validate(String fileName) throws Exception {
		if (StringUtils.isBlank(fileName) || !ExcelType.endsKey(fileName)) {
			throw new Exception("excel 文件名为空或文件类型不对");
		}
		File file = new File(fileName);
		if (!file.exists()) {
			logger.info("****Excel file not exist :" + fileName);
			throw new Exception("excel文件不存在" + fileName);
		}

		if (!file.canRead()) {
			logger.info("*****No authorization to reand excel file:" + fileName);
			throw new Exception("没有权限读取excel文件" + fileName);
		}
		return file;
	}
	
	
	/**
	 * 
	 * @Description: 读取第一行存入list
	 * @param workbook
	 * @return List<String>
	 * @throws
	 */
	private static List<String> readFirstCell(Workbook workbook) throws Exception {
		List<String> list = new ArrayList<String>();
		// 第一面板
		Sheet sheet = workbook.getSheetAt(0);

		// 第一行为列名不获取,获取列名长度作为每一行应有的长度
		if (sheet == null || sheet.getRow(0) == null) {
			return list;
		}
		// 是否读取第一行
		int length = sheet.getRow(0).getLastCellNum();
		Row row = sheet.getRow(0);
		if (row == null) {
			return list;
		}
		int minCol = 0;
		int maxCol = row.getLastCellNum();
		for (int colnum = minCol; colnum < length; colnum++) {
			// 不足长度补"",否则调用者还需判断太麻烦
			String value = (colnum < maxCol) ? getValue(row.getCell(colnum))
					: "";
			list.add(value);
		}
		// 有时候会读入没内容的空行，过滤掉
		return list;
	}
	

	/**
	 * 
	 * @Description: 读取单元格并存入list
	 * @param workbook
	 * @param readFristLine
	 * @param rowProcessor
	 * @return List<List<String>>
	 * @throws
	 */
	private static List<List<String>> readCell(Workbook workbook, boolean readFristLine, IRowProcessor rowProcessor)
			throws Exception {
		List<List<String>> list = new ArrayList<List<String>>();
		//第一面板
		Sheet sheet = workbook.getSheetAt(0);

		// 第一行为列名不获取,获取列名长度作为每一行应有的长度
		if (sheet == null || sheet.getRow(0) == null){
			return list;
		}
		//是否读取第一行
		int beginnum = readFristLine ? 0 : 1;
		int length = sheet.getRow(0).getLastCellNum();
		for (int rownum = beginnum,maxRow=sheet.getLastRowNum(); rownum <= maxRow; rownum++) {
			Row row = sheet.getRow(rownum);
			if (row == null) {
				continue;
			}
			int minCol = 0;
			int maxCol = row.getLastCellNum();
			List<String> rollist = new ArrayList<String>();
			boolean allEmpty = true;
			for (int colnum = minCol; colnum < length; colnum++) {
				//不足长度补"",否则调用者还需判断太麻烦
				String value = (colnum < maxCol) ? getValue(row.getCell(colnum))
						: "";
				if (StringUtils.isNotEmpty(value)){
					allEmpty = false;
				}
				rollist.add(value);
			}
			//有时候会读入没内容的空行，过滤掉
			if (!allEmpty){
				if(rowProcessor!=null)
					rowProcessor.process(rollist);
				else
					list.add(rollist);
			}
		}
		return list;
	}

//	private static String excel2007Path = "D:\\temp\\BatchADD7.xlsx";
//	private static String excel2003Path = "D:\\temp\\BatchADD.xls";
//
//	public static void main(String[] args) throws Exception {
//	
//		List<List<String>> list = readExcel(excel2003Path);
//		System.out.println(list);
//
//		
//		List<List<String>> list11 = readExcel(excel2007Path);
//		System.out.println(list11);
//
//	}
}
