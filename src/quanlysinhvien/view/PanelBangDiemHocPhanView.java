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
import javax.swing.SwingUtilities;
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

public class PanelBangDiemHocPhanView extends JPanel{
	private JTextField tfIdSinhVien, tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfDiemHP;
	private String titleCols[] = {"Học kỳ", "Mã HP", "Tên HP", "TC", "Điểm học phẩn"};
	private JTable table;

	private ArrayList<DiemHocPhan> dsDiem;
	private DefaultTableModel tableModel;
	
	//dùng để tìm kiếm dữ liệu cho nhanh
	String[][] data;
	
	public PanelBangDiemHocPhanView() {
		setLayout(new BorderLayout(15, 15));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	} 

	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Bảng điểm học phần");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/score.png")));
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(5, 40, 40, 40));
		panel.add(createHeaderMain(), BorderLayout.NORTH);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createHeaderMain() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 0, 900));
		panel.add(createLabel("Mã sinh viên:", 16), BorderLayout.WEST);
		panel.add(tfIdSinhVien = new JTextField(20), BorderLayout.CENTER);
		tfIdSinhVien.setText("20153752");
		tfIdSinhVien.setEditable(false);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitle(), BorderLayout.NORTH);
		panel.add(createTable(), BorderLayout.CENTER);
		panel.add(createBottom(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel createTitle() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Bảng điểm học phần sinh viên", 18));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 5, 5, 5));
		
		tfHocKy = new JTextField();
		panelB.add(createtfTimKiem(tfHocKy));
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTable(KetQuaTimKiem(tfHocKy.getText(), 0));
				}
			}
		});
		
		tfIdHP = new JTextField();
		panelB.add(createtfTimKiem(tfIdHP));
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTable(KetQuaTimKiem(tfIdHP.getText(), 1));
				}
			}
		});
		
		tfTenHP = new JTextField();
		panelB.add(createtfTimKiem(tfTenHP));
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTable(KetQuaTimKiem(tfTenHP.getText(), 2));
				}
			}
		});
		
		tfTinChi = new JTextField();
		panelB.add(createtfTimKiem(tfTinChi));
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTable(KetQuaTimKiem(tfTinChi.getText(), 3));
				}
			}
		});
		
		tfDiemHP = new JTextField();
		panelB.add(createtfTimKiem(tfDiemHP));
		tfDiemHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadDataIntoTable(KetQuaTimKiem(tfDiemHP.getText(), 4));
				}
			}
		});
		
		panel.add(panelB, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void loadData(JTable table) {
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			String data[][] = null;
		    //Update the model here
			tableModel = new DefaultTableModel(data, titleCols) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			
			try {
				LoadData();
				LoadDataIntoTable(dsDiem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			table.setModel(tableModel);
		}});
	}
	
	private JPanel createBottom() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		JPanel panelL = new JPanel();
		panelL.add(createLabel("C =", 12));
		panelL.add(new JLabel("32"));
		panel.add(panelL);
		
		JPanel panelR  = new JPanel();
		panelR.add(createLabel("TC =", 12));
		panelR.add(new JLabel("69"));
		panel.add(panelR);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int kickThuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kickThuoc));
		
		return lb;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf, BorderLayout.CENTER);
		
		panel.add(new JLabel(new ImageIcon(this.getClass().getResource("/key.png"))), BorderLayout.EAST);
		return panel;
	}
	
	private void LoadData() throws IOException {
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
			
//			diem.setVienKhoa("");
			
			dsDiem.add(diem);
		}
		
		workbook.close();
		inputStream.close();
	}
	

	
	private ArrayList<DiemHocPhan> KetQuaTimKiem(String strTimKiem, int j) {
		ArrayList<DiemHocPhan> result = new ArrayList<DiemHocPhan>();
		strTimKiem = strTimKiem.toLowerCase();
		for (int i = 0; i < data.length; i++) {
			String str = data[i][j].toLowerCase();
			if (str.indexOf(strTimKiem) > -1)
				result.add(dsDiem.get(i));
		}

		return result;
	}
	
	private void LoadDataIntoTable(ArrayList<DiemHocPhan> ds) {
		tableModel.setRowCount(0);
		for (int i = 0; i < ds.size(); i++) {
			String[] rows = new String[5];
			rows[0] = ds.get(i).getHocKy();
			rows[1] = ds.get(i).getIdHocPhan();
			rows[2] = ds.get(i).getTenHP();
			rows[3] = Integer.toString(ds.get(i).getTinChi());
			rows[4] = ds.get(i).getDiemChu();
			tableModel.addRow(rows);
		}
	}
}
