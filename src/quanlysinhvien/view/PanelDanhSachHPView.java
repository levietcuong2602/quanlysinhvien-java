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

import quanlysinhvien.model.HocPhan;

public class PanelDanhSachHPView extends JPanel{
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem;
	private JComboBox<String> timKiemCB;
 	private JTextField tfIdHocPhan, tfTenHP, tfSoTC, tfIdNganh, tfTrongSo, tfTimKiem, tfSoTCHocPhi;
	private String[] titleCols = {"Mã học phần", "Tên học phần", "Số tín chỉ", "Số TC học phí", "Mã ngành", "Trọng số"};
	private String[] timKiemVals = {"Mã học phần", "Tên học phần", "Mã ngành"};
	
	public PanelDanhSachHPView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Danh sách học phần", Font.BOLD, 18, 0xFFFF00));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		panel.setBorder(new EmptyBorder(10, 50, 40, 50));
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
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
		panel.setBorder(new EmptyBorder(0, 0, 125, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
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
		JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 80, 95, 80));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new GridLayout(4, 1, 5, 5));
		panelL.add(new JLabel("Mã học phần:"));
		panelL.add(new JLabel("Tên học phần:"));
		panelL.add(new JLabel("Số tín chỉ:"));
		
		JPanel panelR = new JPanel(new GridLayout(4, 1, 5, 5));
		panelR.add(tfIdHocPhan = new JTextField());
		panelR.add(tfTenHP = new JTextField());
		panelR.add(tfSoTC = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new GridLayout(4, 1, 5, 5));
		panelL.add(new JLabel("Mã ngành:"));
		panelL.add(new JLabel("Trọng số:"));
		panelL.add(new JLabel("Số TC học phí:"));
		
		JPanel panelR = new JPanel(new GridLayout(4, 1, 5, 5));
		panelR.add(tfIdNganh = new JTextField());
		panelR.add(tfTrongSo = new JTextField());
		panelR.add(tfSoTCHocPhi = new JTextField());
		
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
	
	public void loadData(JTable table, ArrayList<HocPhan> dsHP, String timKiem, String giaTri) {
		String[][] data = convertData(dsHP, timKiem, giaTri);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
	}
	
	private String[][] convertData(ArrayList<HocPhan> list, String timKiem, String giaTri){
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		int index = 0;
		for(int i = 0; i < size; i++) {
			HocPhan hp = list.get(i);
			switch(timKiem) {
			case "Mã học phần":
				if(hp.getIdHocPhan().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = hp.getSoTinChi() + "";
					data[index][3] = hp.getSoTCHocPhi() + "";
					data[index][4] = hp.getIdNganh();
					data[index][5] = hp.getTrongSo() + "";
					index++;
				}
				break;
			case "Tên học phần":
				if(hp.getTenHP().toLowerCase().indexOf(giaTri) >=0 ) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = hp.getSoTinChi() + "";
					data[index][3] = hp.getSoTCHocPhi() + "";
					data[index][4] = hp.getIdNganh();
					data[index][5] = hp.getTrongSo() + "";
					index++;
				}
				break;
			case "Mã ngành":
				if(hp.getIdNganh().toLowerCase().indexOf(giaTri) >=0 ) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = hp.getSoTinChi() + "";
					data[index][3] = hp.getSoTCHocPhi() + "";
					data[index][4] = hp.getIdNganh();
					data[index][5] = hp.getTrongSo() + "";
					index++;
				}
				break;
			case "": {
				data[index][0] = hp.getIdHocPhan();
				data[index][1] = hp.getTenHP();
				data[index][2] = hp.getSoTinChi() + "";
				data[index][3] = hp.getSoTCHocPhi() + "";
				data[index][4] = hp.getIdNganh();
				data[index][5] = hp.getTrongSo() + "";
				index++; }
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

	public JComboBox<String> getTimKiemCB() {
		return timKiemCB;
	}

	public JTextField getTfIdHocPhan() {
		return tfIdHocPhan;
	}

	public JTextField getTfTenHP() {
		return tfTenHP;
	}

	public JTextField getTfSoTC() {
		return tfSoTC;
	}

	public JTextField getTfIdNganh() {
		return tfIdNganh;
	}

	public JTextField getTfTrongSo() {
		return tfTrongSo;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
	}

	public JTextField getTfSoTCHocPhi() {
		return tfSoTCHocPhi;
	}

}
