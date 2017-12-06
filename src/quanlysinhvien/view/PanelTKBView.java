package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.LopHocPhan;

public class PanelTKBView extends JPanel{
	private String titleCols[] = {"Thời gian", "Tuần học", "Phòng học", "Mã lớp", "Loại lớp", "Mã HP", "Tên lớp"};
	private JTable table;
	private JLabel labStatus;
	
	public PanelTKBView() {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(20, 60, 180, 60));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
		add(createStatusPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Thời khóa biểu");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/tkb.png")));
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll);
		
		return panel;
	}
	
	private JPanel createStatusPanel() {
		JPanel panel = new JPanel();
		labStatus = new JLabel("");
		labStatus.setFont(new Font("Caribli", Font.BOLD, 16));
		labStatus.setForeground(Color.BLUE);
		panel.add(labStatus);
		
		return panel;
	}
	
	public void loadData(JTable table, ArrayList<LopHocPhan> dsLopHP) {
		String[][] data = convertData(dsLopHP);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(6).setPreferredWidth(175);
	}
	
	private String[][] convertData(ArrayList<LopHocPhan> dsLopHP){
		int size = dsLopHP.size();
		String data[][] = new String[size][titleCols.length];
		for(int i = 0; i < size; i++) {
			LopHocPhan lopHP = dsLopHP.get(i);
			data[i][0] = lopHP.getThoiGian();
			data[i][1] = lopHP.getTuanHoc();
			data[i][2] = lopHP.getPhongHoc();
			data[i][3] = lopHP.getIdLop();
			data[i][4] = lopHP.getLoaiLop();
			data[i][5] = lopHP.getIdHocPhan();
			data[i][6] = lopHP.getTenLop();
		}
		
		return data;
	}
	
	public JTable getTable() {
		return table;
	}

	public JLabel getLabStatus() {
		return labStatus;
	}
	
}
