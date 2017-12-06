package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.LopHocPhan;

public class PanelLopHocPhanView extends  JPanel{
	private JTable table;
	private JTextField tfIdLop, tfLoaiLop, tfIdHocPhan, tfThoiGian, tfTuanHoc, tfPhongHoc, tfTenGV, tfSoSVMax, tfSoSVHienTai, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatSV;
	private JComboBox<String> timKiemCB, hocKyCB;
	private String[] titleCols = {"Học kỳ", "Mã lớp", "Loại lớp", "Mã học phần", "Tên lớp", "Thời gian", "Tuần học", "Phòng học", "Tên giảng viên", "Số SV max", "Số SV hiện tại"};
	private String[] timKiemVals = {"Mã lớp", "Phòng học", "Tên giảng viên", "Số SV max"};
	private String[] hocKyVals = {"20172", "20171", "20163", "20162", "20161", "20153", "20152", "20151"};
	
	
	
	public PanelLopHocPhanView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Danh sách lớp học phần");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 15, 15));
		panel.setBorder(new EmptyBorder(10, 50, 20, 50));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(null, ""));
		JScrollPane scroll = new JScrollPane(table = new JTable());
		panel.add(scroll);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		return panel;
	}
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 100, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 0, 10));
		JPanel panelL = new JPanel(new GridLayout(5, 1 , 5, 5));
		panelL.add(new JLabel("Học kỳ:"));
		panelL.add(new JLabel("Mã lớp:"));
		panelL.add(new JLabel("Mã học phần:"));
		panelL.add(new JLabel("Thời gian:"));
		panelL.add(new JLabel("Tuần học:"));
		
		JPanel panelR = new JPanel(new GridLayout(5, 1, 5, 5));
		JPanel panelHK = new  JPanel(new BorderLayout());
		panelHK.add(hocKyCB = new JComboBox<>(hocKyVals));
		panelHK.setBorder(new EmptyBorder(0, 0, 0, 120));
		panelR.add(panelHK);
		panelR.add(tfIdLop = new JTextField());
		panelR.add(tfIdHocPhan = new JTextField());
		panelR.add(tfThoiGian = new JTextField());
		panelR.add(tfTuanHoc = new JTextField());

		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new GridLayout(5, 1 , 5, 5));
		panelL.add(new JLabel("Phòng học:"));
		panelL.add(new JLabel("Loại lớp:"));
		panelL.add(new JLabel("Tên giảng viên:"));
		panelL.add(new JLabel("Số SV max:"));
		panelL.add(new JLabel("Số SV hiện tại:"));
		
		JPanel panelR = new JPanel(new GridLayout(5, 1, 5, 5));
		panelR.add(tfPhongHoc = new JTextField());
		panelR.add(tfLoaiLop = new JTextField());
		panelR.add(tfTenGV = new JTextField());
		panelR.add(tfSoSVMax = new JTextField());
		panelR.add(tfSoSVHienTai = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 25, 25, 0));
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
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 80, 40, 80));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(btnCapNhatSV = new JButton("Cập nhật DS sinh viên"));

		return panel;
	}
	
	public void loadData(JTable table, ArrayList<LopHocPhan> dsLopHP, String timKiem, String giaTri) {
		String[][] data = convertData(dsLopHP, timKiem, giaTri);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(4).setPreferredWidth(125);
		table.getColumnModel().getColumn(5).setPreferredWidth(125);
		table.getColumnModel().getColumn(8).setPreferredWidth(125);
	}
	
	private String[][] convertData(ArrayList<LopHocPhan> dsLopHP, String timKiem, String giaTri) {
		int size = dsLopHP.size();
		String data[][] = new String[size][titleCols.length];
		int index = 0;
		for (int i = 0; i < size; i++) {
			LopHocPhan lopHP = dsLopHP.get(i);
			switch(timKiem) {
			case "Mã lớp": 
				if(lopHP.getIdLop().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = lopHP.getHocKy();
					data[index][1] = lopHP.getIdLop();
					data[index][2] = lopHP.getLoaiLop();
					data[index][3] = lopHP.getIdHocPhan();
					data[index][4] = lopHP.getTenLop();
					data[index][5] = lopHP.getThoiGian();
					data[index][6] = lopHP.getTuanHoc();
					data[index][7] = lopHP.getPhongHoc();
					data[index][8] = lopHP.getTenGiangVien();
					data[index][9] = lopHP.getSoSVMax() + "";
					data[index][10] = lopHP.getSoSVHienTai() + "";
					index++;
				}
				break;
			case "Phòng học":
				if(lopHP.getPhongHoc().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = lopHP.getHocKy();
					data[index][1] = lopHP.getIdLop();
					data[index][2] = lopHP.getLoaiLop();
					data[index][3] = lopHP.getIdHocPhan();
					data[index][4] = lopHP.getTenLop();
					data[index][5] = lopHP.getThoiGian();
					data[index][6] = lopHP.getTuanHoc();
					data[index][7] = lopHP.getPhongHoc();
					data[index][8] = lopHP.getTenGiangVien();
					data[index][9] = lopHP.getSoSVMax() + "";
					data[index][10] = lopHP.getSoSVHienTai() + "";
					index++;
				}
				break;
			case "Tên giảng viên":
				if(lopHP.getTenGiangVien().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = lopHP.getHocKy();
					data[index][1] = lopHP.getIdLop();
					data[index][2] = lopHP.getLoaiLop();
					data[index][3] = lopHP.getIdHocPhan();
					data[index][4] = lopHP.getTenLop();
					data[index][5] = lopHP.getThoiGian();
					data[index][6] = lopHP.getTuanHoc();
					data[index][7] = lopHP.getPhongHoc();
					data[index][8] = lopHP.getTenGiangVien();
					data[index][9] = lopHP.getSoSVMax() + "";
					data[index][10] = lopHP.getSoSVHienTai() + "";
					index++;
				}
				break;
			case "Số SV max":
				if(lopHP.getSoSVMax() == Integer.parseInt(giaTri)) {
					data[index][0] = lopHP.getHocKy();
					data[index][1] = lopHP.getIdLop();
					data[index][2] = lopHP.getLoaiLop();
					data[index][3] = lopHP.getIdHocPhan();
					data[index][4] = lopHP.getTenLop();
					data[index][5] = lopHP.getThoiGian();
					data[index][6] = lopHP.getTuanHoc();
					data[index][7] = lopHP.getPhongHoc();
					data[index][8] = lopHP.getTenGiangVien();
					data[index][9] = lopHP.getSoSVMax() + "";
					data[index][10] = lopHP.getSoSVHienTai() + "";
					index++;
				}
				break;
			case "":{
				data[index][0] = lopHP.getHocKy();
				data[index][1] = lopHP.getIdLop();
				data[index][2] = lopHP.getLoaiLop();
				data[index][3] = lopHP.getIdHocPhan();
				data[index][4] = lopHP.getTenLop();
				data[index][5] = lopHP.getThoiGian();
				data[index][6] = lopHP.getTuanHoc();
				data[index][7] = lopHP.getPhongHoc();
				data[index][8] = lopHP.getTenGiangVien();
				data[index][9] = lopHP.getSoSVMax() + "";
				data[index][10] = lopHP.getSoSVHienTai() + "";
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

	public JTextField getTfIdLop() {
		return tfIdLop;
	}

	public JTextField getTfLoaiLop() {
		return tfLoaiLop;
	}

	public JTextField getTfIdHocPhan() {
		return tfIdHocPhan;
	}

	public JTextField getTfThoiGian() {
		return tfThoiGian;
	}

	public JTextField getTfTuanHoc() {
		return tfTuanHoc;
	}

	public JTextField getTfPhongHoc() {
		return tfPhongHoc;
	}

	public JTextField getTfTenGV() {
		return tfTenGV;
	}

	public JTextField getTfSoSVMax() {
		return tfSoSVMax;
	}

	public JTextField getTfSoSVHienTai() {
		return tfSoSVHienTai;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
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

	public JButton getBtnCapNhatSV() {
		return btnCapNhatSV;
	}

	public JComboBox<String> getTimKiemCB() {
		return timKiemCB;
	}

	public JComboBox<String> getHocKyCB() {
		return hocKyCB;
	}

}
