package com.eribank.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtility {
	static FileInputStream fis = null;
	static BufferedReader br = null;
	static String line = "";
	static String cvsSplitBy = ",";
	static HSSFWorkbook workbook;
	static HSSFSheet sheet;
	static DataFormatter formatter;
	static int totanumberofcolumns;

	public static int getRowCount(String filename, String sheetName) {
		try {
			fis = new FileInputStream(new File(filename));
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			Row rowkey = sheet.getRow(0);
			totanumberofcolumns = rowkey.getLastCellNum();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return sheet.getLastRowNum();
	}
	
	public static HashMap<String, ArrayList<String>> readTestDataFile (String filename, String sheetName) {
		HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
		try {
			fis = new FileInputStream(new File(filename));
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			formatter = new DataFormatter();
			Row rowkey = sheet.getRow(0);
			totanumberofcolumns = rowkey.getLastCellNum();
			for (int rowindex = 1; rowindex <= sheet.getLastRowNum(); rowindex++) {
				Row rowvalue = sheet.getRow(rowindex);
				String key = null;
				for (int colindex = 0; colindex < totanumberofcolumns; colindex++) {
					Cell cellkey = rowkey.getCell(colindex);
					key = formatter.formatCellValue(cellkey);
					String value = null;
					Cell cellvalue = rowvalue.getCell(colindex);
					value = formatter.formatCellValue(cellvalue);
					if (!values.containsKey(key)) {
						ArrayList<String> valuearray = new ArrayList<String>();
						valuearray.add(value);
						values.put(key, valuearray);
						}
					else {
						values.get(key).add(value);
					}
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		return values;
	}
}
