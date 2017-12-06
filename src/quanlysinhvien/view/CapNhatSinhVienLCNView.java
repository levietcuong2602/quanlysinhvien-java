package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.SinhVien;

public class CapNhatSinhVienLCNView extends JDialog{
	private JTable table;
	private JButton btnThem, btnXoa;
	private JTextField tfIdSinhVien;
	private JComboBox<String> loaiSVCB;
	private String[] titleCols = {"Mã sinh viên", "Họ tên", "Khóa", "Tên lớp", "Ngày sinh", "Giới tính", "Email", "Địa chỉ"};
	private String[] loaiSV = {"Sinh viên tín chỉ", "Sinh viên niên chế"};
	private ArrayList<SinhVien> dsSinhVien;
	
	public CapNhatSinhVienLCNView(ArrayList<SinhVien> dsSinhVien, String idLop) {
		this.dsSinhVien = dsSinhVien;
		setSize(950, 650);
		setLocationRelativeTo(null);
		add(createTitlePanel(idLop), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private JPanel createTitlePanel(String idLop) {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x0009999));
		panel.add(createLabel(idLop));
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(5, 25, 20, 25));
		panel.add(createTablePanel(), BorderLayout.CENTER);
		panel.add(createBottomPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(null, ""));
		JScrollPane scroll = new JScrollPane(table = new JTable());
		panel.add(scroll);
		
		return panel;
	}
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
		panel.setBorder(new EmptyBorder(0, 200, 0, 200));
		JPanel panel1 = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 5, 5));
		panelL.add(new JLabel("Mã sinh viên:"));
		panelL.add(new JLabel("Loại sinh viên:"));
		JPanel panelR = new JPanel(new GridLayout(2, 1, 5, 5));
		panelR.add(tfIdSinhVien = new JTextField());
		panelR.add(loaiSVCB = new JComboBox<>(loaiSV));
		panel1.add(panelL, BorderLayout.WEST);
		panel1.add(panelR, BorderLayout.CENTER);
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 5, 5));
		panel2.setBorder(new EmptyBorder(0, 0, 25, 0));
		panel2.add(btnThem = new JButton("Thêm"));
		panel2.add(btnXoa = new JButton("Xóa"));
		
		panel.add(panel1);
		panel.add(panel2);
		return panel;
	}
	
	private JLabel createLabel(String idLop) {
		JLabel label = new JLabel("Danh sách sinh viên mã lớp: " + idLop);
		label.setFont(new Font("Calibri", Font.BOLD, 16));
		label.setForeground(Color.yellow);
		return label;
	}
	
	public void loadData(JTable table, ArrayList<SinhVien> dsSinhVien) {
		String[][] data = convertData(dsSinhVien);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(125);
		table.getColumnModel().getColumn(3).setPreferredWidth(125);
		table.getColumnModel().getColumn(6).setPreferredWidth(125);
		table.getColumnModel().getColumn(7).setPreferredWidth(125);
	}
	
	private String[][] convertData(ArrayList<SinhVien> dsSinhVien){
		int length = dsSinhVien.size();
		String[][] data = new String[length][titleCols.length];
		for(int i = 0; i < length; i++) {
			data[i][0] = dsSinhVien.get(i).getIdSinhVien();
			data[i][1] = dsSinhVien.get(i).getHoTen();
			data[i][2] = dsSinhVien.get(i).getKhoa();
			data[i][3] = dsSinhVien.get(i).getTenLop();
			data[i][4] = dsSinhVien.get(i).getNgaySinh();
			data[i][5] = dsSinhVien.get(i).getGioiTinh();
			data[i][6] = dsSinhVien.get(i).getEmail();
			data[i][7] = dsSinhVien.get(i).getDiaChi();
		}
		
		return data;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getBtnThem() {
		return btnThem;
	}

	public JButton getBtnXoa() {
		return btnXoa;
	}

	public JTextField getTfIdSinhVien() {
		return tfIdSinhVien;
	}

	public JComboBox<String> getLoaiSVCB() {
		return loaiSVCB;
	}

	public ArrayList<SinhVien> getDsSinhVien() {
		return dsSinhVien;
	}
	
}
