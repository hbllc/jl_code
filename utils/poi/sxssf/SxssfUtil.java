package com.xgd.boss.core.poi.sxssf;

import com.xgd.boss.core.utils.CommonUtil;
import com.xgd.boss.core.utils.JsonUtil;
import com.xgd.boss.core.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * SXSSF工具类（用于大数据量导出）
 * @author huangweiqi
 * 2015-2-2
 */
public class SxssfUtil{
	
	private static final Log logger = LogFactory.getLog(SxssfUtil.class);

	/**
	 * SXSSF数据导出上下文
	 */
	private static final ThreadLocal<SxssfExportDataContext> threadLocalContext = new ThreadLocal<>();
	
	/**
	 * 导出数据
	 * @param excelPath excel路径
	 * @param headerList excel表头List
	 * @param totalCount 总记录数
	 * @param sxssfExportDataCallback SXSSF导出数据回调
	 * @param option 可选参数
	 */
	public static void exportData(String excelPath, List<String> headerList,
			final long totalCount, SxssfExportDataCallback exportDataCallback,
			SxssfExportDataOption option) {
		final String prefix = "exportData->";
		long startTime = System.currentTimeMillis();
		
		if (option == null) {
			option = new SxssfExportDataOption();
		}
		
		logger.info(prefix+"excelPath:{}"+JsonUtil.toJson(excelPath));
		logger.info(prefix+"{}headerList:{}"+JsonUtil.toJson(headerList));
		logger.info(prefix+"{}totalCount:{}"+totalCount);
		logger.info(prefix+"{}option:{}"+JsonUtil.toJson(option));
		
		//上下文
		SxssfExportDataContext context = new SxssfExportDataContext();
		setExportDataContext(context);
		
		context.setOption(option);
		context.setTotalCount(totalCount);
		context.setCurrentSheetName(option.getFirstSheetName());
		
		SXSSFWorkbook wb = null;
		FileOutputStream out = null;
		
		try {
			wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
			
			context.setWorkbook(wb);
			
			wb.setCompressTempFiles(true); // temp files will be gzipped
			
			int activeSheetIndex = 0; //当前活动的sheet索引号
			int dataRowNum; //数据行指针，从1开始
			do {
				int rownum = 0; //行指针
				dataRowNum = 1;
				
				SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(
						context.getCurrentSheetName()); // create sheet
				
				wb.setActiveSheet(activeSheetIndex);
				
				
				logger.info(prefix+"start printing sheet "+wb.getActiveSheetIndex()+"----{}"+context.getCurrentSheetName());
				
				//处理导出表头
				Map<Integer, List<String>> headerMap = option.getHeaderMap();
				if(null != headerMap){
					headerList = headerMap.get(activeSheetIndex);
				}
				rownum = doExportHeader(headerList, sheet, rownum);
				
				//处理导出数据
				doExportData(rownum, dataRowNum, exportDataCallback);
				
				activeSheetIndex++;
				
			} while (exportDataCallback.hasNextSheet(dataRowNum));
			
			logger.info(prefix+"{} start generating excel file");
			out = new FileOutputStream(excelPath);
	        wb.write(out);
	        
	        logger.info("cost time:" + (System.currentTimeMillis() - startTime));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
			if (wb != null) {
				// dispose of temporary files backing this workbook on disk
				wb.dispose();
			}
		}
		
	}
	
	/**
	 * 处理导出表头
	 * @param headerList excel表头List
	 * @param sheet sheet
	 * @param rownum 行指针
	 * @return 返回行指针
	 */
	private static int doExportHeader(List<String> headerList,
			SXSSFSheet sheet, int rownum) {
		if (headerList != null && headerList.size() != 0) {
			Row row = sheet.createRow(rownum); //create row
			for (int i = 0, len = headerList.size(); i < len; i++) {
				Cell cell = row.createCell(i); //create cell
                cell.setCellValue(headerList.get(i));
			}
			rownum++;
			
			logger.info("doExportHeader-> print header success");
		}
		return rownum;
	}
	
	/**
	 * 处理导出数据
	 * @param rownum 行指针
	 * @param dataRowNum 数据行指针，从1开始
	 * @throws IOException 
	 */
	private static void doExportData(int rownum, int dataRowNum,
			SxssfExportDataCallback exportDataCallback) throws IOException {
		final String prefix = "doExportData->"; 
		SxssfExportDataOption option = getExportDataContext().getOption();
		
		SXSSFSheet sheet = getExportDataContext().getActiveSheet();
		
		XSSFCellStyle cellStyle = null;
		
		Workbook wb = sheet.getWorkbook();
		cellStyle = (XSSFCellStyle) wb.createCellStyle();
		
		List<Integer> micrometerAmountColumn = option.getMicrometerAmountColumn();
		if (micrometerAmountColumn.size() > 0) {
			DataFormat dataFormat = wb.createDataFormat();
			short format = dataFormat.getFormat("#,##0.00");
			cellStyle.setDataFormat(format);
			sheet.setDefaultColumnWidth(12);
		}
		if (option.getHasStyle()) {
			
			cellStyle.setWrapText(option.getStyleIsWrapText());
			
		}
		
		//前部追加
		List<List<String>> extPreDataList = exportDataCallback.queryExtPreSheetData();
		if(CommonUtil.isNotEmpty(extPreDataList)){
			for (List<String> columns : extPreDataList) {			
				Row row = sheet.createRow(rownum); //create row				
				for (int j = 0, len1 = columns.size(); j < len1; j++) {
					SXSSFCell cell = (SXSSFCell) row.createCell(j); //create cell
					String value = columns.get(j);
					cell.setCellValue(value != null ? value : "");
				}				      
				rownum++;
			}			
			sheet.flushRows(); //写入磁盘
		}
		
		do {
			//查询单个sheet的数据
			List<List<String>> rowList = exportDataCallback.querySheetData(rownum, dataRowNum);
			
			if (rowList == null || rowList.size() == 0) {
				logger.info(prefix+" no data has been found");
				break;
			}
			
			for (int i = 0, len = rowList.size(); i < len; i++) {
				List<String> columns = rowList.get(i); //列数组				
				Row row = sheet.createRow(rownum); //create row				
				for (int j = 0, len1 = columns.size(); j < len1; j++) {
					SXSSFCell cell = (SXSSFCell) row.createCell(j); //create cell	
					
					if (option.getHasStyle()) {
						cell.setCellStyle(cellStyle);
					}
					String value = columns.get(j);
					if(StringUtil.isNotEmpty(value)){
						if(option.getToNumColum().contains(j)){
							cell.setCellStyle(createCellContent4DoubleStyle(wb));
							cell.setCellValue(Double.valueOf(value));
							if (micrometerAmountColumn.contains(j)) {
								cell.setCellStyle(cellStyle);
							}
						}else{
							cell.setCellValue(value);
						}
					}else{
						cell.setCellValue("");
					}
				}
				
				rownum++;
				dataRowNum++;
			}
			
			sheet.flushRows(); //写入磁盘
			logger.info(prefix+rowList.size()+"{}{} has been written to the disk, dataRowNum:{}"+ dataRowNum);
			
		} while (exportDataCallback.hasNextSheetDataQuery(rownum, dataRowNum));
		
		
		//后部追加
		List<List<String>> extSuffixDataList = exportDataCallback.queryExtSuffixSheetData();
		if(CommonUtil.isNotEmpty(extSuffixDataList)){
			for (List<String> columns : extSuffixDataList) {			
				Row row = sheet.createRow(rownum); //create row					
				for (int j = 0, len1 = columns.size(); j < len1; j++) {					
					SXSSFCell cell = (SXSSFCell) row.createCell(j); //create cell
					String value = columns.get(j);
					if(value!=null && value.trim().length() > 0){
						if(option.getToNumColum().contains(j)){
							cell.setCellStyle(createCellContent4DoubleStyle(wb));
							cell.setCellValue(Double.valueOf(value));
							if (micrometerAmountColumn.contains(j)) {
								cell.setCellStyle(cellStyle);
							}
						}else{
							cell.setCellValue(value);
						}
					}else{
						cell.setCellValue("");
					}
					//cell.setCellValue(value != null ? value : "");
				}				
				rownum++;
			}			
			sheet.flushRows(); //写入磁盘
		}
	}
	
	/**
	 * 获取SXSSF数据导出上下文
	 * @return
	 */
	public static SxssfExportDataContext getExportDataContext() {
		return threadLocalContext.get();
	}
	
	/**
	 * 设置SXSSF数据导出上下文
	 * @param exportDataContext
	 */
	public static void setExportDataContext(SxssfExportDataContext exportDataContext) {
		threadLocalContext.set(exportDataContext);
	}

	private static CellStyle createCellContent4DoubleStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		// 生成字体
		Font font = workbook.createFont();
		// 正文样式
		style.setFillPattern(XSSFCellStyle.NO_FILL);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font);
		font.setFontName("宋体");
		// 保留两位小数点
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
		return style;
	}
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		String excelPath = "/temp/sxssf1.xlsx";
		
		File excelFile = new File(excelPath);
		excelFile.delete();
		
//		List<String> headers = Arrays.asList(new String[] {"列1", "列2", "列3", "列4"});
		
//		exportData(excelPath, headers, null);
		logger.info("cost time:" + (System.currentTimeMillis() - startTime));
		
		/*创建单元格格式
		SXSSFWorkbook wb = new SXSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();  
		DataFormat format=createHelper.createDataFormat();//或wb.createDataFormat();  
		format.getFormat("0.0");//数字格式化  
		short format2 = format.getFormat("#,##0.0000");  
		format.getFormat("m/d/yy h:mm");//设置日期格式化  
		
		cellStyle.setDataFormat(format2);
		*/
	}
	
	
	
}
