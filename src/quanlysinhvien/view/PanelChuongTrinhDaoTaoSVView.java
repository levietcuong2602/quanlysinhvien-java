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

public class PanelChuongTrinhDaoTaoSVView extends JPanel{
	private JTextField tfIdSinhVien, tfIdHP, tfTenHP, tfKyHoc, tfTinChi, tfDiemChu, tfDiemSo, tfVien_Khoa;
	private JTable table;
	private String[] titleCols = {"Mã HP", "Tên HP", "Kỳ học", "Tín chỉ", "Điểm chữ", "Điểm số", "Viện/Khoa"};
	
	private DefaultTableModel tableModel = new DefaultTableModel();
	
	//tạo danh sách điểm
	private ArrayList<DiemHocPhan> dsDiem;
	
	//load dữ liệu vào mảng
	private String[][] Data;
	
	
	public PanelChuongTrinhDaoTaoSVView() {
		setLayout(new BorderLayout(15, 15));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Các môn trong chương trình đào tạo của sinh viên");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/list.png")));
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(0, 35, 20, 35));
		panel.add(createHeaderMain(), BorderLayout.NORTH);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createHeaderMain() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 0, 900));
		panel.add(createLabel("Mã sinh viên:", 16), BorderLayout.WEST);
		panel.add(tfIdSinhVien = new JTextField(20), BorderLayout.CENTER);
		tfIdSinhVien.setText("20150001");
		tfIdSinhVien.setEditable(false);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitle(), BorderLayout.NORTH);
		panel.add(createTable(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createTitle() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Chương trình đào tạo sinh viên", 18));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 7, 5, 5));
		
		tfIdHP = new JTextField();
		panelB.add(createtfTimKiem(tfIdHP));
		
		tfTenHP = new JTextField();
		panelB.add(createtfTimKiem(tfTenHP));
		
		tfKyHoc = new JTextField();
		panelB.add(createtfTimKiem(tfKyHoc));
		
		tfTinChi = new JTextField();
		panelB.add(createtfTimKiem(tfTinChi));
		
		tfDiemChu = new JTextField();
		panelB.add(createtfTimKiem(tfDiemChu));
		
		tfDiemSo = new JTextField();
		panelB.add(createtfTimKiem(tfDiemSo));
		
		tfVien_Khoa = new JTextField();
		panelB.add(createtfTimKiem(tfVien_Khoa));
		
		panel.add(panelB, BorderLayout.SOUTH);
		
		//keylistener
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfIdHP.getText(), 0));
				}
			}
		});
		
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfTenHP.getText(), 1));
				}
			}
		});
		
		tfKyHoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfKyHoc.getText(), 2));
				}
			}
		});
		
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfTinChi.getText(), 3));
				}
			}
		});
		
		tfDiemChu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfDiemChu.getText(), 4));
				}
			}
		});
		
		tfDiemSo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfDiemSo.getText(), 5));
				}
			}
		});
		
		tfVien_Khoa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadKetQuaTimKiem(KetQuaTimKiem(tfVien_Khoa.getText(), 6));
				}
			}
		});
		
		return panel;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf, BorderLayout.CENTER);
		
		panel.add(new JLabel(new ImageIcon(this.getClass().getResource("/key.png"))), BorderLayout.EAST);
		return panel;
	}
	
	private JLabel createLabel(String name, int kickThuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kickThuoc));
		
		return lb;
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
			table.setModel(tableModel);
			
			//load Table data
			try {
				LoadDSDiem();
				
				for (int i = 0; i < dsDiem.size(); i++) {
					String[] rows  = new String[7];
					
					rows[0] = dsDiem.get(i).getIdHocPhan();
					rows[1] = dsDiem.get(i).getTenHP();
					rows[2] = dsDiem.get(i).getHocKy();
					rows[3] = Double.toString(dsDiem.get(i).getTinChi());
					rows[4] = dsDiem.get(i).getDiemChu();
					rows[5] = Double.toString(dsDiem.get(i).getDiemThang4());
					rows[6] = "null";
//					rows[6] = dsDiem.get(i).getVienKhoa();
					tableModel.addRow(rows);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
	}
	
	private void LoadDSDiem() throws IOException {
		
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
		
		LoadDataIntoPiece();
	}
	
	
	
	private void LoadDataIntoPiece() {
		Data = new String[dsDiem.size()][7];
		for (int i = 0; i < Data.length; i++) {
			Data[i][0] = dsDiem.get(i).getIdHocPhan();
			Data[i][1] = dsDiem.get(i).getTenHP();
			Data[i][2] = dsDiem.get(i).getHocKy();
			Data[i][3] = Double.toString(dsDiem.get(i).getTinChi());
			Data[i][4] = dsDiem.get(i).getDiemChu();
			Data[i][5] = Double.toString(dsDiem.get(i).getDiemThang4());
			Data[i][6] = "null";
		}		
	}
	
	private void LoadKetQuaTimKiem(ArrayList<DiemHocPhan> kq) {
		tableModel.setRowCount(0);
		for (int i = 0; i < kq.size(); i++) {
			String[] rows  = new String[7];
			
			rows[0] = kq.get(i).getIdHocPhan();
			rows[1] = kq.get(i).getTenHP();
			rows[2] = kq.get(i).getHocKy();
			rows[3] = Double.toString(kq.get(i).getTinChi());
			rows[4] = kq.get(i).getDiemChu();
			rows[5] = Double.toString(kq.get(i).getDiemThang4());
			rows[6] = "null";
			tableModel.addRow(rows);
		}
	}
	
	private ArrayList<DiemHocPhan> KetQuaTimKiem(String strTimKiem, int j) {
		ArrayList<DiemHocPhan> result = new ArrayList<DiemHocPhan>();
		LoadDataIntoPiece();
		strTimKiem = strTimKiem.toLowerCase();
		for (int i = 0; i < Data.length; i++) {
			String str = Data[i][j].toLowerCase();
			if (str.indexOf(strTimKiem) > -1)
				result.add(dsDiem.get(i));
		}
		
		return result;
	}
}
