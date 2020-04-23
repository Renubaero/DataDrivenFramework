package com.w2a.utilities;

import java.io.FileInputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public String path;
	public FileInputStream fis;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	
	
	public ExcelReader( String path) {
		try {
		this.path= path;
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		//sheet = workbook.getSheetAt(0);
		fis.close();		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	// return row count
	public int getRowCount(String sheetName) {
		
		int index=workbook.getSheetIndex(sheetName);
		if(index==-1) {
			return 0;
		}else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum()+1;
			return number;
		}	
		
	}
	
	public boolean isSheetExist(String sheetName) {
		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1) {
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}		
	}
	//return column count
	public int getColumnCount(String sheetName) {
		
		if(!isSheetExist(sheetName)) {
			return -1;
		}
		sheet= workbook.getSheet(sheetName);
		row= sheet.getRow(0);
		if(row==null) {
			return -1;
		}
		return row.getLastCellNum();
		
	}
	// returns the data from a cell
		public String getCellData(String sheetName, int colNum, int rowNum) {
			try {
				if (rowNum <= 0)
					return "";

				int index = workbook.getSheetIndex(sheetName);

				if (index == -1)
					return "";

				sheet = workbook.getSheetAt(index);
				row = sheet.getRow(rowNum - 1);
				if (row == null)
					return "";
				cell = row.getCell(colNum);
				if (cell == null)
					return "";

				if (cell.getCellType() == CellType.STRING)
					return cell.getStringCellValue();
				else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

					String cellText = String.valueOf(cell.getNumericCellValue());
					if (DateUtil.isCellDateFormatted(cell)) {
						// format in form of M/D/YY
						double d = cell.getNumericCellValue();

						Calendar cal = Calendar.getInstance();
						cal.setTime(DateUtil.getJavaDate(d));
						cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
						cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
					}
					return cellText;
				} else if (cell.getCellType() == CellType.BLANK)
					return "";
				else
					return String.valueOf(cell.getBooleanCellValue());
			} catch (Exception e) {

				e.printStackTrace();
				return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
			}
		}
}
