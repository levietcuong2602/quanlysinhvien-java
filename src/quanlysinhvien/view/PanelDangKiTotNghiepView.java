package quanlysinhvien.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelDangKiTotNghiepView extends JPanel{
	private JLabel labDiemTB, labTongSoTC, labSoTCNo, labTongSoKy, labDiemTB1;
	private JButton btnDangKy;
	
	public PanelDangKiTotNghiepView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Đăng ký tốt nghiệp");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 50, 40, 50));
		panel.add(createMessagePanel(), BorderLayout.NORTH);
		panel.add(createContentPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMessagePanel() {
		JPanel panel = new JPanel();
		JLabel label;
		panel.add(label = new JLabel("Bạn không có quyền truy cập vào mục này..."));
		label.setFont(new Font("Caribli", Font.BOLD, 34));
		
		return panel;
	}
	
	private JPanel createContentPanel() {
		JPanel panelMain = new JPanel(new GridLayout(2, 1, 15, 15));
		panelMain.setBorder(new EmptyBorder(10, 400, 250, 400));
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelSVTC = new JPanel(new GridLayout(4, 2, 5, 5));
		panelSVTC.add(createLabel("Họ tên SV:"));
		panelSVTC.add(createLabel("Nguyễn Văn A"));
		panelSVTC.add(createLabel("Điểm TB:"));
		panelSVTC.add(labDiemTB = createLabel("3.21"));
		panelSVTC.add(createLabel("Tổng số TC qua:"));
		panelSVTC.add(labTongSoTC = createLabel("158"));
		panelSVTC.add(createLabel("Số TC nợ:"));
		panelSVTC.add(labSoTCNo = createLabel("0"));
		
		JPanel panelSVNC = new JPanel(new GridLayout(2, 1, 5, 5));
		panelSVNC.add(createLabel("Họ tên SV:"));
		panelSVNC.add(createLabel("Nguyễn Văn A"));
		panelSVNC.add(createLabel("Điểm TB:"));
		panelSVNC.add(labDiemTB1 = createLabel("3.21"));
		panelSVNC.add(createLabel("Tổng số kỳ:"));
		panelSVNC.add(labTongSoKy = createLabel("4"));
		
		panel.add(panelSVNC);
		panel.add(panelSVTC);
		
		JPanel panelDangKy = new JPanel();
		panelDangKy.add(btnDangKy = new JButton("Đăng ký"));
		
		panelMain.add(panel);
		panelMain.add(panelDangKy);
		return panelMain;
	}
	
	private JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", Font.BOLD, 16));
		return label;
	}

	public JLabel getLabDiemTB() {
		return labDiemTB;
	}

	public void setLabDiemTB(JLabel labDiemTB) {
		this.labDiemTB = labDiemTB;
	}

	public JLabel getLabTongSoTC() {
		return labTongSoTC;
	}

	public void setLabTongSoTC(JLabel labTongSoTC) {
		this.labTongSoTC = labTongSoTC;
	}

	public JLabel getLabSoTCNo() {
		return labSoTCNo;
	}

	public void setLabSoTCNo(JLabel labSoTCNo) {
		this.labSoTCNo = labSoTCNo;
	}

	public JLabel getLabTongSoKy() {
		return labTongSoKy;
	}

	public void setLabTongSoKy(JLabel labTongSoKy) {
		this.labTongSoKy = labTongSoKy;
	}

	public JLabel getLabDiemTB1() {
		return labDiemTB1;
	}

	public void setLabDiemTB1(JLabel labDiemTB1) {
		this.labDiemTB1 = labDiemTB1;
	}

	public JButton getBtnDangKy() {
		return btnDangKy;
	}

	public void setBtnDangKy(JButton btnDangKy) {
		this.btnDangKy = btnDangKy;
	}
	
	
}

