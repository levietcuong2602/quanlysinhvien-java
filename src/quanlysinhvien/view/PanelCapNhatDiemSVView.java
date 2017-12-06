package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PanelCapNhatDiemSVView extends JPanel{
	private JTextField tfIdHocPhan, tfTenHP, tfTinChi, tfIdLopHoc, tfDiemQT, tfDiemThi, tfDiemChu;
	private JButton btnThem, btnLuu, btnHuy;
	private JComboBox<String> hocKyCB, loaiSVCB;
	private String[] hocKyVals = {"20172", "20171", "20163", "20162", "20161", "20153", "20152", "20151"};
	private String[] loaiSVVals = {"Sinh viên tín chỉ", "Sinh viên niên chế"};
	public PanelCapNhatDiemSVView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Cập nhật điểm sinh viên", Font.BOLD, 18, 0xFFFF00));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 185, 300, 185));
		panel.add(createInputPanel());
		panel.add(createButtonPanel());
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(4, 1, 5, 5));
		panelL.add(new JLabel("Học kỳ:"));
		panelL.add(new JLabel("Mã học phần:"));
		panelL.add(new JLabel("Tín chỉ:"));
		panelL.add(new JLabel("Mã lớp học:"));
		
		JPanel panelR = new JPanel(new GridLayout(4, 1, 5, 5));
		panelR.add(createPanelCB(hocKyCB, hocKyVals));
		panelR.add(tfIdHocPhan = new JTextField());
		panelR.add(tfTinChi = new JTextField());
		panelR.add(tfIdLopHoc = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(4, 1, 5, 5));
		panelL.add(new JLabel("Loại sinh viên:"));
		panelL.add(new JLabel("Tên học phần:"));
		panelL.add(new JLabel("Điểm quá trình:"));
		panelL.add(new JLabel("Điểm thi:"));
		
		JPanel panelR = new JPanel(new GridLayout(4, 1, 5, 5));
		panelR.add(createPanelCB(loaiSVCB, loaiSVVals));
		panelR.add(tfTenHP = new JTextField());
		panelR.add(tfDiemQT = new JTextField());
		panelR.add(tfDiemThi = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.setBorder(new EmptyBorder(10, 185, 110, 185));
		panel.add(btnThem = new JButton("Thêm"));
		panel.add(btnHuy = new JButton("Hủy"));
		panel.add(btnLuu = new JButton("Lưu"));
		
		return panel;
	}
	
	private JPanel createPanelCB(JComboBox<String> cb, String[] vals) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 0, 0, 175));
		panel.add(cb = new JComboBox<>(vals));
		
		return panel;
	}
	
	private JLabel createLabel(String name, int inDam, int kichThuoc, int maMau) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", inDam, kichThuoc));
		if(maMau != 0)
			label.setForeground(new Color(maMau));
		
		return label;
	}
}
