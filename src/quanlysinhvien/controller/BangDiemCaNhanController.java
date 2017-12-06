package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelBangDiemCaNhanView;

class sapXepDiem implements Comparator<DiemHocPhan>{

	@Override
	public int compare(DiemHocPhan o1, DiemHocPhan o2) {
		// TODO Auto-generated method stub
		if(o1.getHocKy().compareTo(o2.getHocKy()) > 0) return 1;
		else if(o1.getHocKy().compareTo(o2.getHocKy()) < 0) return -1;
		else return 0;
	}
	
}

public class BangDiemCaNhanController {
	private JTable tableDiem, tableKetQua;
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfLopHoc, tfDiemQT, tfDiemThi, tfDiemChu;
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop;
	private SinhVien sv;
	private PanelBangDiemCaNhanView bangDiemCaNhan;
	private ArrayList<DiemHocPhan> dsDiem;
	private String fileName;
	private String[][] data;
	
	public BangDiemCaNhanController(PanelBangDiemCaNhanView bangDiemCaNhan, TaiKhoan tk) {
		this.bangDiemCaNhan = bangDiemCaNhan;
		this.tableDiem = bangDiemCaNhan.getTableDiem();
		this.tableKetQua = bangDiemCaNhan.getTableKetQua();
		this.tfHocKy = bangDiemCaNhan.getTfHocKy();
		this.tfIdHP = bangDiemCaNhan.getTfIdHP();
		this.tfTenHP = bangDiemCaNhan.getTfTenHP();
		this.tfTinChi = bangDiemCaNhan.getTfTinChi();
		this.tfLopHoc = bangDiemCaNhan.getTfLopHoc();
		this.tfDiemQT = bangDiemCaNhan.getTfDiemQT();
		this.tfDiemThi = bangDiemCaNhan.getTfDiemThi();
		this.tfDiemChu = bangDiemCaNhan.getTfDiemChu();
		this.gtIdSinhVien = bangDiemCaNhan.getGtIdSinhVien();
		this.gtHoTen = bangDiemCaNhan.getGtHoTen();
		this.gtNgaySinh = bangDiemCaNhan.getGtNgaySinh();
		this.gtLop = bangDiemCaNhan.getGtLop();
		
		if(tk.getLoaiTK().equals("svnc")) {
		}
		try {
			dsDiem = readFile(tk.getTaiKhoan(), tk.getLoaiTK());
			Collections.sort(dsDiem, new sapXepDiem());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsDiem = new ArrayList<>();
			System.out.println("Error bangDiemCaNhan: " + e);
		}
		
		this.bangDiemCaNhan.loadData1(tableDiem, dsDiem);
		this.bangDiemCaNhan.loadData2(tableKetQua, dsDiem);
		if(tk.getLoaiTK().equals("svtc")) {
			fileName = "quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		}
		if(tk.getLoaiTK().equals("svnc")) {
			fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
			tableKetQua.getColumnModel().removeColumn(tableKetQua.getColumnModel().getColumn(3)); 
			tableKetQua.getColumnModel().removeColumn(tableKetQua.getColumnModel().getColumn(4)); 
			tableKetQua.getColumnModel().removeColumn(tableKetQua.getColumnModel().getColumn(4)); 
		}
		try {
			sv = getSinhVien(tk, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			sv = new SinhVien();
			System.out.println("Error updateThongTin: " + e);
		}
		
		setThongTin();
		loadDataIntoPiece();
		setAction();
	}
	
	private void setAction() {
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem, ketQuaTimKiem(tfHocKy.getText(), 0));
				}
			}
		});
		
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfIdHP.getText(), 1));
				}
			}
		});
		
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfTenHP.getText(), 2));
				}
			}
		});
		
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfTinChi.getText(), 3));
				}
			}
		});
		
		tfLopHoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfLopHoc.getText(), 4));
				}
			}
		});
		
		tfDiemQT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfDiemQT.getText(), 5));
				}
			}
		});
		
		tfDiemThi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfDiemThi.getText(), 6));
				}
			}
		});
		
		tfDiemChu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemCaNhan.loadData1(tableDiem,ketQuaTimKiem(tfDiemChu.getText(), 7));
				}
			}
		});
	}
	
	private void setThongTin() {
		gtHoTen.setText(sv.getHoTen());
		gtIdSinhVien.setText(sv.getIdSinhVien());
		gtLop.setText(sv.getTenLop());
		gtNgaySinh.setText(sv.getNgaySinh());
	}
	
	public static SinhVien getSinhVien(TaiKhoan tk, String fileName) throws IOException {
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext())
			nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if(idSV.equals(tk.getTaiKhoan())) {
				cell = nextRow.getCell(2);
				String hoTen = cell.getStringCellValue();
				cell = nextRow.getCell(3);
				String khoa = cell.getStringCellValue();
				cell = nextRow.getCell(4);
				String tenLop = cell.getStringCellValue();
				cell = nextRow.getCell(5);
				String ngaySinh = cell.getStringCellValue();
				cell = nextRow.getCell(6);
				String gioiTinh = cell.getStringCellValue();
				cell = nextRow.getCell(7);
				String email = cell.getStringCellValue();
				cell = nextRow.getCell(8);
				String soDT = cell.getStringCellValue();
				cell = nextRow.getCell(9);
				String diaChi = cell.getStringCellValue();
				cell = nextRow.getCell(10);
				double diemTB = Double.parseDouble(Double.toString(cell.getNumericCellValue()));
				SinhVien sv = new SinhVien(idSV, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
				return sv;
			}
		}
		return null;
	}
	
	private ArrayList<DiemHocPhan> readFile(String idSV, String loaiSV) throws IOException {
		dsDiem = new ArrayList<DiemHocPhan>();
		
		File file; 
		if(loaiSV.equals("svtc")) file = new File ("quanlysinhvien/sinhvientinchi/" + idSV +"/diem.xlsx");
		else file = new File ("quanlysinhvien/sinhviennienche/" + idSV +"/diem.xlsx");
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
			diem.setTinChi((int) cell.getNumericCellValue());
			
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
		
		workbook.close();
		inputStream.close();
		
		return dsDiem;
	}
	
	private void loadDataIntoPiece() {
		data = new String[dsDiem.size()][8];
		for (int i = 0; i < dsDiem.size(); i++) {
			data[i][0] = dsDiem.get(i).getHocKy();
			data[i][1] = dsDiem.get(i).getIdHocPhan();
			data[i][2] = dsDiem.get(i).getTenHP();
			data[i][3] = Integer.toString(dsDiem.get(i).getTinChi());
			data[i][4] = "";
			data[i][5] = Double.toString(dsDiem.get(i).getDiemQT());
			data[i][6] = Double.toString(dsDiem.get(i).getDiemThi());
			data[i][7] = dsDiem.get(i).getDiemChu();
		}
	}
	
	private ArrayList<DiemHocPhan> ketQuaTimKiem(String strTimKiem, int j) {
		strTimKiem = strTimKiem.toLowerCase();
		ArrayList<DiemHocPhan> result = new ArrayList<DiemHocPhan>();
		for (int i = 0; i < data.length; i++) {
			String str = data[i][j].toLowerCase();
			if (str.indexOf(strTimKiem) > -1)
				result.add(dsDiem.get(i));
		}
		
		return result;
	}
	
}
