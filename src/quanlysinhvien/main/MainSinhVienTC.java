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

import quanlysinhvien.controller.DangKiHocPhanController;
import quanlysinhvien.controller.DangKiLopHocController;
import quanlysinhvien.controller.DangKiTotNghiepController;
import quanlysinhvien.controller.DoiMatKhauController;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelBangDiemCaNhanView;
import quanlysinhvien.view.PanelBangDiemHocPhanView;
import quanlysinhvien.view.PanelChuongTrinhDaoTaoSVView;
import quanlysinhvien.view.PanelDangKiHocPhanView;
import quanlysinhvien.view.PanelDangKiLopHocView;
import quanlysinhvien.view.PanelDangKiTotNghiepView;
import quanlysinhvien.view.PanelDanhMucHP;
import quanlysinhvien.view.PanelDoiMatKhauView;
import quanlysinhvien.view.PanelHeThongView;
import quanlysinhvien.view.PanelTKBView;
import quanlysinhvien.view.PanelThongTinCaNhanView;

public class MainSinhVienTC extends JFrame implements ActionListener{
	private JMenuBar menuBar;
	private JMenu menuTrangChu, menuThongTinCaNhan, menuChuongTrinhDaoTao, menuKeHoachHocTap, menuKetQuaHocTap, menuDangKyHocTap;
	private JMenuItem miTrangChu, miTTCN, miDoiMK, miTKB, miBangDiemCN, miBangDiemHP, miDanhMucHocPhan, miChuongTrinhDaoTaoSV,
		miDKHP, miDKLH, miDKTN;
	private CardLayout carLayout;
	private Container conter;
	private JPanel mainPanel;
	private JButton btnLogout;
	
	private PanelHeThongView heThong;
	
	private PanelDoiMatKhauView doiMatKhau;
	private PanelThongTinCaNhanView thongTinCaNhan;
	
	private PanelDanhMucHP danhMucHP;
	private PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV;
	
	private PanelTKBView tkb;
	
	private PanelBangDiemHocPhanView bangDiemHP;
	private PanelBangDiemCaNhanView bangDiemCaNhan;
	
	private PanelDangKiHocPhanView dangKyHP;
	private PanelDangKiLopHocView dangKyLopHoc;
	private PanelDangKiTotNghiepView dangKyTotNghiep;
	
	
	public MainSinhVienTC(TaiKhoan tk) {
		conter = this.getContentPane();
		mainPanel = new JPanel();
		mainPanel.setLayout(carLayout = new CardLayout());
		
		mainPanel.add(heThong = new PanelHeThongView(), "trangChu");
		
		mainPanel.add(doiMatKhau = new PanelDoiMatKhauView(), "doiMatKhau");
		new DoiMatKhauController(doiMatKhau, tk);
		
		mainPanel.add(tkb = new PanelTKBView(), "tkb");
		
		mainPanel.add(thongTinCaNhan = new PanelThongTinCaNhanView(), "thongTinCaNhan");
		
		mainPanel.add(danhMucHP = new PanelDanhMucHP(), "danhMucHP");
		mainPanel.add(chuongTrinhDaoTaoSV = new PanelChuongTrinhDaoTaoSVView(), "chuongTrinhDaoTaoSV");
		
		mainPanel.add(bangDiemCaNhan = new PanelBangDiemCaNhanView(), "bangDiemCaNhan");
		mainPanel.add(bangDiemHP = new PanelBangDiemHocPhanView(), "bangDiemHP");
		
		mainPanel.add(dangKyHP = new PanelDangKiHocPhanView(), "dangKyHP");
		new DangKiHocPhanController(dangKyHP, tk.getTaiKhoan());
		mainPanel.add(dangKyLopHoc = new PanelDangKiLopHocView(), "dangKyLopHoc");
		new DangKiLopHocController(dangKyLopHoc, tk.getTaiKhoan());
		mainPanel.add(dangKyTotNghiep = new PanelDangKiTotNghiepView(), "dangKyTN");
		new DangKiTotNghiepController(dangKyTotNghiep, tk.getTaiKhoan());
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
		
		menuBar.add(menuTrangChu = createJMenu("Trang chủ"));
		menuTrangChu.add(miTrangChu = createMenuItem("Trang chủ"));
		
		menuBar.add(menuThongTinCaNhan = createJMenu("Thông tin người dùng"));
		menuThongTinCaNhan.add(miDoiMK = createMenuItem("Đổi mật khẩu"));
		menuThongTinCaNhan.add(miTTCN = createMenuItem("Thông tin cá nhân"));
		
		menuBar.add(menuChuongTrinhDaoTao = createJMenu("Chương trình đào tạo"));
		menuChuongTrinhDaoTao.add(miDanhMucHocPhan = createMenuItem("Danh mục học phần"));
		menuChuongTrinhDaoTao.add(miChuongTrinhDaoTaoSV = createMenuItem("Chương trình đào tạo sinh viên"));
		
		menuBar.add(menuKeHoachHocTap = createJMenu("Kế hoạch học tập"));
		menuKeHoachHocTap.add(miTKB = createMenuItem("Xem thời khóa biểu"));
		
		menuBar.add(menuKetQuaHocTap = createJMenu("Kết quả học tập"));
		menuKetQuaHocTap.add(miBangDiemCN = createMenuItem("Bảng điểm cá nhân"));
		menuKetQuaHocTap.add(miBangDiemHP = createMenuItem("Bảng điểm học phần"));
		
		menuBar.add(menuDangKyHocTap = createJMenu("Đăng ký học tập"));
		menuDangKyHocTap.add(miDKHP = createMenuItem("Đăng ký học phần"));
		menuDangKyHocTap.add(miDKLH = createMenuItem("Đăng ký lớp học"));
		menuDangKyHocTap.add(miDKTN = createMenuItem("Đăng ký tốt nghiệp"));
		
		return menuBar;
	}
	
	private JMenu createJMenu(String name) {
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
		if(e.getSource() == miTrangChu) {
			carLayout.show(mainPanel, "trangChu");
			return;
		}
		if(e.getSource() == miDoiMK) {
			carLayout.show(mainPanel, "doiMatKhau");
			return;
		}
		if(e.getSource() == miTTCN) {
			carLayout.show(mainPanel, "thongTinCaNhan");
			return;
		}
		if(e.getSource() == miDanhMucHocPhan) {
			carLayout.show(mainPanel, "danhMucHP");
			return;
		}
		if(e.getSource() == miChuongTrinhDaoTaoSV) {
			carLayout.show(mainPanel, "chuongTrinhDaoTaoSV");
			return;
		}
		if(e.getSource() == miTKB) {
			carLayout.show(mainPanel, "tkb");
			return;
		}
		if(e.getSource() == miBangDiemCN) {
			carLayout.show(mainPanel, "bangDiemCaNhan");
			return;
		}
		if(e.getSource() == miBangDiemHP) {
			carLayout.show(mainPanel, "bangDiemHP");
			return;
		}
		if(e.getSource() == miDKHP) {
			carLayout.show(mainPanel, "dangKyHP");
			return;
		}
		if(e.getSource() == miDKLH) {
			carLayout.show(mainPanel, "dangKyLopHoc");
			return;
		}
		if(e.getSource() == miDKTN) {
			carLayout.show(mainPanel, "dangKyTN");
			return;
		}
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainSinhVienTC(new TaiKhoan());
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

