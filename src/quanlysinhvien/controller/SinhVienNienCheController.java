package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.CapNhatDiemSVView;
import quanlysinhvien.view.PanelSinhVienNienCheView;

public class SinhVienNienCheController {
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatDiem;
	private ButtonGroup bg;
	private JRadioButton radNam, radNu;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfGioiTinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfTongSoKy, tfTimKiem;
	private PanelSinhVienNienCheView sinhVienNC;
	private ArrayList<SinhVienNienChe> dsSinhVien;
	private String fileName;
	private final String PATTERNNGAYSINH = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|"
			+ "-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|"
			+ "^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468]"
			+ "[048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|"
			+ "^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4("
			+ "?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    private final String PATTERNEMAIL = "^[\\w-]{1,30}@[\\w&&[^0-9_]]+\\.[\\w&&[^0-9]]+$";
    private final String PATTERNSDT = "0\\d{9,10}";
	
	public SinhVienNienCheController(PanelSinhVienNienCheView sinhVienNC) {
		this.sinhVienNC = sinhVienNC;
		fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		try {
			this.dsSinhVien = readFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsSinhVien = new ArrayList<>();
			e.printStackTrace();
		}
		this.table = sinhVienNC.getTable();
		this.bg = sinhVienNC.getBg();
		this.radNam = sinhVienNC.getRadNam();
		this.radNu = sinhVienNC.getRadNu();
		this.btnThem = sinhVienNC.getBtnThem();
		this.btnSua = sinhVienNC.getBtnSua();
		this.btnXoa = sinhVienNC.getBtnXoa();
		this.btnHuy = sinhVienNC.getBtnHuy();
		this.btnCapNhatDiem = sinhVienNC.getBtnCapNhatDiem();
		this.btnTimKiem = sinhVienNC.getBtnTimKiem();
		this.timKiemCB = sinhVienNC.getTimKiemCB();
		this.tfIdSV = sinhVienNC.getTfIdSV();
		this.tfHoTen = sinhVienNC.getTfHoTen();
		this.tfKhoa = sinhVienNC.getTfKhoa();
		this.tfNgaySinh = sinhVienNC.getTfNgaySinh();
		this.tfEmail = sinhVienNC.getTfEmail();
		this.tfSoDT = sinhVienNC.getTfSoDT();
		this.tfDiaChi = sinhVienNC.getTfDiaChi();
		this.tfDiemTB = sinhVienNC.getTfDiemTB();
		this.tfTongSoKy = sinhVienNC.getTfTongSoKy();
		this.tfTimKiem = sinhVienNC.getTfTimKiem();
		this.sinhVienNC.loadData(table, dsSinhVien, "", "");
		
		setAction();
	}
	
	private void setAction() {
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row >= 0) {
					tfIdSV.setText((String) table.getValueAt(row, 0));
					tfIdSV.setEnabled(false);
					tfHoTen.setText((String) table.getValueAt(row, 1));
					tfKhoa.setText((String) table.getValueAt(row, 2));
					tfNgaySinh.setText((String) table.getValueAt(row, 3));
					if (table.getValueAt(row, 4).equals("Nam")) {
						radNam.setSelected(true);
					} else {
						radNu.setSelected(true);
					}
					tfEmail.setText((String) table.getValueAt(row, 5));
					tfSoDT.setText((String) table.getValueAt(row, 6));
					tfDiaChi.setText((String) table.getValueAt(row, 7));
					tfDiemTB.setText((String) table.getValueAt(row, 8));
					tfTongSoKy.setText((String) table.getValueAt(row, 9));
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SinhVienNienChe svnc = getSinhVienNC();
				if(svnc != null) {
					String idSV = svnc.getIdSinhVien();
					if (checkSV(idSV)) {
						dsSinhVien.add(svnc);
						sinhVienNC.loadData(table, dsSinhVien, "", "");
						try {
							addSV(svnc, fileName);
							System.out.println(Directory.createDir("quanlysinhvien\\sinhviennienche\\" + idSV));
							QuanLyTaiKhoan.addTaiKhoan(new TaiKhoan(idSV, idSV, "svnc"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã sinh viên", "Error insert",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				SinhVienNienChe svnc = getSinhVienNC();
				if (svnc != null) {
					for (int i = 0; i < dsSinhVien.size(); i++) {
						if (dsSinhVien.get(i).getIdSinhVien().equals(svnc.getIdSinhVien())) {
							dsSinhVien.get(i).setHoTen(svnc.getHoTen());
							dsSinhVien.get(i).setKhoa(svnc.getKhoa());
							dsSinhVien.get(i).setNgaySinh(svnc.getNgaySinh());
							dsSinhVien.get(i).setEmail(svnc.getEmail());
							dsSinhVien.get(i).setGioiTinh(svnc.getGioiTinh());
							dsSinhVien.get(i).setSoDT(svnc.getSoDT());
							dsSinhVien.get(i).setDiaChi(svnc.getDiaChi());
							dsSinhVien.get(i).setDiemTB(svnc.getDiemTB());
							dsSinhVien.get(i).setTongSoKy(svnc.getTongSoKy());;
							break;
						}
					}
					sinhVienNC.loadData(table, dsSinhVien, "", "");
					boolean ck = false;
					try {
						ck = updateSV(svnc, fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(ck) JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					else {
						JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
					}
					cancel();
				}

			}
		});
		
		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
							JOptionPane.YES_NO_OPTION);
					if (select == 0) {
						String id = (String) table.getValueAt(row, 0);
						for (int i = 0; i < dsSinhVien.size(); i++) {
							String idSV = dsSinhVien.get(i).getIdSinhVien();
							if (idSV.equals(id)) {
								boolean ck = false;
								try {
									ck = deleteSV(dsSinhVien.get(i), fileName);
									System.out.println(Directory.deleteDir(new File("quanlysinhvien\\sinhviennienche\\" + idSV)));
									QuanLyTaiKhoan.deleteTaiKhoan(idSV);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dsSinhVien.remove(i);
								sinhVienNC.loadData(table, dsSinhVien, "", "");
								if(ck) JOptionPane.showMessageDialog(null, "Xóa thành công");
								else {
									JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
								}
								cancel();
								return;
							}
						}
					}
				}
			}
		});
		
		btnHuy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cancel();
				sinhVienNC.loadData(table, dsSinhVien, "", "");
			}
		});
		
		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				sinhVienNC.loadData(table, dsSinhVien, timKiem, giaTri);
			}
		});
		
		btnCapNhatDiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn sinh viên để cập nhật điểm", "Error update", JOptionPane.ERROR_MESSAGE);
				}else {
					String idSV = (String) table.getValueAt(row, 0);
					CapNhatDiemSVView capNhatDiem = new CapNhatDiemSVView(idSV);
					new CapNhatDiemSVController(capNhatDiem, idSV, "svnc");
					capNhatDiem.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {
						}
						@Override
						public void windowIconified(WindowEvent e) {
						}
						@Override
						public void windowDeiconified(WindowEvent e) {
						}
						@Override
						public void windowDeactivated(WindowEvent e) {
						}
						@Override
						public void windowClosing(WindowEvent e) {
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							System.out.println("Hello world");
							try {
								SinhVienNienChe sv = getSinhVien(idSV);
								updateDiemSV(sv);
								sinhVienNC.loadData(table, dsSinhVien, "", "");
								cancel();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("Error CapNhatDiem SVTC: " + e1);
							}
							
						}
						@Override
						public void windowActivated(WindowEvent e) {
						}
					});
				}
			}
		});
	}
	
	private boolean checkSV(String id) {
		boolean ck = true;
		for (SinhVienNienChe svnc: dsSinhVien) {
			if (svnc.getIdSinhVien().equals(id))
				ck = false;
		}
		ArrayList<SinhVienTinChi> dsSVTC; 
		try {
			dsSVTC = readFileSVTC();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsSVTC = new ArrayList<>();
		}
		for(SinhVienTinChi svtc: dsSVTC) {
			if(svtc.getIdSinhVien().equals(id))
				ck = false;
		}
		return ck;
	}
	
	private SinhVienNienChe getSinhVienNC() {
		String idSinhVien = tfIdSV.getText().toUpperCase().trim();
		String hoTen = tfHoTen.getText().trim();
		String khoa = tfKhoa.getText().trim();
		String ngaySinh = tfNgaySinh.getText().trim();
		String gioiTinh = "";
		if (radNam.isSelected())
			gioiTinh = "Nam";
		else if (radNu.isSelected())
			gioiTinh = "Nữ";
		String email = tfEmail.getText().trim();
		String soDT = tfSoDT.getText().trim();
		String diaChi = tfDiaChi.getText().trim();
		if (idSinhVien.equals("") || hoTen.equals("") || khoa.equals("") || ngaySinh.equals("") || gioiTinh.equals("")
				|| email.equals("") || soDT.equals("") || diaChi.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!email.matches(PATTERNEMAIL)) {
            JOptionPane.showMessageDialog(null, "Email không đúng!!!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!ngaySinh.matches(PATTERNNGAYSINH)) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không đúng!!!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!soDT.matches(PATTERNSDT)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không đúng!!!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
		double diemTB;
		int tongSoKy;
		try {
			diemTB = Double.parseDouble(tfDiemTB.getText().trim());
			tongSoKy = Integer.parseInt(tfTongSoKy.getText().trim());
		}catch(Exception exc) {
			JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Điểm TB, Tổng số kỳ", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		SinhVienNienChe svnc = new SinhVienNienChe(idSinhVien, hoTen, khoa, "null", ngaySinh, gioiTinh, email, soDT, diaChi, diemTB, tongSoKy);
		return svnc;
	}
	
	private void cancel() {
		timKiemCB.setSelectedIndex(0);
		tfIdSV.setText("");
		tfIdSV.setEnabled(true);
		tfHoTen.setText("");
		tfKhoa.setText("");
		tfNgaySinh.setText("");
		bg.clearSelection();
		tfEmail.setText("");
		tfSoDT.setText("");
		tfDiaChi.setText("");
		tfTongSoKy.setText("");
		tfDiemTB.setText("");
		tfTimKiem.setText("");
	}
	
	private void updateDiemSV(SinhVienNienChe svnc) throws IOException {
		FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\sinhviennienche\\" + svnc.getIdSinhVien() + "\\diem.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int soTCQua = 0;
		int soTCNo = 0;
		double diemTB = 0.0;
		double sum = 0.0;
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cellTC = nextRow.getCell(4);
			int tinChi = (int) Double.parseDouble(Double.toString(cellTC.getNumericCellValue()));
			Cell cellDiem4 = nextRow.getCell(9);
			double diem4 = Double.parseDouble(Double.toString(cellDiem4.getNumericCellValue()));
			sum += tinChi*diem4;
			if(checkNo(diem4)) soTCNo += tinChi;
			else soTCQua += tinChi;
		}
		diemTB = (double) Math.round(((double) sum/(soTCQua + soTCNo))*100)/100;
//		svnc.setSoTCQua(soTCQua);
//		svnc.setSoTCNo(soTCNo);
		svnc.setDiemTB(diemTB);
		fin.close();
		
		String fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		FileInputStream fin1 = new FileInputStream(new File(fileName));
		workbook = new XSSFWorkbook(fin1);
		sheet = workbook.getSheetAt(0);
		iterator = sheet.iterator();
		if(iterator.hasNext())
			nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if(idSV.equals(svnc.getIdSinhVien())) {
				cell = nextRow.getCell(10);
				cell.setCellValue(diemTB);
//				cell = nextRow.getCell(11);
//				cell.setCellValue(soTCQua);
//				cell = nextRow.getCell(12);
//				cell.setCellValue(soTCNo);
				break;
			}
			
		}
		fin1.close();
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private SinhVienNienChe getSinhVien(String idNV) {
		for(SinhVienNienChe svnc: dsSinhVien) {
			if(svnc.getIdSinhVien().equals(idNV))
				return svnc;
		}
		return null;
	}
	
	private boolean checkNo(double diem4) {
		if(diem4 == 0.0) return true;
		else return false;
	}
	
	private ArrayList<SinhVienNienChe> readFile(String fileName) throws IOException {
		ArrayList<SinhVienNienChe> dsSV = new ArrayList<>();
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
			ArrayList<String> dataSV = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Double.toString(cell.getNumericCellValue());
					break;
				default:
					data = "";
					break;
				}
				dataSV.add(data);
			}
			if(dataSV.size() > 0) {
				SinhVienNienChe sv = new SinhVienNienChe(dataSV.get(0), dataSV.get(1),
						Integer.toString((int) Double.parseDouble(dataSV.get(2))), dataSV.get(3), dataSV.get(4),
						dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)),
						(int) (Double.parseDouble(dataSV.get(10))));
				dsSV.add(sv);
			}
		}

		workbook.close();
		inputStream.close();
		return dsSV;
	}

	private ArrayList<SinhVienTinChi> readFileSVTC() throws IOException {
		ArrayList<SinhVienTinChi> dsSV = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx"));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataSV = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Double.toString(cell.getNumericCellValue());
					break;
				default:
					data = "";
					break;
				}
				dataSV.add(data);
				if(dataSV.size() < 1) return null;
			}
			if(dataSV.size() > 0) {
				SinhVienTinChi sv = new SinhVienTinChi(dataSV.get(0), dataSV.get(1),
						Integer.toString((int) Double.parseDouble(dataSV.get(2))), dataSV.get(3), dataSV.get(4),
						dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)),
						(int) (Double.parseDouble(dataSV.get(10))), (int) Double.parseDouble(dataSV.get(11)));
				dsSV.add(sv);
			}
		}

		workbook.close();
		inputStream.close();
		return dsSV;
	}
	
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellIdSV = row.createCell(1);
		cellIdSV.setCellStyle(cellStyle);
		cellIdSV.setCellValue("Mã sinh viên");

		Cell cellHoTen = row.createCell(2);
		cellHoTen.setCellStyle(cellStyle);
		cellHoTen.setCellValue("Họ tên");

		Cell cellKhoa = row.createCell(3);
		cellKhoa.setCellStyle(cellStyle);
		cellKhoa.setCellValue("Khóa");
		
		Cell cellTenLop = row.createCell(4);
		cellTenLop.setCellStyle(cellStyle);
		cellTenLop.setCellValue("Tên lớp");

		Cell cellNgaySinh = row.createCell(5);
		cellNgaySinh.setCellStyle(cellStyle);
		cellNgaySinh.setCellValue("Ngày sinh");

		Cell cellGioiTinh = row.createCell(6);
		cellGioiTinh.setCellStyle(cellStyle);
		cellGioiTinh.setCellValue("Giới tính");

		Cell cellEmail = row.createCell(7);
		cellEmail.setCellStyle(cellStyle);
		cellEmail.setCellValue("Email");

		Cell cellSoDT = row.createCell(8);
		cellSoDT.setCellStyle(cellStyle);
		cellSoDT.setCellValue("Số ĐT");

		Cell cellDiaChi = row.createCell(9);
		cellDiaChi.setCellStyle(cellStyle);
		cellDiaChi.setCellValue("Địa chỉ");

		Cell cellDiemTB = row.createCell(10);
		cellDiemTB.setCellStyle(cellStyle);
		cellDiemTB.setCellValue("Điểm TB");

		Cell cellSoTCQua = row.createCell(11);
		cellSoTCQua.setCellStyle(cellStyle);
		cellSoTCQua.setCellValue("Tổng số kỳ");

	}

	private void writeSV(SinhVienNienChe sv, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(sv.getIdSinhVien());
		cell = row.createCell(2);
		cell.setCellValue(sv.getHoTen());
		cell = row.createCell(3);
		cell.setCellValue(sv.getKhoa());
		cell = row.createCell(4);
		cell.setCellValue(sv.getTenLop());
		cell = row.createCell(5);
		cell.setCellValue(sv.getNgaySinh());
		cell = row.createCell(6);
		cell.setCellValue(sv.getGioiTinh());
		cell = row.createCell(7);
		cell.setCellValue(sv.getEmail());
		cell = row.createCell(8);
		cell.setCellValue(sv.getSoDT());
		cell = row.createCell(9);
		cell.setCellValue(sv.getDiaChi());
		cell = row.createCell(10);
		cell.setCellValue(sv.getDiemTB());
		cell = row.createCell(11);
		cell.setCellValue(sv.getTongSoKy());
	}
	
	private void addSV(SinhVienNienChe svnc, String fileName) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheetAt(0);
			lastRow = sheet.getLastRowNum();
		}catch (Exception e) {
			// TODO: handle exception
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet();
			System.out.println(e);
		}

		Row row = null;
		if(lastRow < 0) {
			createHeader(sheet);
			row = sheet.createRow(1);
		}else {
			row = sheet.createRow(lastRow + 1);
		}
		if(row != null) {
			writeSV(svnc, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private boolean updateSV(SinhVienNienChe svnc, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if(idSV.equalsIgnoreCase(svnc.getIdSinhVien())) {
				cell = nextRow.createCell(2);
				cell.setCellValue(svnc.getHoTen());
				cell = nextRow.createCell(3);
				cell.setCellValue(svnc.getKhoa());
				cell = nextRow.createCell(4);
				cell.setCellValue(svnc.getTenLop());
				cell = nextRow.createCell(5);
				cell.setCellValue(svnc.getNgaySinh());
				cell = nextRow.createCell(6);
				cell.setCellValue(svnc.getGioiTinh());
				cell = nextRow.createCell(7);
				cell.setCellValue(svnc.getEmail());
				cell = nextRow.createCell(8);
				cell.setCellValue(svnc.getSoDT());
				cell = nextRow.createCell(9);
				cell.setCellValue(svnc.getDiaChi());
				cell = nextRow.createCell(10);
				cell.setCellValue(svnc.getDiemTB());
				cell = nextRow.createCell(11);
				cell.setCellValue(svnc.getTongSoKy());
				ck = true;
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
	
	private boolean deleteSV(SinhVienNienChe svnc, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if(idSV.equalsIgnoreCase(svnc.getIdSinhVien())) {
				int lastRow = sheet.getLastRowNum();
				if(i < lastRow) {
					sheet.shiftRows(i + 1, lastRow, -1);
					ck = true;
				}
				if(i == lastRow) {
					Row removeRow = sheet.getRow(i);
					if(removeRow != null) {
						sheet.removeRow(removeRow);
						ck = true;
					}
				}
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
}
