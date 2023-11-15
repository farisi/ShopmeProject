package com.shopme.admin.utilities;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestDataExcelGenerator {
	public static void generateTestDataExcel(String filePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = (Sheet) workbook.createSheet("TestData");

        // Membuat baris judul
        Row headerRow = ((XSSFSheet) sheet).createRow(0);
        headerRow.createCell(0).setCellValue("Column1");
        headerRow.createCell(1).setCellValue("Column2");
        // Tambahkan kolom lain sesuai kebutuhan

        // Membuat data pengujian
        for (int i = 1; i <= 10; i++) {
            Row dataRow = ((XSSFSheet) sheet).createRow(i);
            dataRow.createCell(0).setCellValue("Value" + i);
            dataRow.createCell(1).setCellValue("Description" + i);
            // Tambahkan data lain sesuai kebutuhan
        }

        // Menyimpan ke file Excel
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
