package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;

public class PanelBangDiemCaNhanView extends JPanel{
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop, gtChuongTrinh, gtHeHoc, gtTrangThai;
	private JTable tableDiem, tableKetQua;
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfLopHoc, tfDiemQT, tfDiemThi, tfDiemChu;
	private String[] titleCols1 = {"Học kỳ", "Mã HP", "Tên HP", "TC", "Lớp học", "Điểm QT", "Điểm thi", "Điểm chữ"};
	private String[] titleCols2 = {"Học kỳ", "GPA", "CPA", "TC qua", "TC tích lũy", "TC nợ ĐK", "TC ĐK", "Trình độ",
			"mức CC", "CTĐT", "Dự kiến XLHT", "Xử lý chính thức"};
	
	private ArrayList<DiemHocPhan> dsDiem;

	private DefaultTableModel modelDiem, modelKetQua;
	
	private String[][] data;
	 
	public PanelBangDiemCaNhanView() {
		setLayout(new BorderLayout(5, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
		try {
			LoadTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Bảng điểm cá nhân");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/score.png")));
//		label.setIcon(new ImageIcon("/score.png"));
		
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createHeaderMain(), BorderLayout.NORTH);
		panel.add(createMainTable(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createHeaderMain() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 50, 0, 750));
		panel.add(createTitleHeader(), BorderLayout.NORTH);
		panel.add(createMainHeader(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainTable() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBorder(new EmptyBorder(0, 35, 5, 35));
		panel.add(createTablePanel1());
		panel.add(createTablePanel2());
		
		return panel;
	}
	
	private JPanel createTablePanel1() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitle("Bảng điểm học phần sinh viên"), BorderLayout.NORTH);
		panel.add(createTable(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTablePanel2() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(20, 0, 5, 0));
		panel.add(createTitle("Kết quả học tập sinh viên"), BorderLayout.NORTH);
		panel.add(createTable2(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTable2() {
		JPanel panel = new JPanel(new BorderLayout());
		tableKetQua = new JTable();
//		loadData(tableKetQua, titleCols2);
		JScrollPane scroll = new JScrollPane(tableKetQua);
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTitle(String name) {
		JPanel panel = new JPanel();
		panel.add(createLabel(name, Font.BOLD, 16));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		tableDiem = new JTable();
//		loadData(tableDiem, titleCols1);
		JScrollPane scroll = new JScrollPane(tableDiem);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 8, 5, 5));
		
		tfHocKy = new JTextField();
		panelB.add(createtfTimKiem(tfHocKy));
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfHocKy.getText(), 0));
				}
			}
		});
		
		tfIdHP = new JTextField();
		panelB.add(createtfTimKiem(tfIdHP));
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfIdHP.getText(), 1));
				}
			}
		});
		
		tfTenHP = new JTextField();
		panelB.add(createtfTimKiem(tfTenHP));
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfTenHP.getText(), 2));
				}
			}
		});
		
		tfTinChi = new JTextField();
		panelB.add(createtfTimKiem(tfTinChi));
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfTinChi.getText(), 3));
				}
			}
		});
		
		tfLopHoc = new JTextField();
		panelB.add(createtfTimKiem(tfLopHoc));
		tfLopHoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfLopHoc.getText(), 4));
				}
			}
		});
		
		tfDiemQT = new JTextField();
		panelB.add(createtfTimKiem(tfDiemQT));
		tfDiemQT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfDiemQT.getText(), 5));
				}
			}
		});
		
		tfDiemThi = new JTextField();
		panelB.add(createtfTimKiem(tfDiemThi));
		tfDiemThi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfDiemThi.getText(), 6));
				}
			}
		});
		
		tfDiemChu = new JTextField();
		panelB.add(createtfTimKiem(tfDiemChu));
		tfDiemChu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTableDiem(KetQuaTimKiem(tfDiemChu.getText(), 7));
				}
			}
		});
		panel.add(panelB, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf, BorderLayout.CENTER);
		
		panel.add(new JLabel(new ImageIcon(this.getClass().getResource("/key.png"))), BorderLayout.EAST);
		return panel;
	}
	
//	private void loadData(JTable table, String[] titleCols) {
//		SwingUtilities.invokeLater(new Runnable(){public void run(){
//			String data[][] = null;
//		    //Update the model here
//			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
//				@Override
//				public boolean isCellEditable(int row, int column) {
//					// TODO Auto-generated method stub
//					return false;
//				}
//			};
//			table.setModel(tableModel);
//		}});
//	}
	
	private JPanel createTitleHeader() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(createLabel("Thông tin sinh viên", Font.PLAIN, 14));
		
		return panel;
	}
	
	private JPanel createMainHeader() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(7, 1, 5, 5));
		panelL.add(createLabel("+ Mã sinh viên:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Họ tên SV:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Ngày sinh:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Lớp:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Chương trình:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Hệ học", Font.PLAIN, 14));
		panelL.add(createLabel("+ Trạng thái:", Font.PLAIN, 14));
		panel.add(panelL, BorderLayout.WEST);
		
		JPanel panelR = new JPanel(new GridLayout(7, 1, 5, 5));
		panelR.add(gtIdSinhVien = createLabel("20153752", Font.BOLD, 14));
		panelR.add(gtHoTen = createLabel("Nguyễn Tài Tiêu", Font.BOLD, 14));
		panelR.add(gtNgaySinh = createLabel("27.10.1997", Font.BOLD, 14));
		panelR.add(gtLop = createLabel("CNTT2-1 K60", Font.BOLD, 14));
		panelR.add(gtChuongTrinh = createLabel("CT Nhóm ngành CNTT-TT 2-2015", Font.BOLD, 14));
		panelR.add(gtHeHoc = createLabel("Đại học", Font.BOLD, 14));
		panelR.add(gtTrangThai = createLabel("Học", Font.BOLD, 14));
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int indam, int kichThuoc) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", indam, kichThuoc));
		
		return label;
	}
	
	private void LoadTable() throws IOException {
		dsDiem = new ArrayList<DiemHocPhan>();
		
		File file = new File ("quanlysinhvien/sinhvientinchi/SV001/diem.xlsx");
		
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
			
			//lop hoc
			cell = row.getCell(5);
			
			cell = row.getCell(6);
			diem.setDiemQT((double) cell.getNumericCellValue());
			
			cell = row.getCell(7);
			diem.setDiemThi((double) cell.getNumericCellValue());
			
			cell = row.getCell(8);
			diem.setDiemChu(cell.getStringCellValue());
			
			switch (diem.getDiemChu()) {
			case "A+":
				diem.setDiemThang4(4);
				break;
			case "A":
				diem.setDiemThang4(4);
				break;
			case "B+":
				diem.setDiemThang4(3.5);
				break;
			case "B":
				diem.setDiemThang4(3);
				break;
			case "C+":
				diem.setDiemThang4(2.5);
				break;
			case "C":
				diem.setDiemThang4(2);
				break;
			case "D+":
				diem.setDiemThang4(1.5);
				break;
			case "D":
				diem.setDiemThang4(1);
				break;
			case "F":
				diem.setDiemThang4(0);
				break;

			default:
				break;
			}
			
			dsDiem.add(diem);
		}
		
		workbook.close();
		inputStream.close();
		
		
		LoadTableDiem();
		LoadTableKetQua();

		LoadDataIntoPiece();
	}
	
	private void LoadTableDiem() {
		modelDiem = new DefaultTableModel();
		modelDiem.setColumnIdentifiers(titleCols1);;
		LoadDataIntoTableDiem(dsDiem);
		tableDiem.setModel(modelDiem);
	}
	
	private void LoadTableKetQua() {
		modelKetQua = new DefaultTableModel();
		modelKetQua.setColumnIdentifiers(titleCols2);
		LoadDataIntoTableKetQua();
		tableKetQua.setModel(modelKetQua);
	}
	
	private void LoadDataIntoTableDiem(ArrayList<DiemHocPhan> ds) {
		modelDiem.setRowCount(0);
		for (int i = 0; i < ds.size(); i++) {
			String[] rows = new String[8];
			
			rows[0] = ds.get(i).getHocKy();
			rows[1] = ds.get(i).getIdHocPhan();
			rows[2] = ds.get(i).getTenHP();
			rows[3] = Integer.toString(ds.get(i).getTinChi());
			rows[4] = "";
			rows[5] = Double.toString(ds.get(i).getDiemQT());
			rows[6] = Double.toString(ds.get(i).getDiemThi());
			rows[7] = ds.get(i).getDiemChu();
			
			modelDiem.addRow(rows);
		}
	}
	
	private void LoadDataIntoPiece() {
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
	
	private ArrayList<DiemHocPhan> KetQuaTimKiem(String strTimKiem, int j) {
		strTimKiem = strTimKiem.toLowerCase();
		ArrayList<DiemHocPhan> result = new ArrayList<DiemHocPhan>();
		for (int i = 0; i < data.length; i++) {
			String str = data[i][j].toLowerCase();
			if (str.indexOf(strTimKiem) > -1)
				result.add(dsDiem.get(i));
		}
		
		return result;
	}
	
	private void LoadDataIntoTableKetQua() {
		int i = 0;
		float tong = 0;
		int TCTichLuy = 0;
		int TCNo = 0;
		int TCDK = 0;
		float trinhDo = 1;
		while (true) {
			if (i >= dsDiem.size()) break;
			int begin = i;
			trinhDo += 1;
			String hocky = dsDiem.get(i).getHocKy();
			float GPA = 0;
			int TCQua = 0;
			
			for (int j = i+1; j < dsDiem.size(); j++) {
				if (dsDiem.get(j).getHocKy().equals(dsDiem.get(i).getHocKy())) i++; 
			}
			for (int j = begin; j <= i; j++) {
				if (dsDiem.get(j).getHocKy().equals(hocky)) {
					GPA += dsDiem.get(j).getDiemThang4()*dsDiem.get(j).getTinChi();
					tong += dsDiem.get(j).getDiemThang4()*dsDiem.get(j).getTinChi();
					TCQua += dsDiem.get(j).getTinChi();
					TCTichLuy += dsDiem.get(j).getTinChi();
					TCDK += dsDiem.get(j).getTinChi();
				}
				else {
					tong += dsDiem.get(j).getDiemThang4()*dsDiem.get(j).getTinChi();
					TCTichLuy += dsDiem.get(j).getTinChi();
					TCDK += dsDiem.get(j).getTinChi();
				}

			}
			
			GPA = GPA/TCQua;
			float CPA = tong/TCTichLuy;
			
			String[] rows = new String[12];
			rows[0] = hocky;
			rows[1] = Float.toString(GPA);
			rows[2] = Float.toString(CPA);
			rows[3] = Integer.toString(TCQua);
			rows[4] = Integer.toString(TCTichLuy);
			rows[5] = Integer.toString(TCNo);
			rows[6] = Integer.toString(TCDK);
			rows[7] = "Năm thứ " + Integer.toString((int) trinhDo/2);
			if (TCNo < 8) rows[8] = "0";
			else if (TCNo < 16) rows[8] = "1";
			else if (TCNo < 24) rows[8] = "2";
			else rows[8] = "3";
			rows[9] = "Đại học";
			rows[10] = "";
			rows[11] = "";
			
			modelKetQua.addRow(rows);
			
			i++;
		}
	}
}
