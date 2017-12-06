package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PanelThongTinCaNhanView extends JPanel{
	private JLabel lbIdSinhVien, lbHoTen, lbNgaySinh, lbLop, lbChuongTrinh, lbHeHoc, lbTrangThai;
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop, gtChuongTring, gtHeHoc, gtTrangThai;
	private JTextField tfEmail, tfSoDT, tfDiaChi;
	private JButton btnCapNhat;
	
	
	public PanelThongTinCaNhanView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Thông tin tài khoản");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 15, 15));
		panel.setBorder(new EmptyBorder(10, 40, 100, 300));
		panel.add(createInfoPanel());
		panel.add(createVarPanel());
		return panel;
	}
	
	private JPanel createInfoPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new BorderLayout(20, 20));
		JPanel panelBL = new JPanel(new GridLayout(7, 1, 10, 10));
		panelBL.add(lbIdSinhVien = createLabel("Mã sinh viên:", 0));
		panelBL.add(lbHoTen = createLabel("Họ tên SV:", 0));
		panelBL.add(lbNgaySinh = createLabel("Ngày Sinh:", 0));
		panelBL.add(lbLop = createLabel("Lớp:", 0));
		panelBL.add(lbChuongTrinh = createLabel("Chương trình:", 0));
		panelBL.add(lbHeHoc = createLabel("Hệ học:", 0));
		panelBL.add(lbTrangThai = createLabel("Trạng thái:", 0));
		JPanel panelBR = new JPanel(new GridLayout(7, 1, 10, 10));
		panelBR.add(gtIdSinhVien = createLabel("20153752", 1));
		panelBR.add(gtHoTen = createLabel("Nguyễn Tài Tiêu", 1));
		panelBR.add(gtNgaySinh = createLabel("27.10.1997", 1));
		panelBR.add(gtLop = createLabel("CNTT2-1 K60", 1));
		panelBR.add(gtChuongTring = createLabel("CT Nhóm ngành CNTT-TT 2-2015", 1));
		panelBR.add(gtHeHoc = createLabel("Đại học", 1));
		panelBR.add(gtTrangThai = createLabel("Học", 1));
		
		panelL.add(panelBL, BorderLayout.WEST);
		panelL.add(panelBR, BorderLayout.CENTER);
		
		JPanel panelR = new JPanel();
		JLabel lab = new JLabel(new ImageIcon(this.getClass().getResource("/infoUser.png")));
		panelR.add(lab);
		
		panel.add(panelL, BorderLayout.CENTER);
		panel.add(panelR, BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createVarPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		panel.setBorder(new EmptyBorder(10, 0, 0, 500));
		panel.add(createInput());
		panel.add(createButton());
		
		return panel;
	}
	
	private JPanel createInput() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(3, 1, 15, 5));
		panelL.add(new JLabel("Email*:"));
		panelL.add(new JLabel("Số điện thoại*:"));
		panelL.add(new JLabel("Địa chỉ*:"));
		JPanel panelR = new JPanel(new GridLayout(3, 1, 15, 5));
		panelR.add(tfEmail = new JTextField());
		panelR.add(tfSoDT = new JTextField());
		panelR.add(tfDiaChi = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createButton() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 0, 65, 200));
		panel.add(btnCapNhat = new JButton("Cập nhật thông tin"));
		return panel;
	}
	
	private JLabel createLabel(String name, int indam) {
		JLabel lab = new JLabel(name);
		int giatri;
		if(indam == 1) {
			giatri = Font.BOLD;
		}else {
			giatri = Font.PLAIN;
		}
		lab.setFont(new Font("Caribli", giatri, 16));
		
		return lab;
	}
	
}
