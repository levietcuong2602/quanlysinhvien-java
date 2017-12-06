package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PanelDangKiHocPhanView extends JPanel{
	private String titleCols[] = {"Mã HP", "Tên lớp", "Ngày đăng ký", "TT đăng ký", "Số TC", "Select"};
	private String hkVals[] = {"20171", "20172"};
	private JComboBox<String> hocKy;
	private JLabel gtThongTin, lbSum;
	private JTextField tfDangky;
	private JButton btnDangKy, btnXoaHP, btnGuiDangKy;
	private JTable table;
	private JCheckBox checkBox;
	
	public PanelDangKiHocPhanView() {
		setLayout(new BorderLayout(15, 15));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Đăng ký học phần");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
//		label.setIcon(new ImageIcon(this.getClass().getResource("/register.png")));
		label.setIcon(new ImageIcon("images/register.png"));
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(5, 25, 20, 25));
		panel.add(createHeaderPanel(), BorderLayout.NORTH);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		panel.add(createBottomPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createHeaderPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 0, 580));
		JPanel panelS = new JPanel();
		FlowLayout layoutS = (FlowLayout) panelS.getLayout();
		layoutS.setAlignment(FlowLayout.LEFT);
		panelS.setBorder(new EmptyBorder(0, 0, 0, 50));
		panelS.add(createLabel("Học kỳ:", 16));
		panelS.add(hocKy = new JComboBox<>(hkVals));
		panel.add(panelS);
		
		JPanel panelC = new JPanel();
		FlowLayout layoutC = (FlowLayout) panelC.getLayout();
		layoutC.setAlignment(FlowLayout.LEFT);
		panelC.setBorder(new EmptyBorder(0, 0, 0, 164));
		panelC.add(createLabel("Mã HP đăng ký:", 16));
		panelC.add(tfDangky = new JTextField(15));
		panelC.add(btnDangKy = new JButton("Đăng ký"));
		panel.add(panelC);
		
		JPanel panelStatus = new JPanel(new BorderLayout());
		panelStatus.setBorder(new EmptyBorder(0, 5, 0, 0));
//		panelStatus.add(lbStatus = new JLabel("không phải thời điểm đăng ký môn học của kỳ 20172"));
//		lbStatus.setForeground(Color.RED);
		panel.add(panelStatus);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelTitle = new JPanel();
		panelTitle.add(createLabel("Bảng đăng ký học phần kỳ " + hocKy.getSelectedItem() + " của sinh viên 20153752", 16));
		panelTitle.setBackground(Color.LIGHT_GRAY);
		panel.add(panelTitle, BorderLayout.NORTH);
		
		panel.add(createTable(), BorderLayout.CENTER);
		
		JPanel panelSum = new JPanel(new FlowLayout());
		panelSum.setBorder(new EmptyBorder(0, 800, 0, 0));
		panelSum.add(createLabel("Tổng số TC đăng ký =", 14));
		panelSum.add(lbSum = createLabel("0", 14));
		
		JPanel panelCheckBox = new JPanel(new FlowLayout());
//		panelCheckBox.setBorder(new EmptyBorder(0, 100, 0, 0));
		checkBox = new JCheckBox();
		panelCheckBox.add(checkBox);
		panelSum.add(panelCheckBox);
		panel.add(panelSum, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout());
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll);
		
		return panel;
	}
	
	private void loadData(JTable table) {
//		SwingUtilities.invokeLater(new Runnable(){public void run(){
//			
//		    //Update the model here
//			DefaultTableModel tableModel = new DefaultTableModel() {
//				@Override
//				public Class<?> getColumnClass(int columnIndex) {
//					// TODO Auto-generated method stub
//					if (columnIndex == 5) {
//						return Boolean.class;
//					}
//					return String.class;
//				}
//			};
//			tableModel.setColumnIdentifiers(titleCols);
//			
//			table.setModel(tableModel);		
//		}});
		
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if (columnIndex == 5) {
					return Boolean.class;
				}
				return String.class;
			}
		};
		model.setColumnIdentifiers(titleCols);
		table.setModel(model);
		
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 15, 15));
		JPanel panelXoaHP = new JPanel();
		panelXoaHP.add(btnXoaHP = new JButton("Xóa các HP chọn"));
		panelXoaHP.setBorder(new EmptyBorder(0, 800, 0, 0));
		panel.add(panelXoaHP);
		
		JPanel panelGuiDK = new JPanel();
		panelGuiDK.add(btnGuiDangKy = new JButton("Gửi đăng ký"));
		panel.add(panelGuiDK);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int kichthuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kichthuoc));
		
		return lb;
	}

	public String[] getHkVals() {
		return hkVals;
	}

	public void setHkVals(String[] hkVals) {
		this.hkVals = hkVals;
	}

	public JComboBox<String> getHocKy() {
		return hocKy;
	}

	public void setHocKy(JComboBox<String> hocKy) {
		this.hocKy = hocKy;
	}

	public JLabel getGtThongTin() {
		return gtThongTin;
	}

	public void setGtThongTin(JLabel gtThongTin) {
		this.gtThongTin = gtThongTin;
	}

	public JTextField getTfDangky() {
		return tfDangky;
	}

	public void setTfDangky(JTextField tfDangky) {
		this.tfDangky = tfDangky;
	}

	public JButton getBtnDangKy() {
		return btnDangKy;
	}

	public void setBtnDangKy(JButton btnDangKy) {
		this.btnDangKy = btnDangKy;
	}

	public JButton getBtnXoaHP() {
		return btnXoaHP;
	}

	public void setBtnXoaHP(JButton btnXoaHP) {
		this.btnXoaHP = btnXoaHP;
	}

	public JButton getBtnGuiDangKy() {
		return btnGuiDangKy;
	}

	public void setBtnGuiDangKy(JButton btnGuiDangKy) {
		this.btnGuiDangKy = btnGuiDangKy;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public JLabel getLbSum() {
		return lbSum;
	}

	public void setLbSum(JLabel lbSum) {
		this.lbSum = lbSum;
	}
	
}
