package cn.com.flaginfo.platform.export.model.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cn.com.flaginfo.platform.common.util.FileUtil;
import cn.com.flaginfo.platform.common.util.NumberUtil;
import cn.com.flaginfo.platform.common.util.SystemMessage;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

/** 
 * @author chengbin.luo 
 * @date 2016-07-15 15:36:16 
 *
 */
public class ExcelDoc {

	public static final int DATA_TYPE_STRING = Cell.CELL_TYPE_STRING;
	public static final int DATA_TYPE_NUMBER = Cell.CELL_TYPE_NUMERIC;
	private Logger logger = Logger.getLogger(getClass());
	/**
	 * 工作薄
	 */
	private SXSSFWorkbook workbook;
	/**
	 * 标题样式
	 */
	private CellStyle titleStyle = null;
	/**
	 * 单元格样式
	 */
	private CellStyle cellStyle = null;
	/**
	 * 导出策略
	 */
	private Policy policy;
	/**
	 * 当前行索引，暂只支持单个sheet，重新写sheet需要充值对应当前行
	 */
	private int rowIndex = 0;
	
	public ExcelDoc(Policy policy){
		this.workbook = new SXSSFWorkbook(100);
		this.policy = policy;
		workbook.setCompressTempFiles(true);
		initDefaultStyle();
	}
	
	public boolean wirteFile(){
		FileOutputStream fos = null;
		try{
			String filePath = SystemMessage.getString("project-path")+policy.getFilePath()+File.separator+policy.getFileName();
			File outDir = new File(filePath.substring(0,filePath.lastIndexOf(File.separator)));
			if(!outDir.exists()){
				outDir.mkdirs();
			}
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(fos != null)
			{
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			clear();
		}
		return true;
	}
	
	/**
	 * 清空对象，释放内存
	 */
	public void clear(){
		workbook = null;
		titleStyle = null;
		cellStyle = null;
		policy = null;
	}
	
	/**
	 * 写表格标题
	 * @param sheet
	 */
	public void writeTitle(Sheet sheet){
		if(policy.getColumns()==null){
			logger.warn("策略组件未设置列属性");
			return;
		}
		Row row = sheet.createRow(rowIndex);
		rowIndex++;
		row.setHeightInPoints(25);
		for (int i = 0; i < policy.getColumns().size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellType(DATA_TYPE_STRING);
			cell.setCellValue(policy.getColumns().get(i).getTitle());
			cell.setCellStyle(titleStyle);
		} 
	}
	
	/**
	 * 每行数据处理写入
	 * @param data
	 * @param row
	 */
	private void writeData(Map<String,Object> data,Row row){
		for (int i=0;i<policy.getColumns().size();i++) {
			ExcelColumn column = policy.getColumns().get(i);
			int type = column.getType();
			Object dataValue = data.get(column.getKey());
			if(column.getFunction()!=null){
				dataValue = column.getFunction().apply(dataValue, data);
			}
			Cell cell = row.createCell(i);
			cell.setCellType(type);
			if(DATA_TYPE_NUMBER == type){
				cell.setCellValue(NumberUtil.parseDouble(dataValue));
			} else {
				cell.setCellValue(dataValue!=null?dataValue.toString():"");
			}
			cell.setCellStyle(cellStyle);
		}
	}
	
	/**
	 * 写入数据集合
	 * @param sheet
	 * @param dataList
	 */
	public void writeDataList(Sheet sheet,List<Map<String,Object>> dataList){
		for(int i=0;i<dataList.size();i++){
			Map<String,Object> data = (Map<String,Object>) dataList.get(i);
			Row row = sheet.createRow(rowIndex);
			rowIndex++;
			row.setHeightInPoints(20);
			writeData(data, row);
		}
		try {
			((SXSSFSheet) sheet).flushRows();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建sheet
	 * @param sheetName
	 * @return
	 */
	public Sheet createSheet(String sheetName){
		Sheet sheet = workbook.createSheet();
		sheet.setDefaultColumnWidth(25);
		return sheet;
	}
	
	/**
	 * 初始化表格样式
	 */
	private void initDefaultStyle(){
		titleStyle = workbook.createCellStyle();
		titleStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		Font font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		titleStyle.setFont(font);
		
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		Font font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		cellStyle.setFont(font2);
	}

	public SXSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(SXSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public CellStyle getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(CellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	
}
