package quanlysinhvien.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import quanlysinhvien.controller.DanhSachHPController;
import quanlysinhvien.controller.DanhSachNganhController;
import quanlysinhvien.controller.LopChuyenNganhController;
import quanlysinhvien.controller.LopHocPhanController;
import quanlysinhvien.controller.SinhVienNienCheController;
import quanlysinhvien.controller.SinhVienTinChiController;
import quanlysinhvien.view.PanelDanhSachHPView;
import quanlysinhvien.view.PanelDanhSachNganhView;
import quanlysinhvien.view.PanelHeThongView;
import quanlysinhvien.view.PanelLopChuyenNganhView;
import quanlysinhvien.view.PanelLopHocPhanView;
import quanlysinhvien.view.PanelSinhVienNienCheView;
import quanlysinhvien.view.PanelSinhVienTinChiView;

public class MainQuanLy extends JFrame implements ActionListener{
	private JMenuBar menuBar;
	private JMenu menuQuanLySV, menuQuanLyMonHoc, menuQuanLyNganh;
	private JMenuItem miSVTinChi, miSVNienChe, miQuanLyHocPhan, miQuanlyLopHP, miQuanLyNganh, miQuanLyLopChuyenNganh;
	private Container conter;
	private JPanel mainPanel;
	private CardLayout carLayout;
	private JButton btnLogout;
	
	private PanelHeThongView trangChu;
	private PanelSinhVienTinChiView SVTC;
	private PanelSinhVienNienCheView SVNC;
	
	private PanelDanhSachHPView dsHP;
	private PanelLopHocPhanView lopHP;
	
	private PanelDanhSachNganhView dsNganh;
	private PanelLopChuyenNganhView lopCN;
	
	public MainQuanLy() {
		conter = this.getContentPane();
		mainPanel = new JPanel();
		mainPanel.setLayout(carLayout = new CardLayout());
		
		mainPanel.add(trangChu = new PanelHeThongView());
		
		mainPanel.add(SVTC = new PanelSinhVienTinChiView(), "SVTC");
		new SinhVienTinChiController(SVTC);
		mainPanel.add(SVNC = new PanelSinhVienNienCheView(), "SVNC");
		new SinhVienNienCheController(SVNC);
		
		mainPanel.add(dsHP = new PanelDanhSachHPView(), "dsHP");
		new DanhSachHPController(dsHP);
		mainPanel.add(lopHP = new PanelLopHocPhanView(), "lopHP");
		new LopHocPhanController(lopHP);
		
		mainPanel.add(dsNganh = new PanelDanhSachNganhView(), "dsNganh");
		new DanhSachNganhController(dsNganh);
		mainPanel.add(lopCN = new PanelLopChuyenNganhView(), "lopCN");
		new LopChuyenNganhController(lopCN);
		conter.setLayout(new BorderLayout(0, 0));
		conter.add(createHeaderPanel(), BorderLayout.NORTH);
		conter.add(mainPanel, BorderLayout.CENTER);
		
		setTitle("Hệ thống quản lý sinh viên");
		setSize(1200, 720);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuQuanLySV = createMenu("Quản lý sinh viên"));
		menuQuanLySV.add(miSVTinChi = createMenuItem("Sinh viên tín chỉ"));
		menuQuanLySV.add(miSVNienChe = createMenuItem("Sinh viên niên chế"));
		
		menuBar.add(menuQuanLyMonHoc = createMenu("Quản lý học phần"));
		menuQuanLyMonHoc.add(miQuanLyHocPhan = createMenuItem("Danh sách học phần"));
		menuQuanLyMonHoc.add(miQuanlyLopHP = createMenuItem("Quản lý lớp học phần"));
		
		menuBar.add(menuQuanLyNganh = createMenu("Quản lý khoa/viện đào tạo"));
		menuQuanLyNganh.add(miQuanLyNganh = createMenuItem("Danh sách khoa/viện đào tạo"));
		menuQuanLyNganh.add(miQuanLyLopChuyenNganh = createMenuItem("Quản lý lớp chuyên ngành"));
		
		return menuBar;
	}
	
	private JMenu createMenu(String name) {
		JMenu menu = new JMenu(name);
		return menu;
	}
	
	private JMenuItem createMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(this);
		return menuItem;
	}
	

	private JPanel createHeaderPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(createPanelDangXuat());
		panel.add(menuBar = createJMenuBar());
		
		return panel;
	}
	
	private JPanel createPanelDangXuat() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 1000, 0, 50));
		btnLogout = new JButton("Đăng xuất");
		panel.add(btnLogout);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == miSVTinChi) {
			carLayout.show(mainPanel, "SVTC");
			return;
		}
		if(e.getSource() == miSVNienChe) {
			carLayout.show(mainPanel, "SVNC");
			return;
		}
		if(e.getSource() == miQuanLyHocPhan) {
			carLayout.show(mainPanel, "dsHP");
			return;
		}
		if(e.getSource() == miQuanlyLopHP) {
			carLayout.show(mainPanel, "lopHP");
			return;
		}
		if(e.getSource() == miQuanLyNganh) {
			carLayout.show(mainPanel, "dsNganh");
			return;
		}
		if(e.getSource() == miQuanLyLopChuyenNganh) {
			carLayout.show(mainPanel, "lopCN");
			return;
		}
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainQuanLy();
			}
		});
	}

	public JButton getBtnLogout() {
		return btnLogout;
	}

	public void setBtnLogout(JButton btnLogout) {
		this.btnLogout = btnLogout;
	}
	
}

