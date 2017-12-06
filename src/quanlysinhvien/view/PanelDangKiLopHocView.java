package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PanelDangKiLopHocView extends JPanel{
	private JTextField tfDangKy;
	private JButton btnDangKy, btnXoaDangKy, btnGuiDangKy;
	private JTable tableDangKy, tableTKB;
	private String[] titleCols1 = {"Mã lớp", "Tên lớp", "Mã học phần", "loại lớp", "Trạng thái ĐK", "Select"};
	private String[] titleCols2 = {"Thứ", "Thời gian", "Tuần học", "Phòng học", "Mã lớp"};
	private JLabel lblSum;
	private JCheckBox cbCheck;
	
	
	public PanelDangKiLopHocView() {
		setLayout(new BorderLayout(15, 15));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Đăng ký lớp học");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
//		label.setIcon(new ImageIcon(this.getClass().getResource("/register.png")));
		label.setIcon(new ImageIcon("images/register.png"));
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0,  40, 10, 40));
		panel.add(createPanelDangKy(), BorderLayout.NORTH);
		panel.add(createMainTable(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createPanelDangKy() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Mã lớp: "));
		panel.add(tfDangKy = new JTextField(10));
		panel.add(btnDangKy = new JButton("Đăng ký"));
		
		return panel;
	}
	
	private JPanel createMainTable() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		panel.add(createTablePanel1());
		panel.add(createTablePanel2());
		
		return panel;
	}
	
	private JPanel createTablePanel1() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitleTable("Các lớp đăng ký"), BorderLayout.NORTH);
		
		tableDangKy = new JTable();
		//loadtitle table dang ki
		loadData(tableDangKy, titleCols1, 1);
		JScrollPane scroll = new JScrollPane(tableDangKy);
		panel.add(scroll, BorderLayout.CENTER);
		
		JPanel panelSouth = new JPanel(new GridLayout(2, 1, 5, 5));
		JPanel panelTongTC = new JPanel(new BorderLayout(50, 0));
		panelTongTC.setBorder(new EmptyBorder(0, 850, 0, 50));
		JPanel panelLabel = new JPanel(new BorderLayout());
		JLabel lblTongTC = new JLabel("Tổng số TC đăng ký = ");
		lblSum = new JLabel("0");
		panelLabel.add(lblTongTC, BorderLayout.CENTER);
		panelLabel.add(lblSum, BorderLayout.EAST);
		panelTongTC.add(panelLabel, BorderLayout.CENTER);
		
		cbCheck = new JCheckBox();
		panelTongTC.add(cbCheck, BorderLayout.EAST);
		
		panelSouth.add(panelTongTC);
		
		JPanel btnPanel = new JPanel(new BorderLayout());
		btnPanel.setBorder(new EmptyBorder(5, 875, 0, 50));
		btnPanel.add(btnXoaDangKy = new JButton("Xóa lớp"), BorderLayout.CENTER);
		panelSouth.add(btnPanel);
		
		panel.add(panelSouth, BorderLayout.SOUTH);
		
		
		return panel;
	}
	private JPanel createTablePanel2() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(15, 0, 0, 0));
		panel.add(createTitleTable("Thời khóa biểu các lớp đăng ký"), BorderLayout.NORTH);
		
		tableTKB = new JTable();
		loadData(tableTKB, titleCols2, 2);
		JScrollPane scroll = new JScrollPane(tableTKB);
		panel.add(scroll, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(btnGuiDangKy = new JButton("Gửi đăng ký"));
		panel.add(btnPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createTitleTable(String name) {
		JPanel panel = new JPanel();
		panel.add(createLabel(name, Font.BOLD, 16));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int indam, int kichThuoc) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", indam, kichThuoc));
		
		return label;
	}
	
	
	private void loadData(JTable table, String[] titleCols, int loaiTable) {
//		SwingUtilities.invokeLater(new Runnable(){public void run(){
//			String data[][] = null;
//		    //Update the model here
//			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
//				@Override
//				public boolean isCellEditable(int row, int column) {
//					// TODO Auto-generated method stub
//					return false;
//				}
//				
//				@Override
//				public Class<?> getColumnClass(int column) {
//					// TODO Auto-generated method stub
//					if(loaiTable ==1){
//						if(column == 5){
//							return Boolean.class;
//						}
//					}
//					return String.class;
//				}
//			};
//			table.setModel(tableModel);
//		}});
		
		DefaultTableModel model = new DefaultTableModel(){
			@Override
			public Class<?> getColumnClass(int column) {
				// TODO Auto-generated method stub
				if(loaiTable ==1){
					if(column == 5){
						return Boolean.class;
					}
				}
				return String.class;
			}
		};
		
		model.setColumnIdentifiers(titleCols);
		table.setModel(model);
	}

	public JTextField getTfDangKy() {
		return tfDangKy;
	}

	public void setTfDangKy(JTextField tfDangKy) {
		this.tfDangKy = tfDangKy;
	}

	public JButton getBtnDangKy() {
		return btnDangKy;
	}

	public void setBtnDangKy(JButton btnDangKy) {
		this.btnDangKy = btnDangKy;
	}

	public JButton getBtnXoaDangKy() {
		return btnXoaDangKy;
	}

	public void setBtnXoaDangKy(JButton btnXoaDangKy) {
		this.btnXoaDangKy = btnXoaDangKy;
	}

	public JButton getBtnGuiDangKy() {
		return btnGuiDangKy;
	}

	public void setBtnGuiDangKy(JButton btnGuiDangKy) {
		this.btnGuiDangKy = btnGuiDangKy;
	}

	public JTable getTableDangKy() {
		return tableDangKy;
	}

	public void setTableDangKy(JTable tableDangKy) {
		this.tableDangKy = tableDangKy;
	}

	public JTable getTableTKB() {
		return tableTKB;
	}

	public void setTableTKB(JTable tableTKB) {
		this.tableTKB = tableTKB;
	}

	public JLabel getLblSum() {
		return lblSum;
	}

	public void setLblSum(JLabel lblSum) {
		this.lblSum = lblSum;
	}

	public JCheckBox getCbCheck() {
		return cbCheck;
	}

	public void setCbCheck(JCheckBox cbCheck) {
		this.cbCheck = cbCheck;
	}
	
	
}
