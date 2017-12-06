package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.Khoa_Vien;

public class PanelDanhSachNganhView extends JPanel{
	private JTable table;
	private JTextField tfIdKhoa_Vien, tfTenKhoa_Vien, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnHuy, btnXemDSNganh;
	private JComboBox<String> timKiemCB;
	private String[] titleCols = {"Mã Khoa/Viện", "Tên Khoa/Viện"};
	private String[] timKiemVals = {"Mã Khoa/Viện", "Tên Khoa/Viện"};
	
	
	public PanelDanhSachNganhView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Danh sách Khoa/Viện", Font.BOLD, 18, 0xFFFF00));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
		panel.setBorder(new EmptyBorder(10, 50, 40, 50));
		panel.add(createTablePanel());
		panel.add(createRightPanel());
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(null, ""));
		JScrollPane scroll = new JScrollPane(table = new JTable());
		panel.add(scroll);
		
		return panel;
	}
	
	private JPanel createRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(50, 0, 35, 0));
		panel.add(createInputPanel(), BorderLayout.NORTH);
		panel.add(createBottomPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 5));
		panel.add(createInputLeftPanel(), BorderLayout.WEST);
		panel.add(createInputRightPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 40, 0, 40));
		panel.add(createTimKiemPanel(), BorderLayout.NORTH);
		panel.add(createButtonPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(new EmptyBorder(25, 45, 145, 45));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(btnXemDSNganh = new JButton("Xem danh sách lớp chuyên ngành"));

		return panel;
	}
	
	private JPanel createTimKiemPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		JPanel panelButCB = new JPanel(new GridLayout(1, 2, 5, 5));
		panelButCB.add(btnTimKiem = new JButton("Tìm kiếm"));
		panelButCB.add(timKiemCB = new JComboBox<>(timKiemVals));
		
		JPanel panelTF = new JPanel(new BorderLayout());
		panelTF.add(tfTimKiem = new JTextField());
		panel.add(panelButCB);
		panel.add(panelTF);
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 10, 10));
		panelL.add(new JLabel("Mã Khoa/Viện:"));
		JPanel panelR = new JPanel(new GridLayout(2, 1, 10, 10));
		panelR.add(tfIdKhoa_Vien = new JTextField(7));
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 10, 10));
		panelL.add(new JLabel("Tên Khoa/Viện:"));
		JPanel panelR = new JPanel(new GridLayout(2, 1, 10, 10));
		panelR.add(tfTenKhoa_Vien = new JTextField());
		JPanel panelCapNhat = new JPanel(new GridLayout());
		panelR.add(panelCapNhat);
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int inDam, int kichThuoc, int maMau) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", inDam, kichThuoc));
		if(maMau != 0)
			label.setForeground(new Color(maMau));
		
		return label;
	}
	
	public void loadData(JTable table, ArrayList<Khoa_Vien> dsKhoa_Vien, String timKiem, String giaTri) {
		String[][] data = convertData(dsKhoa_Vien, timKiem, giaTri);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
	}
	
	private String[][] convertData(ArrayList<Khoa_Vien> list, String timKiem, String giaTri) {
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		int index = 0;
		for (int i = 0; i < size; i++) {
			Khoa_Vien khoa_vien = list.get(i);
			switch(timKiem) {
			case "Mã Khoa/Viện": 
				if(khoa_vien.getIdKhoa_Vien().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = khoa_vien.getIdKhoa_Vien();
					data[index][1] = khoa_vien.getTenKhoa_Vien();
					index++;
				}
				break;
			case "Tên Khoa/Viện":
				if(khoa_vien.getTenKhoa_Vien().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = khoa_vien.getIdKhoa_Vien();
					data[index][1] = khoa_vien.getTenKhoa_Vien();
					index++;
				}
				break;
			case "":{
				data[index][0] = khoa_vien.getIdKhoa_Vien();
				data[index][1] = khoa_vien.getTenKhoa_Vien();
				index++;
				}
				break;
			}
		}
		if(!giaTri.equals("")) {
			String[][] datatk = new String[index][titleCols.length];
			for(int i = 0; i < index; i++) {
				datatk[i] = data[i];
			}
			return datatk;
		}
		return data;
	}

	public JTable getTable() {
		return table;
	}

	public JTextField getTfIdKhoa_Vien() {
		return tfIdKhoa_Vien;
	}

	public JTextField getTfTenKhoa_Vien() {
		return tfTenKhoa_Vien;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
	}

	public JButton getBtnThem() {
		return btnThem;
	}

	public JButton getBtnSua() {
		return btnSua;
	}

	public JButton getBtnXoa() {
		return btnXoa;
	}

	public JButton getBtnTimKiem() {
		return btnTimKiem;
	}

	public JButton getBtnHuy() {
		return btnHuy;
	}

	public JButton getBtnXemDSNganh() {
		return btnXemDSNganh;
	}

	public JComboBox<String> getTimKiemCB() {
		return timKiemCB;
	}
	
}
