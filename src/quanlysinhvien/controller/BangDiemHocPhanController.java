package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.view.PanelBangDiemHocPhanView;

public class BangDiemHocPhanController {
	private JTextField  tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfDiemHP, tfIdSinhVien;
	private JLabel labSumHP, labSumTC;
	private JTable table;
	private PanelBangDiemHocPhanView bangDiemHocPhan;
	private ArrayList<DiemHocPhan> dsDiem;
	
	private String[][] data;
	
	public BangDiemHocPhanController(PanelBangDiemHocPhanView bangDiemHocPhan, String idSV, String loaiSV) {
		this.bangDiemHocPhan = bangDiemHocPhan;
		this.tfHocKy = bangDiemHocPhan.getTfHocKy();
		this.tfIdHP = bangDiemHocPhan.getTfIdHP();
		this.tfTenHP = bangDiemHocPhan.getTfTenHP();
		this.tfTinChi = bangDiemHocPhan.getTfTinChi();
		this.tfDiemHP = bangDiemHocPhan.getTfDiemHP();
		this.tfIdSinhVien = bangDiemHocPhan.getTfIdSinhVien();
		tfIdSinhVien.setText(idSV);
		this.labSumHP = bangDiemHocPhan.getLabSumHP();
		this.labSumTC = bangDiemHocPhan.getLabSumTC();
		this.labSumTC.setText("0");
		this.table = bangDiemHocPhan.getTable();
		
		try {
			dsDiem = readFile(idSV, loaiSV);
			Collections.sort(dsDiem, new sapXepDiem());
			labSumHP.setText(Integer.toString(dsDiem.size()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsDiem = new ArrayList<>();
			labSumHP.setText("0");
			System.out.println("Error bangDiemHP: " + e);
		}
		
		this.bangDiemHocPhan.loadData(table, dsDiem);
		
		loadDataIntoPiece();
		
		setAction();
	}
	
	private void setAction() {
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfHocKy.getText(), 0));
				}
			}
		});
		
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfIdHP.getText(), 1));
				}
			}
		});
		
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfTenHP.getText(), 2));
				}
			}
		});
		
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfTinChi.getText(), 3));
				}
			}
		});
		
		tfDiemHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfDiemHP.getText(), 4));
				}
			}
		});
	}
	
	private ArrayList<DiemHocPhan> readFile(String idSV, String loaiSV) throws IOException {
		dsDiem = new ArrayList<DiemHocPhan>();
		int sumTC = 0;
		
		File file; 
		if(loaiSV.equals("svtc")) file = new File ("quanlysinhvien/sinhvientinchi/" + idSV + "/diem.xlsx");
		else file = new File ("quanlysinhvien/sinhviennienche/" + idSV + "/diem.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowCount = sheet.getLastRowNum();
		
		for (int i = 1; i <= rowCount; i++) {
			
			DiemHocPhan diem = new DiemHocPhan();
			
			Row row = sheet.getRow(i);
			Cell cell;
			
			cell = row.getCell(1);
			diem.setHocKy(cell.getStringCellValue());
			
			cell = row.getCell(2);
			diem.setIdHocPhan(cell.getStringCellValue());
			
			cell = row.getCell(3);
			diem.setTenHP(cell.getStringCellValue());
			
			cell = row.getCell(4);
			int tc = (int) cell.getNumericCellValue();
			diem.setTinChi(tc);
			sumTC += tc;
			
			cell = row.getCell(5);
			diem.setIdLopHoc(cell.getStringCellValue());
			
			cell = row.getCell(6);
			diem.setDiemQT((double) cell.getNumericCellValue());
			
			cell = row.getCell(7);
			diem.setDiemThi((double) cell.getNumericCellValue());
			
			cell = row.getCell(8);
			diem.setDiemChu(cell.getStringCellValue());
			
			cell = row.getCell(9);
			diem.setDiemThang4(cell.getNumericCellValue());
			
			dsDiem.add(diem);
		}
		labSumTC.setText(Integer.toString(sumTC));
		
		workbook.close();
		inputStream.close();
		
		return dsDiem;
	}
	
	private ArrayList<DiemHocPhan> ketQuaTimKiem(String strTimKiem, int j) {
		ArrayList<DiemHocPhan> result = new ArrayList<DiemHocPhan>();
		strTimKiem = strTimKiem.toLowerCase();
		for (int i = 0; i < data.length; i++) {
			String str = data[i][j].toLowerCase();
			if (str.indexOf(strTimKiem) > -1)
				result.add(dsDiem.get(i));
		}

		return result;
	}
	
	private void loadDataIntoPiece() {
		data = new String[dsDiem.size()][5];
		for (int i = 0; i < dsDiem.size(); i++) {
			data[i][0] = dsDiem.get(i).getHocKy();
			data[i][1] = dsDiem.get(i).getIdHocPhan();
			data[i][2] = dsDiem.get(i).getTenHP();
			data[i][3] = Integer.toString(dsDiem.get(i).getTinChi());
			data[i][4] = dsDiem.get(i).getDiemChu();
		}
	}
}
