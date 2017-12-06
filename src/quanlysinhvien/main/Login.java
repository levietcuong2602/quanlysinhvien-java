package quanlysinhvien.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.TaiKhoan;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tfTaiKhoan;
	private JPasswordField pwMatKhau;
	private MainQuanLy mainQL;
	private MainSinhVienTC mainSVTC;
	private MainSinhVienNC mainSVNC;
	private JButton btnDangXuat;
	private TaiKhoan tk;
	private String fileName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
//					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 370);
		fileName = "quanlysinhvien\\dsTaiKhoan.xlsx";
		this.tk = new TaiKhoan();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Tài khoản:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(117, 97, 88, 30);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(117, 148, 88, 30);
		contentPane.add(lblPassword);
		
		tfTaiKhoan = new JTextField();
		tfTaiKhoan.setBounds(211, 97, 212, 30);
		contentPane.add(tfTaiKhoan);
		tfTaiKhoan.setColumns(10);
		
		pwMatKhau = new JPasswordField();
		pwMatKhau.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						Login(fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Đăng nhập thất bại", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		pwMatKhau.setBounds(211, 148, 212, 30);
		contentPane.add(pwMatKhau);
		
		JButton btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBackground(new Color(44, 62, 80));
		btnDangNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Login(fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Đăng nhập thất bại", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				return;
			}
		});
		btnDangNhap.setBounds(310, 212, 113, 34);
		contentPane.add(btnDangNhap);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setBackground(new Color(44, 62, 80));
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
				return;
			}
		});
		btnHuy.setBounds(187, 212, 113, 34);
		contentPane.add(btnHuy);
		
		JButton btnThoat = new JButton("");
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnThoat.setToolTipText("Thoát");
		btnThoat.setIcon(new ImageIcon(this.getClass().getResource("/logout.png")));
		btnThoat.setBounds(507, 11, 24, 23);
		contentPane.add(btnThoat);
		
		JLabel lblHThngQun = new JLabel("Hệ thống quản lý sinh viên - nhóm 9");
		lblHThngQun.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHThngQun.setBounds(10, 0, 290, 27);
		contentPane.add(lblHThngQun);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/bgLogin.jpg")));
		label.setBounds(0, 0, 541, 341);
		contentPane.add(label);
	}
	
	private void Login(String fileName) throws IOException {
		String taiKhoan = tfTaiKhoan.getText().trim();
		String matKhau = pwMatKhau.getText().trim();
		TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau, "");
		if(taiKhoan.equals("") || matKhau.equals("")) {
			JOptionPane.showMessageDialog(null, "Nhập tài khoản và mật khẩu để đăng nhập", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(checkTK(fileName, tk)) {
			cancel();
			if(this.tk.getLoaiTK().equals("admin")) {
				mainQL = new MainQuanLy();
				btnDangXuat = mainQL.getBtnLogout();
			}else if(this.tk.getLoaiTK().equals("svtc")) {
				mainSVTC = new MainSinhVienTC(tk);
				btnDangXuat = mainSVTC.getBtnLogout();
			}else if(this.tk.getLoaiTK().equals("svnc")) {
				mainSVNC = new MainSinhVienNC(tk);
				btnDangXuat = mainSVNC.getBtnLogout();
			}
			btnDangXuat.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(mainQL != null) {
						mainQL.dispose();
					}
					if(mainSVTC != null) {
						mainSVTC.dispose();
					}
					if(mainSVNC != null) {
						mainSVNC.dispose();
					}
					setVisible(true);
					return;
				}
			});
			setVisible(false);
		}else {
			JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean checkTK(String fileName, TaiKhoan taiKhoan) throws IOException {
		ArrayList<TaiKhoan> dsTaiKhoan = getTaiKhoan(fileName);
		for(int i = 0; i< dsTaiKhoan.size(); i++) {
			this.tk.setTaiKhoan(dsTaiKhoan.get(i).getTaiKhoan());;
			this.tk.setMatKhau(dsTaiKhoan.get(i).getMatKhau());
			this.tk.setLoaiTK(dsTaiKhoan.get(i).getLoaiTK());;
			if(this.tk.check(taiKhoan)) {
				return true;
			}
		}
		return false;
	}
	
	private void cancel() {
		tfTaiKhoan.setText("");
		pwMatKhau.setText("");
	}
	
	private ArrayList<TaiKhoan> getTaiKhoan(String fileName) throws IOException{
		ArrayList<TaiKhoan> dsTaiKhoan = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(fileName));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataTK = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Integer.toString((int) cell.getNumericCellValue());
					break;
				default:
					data = "";
					break;
				}
				dataTK.add(data);
			}
			if(dataTK.size() > 0) {
				TaiKhoan tk = new TaiKhoan(dataTK.get(0), dataTK.get(1), dataTK.get(2));
				dsTaiKhoan.add(tk);
			}
		}

		workbook.close();
		inputStream.close();
		return dsTaiKhoan;
	}
}
