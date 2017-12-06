package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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

import quanlysinhvien.model.DiemHocPhan;

public class PanelBangDiemCaNhanView extends JPanel{
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop, gtHeHoc, gtTrangThai;
	private JTable tableDiem, tableKetQua;
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfLopHoc, tfDiemQT, tfDiemThi, tfDiemChu;
	private String[] titleCols1 = {"Học kỳ", "Mã HP", "Tên HP", "TC", "Lớp học", "Điểm QT", "Điểm thi", "Điểm chữ"};
	private String[] titleCols2 = {"Học kỳ", "GPA", "CPA", "TC qua", "TC tích lũy", "TC nợ ĐK", "TC ĐK", "Trình độ",
			"mức CC", "CTĐT", "Dự kiến XLHT", "Xử lý chính thức"};

	 
	public PanelBangDiemCaNhanView() {
		setLayout(new BorderLayout(5, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Bảng điểm cá nhân");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon("images/score.png"));
		
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
		JScrollPane scroll = new JScrollPane(tableDiem);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 8, 5, 5));
		
		tfHocKy = new JTextField();
		panelB.add(createtfTimKiem(tfHocKy));
		tfIdHP = new JTextField();
		panelB.add(createtfTimKiem(tfIdHP));
		tfTenHP = new JTextField();
		panelB.add(createtfTimKiem(tfTenHP));
		tfTinChi = new JTextField();
		panelB.add(createtfTimKiem(tfTinChi));
		tfLopHoc = new JTextField();
		panelB.add(createtfTimKiem(tfLopHoc));
		tfDiemQT = new JTextField();
		panelB.add(createtfTimKiem(tfDiemQT));
		tfDiemThi = new JTextField();
		panelB.add(createtfTimKiem(tfDiemThi));
		tfDiemChu = new JTextField();
		panelB.add(createtfTimKiem(tfDiemChu));
		panel.add(panelB, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf, BorderLayout.CENTER);
		panel.add(new JLabel(new ImageIcon("images/key.png")), BorderLayout.EAST);
		return panel;
	}
	
	public void loadData1(JTable table, ArrayList<DiemHocPhan> dsDiem) {
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			String data[][] = convertData1(dsDiem);
		    //Update the model here
			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols1) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			table.setModel(tableModel);
			table.getColumnModel().getColumn(2).setPreferredWidth(175);
		}});
	}
	
	private String[][] convertData1(ArrayList<DiemHocPhan> dsDiem) {
		String[][] data = new String[dsDiem.size()][8];
		for (int i = 0; i < dsDiem.size(); i++) {
			data[i][0] = dsDiem.get(i).getHocKy()+"";
			data[i][1] = dsDiem.get(i).getIdHocPhan();
			data[i][2] = dsDiem.get(i).getTenHP();
			data[i][3] = dsDiem.get(i).getTinChi()+"";
			data[i][4] = dsDiem.get(i).getIdLopHoc();
			data[i][5] = dsDiem.get(i).getDiemQT()+"";
			data[i][6] = dsDiem.get(i).getDiemThi()+"";
			data[i][7] = dsDiem.get(i).getDiemChu();
		}
		
		return data;
	}
	
	public void loadData2(JTable table, ArrayList<DiemHocPhan> dsDiem) {
		String[][] data = null;
		DefaultTableModel model = new DefaultTableModel(data, titleCols2);
		updateModel(model, dsDiem);
		table.setModel(model);
		if(table.getColumnCount() == 12) table.getColumnModel().getColumn(11).setPreferredWidth(100);
	}
	
	private void updateModel(DefaultTableModel tableModel, ArrayList<DiemHocPhan> dsDiem) {
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
			rows[9] = "";
			rows[10] = "";
			rows[11] = "";
			
			tableModel.addRow(rows);
			
			i++;
		}
	}
	
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
		panelL.add(createLabel("+ Hệ học", Font.PLAIN, 14));
		panelL.add(createLabel("+ Trạng thái:", Font.PLAIN, 14));
		panel.add(panelL, BorderLayout.WEST);
		
		JPanel panelR = new JPanel(new GridLayout(7, 1, 5, 5));
		panelR.add(gtIdSinhVien = createLabel("", Font.BOLD, 14));
		panelR.add(gtHoTen = createLabel("", Font.BOLD, 14));
		panelR.add(gtNgaySinh = createLabel("", Font.BOLD, 14));
		panelR.add(gtLop = createLabel("", Font.BOLD, 14));
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

	public JTable getTableDiem() {
		return tableDiem;
	}

	public void setTableDiem(JTable tableDiem) {
		this.tableDiem = tableDiem;
	}

	public JTable getTableKetQua() {
		return tableKetQua;
	}

	public void setTableKetQua(JTable tableKetQua) {
		this.tableKetQua = tableKetQua;
	}

	public JTextField getTfHocKy() {
		return tfHocKy;
	}

	public void setTfHocKy(JTextField tfHocKy) {
		this.tfHocKy = tfHocKy;
	}

	public JTextField getTfIdHP() {
		return tfIdHP;
	}

	public void setTfIdHP(JTextField tfIdHP) {
		this.tfIdHP = tfIdHP;
	}

	public JTextField getTfTenHP() {
		return tfTenHP;
	}

	public void setTfTenHP(JTextField tfTenHP) {
		this.tfTenHP = tfTenHP;
	}

	public JTextField getTfTinChi() {
		return tfTinChi;
	}

	public void setTfTinChi(JTextField tfTinChi) {
		this.tfTinChi = tfTinChi;
	}

	public JTextField getTfLopHoc() {
		return tfLopHoc;
	}

	public void setTfLopHoc(JTextField tfLopHoc) {
		this.tfLopHoc = tfLopHoc;
	}

	public JTextField getTfDiemQT() {
		return tfDiemQT;
	}

	public void setTfDiemQT(JTextField tfDiemQT) {
		this.tfDiemQT = tfDiemQT;
	}

	public JTextField getTfDiemThi() {
		return tfDiemThi;
	}

	public void setTfDiemThi(JTextField tfDiemThi) {
		this.tfDiemThi = tfDiemThi;
	}

	public JTextField getTfDiemChu() {
		return tfDiemChu;
	}

	public void setTfDiemChu(JTextField tfDiemChu) {
		this.tfDiemChu = tfDiemChu;
	}

	public JLabel getGtIdSinhVien() {
		return gtIdSinhVien;
	}

	public JLabel getGtHoTen() {
		return gtHoTen;
	}

	public JLabel getGtNgaySinh() {
		return gtNgaySinh;
	}

	public JLabel getGtLop() {
		return gtLop;
	}
	
}
