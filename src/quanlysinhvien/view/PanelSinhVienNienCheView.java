package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.SinhVienNienChe;

public class PanelSinhVienNienCheView extends JPanel{
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatDiem;
	private ButtonGroup bg;
	private JRadioButton radNam, radNu;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfTongSoKy, tfTimKiem;
	private String[] titleCols = {"Mã sinh viên", "Họ tên", "Khóa", "Ngày sinh", "Giới tính", 
			"Email", "Số ĐT", "Địa chỉ", "Điểm TB", "Tổng số kỳ"};
	private String[] timKiemVals = {"Mã sinh viên", "Họ tên", "Khóa", "Địa chỉ", "Điểm TB", "Tổng số kỳ"};
	
	public PanelSinhVienNienCheView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Danh sách sinh viên niên chế", Font.BOLD, 18, 0xFFFF00));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 50, 40, 50));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(null, ""));
		JScrollPane scroll = new JScrollPane(table = new JTable());
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
		panel.setBorder(new EmptyBorder(0, 0, 60, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(6, 1, 5, 5));
		panelL.add(new JLabel("Mã sinh viên:"));
		panelL.add(new JLabel("Họ tên:"));
		panelL.add(new JLabel("Khóa:"));
		panelL.add(new JLabel("Ngày sinh:"));
		panelL.add(new JLabel("Điểm TB:"));
		panelL.add(new JLabel(""));
		
		
		JPanel panelR = new JPanel(new GridLayout(6, 1, 5, 5));
		panelR.add(tfIdSV = new JTextField());
		panelR.add(tfHoTen = new JTextField());
		panelR.add(tfKhoa = new JTextField());
		panelR.add(tfNgaySinh = new JTextField());
		panelR.add(tfDiemTB = new JTextField());
		panelR.add(new JLabel(""));
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(6, 1, 5, 5));
		panelL.add(new JLabel("Giới tính:"));
		panelL.add(new JLabel("Email:"));
		panelL.add(new JLabel("Số ĐT:"));
		panelL.add(new JLabel("Địa chỉ:"));
		panelL.add(new JLabel("Tổng số kỳ:"));
		
		JPanel panelR = new JPanel(new GridLayout(6, 1, 5, 5));
		panelR.add(panelGioiTinh());
		panelR.add(tfEmail = new JTextField());
		panelR.add(tfSoDT = new JTextField());
		panelR.add(tfDiaChi = new JTextField());
		panelR.add(tfTongSoKy = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 25, 0, 0));
		panel.add(createTimKiemPanel(), BorderLayout.NORTH);
		panel.add(createBtnOtherPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createTimKiemPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		JPanel panelButCB = new JPanel(new GridLayout(1, 2, 5, 5));
		panelButCB.add(btnTimKiem = new JButton("Tìm kiếm"));
		panelButCB.add(timKiemCB = new JComboBox<>(timKiemVals));

		JPanel panelTF = new JPanel(new BorderLayout());
		panelTF.add(tfTimKiem = new JTextField());
		panel.add(panelButCB);
		panel.add(panelTF);

		return panel;
	}

	private JPanel createBtnOtherPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 80, 95, 80));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(btnCapNhatDiem = new JButton("Cập nhật điểm SV"));

		return panel;
	}
	
	private JPanel panelGioiTinh() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBorder(new EmptyBorder(0, 0, 0, 135));
		panel.add(radNam = new JRadioButton("Nam"));
		panel.add(radNu = new JRadioButton("Nữ"));
		bg = new ButtonGroup();
		bg.add(radNam);
		bg.add(radNu);

		return panel;
	}
	
	private JLabel createLabel(String name, int inDam, int kichThuoc, int maMau) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", inDam, kichThuoc));
		if(maMau != 0)
			label.setForeground(new Color(maMau));
		
		return label;
	}
	
	public void loadData(JTable table, ArrayList<SinhVienNienChe> dsSVNC, String timKiem, String giaTri) {
		String[][] data = convertData(dsSVNC, timKiem, giaTri);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		table.getColumnModel().getColumn(6).setPreferredWidth(140);
		table.getColumnModel().getColumn(7).setPreferredWidth(145);
	}
	
	private String[][] convertData(ArrayList<SinhVienNienChe> list, String timKiem, String giaTri) {
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		int index = 0;
		for (int i = 0; i < size; i++) {
			SinhVienNienChe svnc = list.get(i);
			switch (timKiem) {
			case "Mã sinh viên":
				if(svnc.getIdSinhVien().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = svnc.getIdSinhVien();
					data[index][1] = svnc.getHoTen();
					data[index][2] = svnc.getKhoa();
					data[index][3] = svnc.getNgaySinh();
					data[index][4] = svnc.getGioiTinh();
					data[index][5] = svnc.getEmail();
					data[index][6] = svnc.getSoDT();
					data[index][7] = svnc.getDiaChi();
					data[index][8] = svnc.getDiemTB() + "";
					data[index][9] = svnc.getTongSoKy() + "";
					index++;
				}
				break;
			case "Họ tên":
				if(svnc.getHoTen().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = svnc.getIdSinhVien();
					data[index][1] = svnc.getHoTen();
					data[index][2] = svnc.getKhoa();
					data[index][3] = svnc.getNgaySinh();
					data[index][4] = svnc.getGioiTinh();
					data[index][5] = svnc.getEmail();
					data[index][6] = svnc.getSoDT();
					data[index][7] = svnc.getDiaChi();
					data[index][8] = svnc.getDiemTB() + "";
					data[index][9] = svnc.getTongSoKy() + "";
					index++;
				}
				break;
			case "Khóa":
				if(svnc.getKhoa().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = svnc.getIdSinhVien();
					data[index][1] = svnc.getHoTen();
					data[index][2] = svnc.getKhoa();
					data[index][3] = svnc.getNgaySinh();
					data[index][4] = svnc.getGioiTinh();
					data[index][5] = svnc.getEmail();
					data[index][6] = svnc.getSoDT();
					data[index][7] = svnc.getDiaChi();
					data[index][8] = svnc.getDiemTB() + "";
					data[index][9] = svnc.getTongSoKy() + "";
					index++;
				}
				break;
			case "Địa chỉ":
				if(svnc.getDiaChi().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = svnc.getIdSinhVien();
					data[index][1] = svnc.getHoTen();
					data[index][2] = svnc.getKhoa();
					data[index][3] = svnc.getNgaySinh();
					data[index][4] = svnc.getGioiTinh();
					data[index][5] = svnc.getEmail();
					data[index][6] = svnc.getSoDT();
					data[index][7] = svnc.getDiaChi();
					data[index][8] = svnc.getDiemTB() + "";
					data[index][9] = svnc.getTongSoKy() + "";
					index++;
				}
				break;
			case "Điểm TB":
				if(Double.toString(svnc.getDiemTB()).equals(Double.toString(Double.parseDouble(giaTri)))) {
					data[index][0] = svnc.getIdSinhVien();
					data[index][1] = svnc.getHoTen();
					data[index][2] = svnc.getKhoa();
					data[index][3] = svnc.getNgaySinh();
					data[index][4] = svnc.getGioiTinh();
					data[index][5] = svnc.getEmail();
					data[index][6] = svnc.getSoDT();
					data[index][7] = svnc.getDiaChi();
					data[index][8] = svnc.getDiemTB() + "";
					data[index][9] = svnc.getTongSoKy() + "";
					index++;
				}
				break;
			case "Tổng số kỳ":
				if(Integer.toString(svnc.getTongSoKy()).equals(Integer.toString(Integer.parseInt(giaTri)))) {
					data[index][0] = svnc.getIdSinhVien();
					data[index][1] = svnc.getHoTen();
					data[index][2] = svnc.getKhoa();
					data[index][3] = svnc.getNgaySinh();
					data[index][4] = svnc.getGioiTinh();
					data[index][5] = svnc.getEmail();
					data[index][6] = svnc.getSoDT();
					data[index][7] = svnc.getDiaChi();
					data[index][8] = svnc.getDiemTB() + "";
					data[index][9] = svnc.getTongSoKy() + "";
					index++;
				}
				break;
			case "":{
				data[index][0] = svnc.getIdSinhVien();
				data[index][1] = svnc.getHoTen();
				data[index][2] = svnc.getKhoa();
				data[index][3] = svnc.getNgaySinh();
				data[index][4] = svnc.getGioiTinh();
				data[index][5] = svnc.getEmail();
				data[index][6] = svnc.getSoDT();
				data[index][7] = svnc.getDiaChi();
				data[index][8] = svnc.getDiemTB() + "";
				data[index][9] = svnc.getTongSoKy() + "";
				index++;
			}
				break;
			}
		}
		if (!giaTri.equals("")) {
			String[][] datatk = new String[index][titleCols.length];
			for (int i = 0; i < index; i++) {
				datatk[i] = data[i];
			}
			return datatk;
		}
		return data;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getBtnThem() {
		return btnThem;
	}

	public JButton getBtnSua() {
		return btnSua;
	}

	public JButton getBtnXoa() {
		return btnXoa;
	}

	public JButton getBtnHuy() {
		return btnHuy;
	}

	public JButton getBtnTimKiem() {
		return btnTimKiem;
	}

	public JComboBox<String> getTimKiemCB() {
		return timKiemCB;
	}

	public JTextField getTfIdSV() {
		return tfIdSV;
	}

	public JTextField getTfHoTen() {
		return tfHoTen;
	}

	public JTextField getTfKhoa() {
		return tfKhoa;
	}

	public JTextField getTfNgaySinh() {
		return tfNgaySinh;
	}

	public JTextField getTfEmail() {
		return tfEmail;
	}

	public JTextField getTfSoDT() {
		return tfSoDT;
	}

	public JTextField getTfDiaChi() {
		return tfDiaChi;
	}

	public JTextField getTfDiemTB() {
		return tfDiemTB;
	}

	public JTextField getTfTongSoKy() {
		return tfTongSoKy;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
	}

	public JRadioButton getRadNam() {
		return radNam;
	}

	public JRadioButton getRadNu() {
		return radNu;
	}

	public JButton getBtnCapNhatDiem() {
		return btnCapNhatDiem;
	}

	public ButtonGroup getBg() {
		return bg;
	}
}
