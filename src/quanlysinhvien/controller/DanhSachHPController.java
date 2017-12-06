package quanlysinhvien.controller;

import java.awt.HeadlessException;
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

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.view.PanelDanhSachHPView;

public class DanhSachHPController {
	private PanelDanhSachHPView danhSachHP;
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdHocPhan, tfTenHP, tfSoTC, tfIdNganh, tfTrongSo, tfTimKiem, tfSoTCHocPhi;
	private ArrayList<HocPhan> dsHocPhan;
	private String fileName;

	public DanhSachHPController(PanelDanhSachHPView danhSachHP) {
		this.danhSachHP = danhSachHP;
		fileName = "quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx";
		try {
			this.dsHocPhan = readFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsHocPhan = new ArrayList<>();
			System.out.println("Error danhSachHP: " + e);
		}
		this.table = danhSachHP.getTable();
		this.btnThem = danhSachHP.getBtnThem();
		this.btnSua = danhSachHP.getBtnSua();
		this.btnXoa = danhSachHP.getBtnXoa();
		this.btnHuy = danhSachHP.getBtnHuy();
		this.btnTimKiem = danhSachHP.getBtnTimKiem();
		this.tfIdHocPhan = danhSachHP.getTfIdHocPhan();
		this.tfTenHP = danhSachHP.getTfTenHP();
		this.tfSoTC = danhSachHP.getTfSoTC();
		this.tfSoTCHocPhi = danhSachHP.getTfSoTCHocPhi();
		this.tfIdNganh = danhSachHP.getTfIdNganh();
		this.tfTrongSo = danhSachHP.getTfTrongSo();
		this.tfTimKiem = danhSachHP.getTfTimKiem();
		this.timKiemCB = danhSachHP.getTimKiemCB();
		this.danhSachHP.loadData(table, dsHocPhan, "", "");

		setAction();
	}

	private void setAction() {
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row >= 0) {
					tfIdHocPhan.setText((String) table.getValueAt(row, 0));
					tfIdHocPhan.setEnabled(false);
					tfTenHP.setText((String) table.getValueAt(row, 1));
					tfSoTC.setText((String) table.getValueAt(row, 2));
					tfSoTCHocPhi.setText((String) table.getValueAt(row, 3));
					tfIdNganh.setText((String) table.getValueAt(row, 4));
					tfTrongSo.setText((String) table.getValueAt(row, 5));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HocPhan hp = getHocPhan();
				if (hp != null) {
					if (checkID(hp.getIdHocPhan())) {
						dsHocPhan.add(hp);
						danhSachHP.loadData(table, dsHocPhan, "", "");
						try {
							addHP(hp, fileName);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã học phần", "Error insert",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				HocPhan hp = getHocPhan();
				if (hp != null) {
					for (int i = 0; i < dsHocPhan.size(); i++) {
						String idHP = dsHocPhan.get(i).getIdHocPhan();
						if (idHP.equals(hp.getIdHocPhan())) {
							dsHocPhan.get(i).setTenHP(hp.getTenHP());
							dsHocPhan.get(i).setSoTinChi(hp.getSoTinChi());
							dsHocPhan.get(i).setSoTCHocPhi(hp.getSoTCHocPhi());
							dsHocPhan.get(i).setIdNganh(hp.getIdNganh());
							dsHocPhan.get(i).setTrongSo(hp.getTrongSo());
							break;
						}
					}
					boolean ck = false;
					try {
						ck = updateHP(hp, fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(ck) JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					else {
						JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
					}
					danhSachHP.loadData(table, dsHocPhan, "", "");
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
						for (int i = 0; i < dsHocPhan.size(); i++) {
							if (dsHocPhan.get(i).getIdHocPhan().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteHP(dsHocPhan.get(i), fileName);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dsHocPhan.remove(i);
								danhSachHP.loadData(table, dsHocPhan, "", "");
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
				danhSachHP.loadData(table, dsHocPhan, "", "");
			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				danhSachHP.loadData(table, dsHocPhan, timKiem, giaTri);
			}
		});

	}

	private void cancel() {
		tfIdHocPhan.setText("");
		tfIdHocPhan.setEnabled(true);
		tfTenHP.setText("");
		tfIdNganh.setText("");
		tfSoTC.setText("");
		tfSoTCHocPhi.setText("");
		tfTrongSo.setText("");
		tfTimKiem.setText("");
	}

	private boolean checkID(String id) {
		for (HocPhan hp: dsHocPhan) {
			if (hp.getIdHocPhan().equals(id))
				return false;
		}
		return true;
	}

	private HocPhan getHocPhan() {
		String idHocPhan = tfIdHocPhan.getText().trim().toUpperCase();
		String tenHP = tfTenHP.getText().trim();
		String idNganh = tfIdNganh.getText().trim().toUpperCase();
		double soTCHocPhi;
		int soTinChi;
		double trongSo;
		if (idHocPhan.equals("") || tenHP.equals("") || idNganh.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		try {
			if(!checkNganh(idNganh)) {
				JOptionPane.showMessageDialog(null, "Mã ngành không tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			soTinChi = Integer.parseInt(tfSoTC.getText().trim());
			soTCHocPhi = Double.parseDouble(tfSoTCHocPhi.getText().trim());
			trongSo = Double.parseDouble(tfTrongSo.getText().trim());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Số tín chỉ, Số TC học phí, Trọng số", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		HocPhan hp = new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo);
		return hp;
	}
	
	private boolean checkNganh(String idNganh) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\danhsachchuyennganh\\dsNganh.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idN = cell.getStringCellValue();
			if(idN.equals(idNganh)) {
				ck = true;
				break;
			}
		}
		
		return ck;
	}
	
	private ArrayList<HocPhan> readFile(String fileName) throws IOException {
		ArrayList<HocPhan> dsHocPhan = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(fileName));
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        Row nextRow;
        if(iterator.hasNext()) nextRow = iterator.next();  //loại bỏ dòng tiêu đề
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            ArrayList<String> dataHP = new ArrayList<>();
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
                    default: data = "";
                        break;
                }
                dataHP.add(data);
            }
            HocPhan hp = new HocPhan(dataHP.get(0), dataHP.get(1), (int)(Double.parseDouble(dataHP.get(2))), (int)(Double.parseDouble(dataHP.get(3))), dataHP.get(4), Double.parseDouble(dataHP.get(5)));
            dsHocPhan.add(hp);
        }
        
        workbook.close();
        inputStream.close();
        return dsHocPhan;
	}
	
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		
		Row row = sheet.createRow(0);
		Cell cellIdHP  = row.createCell(1);
		cellIdHP.setCellStyle(cellStyle);
		cellIdHP.setCellValue("Mã học phần");
		
		Cell cellTenHP = row.createCell(2);
		cellTenHP.setCellStyle(cellStyle);
		cellTenHP.setCellValue("Tên HP");
		
		Cell cellSoTC = row.createCell(3);
		cellSoTC.setCellStyle(cellStyle);
		cellSoTC.setCellValue("Số TC");
		
		Cell cellSoTCHocPhi = row.createCell(4);
		cellSoTCHocPhi.setCellStyle(cellStyle);
		cellSoTCHocPhi.setCellValue("Số TC học phí");
		
		Cell cellIdNganh = row.createCell(5);
		cellIdNganh.setCellStyle(cellStyle);
		cellIdNganh.setCellValue("Mã ngành");
		
		Cell cellTrongSo = row.createCell(6);
		cellTrongSo.setCellStyle(cellStyle);
		cellTrongSo.setCellValue("Trọng số");
		
	}

	private void writeHocPhan(HocPhan hp, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(hp.getIdHocPhan());
		cell = row.createCell(2);
		cell.setCellValue(hp.getTenHP());
		cell = row.createCell(3);
		cell.setCellValue(hp.getSoTinChi());
		cell = row.createCell(4);
		cell.setCellValue(hp.getSoTCHocPhi());
		cell = row.createCell(5);
		cell.setCellValue(hp.getIdNganh());
		cell = row.createCell(6);
		cell.setCellValue(hp.getTrongSo());
	}
	
	private void addHP(HocPhan hp, String fileName) throws IOException {
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
			writeHocPhan(hp, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private boolean updateHP(HocPhan hp, String fileName) throws IOException {
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
			String idHP = cell.getStringCellValue();
			if(idHP.equalsIgnoreCase(hp.getIdHocPhan())) {
//				String idHocPhan, String tenHP, int soTinChi, double soTCHocPhi, String idNganh, double trongSo) {
				cell = nextRow.createCell(2);
				cell.setCellValue(hp.getTenHP());
				cell = nextRow.createCell(3);
				cell.setCellValue(hp.getSoTinChi());
				cell = nextRow.createCell(4);
				cell.setCellValue(hp.getSoTCHocPhi());
				cell = nextRow.createCell(5);
				cell.setCellValue(hp.getIdNganh());
				cell = nextRow.createCell(6);
				cell.setCellValue(hp.getTrongSo());
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
	
	private boolean deleteHP(HocPhan hp, String fileName) throws IOException {
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
			if(idSV.equalsIgnoreCase(hp.getIdHocPhan())) {
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

}
