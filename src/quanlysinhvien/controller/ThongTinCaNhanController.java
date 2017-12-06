package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelThongTinCaNhanView;

public class ThongTinCaNhanController {
	private PanelThongTinCaNhanView thongTinCaNhan;
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop;
	private JTextField tfEmail, tfSoDT, tfDiaChi;
	private JButton btnCapNhat;
	private String fileName;
	private SinhVien sv;
	
	public ThongTinCaNhanController(PanelThongTinCaNhanView thongTinCaNhan, TaiKhoan tk) {
		this.thongTinCaNhan = thongTinCaNhan;
		this.gtIdSinhVien = thongTinCaNhan.getGtIdSinhVien();
		this.gtHoTen = thongTinCaNhan.getGtHoTen();
		this.gtNgaySinh = thongTinCaNhan.getGtNgaySinh();
		this.gtLop = thongTinCaNhan.getGtLop();
		this.tfEmail = thongTinCaNhan.getTfEmail();
		this.tfSoDT = thongTinCaNhan.getTfSoDT();
		this.tfDiaChi = thongTinCaNhan.getTfDiaChi();
		this.btnCapNhat = thongTinCaNhan.getBtnCapNhat();
		if(tk.getLoaiTK().equals("svtc")) {
			fileName = "quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		}
		if(tk.getLoaiTK().equals("svnc")) {
			fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		}
		
		
		try {
			sv = getSinhVien(tk, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			sv = new SinhVien();
			System.out.println("Error updateThongTin: " + e);
		}
		
		setThongTin();
		setAction();
	}
	
	private void setThongTin() {
		gtHoTen.setText(sv.getHoTen());
		gtIdSinhVien.setText(sv.getIdSinhVien());
		gtLop.setText(sv.getTenLop());
		gtNgaySinh.setText(sv.getNgaySinh());
	}
	
	private String[] getCapNhat() {
		String[] capNhat = new String[3];
		String email = tfEmail.getText();
		String soDT = tfSoDT.getText();
		String diaChi = tfDiaChi.getText();
		if(email.equals("") || soDT.equals("") || diaChi.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		capNhat[0] = email;
		capNhat[1] = soDT;
		capNhat[2] = diaChi;
	
		return capNhat;
	}
	
	private void setAction() {
		btnCapNhat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String[] capNhat = getCapNhat();
				if(capNhat == null) return;
				boolean ck = false;
				try {
					ck = updateSV(capNhat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(ck) JOptionPane.showMessageDialog(null, "Cập nhật thành công");
				cancel();
			}
		});
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
	
	private boolean updateSV(String gt[]) throws IOException {
		boolean ck = false;
		String id = gtIdSinhVien.getText();
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
			if(idSV.equals(id)) {
				cell = nextRow.createCell(7);
				cell.setCellValue(gt[0]);
				cell = nextRow.createCell(8);
				cell.setCellValue(gt[1]);
				cell = nextRow.createCell(9);
				cell.setCellValue(gt[2]);
				ck = true;
				break;
			}
		}
		
		fin.close();
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
	
	private void cancel() {
		tfDiaChi.setText("");
		tfEmail.setText("");
		tfSoDT.setText("");
	}
}
