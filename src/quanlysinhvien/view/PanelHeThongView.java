package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelHeThongView extends JPanel{
	public PanelHeThongView() {
		setLayout(new BorderLayout());
		add(createMainPanel(),  BorderLayout.CENTER);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("HỆ THỐNG QUẢN LÝ SINH VIÊN");
		label.setFont(new Font("Caribli", Font.BOLD, 28));
		label.setForeground(Color.YELLOW);
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		return panel;
	}
} 
 