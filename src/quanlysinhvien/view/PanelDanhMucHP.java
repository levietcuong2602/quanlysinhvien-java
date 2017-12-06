package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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

import quanlysinhvien.model.HocPhan;

public class PanelDanhMucHP extends JPanel {
	private JComboBox<String> khoaVienCB;
	private JTextField tfTimIdHP, tfTimTenHP;
	private String[] vals = {"All", "Viện CNTT-TT", "Viện Cơ khí", "Khoa thể chất", "Viện điện"};
	private String[] titleCols = {"Mã học phần", "Tên học phần", "Số tín chỉ", "TC học phí", "Trọng số"};
	private JTable table;
	private DefaultTableModel tableModel;
	
	//tạo đối tượng danh sách học phần
	private ArrayList<HocPhan> dsHocPhanVienCNTT_TT, dsHocPhanVienCoKhi, dsHocPhanKhoaTheChat, dsHocPhanVienDien;
	private ArrayList<HocPhan> dsHocPhan, dsHocPhanTable;
	
	public PanelDanhMucHP() {
		setLayout(new BorderLayout(15, 15));
		add(createHeaderPanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	} 
	 
	private JPanel createHeaderPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 10, 0, 0));
		JLabel label;
		panel.add(label = createLabel("Danh mục học phần", Font.BOLD, 18, 0xFFFF00));
		label.setIcon(new ImageIcon(this.getClass().getResource("/list.png")));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(10, 35, 40, 35));
		panel.add(createTimKiemPanel(), BorderLayout.NORTH);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTimKiemPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 0, 400));
		JPanel panelL = new JPanel(new BorderLayout(5, 5));
		panelL.add(createLabel("Tìm theo khoa viện", Font.PLAIN, 14, 0), BorderLayout.NORTH);
		
		panelL.add(khoaVienCB = new JComboBox<>(vals), BorderLayout.CENTER);
		dsHocPhanTable = new ArrayList<HocPhan>();      //khởi tạo ds HP trong bảng
		dsHocPhanTable = dsHocPhan;
		khoaVienCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = (String) khoaVienCB.getSelectedItem();
				switch (str) {
				case "All":
					dsHocPhanTable = dsHocPhan;
					LoadTableData(dsHocPhan);
					break;
				case "Viện CNTT-TT":
					dsHocPhanTable = dsHocPhanVienCNTT_TT;
					LoadTableData(dsHocPhanVienCNTT_TT);
					break;
				case "Viện Cơ khí":
					dsHocPhanTable = dsHocPhanVienCoKhi;
					LoadTableData(dsHocPhanVienCoKhi);
					break;
				case "Khoa thể chất":
					dsHocPhanTable = dsHocPhanKhoaTheChat;
					LoadTableData(dsHocPhanKhoaTheChat);
					break;
				case "Viện điện":
					dsHocPhanTable = dsHocPhanVienDien;
					LoadTableData(dsHocPhanVienDien);
					break;
				default:
					break;
				} 
					
			}
		});
		panel.add(panelL);
		
		JPanel panelR = new JPanel(new GridLayout(1, 2, 5, 5));
		
		tfTimIdHP = new JTextField();
		tfTimTenHP = new JTextField();
		panelR.add(createLabTFPanel("Tìm kiếm theo mã học phần", tfTimIdHP));
		panelR.add(createLabTFPanel("Tìm kiếm theo tên học phần", tfTimTenHP));

		tfTimIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadTableData(TimIdHP(tfTimIdHP.getText()));
				}
			}
		});
		
		tfTimTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					LoadTableData(TimTenHP(tfTimTenHP.getText()));
				}
			}
		});
		
		panel.add(panelR);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		JPanel panelTitle = new JPanel();
		panelTitle.add(createLabel("Danh sách các học phần", Font.BOLD, 16, 0));
		panelTitle.setBackground(Color.LIGHT_GRAY);
		panel.add(panelTitle, BorderLayout.NORTH);
		
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		
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
			table.setModel(tableModel);
			
			//load data into table here
			try {
				dsHocPhan = new ArrayList<HocPhan>();
				LoadDSHocPhan();
				
				dsHocPhanTable = dsHocPhan;
				LoadTableData(dsHocPhan);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
	}
	
	private JPanel createLabTFPanel(String name, JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.add(createLabel(name, Font.PLAIN, 12, 0), BorderLayout.NORTH);
		panel.add(tf, BorderLayout.CENTER);
		return panel;
	}
	
	private JLabel createLabel(String name, int inDam, int kichThuoc, int maMau) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", inDam, kichThuoc));
		if(maMau != 0)
			label.setForeground(new Color(maMau));
		
		return label;
	}
	
	private void LoadDSHocPhan() throws IOException {
		
		dsHocPhanVienCNTT_TT = new ArrayList<HocPhan>();
		dsHocPhanVienCoKhi = new ArrayList<HocPhan>();
		dsHocPhanVienDien = new ArrayList<HocPhan>();
		dsHocPhanKhoaTheChat = new ArrayList<HocPhan>();
		
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
			hp.setSoTCHocPhi((int) cell.getNumericCellValue());
			
			cell = row.getCell(5);
			hp.setIdNganh(cell.getStringCellValue());
			
			cell = row.getCell(6);
			hp.setTrongSo((double) cell.getNumericCellValue());
			
			dsHocPhan.add(hp);
			switch (hp.getIdNganh()) {
			case "IT":
				dsHocPhanVienCNTT_TT.add(hp);
				break;
			case "EE":
				dsHocPhanVienDien.add(hp);
				break;
			case "ME":
				dsHocPhanVienCoKhi.add(hp);
				break;
			case "PE":
				dsHocPhanKhoaTheChat.add(hp);
				break;
			default:
				break;
			}	
		}
		workbook.close();
		inputStream.close();
	}
	
	private void LoadTableData(ArrayList<HocPhan> dsHP) {
		tableModel.setRowCount(0);
		
		for (int i = 0; i < dsHP.size(); i++) {
			String[] rows = new String[5];
			
			rows[0] = dsHP.get(i).getIdHocPhan();
			rows[1] = dsHP.get(i).getTenHP();
			rows[2] = Double.toString(dsHP.get(i).getSoTinChi());
			rows[3] = Double.toString(dsHP.get(i).getSoTCHocPhi());
			rows[4] = Double.toString(dsHP.get(i).getTrongSo());
			tableModel.addRow(rows);
		}
	}

	private ArrayList<HocPhan> TimIdHP(String strTim) {
		strTim = strTim.toLowerCase();
		ArrayList<HocPhan> result = new ArrayList<HocPhan>();
		for (int i = 0; i < dsHocPhanTable.size(); i++) {
			String str = dsHocPhanTable.get(i).getIdHocPhan();
			str = str.toLowerCase();
			if (str.indexOf(strTim) > -1)
				result.add(dsHocPhanTable.get(i));
		}
		return result;
	}
	
	private ArrayList<HocPhan> TimTenHP(String strTim) {
		strTim = strTim.toLowerCase();
		ArrayList<HocPhan> result = new ArrayList<HocPhan>();
		for (int i = 0; i < dsHocPhanTable.size(); i++) {
			String str = dsHocPhanTable.get(i).getTenHP();
			str = str.toLowerCase();
			if (str.indexOf(strTim) > -1)
				result.add(dsHocPhanTable.get(i));
		}
		return result;
	}
}
