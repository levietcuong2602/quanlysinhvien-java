package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelChuongTrinhDaoTaoSVView;

public class ChuongTrinhDaoTaoSVController {
	private JTextField tfIdSinhVien, tfIdHP, tfTenHP, tfKyHoc, tfTinChi, tfDiemChu, tfDiemSo, tfVien_Khoa;
	private JTable table;

	private PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV;

	private ArrayList<DiemHocPhan> dsDiem;

	private String[] vienKhoa;

	public ChuongTrinhDaoTaoSVController(PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV, TaiKhoan tk) {
		this.chuongTrinhDaoTaoSV = chuongTrinhDaoTaoSV;
		this.tfIdSinhVien = chuongTrinhDaoTaoSV.getTfIdSinhVien();
		this.tfIdSinhVien.setText(tk.getTaiKhoan());
		this.tfIdHP = chuongTrinhDaoTaoSV.getTfIdHP();
		this.tfTenHP = chuongTrinhDaoTaoSV.getTfTenHP();
		this.tfKyHoc = chuongTrinhDaoTaoSV.getTfKyHoc();
		this.tfTinChi = chuongTrinhDaoTaoSV.getTfTinChi();
		this.tfDiemChu = chuongTrinhDaoTaoSV.getTfDiemChu();
		this.tfVien_Khoa = chuongTrinhDaoTaoSV.getTfVien_Khoa();
		this.tfDiemSo = chuongTrinhDaoTaoSV.getTfDiemSo();
		this.table = chuongTrinhDaoTaoSV.getTable();

		try {
			dsDiem = readFile(tk.getTaiKhoan(), tk.getLoaiTK());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsDiem = new ArrayList<>();
			System.out.println("Error chuongTrinhDaoTao: " + e);
		}

		this.chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");

		setAction();
	}

	private void setAction() {
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfIdHP.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "idHP", giaTri);
			}
		});
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfTenHP.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "tenHP", giaTri);
			}
		});
		tfKyHoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfKyHoc.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "hocKy", giaTri);
			}
		});
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfTinChi.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "tinChi", giaTri);
			}
		});
		tfDiemChu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfDiemChu.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "diemChu", giaTri);
			}
		});
		tfDiemSo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfDiemSo.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "diem4", giaTri);
			}
		});
		tfVien_Khoa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfVien_Khoa.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa, "vienKhoa", giaTri);
			}
		});
	}

	private ArrayList<DiemHocPhan> readFile(String idSV, String loaiSV) throws IOException {
		dsDiem = new ArrayList<DiemHocPhan>();
		File file;
		if (loaiSV.equals("svtc")) {
			file = new File("quanlysinhvien/sinhvientinchi/" + idSV + "/ctdt.xlsx");
		} else {
			file = new File("quanlysinhvien/sinhviennienche/" + idSV + "/ctdt.xlsx");
		}
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		int rowCount = sheet.getLastRowNum();
		vienKhoa = new String[rowCount];
		for (int i = 1; i <= rowCount; i++) {

			DiemHocPhan diem = new DiemHocPhan();

			Row row = sheet.getRow(i);
			Cell cell;

			cell = row.getCell(3);
			diem.setHocKy((int) cell.getNumericCellValue() + "");

			cell = row.getCell(1);
			diem.setIdHocPhan(cell.getStringCellValue());

			cell = row.getCell(2);
			diem.setTenHP(cell.getStringCellValue());

			cell = row.getCell(4);
			diem.setTinChi((int) cell.getNumericCellValue());

			cell = row.getCell(5);
			if (cell != null)
				diem.setDiemChu(cell.getStringCellValue());

			cell = row.getCell(6);
			if (cell != null)
				diem.setDiemThang4(cell.getNumericCellValue());

			cell = row.getCell(7);
			vienKhoa[i - 1] = cell.getStringCellValue();

			dsDiem.add(diem);

		}
		workbook.close();
		inputStream.close();

		return dsDiem;
	}
}
