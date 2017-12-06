package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import quanlysinhvien.model.Khoa_Vien;
import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.view.PanelDanhSachNganhView;
import quanlysinhvien.view.XemDanhSachLopCNView;

public class DanhSachNganhController {
	private PanelDanhSachNganhView danhSachNganh;
	private JTable table;
	private JTextField tfIdKhoa_Vien, tfTenKhoa_Vien, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnHuy, btnXemDSNganh;
	private JComboBox<String> timKiemCB;
	private ArrayList<Khoa_Vien> dsKhoa_Vien;
	private String fileName;
	
	public DanhSachNganhController(PanelDanhSachNganhView danhSachNganh) {
		this.danhSachNganh = danhSachNganh;
		this.fileName = "quanlysinhvien\\danhsachchuyennganh\\dsNganh.xlsx";
		try {
			this.dsKhoa_Vien = readFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsKhoa_Vien = new ArrayList<>();
//			e.printStackTrace();
			System.out.println("Error DanhSachNganh: " + e);
		}
		this.table = danhSachNganh.getTable();
		this.btnThem = danhSachNganh.getBtnThem();
		this.btnSua = danhSachNganh.getBtnSua();
		this.btnXoa = danhSachNganh.getBtnXoa();
		this.btnTimKiem = danhSachNganh.getBtnTimKiem();
		this.btnHuy = danhSachNganh.getBtnHuy();
		this.btnXemDSNganh = danhSachNganh.getBtnXemDSNganh();
		this.timKiemCB = danhSachNganh.getTimKiemCB();
		this.tfIdKhoa_Vien = danhSachNganh.getTfIdKhoa_Vien();
		this.tfTenKhoa_Vien = danhSachNganh.getTfTenKhoa_Vien();
		this.tfTimKiem = danhSachNganh.getTfTimKiem();
		danhSachNganh.loadData(table, dsKhoa_Vien, "", "");
		
		setAction();
	}
	
	private void setAction() {
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row >= 0) {
					tfIdKhoa_Vien.setText((String) table.getValueAt(row, 0));
					tfIdKhoa_Vien.setEnabled(false);
					tfTenKhoa_Vien.setText((String) table.getValueAt(row, 1));
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
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
				Khoa_Vien khoa_vien = getKhoa_Vien();
				if (khoa_vien != null) {
					if (checkNganh(khoa_vien.getIdKhoa_Vien())) {
						dsKhoa_Vien.add(khoa_vien);
						danhSachNganh.loadData(table, dsKhoa_Vien, "", "");
						try {
							addKhoa_Vien(khoa_vien, fileName);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã ngành", "Error insert",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Khoa_Vien khoa_vien = getKhoa_Vien();
				if (khoa_vien != null) {
					for (int i = 0; i < dsKhoa_Vien.size(); i++) {
						String idNganh = dsKhoa_Vien.get(i).getIdKhoa_Vien();
						if (idNganh.equals(khoa_vien.getIdKhoa_Vien())) {
							dsKhoa_Vien.get(i).setTenKhoa_Vien(khoa_vien.getTenKhoa_Vien());;
							break;
						}
					}
					boolean ck = false;
					try {
						ck = updateKhoa_Vien(khoa_vien, fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(ck) JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					else {
						JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
					}
					danhSachNganh.loadData(table, dsKhoa_Vien, "", "");
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
				} else {
					int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
							JOptionPane.YES_NO_OPTION);
					if (select == 0) {
						String id = (String) table.getValueAt(row, 0);
						for (int i = 0; i < dsKhoa_Vien.size(); i++) {
							if (dsKhoa_Vien.get(i).getIdKhoa_Vien().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteKhoa_Vien(dsKhoa_Vien.get(i).getIdKhoa_Vien(), fileName);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dsKhoa_Vien.remove(i);
								danhSachNganh.loadData(table, dsKhoa_Vien, "", "");
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
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancel();
				danhSachNganh.loadData(table, dsKhoa_Vien, "", "");
			}
		});
		btnTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				danhSachNganh.loadData(table, dsKhoa_Vien, timKiem, giaTri);
			}
		});
		btnXemDSNganh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn ngành để xem danh sách lớp chuyên ngành", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String idNganh = (String) table.getValueAt(row, 0);
				XemDanhSachLopCNView xemDSLopCN = new XemDanhSachLopCNView(idNganh, (String) table.getValueAt(row, 1));
				new XemDSLopCNController(xemDSLopCN, idNganh);
			}
		});
	}
	
	private ArrayList<Khoa_Vien> readFile(String fileName) throws IOException{
		ArrayList<Khoa_Vien> dsKhoa_Vien = new ArrayList<>();
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
			ArrayList<String> dataNganh = new ArrayList<>();
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
				dataNganh.add(data);
				if(dataNganh.size() < 1) return null;
			}
			if(dataNganh.size() > 0) {
				ArrayList<LopChuyenNganh> dsLopChuyenNganh;
				try {
					dsLopChuyenNganh = getDSLopChuyenNganh(dataNganh.get(0));
				}catch(Exception exc) {
					dsLopChuyenNganh = new ArrayList<>();
					System.out.println("Error DanhSachNganhController:" + exc);
				}
				Khoa_Vien khoa_vien = new Khoa_Vien(null, dataNganh.get(0), dataNganh.get(1));
				dsKhoa_Vien.add(khoa_vien);
			}
		}

		workbook.close();
		inputStream.close();
		return dsKhoa_Vien;
	}
	
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(cellStyle);
		cellHocKy.setCellValue("Mã Khoa/Viện");

		Cell cellIdLop = row.createCell(2);
		cellIdLop.setCellStyle(cellStyle);
		cellIdLop.setCellValue("Tên Khoa/Viện");
	}
	
	private void writeKhoaVien(Khoa_Vien khoa_vien, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(khoa_vien.getIdKhoa_Vien());
		cell = row.createCell(2);
		cell.setCellValue(khoa_vien.getTenKhoa_Vien());
	}
	
	private void addKhoa_Vien(Khoa_Vien khoa_vien, String fileName) throws IOException {
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
			writeKhoaVien(khoa_vien, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private boolean updateKhoa_Vien(Khoa_Vien khoa_vien, String fileName) throws IOException {
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
			String idKhoaVien = cell.getStringCellValue();
			if(idKhoaVien.equalsIgnoreCase(khoa_vien.getIdKhoa_Vien())) {
				cell = nextRow.createCell(2);
				cell.setCellValue(khoa_vien.getTenKhoa_Vien());
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
	
	private boolean deleteKhoa_Vien(String idKhoa_Vien, String fileName) throws IOException {
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
			String idN = cell.getStringCellValue();
			if(idN.equalsIgnoreCase(idKhoa_Vien)) {
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
	
	private ArrayList<LopChuyenNganh> getDSLopChuyenNganh(String idNganh) throws IOException {
		ArrayList<LopChuyenNganh> dsLopChuyenNganh = new ArrayList<>();
		String fileName = "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\dsLopCN.xlsx";
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
			ArrayList<String> dataLopChuyenNganh = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cellCK = nextRow.getCell(1);
				if(!cellCK.equals(idNganh)) break; //bỏ qua lớp ngành khác
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
				dataLopChuyenNganh.add(data);
				if(dataLopChuyenNganh.size() < 1) return null;
			}
			if(dataLopChuyenNganh.size() > 0) {
				LopChuyenNganh lopCN = new LopChuyenNganh(null, dataLopChuyenNganh.get(0), dataLopChuyenNganh.get(1), dataLopChuyenNganh.get(2), dataLopChuyenNganh.get(3), dataLopChuyenNganh.get(4));
				dsLopChuyenNganh.add(lopCN);
			}
		}

		workbook.close();
		inputStream.close();
		return dsLopChuyenNganh;
	}

	
	private Khoa_Vien getKhoa_Vien() {
		String idNganh = tfIdKhoa_Vien.getText().toUpperCase();
		String tenNganh = tfTenKhoa_Vien.getText();
		if(idNganh.equals("") || tenNganh.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		Khoa_Vien khoa_vien = new Khoa_Vien(null, idNganh, tenNganh);
		return khoa_vien;
	}
	
	private boolean checkNganh(String idKhoa_Vien) {
		for (Khoa_Vien khoa_vien: dsKhoa_Vien) {
			if (khoa_vien.getIdKhoa_Vien().equals(idKhoa_Vien))
				return false;
		}
		return true;
	}
	
	private void cancel() {
		table.getSelectionModel().clearSelection();
		tfIdKhoa_Vien.setText("");
		tfIdKhoa_Vien.setEnabled(true);
		tfTenKhoa_Vien.setText("");
		tfTimKiem.setText("");
	}
}
