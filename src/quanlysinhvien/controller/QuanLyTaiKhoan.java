package quanlysinhvien.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.TaiKhoan;

public class QuanLyTaiKhoan {
	
	public static void addTaiKhoan(TaiKhoan tk) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		try {
			FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien\\dsTaiKhoan.xlsx"));
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheetAt(0);
			lastRow = sheet.getLastRowNum();
		}catch (Exception e) {
			// TODO: handle exception
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet();
			System.out.println(e);
		}

		Row row = null;
		if(lastRow < 0) {
			createHeader(sheet);
			row = sheet.createRow(1);
		}else {
			row = sheet.createRow(lastRow + 1);
		}
		if(row != null) {
			writeTK(tk, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File("quanlysinhvien\\dsTaiKhoan.xlsx"));
		workbook.write(fout);
		fout.close();
	}
	
	public static void deleteTaiKhoan(String idTK) throws IOException {
		FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\dsTaiKhoan.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow = null;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(1);
			String taiKhoan = cell.getStringCellValue();
			if(taiKhoan.equalsIgnoreCase(idTK)) {
				int lastRow = sheet.getLastRowNum();
				if(i < lastRow) {
					sheet.shiftRows(i + 1, lastRow, -1);
				}
				if(i == lastRow) {
					Row removeRow = sheet.getRow(i);
					if(removeRow != null) {
						sheet.removeRow(removeRow);
					}
				}
				//hate you 
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File("quanlysinhvien\\dsTaiKhoan.xlsx"));
		workbook.write(fout);
		fout.close();
	}
	
	private static void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);
		Cell cellTaiKhoan = row.createCell(1);
		cellTaiKhoan.setCellStyle(cellStyle);
		cellTaiKhoan.setCellValue("Tài khoản");

		Cell cellMatKhau = row.createCell(2);
		cellMatKhau.setCellStyle(cellStyle);
		cellMatKhau.setCellValue("Mật khẩu");

		Cell cellLoaiTK = row.createCell(3);
		cellLoaiTK.setCellStyle(cellStyle);
		cellLoaiTK.setCellValue("Loại TK");
	}
	
	private static void writeTK(TaiKhoan tk, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(tk.getTaiKhoan());
		cell = row.createCell(2);
		cell.setCellValue(tk.getMatKhau());
		cell = row.createCell(3);
		cell.setCellValue(tk.getLoaiTK());
	}

}
