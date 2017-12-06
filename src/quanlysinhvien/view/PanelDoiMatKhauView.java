package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class PanelDoiMatKhauView extends JPanel {
	private JTextField tfTaiKhoan;
	private JPasswordField pwMatKhauCu, pwMatKhauMoi, pwXacNhan;
	private JButton btnThayDoi;
	
	public PanelDoiMatKhauView() {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(20, 20, 300, 780));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel titleLab = new JLabel("Đổi mật khẩu");
		titleLab.setFont(new Font("Caribli", Font.BOLD, 18));
		titleLab.setForeground(Color.BLUE);
		panel.add(titleLab);
		 
		return panel;
	} 
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createTitleMain(), BorderLayout.PAGE_START);
		panel.add(createMain(), BorderLayout.CENTER);
		panel.add(createBottom(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel createTitleMain() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Thay đổi mật khẩu. Mật khẩu mới phải đạt tối thiểu 8 ký tự"));
		
		return panel;
	}
	
	private JPanel createMain() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new TitledBorder(null, "Thông tin tài khoản", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Caribli", Font.BOLD, 18)));
		JPanel panelLab = new JPanel(new GridLayout(4, 1, 15, 15));
		panelLab.add(new JLabel("Tài khoản:"));
		panelLab.add(new JLabel("Mật khẩu cũ:"));
		panelLab.add(new JLabel("Mật khẩu mới:"));
		panelLab.add(new JLabel("Xác nhận mật khẩu:"));
		JPanel panelTF = new JPanel(new GridLayout(4, 1, 15, 15));
		panelTF.add(tfTaiKhoan = new JTextField());
		panelTF.add(pwMatKhauCu = new JPasswordField());
		panelTF.add(pwMatKhauMoi = new JPasswordField());
		panelTF.add(pwXacNhan = new JPasswordField());
		panel.add(panelLab, BorderLayout.WEST);
		panel.add(panelTF, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createBottom() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 40, 0, 260));
		panel.add(btnThayDoi = new JButton("Thay đổi"));
		
		return panel;
	}

	public JTextField getTfTaiKhoan() {
		return tfTaiKhoan;
	}

	public JPasswordField getPwMatKhauCu() {
		return pwMatKhauCu;
	}

	public JPasswordField getPwMatKhauMoi() {
		return pwMatKhauMoi;
	}

	public JPasswordField getPwXacNhan() {
		return pwXacNhan;
	}

	public JButton getBtnThayDoi() {
		return btnThayDoi;
	}
	
}
