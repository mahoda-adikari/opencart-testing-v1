package com.opencart.utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    private final String path;
    private CellStyle style;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            return sheet.getLastRowNum();
        }
    }

    public int getCellCount(String sheetName, int rowNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rowNum);
            return row.getLastCellNum();

        }
    }

    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rowNum);
            XSSFCell cell = row.getCell(colNum);

            DataFormatter formatter = new DataFormatter();
            try {
                return formatter.formatCellValue(cell);
            } catch (Exception e) {
                return "";
            }
        }
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
        File excelFile = new File(path);
        if (!excelFile.exists()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 FileOutputStream fo = new FileOutputStream(path)) {

                workbook.write(fo);
            }
        }

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            if (workbook.getSheetIndex(sheetName) == -1) {
                workbook.createSheet(sheetName);
            }
            XSSFSheet sheet = workbook.getSheet(sheetName);

            if (sheet.getRow(rowNum) == null) {
                sheet.createRow(rowNum);
            }
            XSSFRow row = sheet.getRow(rowNum);

            XSSFCell cell;
            if (row.getCell(colNum) == null) {
                cell = row.createCell(colNum);
            } else {
                cell = row.getCell(colNum);
            }
            cell.setCellValue(data);

            try (FileOutputStream fo = new FileOutputStream(path)){
                workbook.write(fo);
            }
        }
    }
}
