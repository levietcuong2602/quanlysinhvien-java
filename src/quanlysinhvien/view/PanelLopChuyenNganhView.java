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

import quanlysinhvien.model.LopChuyenNganh;

public class PanelLopChuyenNganhView extends JPanel{
	private JTable table;
	private JTextField tfIdNganh, tfTenLop, tfIdLopChuyenNganh, tfTenChuNhiem, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatSV;
	private JComboBox<String> timKiemCB;
	private String[] titleCols = {"Mã lớp", "Tên lớp", "Chủ nhiệm", "Mã Khoa/Viện", "Tên Khoa/Viện"};
	private String[] timKiemVals = {"Mã lớp", "Chủ nhiệm", "Mã Khoa/Viện"};
	
	
	
	public PanelLopChuyenNganhView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Danh sách lớp chuyên ngành");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 15, 15));
		panel.setBorder(new EmptyBorder(10, 50, 20, 50));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
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
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		return panel;
	}
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 170, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 0, 15));
		JPanel panelL = new JPanel(new GridLayout(3, 1, 5, 5));
		panelL.add(new JLabel("Mã ngành:"));
		panelL.add(new JLabel("Mã Lớp CN:"));
		
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.add(tfIdNganh = new JTextField());
		panelR.add(tfIdLopChuyenNganh = new JTextField());

		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new GridLayout(3, 1 , 5, 5));
		panelL.add(new JLabel("Tên lớp CN:"));
		panelL.add(new JLabel("Chủ nhiệm lớp:"));
		
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.add(tfTenLop = new JTextField());
		panelR.add(tfTenChuNhiem = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 25, 0, 0));
		panel.add(createTimKiemPanel(), BorderLayout.NORTH);
		panel.add(createBtnOtherPanel(), BorderLayout.CENTER);

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

	private JPanel createBtnOtherPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 80, 45, 80));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(btnCapNhatSV = new JButton("Cập nhật DS sinh viên"));

		return panel;
	}
	
	public void loadData(JTable table, ArrayList<LopChuyenNganh> dsLopCN, String timKiem, String giaTri) {
		String[][] data = convertData(dsLopCN, timKiem, giaTri);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(4).setPreferredWidth(180);
	}
	
	private String[][] convertData(ArrayList<LopChuyenNganh> list, String timKiem, String giaTri) {
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		int index = 0;
		for (int i = 0; i < size; i++) {
			LopChuyenNganh lopCN = list.get(i);
			switch(timKiem) {
			case "Mã lớp": 
				if(lopCN.getIdLopChuyenNganh().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = lopCN.getIdLopChuyenNganh();
					data[index][1] = lopCN.getTenLop();
					data[index][2] = lopCN.getTenChuNhiem();
					data[index][3] = lopCN.getIdKhoaVien();
					data[index][4] = lopCN.getTenKhoaVien();
					index++;
				}
				break;
			case "Chủ nhiệm":
				if(lopCN.getTenChuNhiem().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = lopCN.getIdLopChuyenNganh();
					data[index][1] = lopCN.getTenLop();
					data[index][2] = lopCN.getTenChuNhiem();
					data[index][3] = lopCN.getIdKhoaVien();
					data[index][4] = lopCN.getTenKhoaVien();
					index++;
				}
				break;
			case "Mã Khoa/Viện":
				if(lopCN.getIdKhoaVien().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = lopCN.getIdLopChuyenNganh();
					data[index][1] = lopCN.getTenLop();
					data[index][2] = lopCN.getTenChuNhiem();
					data[index][3] = lopCN.getIdKhoaVien();
					data[index][4] = lopCN.getTenKhoaVien();
					index++;
				}
				break;
			case "": {
				data[index][0] = lopCN.getIdLopChuyenNganh();
				data[index][1] = lopCN.getTenLop();
				data[index][2] = lopCN.getTenChuNhiem();
				data[index][3] = lopCN.getIdKhoaVien();
				data[index][4] = lopCN.getTenKhoaVien();
				index++;
				}
				break;
			}
		}
		if (!giaTri.equals("")) {
			String[][] datatk = new String[index][titleCols.length];
			for (int i = 0; i < index; i++) {
				datatk[i] = data[i];
			}
			return datatk;
		}
		return data;
	}

	public JTable getTable() {
		return table;
	}

	public JTextField getTfIdNganh() {
		return tfIdNganh;
	}

	public JTextField getTfTenLop() {
		return tfTenLop;
	}

	public JTextField getTfIdLopChuyenNganh() {
		return tfIdLopChuyenNganh;
	}

	public JTextField getTfTenChuNhiem() {
		return tfTenChuNhiem;
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

	public JButton getBtnHuy() {
		return btnHuy;
	}

	public JButton getBtnTimKiem() {
		return btnTimKiem;
	}

	public JButton getBtnCapNhatSV() {
		return btnCapNhatSV;
	}

	public JComboBox<String> getTimKiemCB() {
		return timKiemCB;
	}
	
}
