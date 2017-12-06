package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.QuanLyHocPhan;
import quanlysinhvien.view.PanelDangKiHocPhanView;

public class DangKiHocPhanController {
	private JComboBox<String> hocKy;
	private JTextField tfDangky;
	private JButton btnDangKy, btnXoaHP, btnGuiDangKy;
	private JTable table;
	private PanelDangKiHocPhanView panelDkiHP;
	private JCheckBox checkBox;
	private JLabel lblSum;
	private int tongSoTinChiDaDangKy = 0;
	private QuanLyHocPhan dsHocPhan;
	private QuanLyHocPhan dsHPDangKy;
	private String idSV;

	public DangKiHocPhanController(PanelDangKiHocPhanView panelDkiHP, String idSV) {
		this.panelDkiHP = panelDkiHP;
		this.idSV = idSV;
		this.hocKy = panelDkiHP.getHocKy();
		this.tfDangky = panelDkiHP.getTfDangky();
		this.btnDangKy = panelDkiHP.getBtnDangKy();
		this.btnXoaHP = panelDkiHP.getBtnXoaHP();
		this.btnGuiDangKy = panelDkiHP.getBtnGuiDangKy();
		this.table = panelDkiHP.getTable();
		this.checkBox = panelDkiHP.getCheckBox();
		this.lblSum = panelDkiHP.getLbSum();

		/* Danh sách học phần trong bảng học phần */
		String fileDSHocPhan = "quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx";
		this.dsHocPhan = initDanhSachHocPhan(fileDSHocPhan);
		
		/*Danh sach hoc phan da dang ki*/
		String dsHPDaDangKy = "quanlysinhvien\\sinhvientinchi\\"+idSV+"\\dsHocPhanDaDangKy.xlsx";
		this.dsHPDangKy = initDanhSachHocPhanDky(dsHPDaDangKy);

		/*load thông tin cũ vào bảng*/
		loadBangDangKyHocPhan();

		addEvents();
	}

	private QuanLyHocPhan initDanhSachHocPhanDky(String fileName) {
		// TODO Auto-generated method stub
		/*Kiểm tra file có tồn tại*/
		QuanLyHocPhan qlyHocPhan = null;
		File file = new File(fileName);
		if(!file.exists()){
			qlyHocPhan = new QuanLyHocPhan();
			return qlyHocPhan;
		}
		
		ArrayList<HocPhan> dsHP = loadHocPhanDaDangKy(fileName);
		qlyHocPhan = new QuanLyHocPhan(dsHP);
		
		return qlyHocPhan;
		
	}

	private QuanLyHocPhan initDanhSachHocPhan(String fileDSHocPhan) {
		// TODO Auto-generated method stub
		QuanLyHocPhan qlyHocPhan = null;
		File file = new File(fileDSHocPhan);
		if(!file.exists()){
			qlyHocPhan = new QuanLyHocPhan();
			return qlyHocPhan;
		}
		
		ArrayList<HocPhan> dsHP = loadDanhSachHocPhan(fileDSHocPhan);
		qlyHocPhan = new QuanLyHocPhan(dsHP);
		
		return qlyHocPhan;
	}

	private ArrayList<HocPhan> loadDanhSachHocPhan(String fileDSHocPhan) {
		// TODO Auto-generated method stub
		ArrayList<HocPhan> dsHocPhan = new ArrayList<>();
		
		/*Kiểm tra tồn tại file*/
		File file = new File(fileDSHocPhan);
		if(!file.exists()){
			return dsHocPhan;
		}
		/* Khởi tạo file input */
		FileInputStream fis = null;
		XSSFWorkbook wb = null;
		try {
			fis = new FileInputStream(fileDSHocPhan);
			wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Iterator<Row> itrRow = sheet.iterator();
			
			/*Bỏ qua tiêu đề*/
			if(itrRow.hasNext()){
				itrRow.next();
			}
			
			while(itrRow.hasNext()){
				row = itrRow.next();
				
				ArrayList<String> dataHP = new ArrayList<>();
				Iterator<Cell> itrCell = row.iterator();
				while(itrCell.hasNext()){
					Cell cell = itrCell.next();
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
					dataHP.add(data);
					if(dataHP.size() < 1) return null;
				}
				
				if(dataHP.size() != 0){
					String ngayDangKyHP = new String(new Date(System.currentTimeMillis()).toString());
					dsHocPhan.add(new HocPhan(dataHP.get(0), dataHP.get(1), (int) Double.parseDouble(dataHP.get(2)), Double.parseDouble(dataHP.get(3)), dataHP.get(4), Double.parseDouble(dataHP.get(5)), ngayDangKyHP));
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		return dsHocPhan;

	}

	private void loadBangDangKyHocPhan() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] obj = new Object[6];

		ArrayList<HocPhan> dsHocPhanDaDangKy = dsHPDangKy.getDsHocPhan();
		int count = dsHocPhanDaDangKy.size();
		for (int i = 0; i < count; i++) {
			obj[0] = dsHocPhanDaDangKy.get(i).getIdHocPhan();
			obj[1] = dsHocPhanDaDangKy.get(i).getTenHP();
			obj[2] = dsHocPhanDaDangKy.get(i).getNgayDangKy();
			obj[3] = "Thành công";
			obj[4] = dsHocPhanDaDangKy.get(i).getSoTinChi();
			obj[5] = false;
			
			model.addRow(obj);
		}
		tongSoTinChiDaDangKy = dsHPDangKy.getSoTCHienTai();
		lblSum.setText(tongSoTinChiDaDangKy + "");
	}

	private ArrayList<HocPhan> loadHocPhanDaDangKy(String fileName){
		// TODO Auto-generated method stub
		ArrayList<HocPhan> dsHP = new ArrayList<>();
		File file = new File(fileName);
		if(!file.exists()){
			return new ArrayList<>();
		}
		
		FileInputStream fis = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			
			Row row;
			if (iterator.hasNext()){
				iterator.next(); // loại bỏ dòng tiêu đề
			}
			
			while (iterator.hasNext()) {
				row = iterator.next();
				
				ArrayList<String> dataHP = new ArrayList<>();
				Iterator<Cell> itrCell = row.iterator();
				while(itrCell.hasNext()){
					Cell cell = itrCell.next();
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
					dataHP.add(data);
					if(dataHP.size() < 1) return null;
				}
				
				if(dataHP.size() != 0){	
					dsHP.add(new HocPhan(dataHP.get(0), dataHP.get(1), Integer.parseInt(dataHP.get(2)), Double.parseDouble(dataHP.get(3)), dataHP.get(4), Double.parseDouble(dataHP.get(5)), dataHP.get(6)));
				}
				
//				String idHocPhan = row.getCell(1).getStringCellValue();
//				String tenHP = row.getCell(2).getStringCellValue();
//				int soTinChi = Integer.parseInt(Double.toString(row.getCell(3).getNumericCellValue()));
//				double soTCHocPhi = row.getCell(4).getNumericCellValue();
//				String idNganh = row.getCell(5).getStringCellValue();
//				double trongSo = row.getCell(6).getNumericCellValue();
//				String ngayDangKyHP = row.getCell(7).getStringCellValue();
//				
//				HocPhan hocPhan = new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo, ngayDangKyHP);
//				dsHP.add(hocPhan);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return dsHP;
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int count = model.getRowCount();
				for (int row = 0; row < count; row++) {
					if (checkBox.isSelected()) {
						model.setValueAt(true, row, 5);
					} else {
						model.setValueAt(false, row, 5);
					}
				}
			}
		});

		btnDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DangKiHocPhan();
				return;
			}

		});

		tfDangky.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DangKiHocPhan();
					return;
				}

			}
		});

		btnXoaHP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xoaHocPhan();
				return;
			}
		});
		// su kien gui dang ki
		btnGuiDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				guiDangKy();
				return;
			}

		});
	}
	
	private void xoaHocPhan() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int row = 0;
		while (row < model.getRowCount()) {
			Boolean check = Boolean.valueOf(model.getValueAt(row, 5).toString());
			if (check == true) {
				String maHocPhan = model.getValueAt(row, 0).toString();
				HocPhan hocPhan = dsHPDangKy.getHocPhan(maHocPhan);
				/*Xóa học phần trong ds học phần đăng kí. nếu xóa thành công thì xóa trên bảng đăng kí*/
				if(dsHPDangKy.xoaHocPhan(hocPhan)){
					model.removeRow(row);
				}else{
					JOptionPane.showMessageDialog(null, "Không tìm thấy mã học phần: "+maHocPhan);
					return;
				}
				continue;
			}
			row++;
		}
		tongSoTinChiDaDangKy = dsHPDangKy.getSoTCHienTai();
		lblSum.setText("" + tongSoTinChiDaDangKy);
	}

	private void updataTableDanhSachDangKy() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			model.setValueAt("Thành công", i, 3);
		}
	}

	private void guiDangKy() {
		// TODO Auto-generated method stub
		FileOutputStream fos = null;
		XSSFWorkbook wb = null;
		try {
			fos = new FileOutputStream("quanlysinhvien\\sinhvientinchi\\" + idSV + "\\dsHocPhanDaDangKy.xlsx");
			wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			XSSFRow row;

			/* tạo header cho file exel*/
			createHeader(sheet);
			/*lấy dữ liệu từ ds hp đăng kí ghi vào file*/
			ArrayList<HocPhan> dsHocPhan = dsHPDangKy.getDsHocPhan();
			int count = dsHocPhan.size();
			int nextRow = 1;
			for(int i = 0; i < count; i++){
				row = sheet.createRow(nextRow);
				writeHocPhan(dsHocPhan.get(i), row);
				
				nextRow++;
			}
			wb.write(fos);
			
			/*Update trạng thái đăng kí trên bảng đăng kí*/
			updataTableDanhSachDangKy();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void writeHocPhan(HocPhan hp, XSSFRow row) {
		// TODO Auto-generated method stub
		Cell cellMaHocPhan = row.createCell(1);
		cellMaHocPhan.setCellValue(hp.getIdHocPhan());

		Cell cellTenHocPhan = row.createCell(2);
		cellTenHocPhan.setCellValue(hp.getTenHP());

		Cell cellSoTC = row.createCell(3);
		cellSoTC.setCellValue(hp.getSoTinChi());

		Cell cellTCHocPhi = row.createCell(4);
		cellTCHocPhi.setCellValue(hp.getSoTCHocPhi());

		Cell cellMaNganh = row.createCell(5);
		cellMaNganh.setCellValue(hp.getIdNganh());
		
		Cell cellTrongSo = row.createCell(6);
		cellTrongSo.setCellValue(hp.getTrongSo());
		
		Cell cellNgayDangKy = row.createCell(7);
		cellNgayDangKy.setCellValue(hp.getNgayDangKy());
	}

	private void createHeader(XSSFSheet sheet) {
		// TODO Auto-generated method stub
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);

		// tạo dòng mới
		XSSFRow row = sheet.createRow(0);
		
		Cell cellMaHocPhan = row.createCell(1);
		cellMaHocPhan.setCellStyle(cellStyle);
		cellMaHocPhan.setCellValue("Mã Học Phần");

		Cell cellTenHocPhan = row.createCell(2);
		cellTenHocPhan.setCellStyle(cellStyle);
		cellTenHocPhan.setCellValue("Tên học phần");

		Cell cellSoTC = row.createCell(3);
		cellSoTC.setCellStyle(cellStyle);
		cellSoTC.setCellValue("Số tín chỉ");

		Cell cellTCHocPhi = row.createCell(4);
		cellTCHocPhi.setCellStyle(cellStyle);
		cellTCHocPhi.setCellValue("Số TC học phí");

		Cell cellMaNganh = row.createCell(5);
		cellMaNganh.setCellStyle(cellStyle);
		cellMaNganh.setCellValue("Mã ngành");
		
		Cell cellTrongSo = row.createCell(6);
		cellTrongSo.setCellStyle(cellStyle);
		cellTrongSo.setCellValue("Trọng số");
		
		Cell cellNgayDangKy = row.createCell(7);
		cellNgayDangKy.setCellStyle(cellStyle);
		cellNgayDangKy.setCellValue("Ngày Đăng Ký");
	}

	public void DangKiHocPhan() {
		String maHocPhan = tfDangky.getText();
		if (maHocPhan.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập vào mã học phần");
			return;
		}
		/* Kiểm tra học phần có tồn tại */
		HocPhan hocPhan = dsHocPhan.getHocPhan(maHocPhan);
		if (hocPhan == null) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy mã học phần.");
			return;
		}

		/* kiểm tra học phần có trùng trong bảng đăng kí học phần */
		if(dsHPDangKy.checkHocPhan(maHocPhan)){
			JOptionPane.showMessageDialog(null, "Học phần: "+ maHocPhan + " đã tồn tại trong bảng đăng kí");
			return;
		}

		tongSoTinChiDaDangKy += hocPhan.getSoTinChi();
		/* kiểm tra có quá giới hạn tín chỉ HP được phép đăng ki */
		if (tongSoTinChiDaDangKy > 24) {
			JOptionPane.showMessageDialog(null, "Quá 24 tín chỉ.");
			return;
		}
		
		/*thêm học phần vào danh sách học phân đăng kí*/
		dsHPDangKy.themHocPhan(hocPhan);
		
		/* Thêm vào bảng */
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] obj = new Object[6];
		obj[0] = hocPhan.getIdHocPhan();
		obj[1] = hocPhan.getTenHP();
		obj[2] = hocPhan.getNgayDangKy();
		obj[3] = "Chưa gửi đăng kí";
		obj[4] = Math.round(hocPhan.getSoTinChi());
		obj[5] = false;
		model.addRow(obj);
		
		/*set text fiel và label tín chỉ*/
		lblSum.setText(tongSoTinChiDaDangKy + "");
		tfDangky.setText("");
	}

}
