package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.view.PanelDanhMucHP;

public class DanhMucHPController {
	private JComboBox<String> khoaVienCB;
	private JTextField tfTimIdHP, tfTimTenHP;
	private JTable table;
	private PanelDanhMucHP danhMucHocPhan;
	private ArrayList<HocPhan> dsHocPhan;
	
	public DanhMucHPController(PanelDanhMucHP danhMucHocPhan) {
		this.danhMucHocPhan = danhMucHocPhan;
		this.table = danhMucHocPhan.getTable();
		this.tfTimIdHP = danhMucHocPhan.getTfTimIdHP();
		this.tfTimTenHP = danhMucHocPhan.getTfTimTenHP();
		this.khoaVienCB = danhMucHocPhan.getKhoaVienCB();
		
		try {
			dsHocPhan = readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsHocPhan = new ArrayList<>();
			System.out.println("Error danhMucHP: " + e);
		}
		this.danhMucHocPhan.loadData(table, dsHocPhan, "All", "");
		
		setAction();
	}
	
	private void setAction() {
		khoaVienCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				danhMucHocPhan.loadData(table, dsHocPhan, (String) khoaVienCB.getSelectedItem(), "");
			}
		});
		
		tfTimIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					danhMucHocPhan.loadData(table, dsHocPhan, "idHP", tfTimIdHP.getText().trim());
				}
			}
		});
		
		tfTimTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					danhMucHocPhan.loadData(table, dsHocPhan, "tenHP", tfTimTenHP.getText().trim());
				}
			}
		});
	}
	
	private ArrayList<HocPhan> readFile() throws IOException {
		dsHocPhan = new ArrayList<HocPhan>();
		
		File file = new File("quanlysinhvien/danhsachhocphan/dsHocphan.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowCount = sheet.getLastRowNum();
		
		for (int i = 1; i <= rowCount; i++) {
			
			HocPhan hp = new HocPhan();
			
			Row row = sheet.getRow(i);
			Cell cell;
			
			cell = row.getCell(1);
			hp.setIdHocPhan(cell.getStringCellValue());
			
			cell = row.getCell(2);
			hp.setTenHP(cell.getStringCellValue());
			
			cell = row.getCell(3);
			hp.setSoTinChi((int) cell.getNumericCellValue());
			
			cell = row.getCell(4);
			hp.setSoTCHocPhi(cell.getNumericCellValue());
			
			cell = row.getCell(5);
			hp.setIdNganh(cell.getStringCellValue());
			
			cell = row.getCell(6);
			hp.setTrongSo((double) cell.getNumericCellValue());
			
			dsHocPhan.add(hp);
		}
		workbook.close();
		inputStream.close();
		
		return dsHocPhan;
	}
	
}
