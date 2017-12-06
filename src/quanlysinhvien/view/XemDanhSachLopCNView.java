package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.LopChuyenNganh;

public class XemDanhSachLopCNView extends JDialog{
	private JTable table;
	private String[] titleCols = {"Mã lớp", "Tên lớp", "Chủ nhiệm", "Mã Khoa/Viện", "Tên Khoa/Viện"};
	public XemDanhSachLopCNView(String idNganh, String tenNganh) {
		setSize(1000, 650);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(tenNganh), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	private JPanel createTitlePanel(String tenNganh) {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x0009999));
		panel.add(createLabel(tenNganh));
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 20, 40, 20));
		JPanel panelT = new JPanel(new BorderLayout());
		panelT.setBorder(new TitledBorder(null, ""));
		JScrollPane scroll = new JScrollPane(table = new JTable());
		
		panelT.add(scroll);
		panel.add(panelT);
		return panel;
	}
	
	private JLabel createLabel(String tenNganh) {
		JLabel label = new JLabel("Danh sách lớp chuyên ngành: " + tenNganh);
		label.setFont(new Font("Calibri", Font.BOLD, 16));
		label.setForeground(Color.yellow);
		return label;
	}
	
	public void loadData(JTable table, ArrayList<LopChuyenNganh> dsLopCN) {
		String[][] data = convertData(dsLopCN);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(4).setPreferredWidth(175);
	}
	
	private String[][] convertData(ArrayList<LopChuyenNganh> dsLopCN) {
		int size = dsLopCN.size();
		String data[][] = new String[size][titleCols.length];
		for(int i = 0; i < size; i++) {
			data[i][0] = dsLopCN.get(i).getIdLopChuyenNganh();
			data[i][1] = dsLopCN.get(i).getTenLop();
			data[i][2] = dsLopCN.get(i).getTenChuNhiem();
			data[i][3] = dsLopCN.get(i).getIdKhoaVien();
			data[i][4] = dsLopCN.get(i).getTenKhoaVien();
		}
		
		return data;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
}
