package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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

import quanlysinhvien.model.HocPhan;

public class PanelDanhMucHP extends JPanel {
	private JComboBox<String> khoaVienCB;
	private JTextField tfTimIdHP, tfTimTenHP;
	private String[] vals = {"All", "Viện CNTT-TT", "Viện cơ khí", "Khoa thể chất", "Viện điện"};
	private String[] titleCols = {"Mã học phần", "Tên học phần", "Số tín chỉ", "TC học phí", "Trọng số"};
	private JTable table;
	
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
		label.setIcon(new ImageIcon("images/list.png"));
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
		panel.add(panelL);
		
		JPanel panelR = new JPanel(new GridLayout(1, 2, 5, 5));
		tfTimIdHP = new JTextField();
		panelR.add(createLabTFPanel("Tìm kiếm theo mã học phần", tfTimIdHP));
		tfTimTenHP = new JTextField();
		panelR.add(createLabTFPanel("Tìm kiếm theo tên học phần", tfTimTenHP));
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
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	
	public void loadData(JTable table, ArrayList<HocPhan> dsHocPhan, String timKiem, String giaTri) {
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			String data[][] = convertData(dsHocPhan, timKiem, giaTri);
		    //Update the model here
			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			table.setModel(tableModel);	
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		}});
	}
	
	private String[][] convertData(ArrayList<HocPhan> dsHocPhan, String timKiem, String giaTri) {
		String[][] data = new String[dsHocPhan.size()][5];
		int index = 0;
		for (int i = 0; i < dsHocPhan.size(); i++) {
			switch(timKiem) {
			case "Viện CNTT-TT":
				if(dsHocPhan.get(i).getIdHocPhan().indexOf("IT") == 0) {
					data[index][0] = dsHocPhan.get(i).getIdHocPhan();
					data[index][1] = dsHocPhan.get(i).getTenHP();
					data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
					data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
					data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
					index++;
				}
				break;
			case "Viện cơ khí":
				if(dsHocPhan.get(i).getIdHocPhan().indexOf("ME") == 0) {
					data[index][0] = dsHocPhan.get(i).getIdHocPhan();
					data[index][1] = dsHocPhan.get(i).getTenHP();
					data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
					data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
					data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
					index++;
				}
				break;
			case "Khoa thể chất":
				if(dsHocPhan.get(i).getIdHocPhan().indexOf("PE") == 0) {
					data[index][0] = dsHocPhan.get(i).getIdHocPhan();
					data[index][1] = dsHocPhan.get(i).getTenHP();
					data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
					data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
					data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
					index++;
				}
				break;
			case "Viện điện":
				if(dsHocPhan.get(i).getIdHocPhan().indexOf("EE") == 0) {
					data[index][0] = dsHocPhan.get(i).getIdHocPhan();
					data[index][1] = dsHocPhan.get(i).getTenHP();
					data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
					data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
					data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
					index++;
				}
				break;
			case "All": {
				data[index][0] = dsHocPhan.get(i).getIdHocPhan();
				data[index][1] = dsHocPhan.get(i).getTenHP();
				data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
				data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
				data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
				index++;
				}
				break;
			case "idHP":
				if(dsHocPhan.get(i).getIdHocPhan().toUpperCase().indexOf(giaTri.toUpperCase()) >= 0) {
					data[index][0] = dsHocPhan.get(i).getIdHocPhan();
					data[index][1] = dsHocPhan.get(i).getTenHP();
					data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
					data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
					data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
					index++;
				}
				break;
			case "tenHP":
				if(dsHocPhan.get(i).getTenHP().toUpperCase().indexOf(giaTri.toUpperCase()) >= 0) {
					data[index][0] = dsHocPhan.get(i).getIdHocPhan();
					data[index][1] = dsHocPhan.get(i).getTenHP();
					data[index][2] = dsHocPhan.get(i).getSoTinChi()+"";
					data[index][3] = dsHocPhan.get(i).getSoTCHocPhi()+"";
					data[index][4] = dsHocPhan.get(i).getTrongSo()+"";
					index++;
				}
				break;
			}
			
			
		}
		if (index < dsHocPhan.size()) {
			String[][] datatk = new String[index][titleCols.length];
			for (int i = 0; i < index; i++) {
				datatk[i] = data[i];
			}
			return datatk;
		}
		return data;
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
	
	public JComboBox<String> getKhoaVienCB() {
		return khoaVienCB;
	}

	public void setKhoaVienCB(JComboBox<String> khoaVienCB) {
		this.khoaVienCB = khoaVienCB;
	}

	public JTextField getTfTimIdHP() {
		return tfTimIdHP;
	}

	public void setTfTimIdHP(JTextField tfTimIdHP) {
		this.tfTimIdHP = tfTimIdHP;
	}

	public JTextField getTfTimTenHP() {
		return tfTimTenHP;
	}

	public void setTfTimTenHP(JTextField tfTimTenHP) {
		this.tfTimTenHP = tfTimTenHP;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

}
