package com.shopme.admin.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelDataReader {
	public static List<Object[]> readTestDataFromExcel(InputStream inputStream, String sheetName) {
        List<Object[]> testData = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Object[] rowData = new Object[]{
                        row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue()
                        // Sesuaikan dengan jumlah dan jenis kolom
                };
                testData.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData;
    }
}
