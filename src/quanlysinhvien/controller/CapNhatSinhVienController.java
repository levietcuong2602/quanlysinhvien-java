package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.CapNhatSinhVienLCNView;

public class CapNhatSinhVienController {
	private CapNhatSinhVienLCNView capNhatSV;
	private JTable table;
	private JButton btnThem, btnXoa;
	private JTextField tfIdSinhVien;
	private JComboBox<String> loaiSVCB;
	private ArrayList<SinhVien> dsSinhVien;
	private String fileName;
	private String tenLop = "";
	
	public CapNhatSinhVienController(CapNhatSinhVienLCNView capNhatSV, ArrayList<SinhVien> dsSinhVien, String fileName, String tenLopCN) {
		this.capNhatSV = capNhatSV;
		this.fileName = fileName;
		this.tenLop = tenLopCN;
		this.dsSinhVien = dsSinhVien;
		this.table = capNhatSV.getTable();
		this.btnThem = capNhatSV.getBtnThem();
		this.btnXoa = capNhatSV.getBtnXoa();
		this.tfIdSinhVien = capNhatSV.getTfIdSinhVien();
		this.loaiSVCB = capNhatSV.getLoaiSVCB();
		this.capNhatSV.loadData(table, dsSinhVien);
		
		setAction();
	}
	
	private void setAction() {
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String idSV = tfIdSinhVien.getText().toUpperCase().trim();
				String loaiSinhVien = (String) loaiSVCB.getSelectedItem();
				if(idSV.equals("")) {
					JOptionPane.showMessageDialog(null, "Mã sinh viên trống", "Error insert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				SinhVien sv = null;
				try {
					sv = getSinhVien(loaiSinhVien, idSV);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(sv == null) {
					JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại", "Error insert", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					if(checkSV(idSV)) {
						if(!tenLop.equals("") && sv.getTenLop().equals("null")) {
							sv.setTenLop(tenLop);
							try {
								updateLopSV(idSV, tenLop, loaiSinhVien);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("Error cập nhật lớp CN: " + e1);
//								e1.printStackTrace();
							}
						}else if(!sv.getTenLop().equals("null") && !tenLop.equals("")){
							JOptionPane.showMessageDialog(null, "Sinh viên đang thuộc lớp: " + sv.getTenLop() + "\nCần xóa sinh viên khỏi lớp trước khi thêm vào lớp mới", "Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
						dsSinhVien.add(sv);
						capNhatSV.loadData(table, dsSinhVien);
						try {
							addSV(sv, fileName);
							JOptionPane.showMessageDialog(null, "Thêm thành công");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Trùng mã sinh viên", "Error", JOptionPane.ERROR_MESSAGE);
					}
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
							if (dsSinhVien.get(i).getIdSinhVien().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteSV(dsSinhVien.get(i), fileName);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if(!tenLop.equals("")) {
									dsSinhVien.get(i).setTenLop("null");
									try {
										boolean ck1 = false;
										ck1 = updateLopSV(dsSinhVien.get(i).getIdSinhVien(), "null", "Sinh viên tín chỉ");
										if(ck1);
										else {
											updateLopSV(dsSinhVien.get(i).getIdSinhVien(), "null", "Sinh viên niên chế");
										}
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										System.out.println("Error cập nhật lớp CN: " + e1);
									}
								}
								dsSinhVien.remove(i);
								capNhatSV.loadData(table, dsSinhVien);
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
	}
	
	private void cancel() {
		tfIdSinhVien.setText("");
	}
	
	private boolean checkSV(String idSV) {
		for (SinhVien sv: dsSinhVien) {
			if (sv.getIdSinhVien().equals(idSV))
				return false;
		}
		return true;
	}
	
	private SinhVien getSinhVien(String loaiSinhVien, String idSV) throws IOException {
		SinhVien sv = null;
		String fileName = "";
		if(loaiSinhVien.equals("Sinh viên tín chỉ")) {
			fileName = "quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		}
		if(loaiSinhVien.equals("Sinh viên niên chế")) {
			fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		}
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) {
			nextRow = iterator.next();
		}
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSinhVien = cell.getStringCellValue();
			if(idSinhVien.equals(idSV)) {
				cell = nextRow.getCell(2);
				String hoTen = cell.getStringCellValue();
				cell = nextRow.getCell(3);
				String khoa = cell.getStringCellValue();
				cell = nextRow.getCell(4);
				String tenLop = cell.getStringCellValue();
				cell = nextRow.getCell(5);
				String ngaySinh = cell.getStringCellValue();
				cell = nextRow.getCell(6);
				String gioiTinh = cell.getStringCellValue();
				cell = nextRow.getCell(7);
				String email = cell.getStringCellValue();
				cell = nextRow.getCell(8);
				String soDT = cell.getStringCellValue();
				cell = nextRow.getCell(9);
				String diaChi = cell.getStringCellValue();
				cell = nextRow.getCell(10);
				double diemTB = Double.parseDouble(Double.toString(cell.getNumericCellValue()));
				sv = new SinhVien(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
				break;
			}
		}
		workbook.close();
		fin.close();
		
		return sv;
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
	}

	private void writeSV(SinhVien sv, Row row) {
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
	}
	
	private void addSV(SinhVien sv, String fileName) throws IOException {
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
			writeSV(sv, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private boolean deleteSV(SinhVien sv, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow = null;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if(idSV.equalsIgnoreCase(sv.getIdSinhVien())) {
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
				//hate you 
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
	
	private boolean updateLopSV(String idSinhVien, String tenLop, String loaiSV) throws IOException {
		boolean ck = false;
		String fileName = "";
		if(loaiSV.equals("Sinh viên tín chỉ"))
			fileName = "quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		else if(loaiSV.equals("Sinh viên niên chế"))
			fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
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
			if(idSV.equalsIgnoreCase(idSinhVien)) {
				cell = nextRow.createCell(4);
				cell.setCellValue(tenLop);
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
}
