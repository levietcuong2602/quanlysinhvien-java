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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.QuanLyHocPhan;
import quanlysinhvien.model.QuanLyLopHocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelDangKiLopHocView;

public class DangKiLopHocController {
	private JTextField tfDangKy;
	private JButton btnDangKy, btnXoaDangKy, btnGuiDangKy;
	private JTable tableDangKy, tableTKB;
	private PanelDangKiLopHocView dkyLopHoc;
	private QuanLyLopHocPhan dsLopHoc;
	private QuanLyLopHocPhan dsLopHocDangKy;
	private JCheckBox cbCheck;
	private JLabel lblSum;
	private int tongSoTCDangKy = 0;
	private String idSV;
	private QuanLyHocPhan dsHPDaDKy;
	private SinhVien sinhVien;
	
	public DangKiLopHocController(PanelDangKiLopHocView dkiLopHoc, String idSV) {
		// TODO Auto-generated constructor stub
		this.dkyLopHoc = dkiLopHoc;
		this.idSV = idSV;
		this.tfDangKy = dkiLopHoc.getTfDangKy();
		this.btnDangKy = dkiLopHoc.getBtnDangKy();
		this.btnXoaDangKy = dkiLopHoc.getBtnXoaDangKy();
		this.btnGuiDangKy = dkiLopHoc.getBtnGuiDangKy();
		this.tableDangKy = dkiLopHoc.getTableDangKy();
		this.tableTKB = dkiLopHoc.getTableTKB();
		this.cbCheck = dkiLopHoc.getCbCheck();
		this.lblSum = dkiLopHoc.getLblSum();

		String fileLopHoc = "quanlysinhvien\\danhsachhocphan\\lophocphan\\dsLopHP.xlsx";
		String fileLopDaDK = "quanlysinhvien\\sinhvientinchi\\"+idSV+"\\dsLopHocDangKy.xlsx";
		String fileHPDaDK = "quanlysinhvien\\sinhvientinchi\\"+idSV+"\\dsHocPhanDaDangKy.xlsx";
		
		this.dsLopHoc = initDanhSachLopHocPhan(fileLopHoc);
		this.dsLopHocDangKy = initDanhSachLopHocPhan(fileLopDaDK);
		this.dsHPDaDKy = initDSHPDaDangKy(fileHPDaDK);
		
		/*Load thông tin sinh viên đăng nhập*/
		try {
			sinhVien = loadThongTinSinhVien(idSV);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* load thông tin lớp học đăng kí phiên làm việc trước */
		loadThongTinDangKyTruoc();

		addEvents();
	}

	private QuanLyHocPhan initDSHPDaDangKy(String fileHPDaDK) {
		// TODO Auto-generated method stub
		ArrayList<HocPhan> dsHP;
		try {
			dsHP = loadDanhSachHocPhanDaDangKy(fileHPDaDK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			/*Nếu không tìm thấy file hoặc xảy ra lỗi*/
			dsHP = new ArrayList<>();
			System.out.println("Error DSHP da DK: " + e);
		}
		
		QuanLyHocPhan qlyHP = new QuanLyHocPhan(dsHP);
		return qlyHP;
	}

	private QuanLyLopHocPhan initDanhSachLopHocPhan(String fileLopHoc) {
		// TODO Auto-generated method stub
		ArrayList<LopHocPhan> dsLopHP;
		try {
			dsLopHP = loadDanhSachLopHocPhan(fileLopHoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			/*Nếu không tìm thấy file*/
			dsLopHP = new ArrayList<>();
			System.out.println("Error DSLopHP da DK: " + e);
		}
		
		QuanLyLopHocPhan qlyLop = new QuanLyLopHocPhan(dsLopHP);
		return qlyLop;
	}

	private SinhVien loadThongTinSinhVien(String idSV) throws IOException {
		// TODO Auto-generated method stub
		SinhVien sinhVien = new SinhVien();
		FileInputStream fis = new FileInputStream(new File("quanlysinhvien/sinhvientinchi/dsSinhVienTC.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> itrRow = sheet.iterator();
		Row row;
		
		/*bỏ qua header*/
		if(itrRow.hasNext()){
			itrRow.next();
		}
		
		/*đọc file theo dòng*/
		while(itrRow.hasNext()){
			row = itrRow.next();
			ArrayList<String> dataSV = new ArrayList<>();
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
				dataSV.add(data);
				if(dataSV.size() < 1) return null;
			}
			
			if(dataSV.get(0).equalsIgnoreCase(idSV)){
				sinhVien = new SinhVienTinChi(dataSV.get(0), dataSV.get(1), dataSV.get(2), dataSV.get(3), dataSV.get(4), dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)), (int) Double.parseDouble(dataSV.get(10)), (int) Double.parseDouble(dataSV.get(11)));
				return sinhVien;
			}
		}
		
		return new SinhVienTinChi();
	}

	
	private void loadThongTinDangKyTruoc() {
		// TODO Auto-generated method stub
		/* load lên bảng đăng kí */
		if(dsHPDaDKy.getSoTCHienTai() != 0){
			ArrayList<LopHocPhan> dsLopHoc = dsLopHocDangKy.getDsLopHocPhan();
			int count = dsLopHoc.size();
			for (int i = 0; i < count; i++) {
					HocPhan hp = dsHPDaDKy.getHocPhan(dsLopHoc.get(i).getIdHocPhan());
					if(hp != null){
						tongSoTCDangKy += hp.getSoTinChi();
						
						/*load trên bảng đăng kí*/
						addDataTableDangKy(dsLopHoc.get(i), "old");
					}
			}
			
			/* load bảng thời khóa biểu */
			loadTableThoiKhoaBieu(dsLopHoc);
			/* cập nhật lblSum */
			lblSum.setText(tongSoTCDangKy + "");
		}
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		btnDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DangKyLopHoc();
				return;
			}

		});

		btnXoaDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				XoaLopHoc();
				return;
			}

		});

		tfDangKy.addKeyListener(new KeyListener() {

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
					DangKyLopHoc();
					return;
				}
			}
		});

		cbCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
				int count = model.getRowCount();
				for (int i = 0; i < count; i++) {
					if (cbCheck.isSelected()) {
						model.setValueAt(true, i, 5);
					} else {
						model.setValueAt(false, i, 5);
					}
				}
			}
		});

		btnGuiDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GuiDangKy();
				return;
			}
		});
	}

	private void GuiDangKy() {
		// TODO Auto-generated method stub
		/* Chỉnh trạng thái đăng kí sang thành công */
		DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			model.setValueAt("Thành công", i, 4);
		}

		/* tao file danh sách học phần đã đăng kí */
		WriteFileDangKyLopHoc("quanlysinhvien\\sinhvientinchi\\" + idSV + "\\dsLopHocDangKy.xlsx", dsLopHocDangKy.getDsLopHocPhan());

		/* Update lại file dsLopHoc tăng hoặc giảm số lượng sinh viên hiện tại */
		WriteFileDangKyLopHoc("quanlysinhvien\\danhsachhocphan\\lophocphan\\dsLopHP.xlsx", dsLopHoc.getDsLopHocPhan());
		
		/*Cập nhật file danh sách lớp*/
		UpdateDanhSachLop(dsLopHoc.getDsLopHocPhan());
	}
	private void UpdateDanhSachLop(ArrayList<LopHocPhan> dsLop) {
		// TODO Auto-generated method stub
		int count = dsLop.size();
		for(int i = 0; i < count; i++){
			try {
				WriteFileDanhSachSV(dsLop.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void WriteFileDanhSachSV(LopHocPhan lopHocPhan) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "quanlysinhvien/danhsachhocphan/lophocphan/"+lopHocPhan.getIdLop()+"_dsSV.xlsx";
		XSSFWorkbook wb = null;
		XSSFSheet sheet = null;
		int lastRow = -1;
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			wb = new XSSFWorkbook(inputStream);
			sheet = wb.getSheetAt(0);
			lastRow = sheet.getLastRowNum();
		}catch (Exception e) {
			// TODO: handle exception
			wb = new XSSFWorkbook();
			sheet = wb.createSheet();
			System.out.println(e);
		}

		int rowNext = 1;
		Row row = null;
		if(lastRow < 0) { //file khong ton tai
			/*Tao tieu de file*/
			String[] header = {"Mã SV", "Họ tên", "Khóa", "Tên Lớp", "Ngày sinh", "Giới tính", "Email", "SĐT", "Địa chỉ","Điểm TB"};
			createHeaderRow(sheet, header);
			
		} //file ton tai
		
		rowNext = 1;
		row = sheet.createRow(rowNext);
		if(row != null) {
			int count = lopHocPhan.getDsSinhVien().size();
			for(int i = 0; i < count; i++){
				row = sheet.createRow(rowNext);
				writeSV(lopHocPhan.getDsSinhVien().get(i), row);
				rowNext++;
			}
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		wb.write(fout);
		fout.close();	
	}
	
	public void writeSV(SinhVien sv, Row row){
		Cell cellMaSV = row.createCell(1);
		cellMaSV.setCellValue(sv.getIdSinhVien());

		Cell cellHoTen = row.createCell(2);
		cellHoTen.setCellValue(sv.getHoTen());
		
		Cell cellKhoa = row.createCell(3);
		cellKhoa.setCellValue(sv.getKhoa());
		
		Cell cellTenLop = row.createCell(4);
		cellTenLop.setCellValue(sv.getTenLop());
		
		Cell cellNgaySinh = row.createCell(5);
		cellNgaySinh.setCellValue(sv.getNgaySinh());
		
		Cell cellGioiTinh = row.createCell(6);
		cellGioiTinh.setCellValue(sv.getGioiTinh());
		
		Cell cellEmail = row.createCell(7);
		cellEmail.setCellValue(sv.getEmail());
		
		Cell cellSDT = row.createCell(8);
		cellSDT.setCellValue(sv.getSoDT());
		
		Cell cellDiaChi = row.createCell(9);
		cellDiaChi.setCellValue(sv.getDiaChi());
		
		Cell cellDTB = row.createCell(10);
		cellDTB.setCellValue(sv.getDiemTB());
	}

	private void WriteFileDangKyLopHoc(String string, ArrayList<LopHocPhan> dsLopHoc) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = new FileOutputStream(new File(string));
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("DSLopHocDaDangKy");

			/* tạo header cho file */
			String[] header = {"Học kỳ" , "Mã lớp", "Loại lớp", "Mã học phần", "Tên lớp", "Thời gian", "Tuần học", "Phòng học", "Tên giảng viên", "Số SV Max", "Số SV hiện tại"};
			createHeaderRow(sheet, header);

			/* viết nội dung cho file */
			Row row;
			int count = dsLopHoc.size();
			int rowNext = 1;
			for (int i = 0; i < count; i++) {
				row = sheet.createRow(rowNext);
				writeLopHocPhan(dsLopHoc.get(i), row);
				
				rowNext++;
			}

			wb.write(fos);

			wb.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeLopHocPhan(LopHocPhan lopHocPhan, Row row) {
		// TODO Auto-generated method stub
		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellValue(lopHocPhan.getHocKy());

		Cell cellMaLop = row.createCell(2);
		cellMaLop.setCellValue(lopHocPhan.getIdLop());

		Cell cellLoaiLop = row.createCell(3);
		cellLoaiLop.setCellValue(lopHocPhan.getLoaiLop());

		Cell cellMaHP = row.createCell(4);
		cellMaHP.setCellValue(lopHocPhan.getIdHocPhan());

		Cell cellTenLop = row.createCell(5);
		cellTenLop.setCellValue(lopHocPhan.getTenLop());

		Cell cellThoiGian = row.createCell(6);
		cellThoiGian.setCellValue(lopHocPhan.getThoiGian());

		Cell cellTuanHoc = row.createCell(7);
		cellTuanHoc.setCellValue(lopHocPhan.getTuanHoc());

		Cell cellPhongHoc = row.createCell(8);
		cellPhongHoc.setCellValue(lopHocPhan.getPhongHoc());

		Cell cellTenGV = row.createCell(9);
		cellTenGV.setCellValue(lopHocPhan.getTenGiangVien());

		Cell cellSoSVMax = row.createCell(10);
		cellSoSVMax.setCellValue(lopHocPhan.getSoSVMax());

		Cell cellSoSVHT = row.createCell(11);
		cellSoSVHT.setCellValue(lopHocPhan.getSoSVHienTai());
	}

	private void createHeaderRow(XSSFSheet sheet, String[]header) {
		// TODO Auto-generated method stub
		CellStyle style = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		/* tạo mới dòng */
		XSSFRow row = sheet.createRow(0);
		int cell = 1;
		for(int i = 0; i < header.length; i++){
			Cell cellHeader = row.createCell(cell);
			cellHeader.setCellStyle(style);
			cellHeader.setCellValue(header[i]);
			
			cell++;
		}
	}

	private void XoaLopHoc() {
		// TODO Auto-generated method stub
		/* Xóa trên bảng đăng kí lớp học */
		DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
		for (int i = 0; i < model.getRowCount();) {
			Boolean check = (boolean) model.getValueAt(i, 5);

			if (check == true) {
				String maLopHoc = model.getValueAt(i, 0).toString();
				LopHocPhan lopHP = dsLopHocDangKy.getLopPhan(maLopHoc);
				HocPhan hocPhan = dsHPDaDKy.getHocPhan(lopHP.getIdHocPhan());
				/* Xóa trong danh sách lớp học đăng kí */
				dsLopHocDangKy.xoaLopHocPhan(maLopHoc);

				/* giảm số sinh viên hiên tại của lớp trong ds Lớp */
				CapNhatSoLuongSinhVien(maLopHoc, "sub");
				
				/* Cập nhật lại lblSum tổng số tín */
				tongSoTCDangKy -= hocPhan.getSoTinChi();
				lblSum.setText(tongSoTCDangKy + "");
				
				/* xóa trên bảng đăng kí */
				model.removeRow(i);
			} else {
				i++;
			}
		}

		loadTableThoiKhoaBieu(dsLopHocDangKy.getDsLopHocPhan());
	}

//	public HocPhan getHocPhan(String maHP) {
//		int count = dsHPDaDKy.size();
//		for (int i = 0; i < count; i++) {
//			if (maHP.equalsIgnoreCase(dsHPDaDKy.get(i).getIdHocPhan())) {
//				return dsHPDaDKy.get(i);
//			}
//		}
//
//		return new HocPhan();
//	}

	private ArrayList<HocPhan> loadDanhSachHocPhanDaDangKy(String dsHocPhanDaDangKy) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<HocPhan> dsHocPhan = new ArrayList<>();
		/* Khởi tạo file input */
		File file = new File(dsHocPhanDaDangKy);
		if(!file.exists()){
			return dsHocPhan;
		}

		FileInputStream inputStream = new FileInputStream(new File(dsHocPhanDaDangKy));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row row;
		if (iterator.hasNext())
			row = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			row = iterator.next();
			
			String idHocPhan = row.getCell(1).getStringCellValue();
			String tenHP = row.getCell(2).getStringCellValue();
			int soTinChi = (int) row.getCell(3).getNumericCellValue();
			double soTCHocPhi = row.getCell(4).getNumericCellValue();
			String idNganh = row.getCell(5).getStringCellValue();
			double trongSo = row.getCell(6).getNumericCellValue();
			String ngayDangKyHP = row.getCell(7).getStringCellValue();
			
			HocPhan hocPhan = new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo, ngayDangKyHP);
			dsHocPhan.add(hocPhan);
		}

		workbook.close();
		inputStream.close();

		return dsHocPhan;
	}

	private void DangKyLopHoc() {
		// TODO Auto-generated method stub
		String maLopHoc = tfDangKy.getText();
		/* Kiểm tra rỗng */
		if (maLopHoc.equals("")) {
			JOptionPane.showMessageDialog(null, "Nhập vào mã lớp học");
			return;
		}

		/* Kiểm tra tồn tại mã lớp học (getLopHoc)*/ 
		LopHocPhan lopHP = dsLopHoc.getLopPhan(maLopHoc);
		if(lopHP == null){
			JOptionPane.showMessageDialog(null, "Mã lớp học không tồn tại");
			return;
		}
		
		/* Kiểm tra đã đăng kí học phần chưa (getHocPhan)*/
		HocPhan hocPhan = dsHPDaDKy.getHocPhan(lopHP.getIdHocPhan());
		if(hocPhan == null){
			JOptionPane.showMessageDialog(null, "Bạn chưa đăng kí học phần môn này");
			return;
		}
		
		/* kiểm tra xem có trùng mã lớp học không, mã học phần */
		if(dsLopHocDangKy.checkLopHocPhan(maLopHoc)){
			JOptionPane.showMessageDialog(null,
					"Mã lớp: " + maLopHoc + " đã tồn tại trong bảng đăng kí");
			return;
		}
		
		if(dsLopHocDangKy.checkMaHocPHan(lopHP.getIdHocPhan())){
			JOptionPane.showMessageDialog(null,
					"Mã học phần: " + lopHP.getIdHocPhan() + " đã tồn tại trong bảng đăng kí");
			return;
		}

		/* kiểm tra điều kiện xem có vượt quá số tín chỉ đăng kí không */
		tongSoTCDangKy += hocPhan.getSoTinChi();
		if (tongSoTCDangKy <= 24) {
			/* kiểm tra có trùng lịch học */
			ArrayList<LopHocPhan> dsLopHPDK = dsLopHocDangKy.getDsLopHocPhan();
			boolean check = KiemTraDieuKienTrungLich(lopHP, dsLopHPDK);
			if (check == true) {
				/* Cập nhật lại số tín chỉ */
				tongSoTCDangKy -= hocPhan.getSoTinChi();
				JOptionPane.showMessageDialog(null, "Trùng lịch: " + lopHP.getThoiGian());
				return;
			}

			/* Tăng số sinh viên hiện tại lên 1 trong danh sách học phần */
			if(!CapNhatSoLuongSinhVien(lopHP.getIdLop(), "add")){
				JOptionPane.showMessageDialog(null, "Thêm sinh viên không thành công.");
				return;
			}
			/* Thêm vào ds lop hoc dang ki */
			if(!dsLopHocDangKy.themLopHocPhan(lopHP)){
				JOptionPane.showMessageDialog(null, "Thêm lớp học: " + maLopHoc + " không thành công");
				return;
			}

			/* load dữ liệu lớp học vào bảng đăng kí lớp học */
			addDataTableDangKy(lopHP, "new");

			/* Load dữ liệu vào bảng thời khóa biểu */
			loadTableThoiKhoaBieu(dsLopHPDK);

			/* Set lại label tổng số tín chỉ */
			lblSum.setText(tongSoTCDangKy + "");
			tfDangKy.setText("");
		} else {
			JOptionPane.showMessageDialog(null, "Bạn đã đăng kí vượt quá 24 tín chỉ");
			tongSoTCDangKy -= hocPhan.getSoTinChi();
			return;
		}

	}

	private boolean CapNhatSoLuongSinhVien(String idLop, String mode) {
		// TODO Auto-generated method stub
		ArrayList<LopHocPhan> dsLopHP = dsLopHoc.getDsLopHocPhan();
		int count = dsLopHP.size();
		for (int i = 0; i < count; i++) {
			if (idLop.equalsIgnoreCase(dsLopHP.get(i).getIdLop())) {
				if (mode.equalsIgnoreCase("add")) {
					/*Kiểm tra điều kiện trùng mã sinh viên*/
					if(dsLopHP.get(i).kiemTraTrungSV(sinhVien)){
						JOptionPane.showMessageDialog(null, "Trùng mã sinh viên: " + sinhVien.getIdSinhVien() + "- Họ Tên: " + sinhVien.getHoTen());
						return false;
					}
					System.out.println("add");
					return dsLopHP.get(i).themSinhVien(sinhVien);
				}

				if (mode.equalsIgnoreCase("sub")) {
					System.out.println("sub");
					return dsLopHP.get(i).xoaSinhVien(sinhVien.getIdSinhVien());
				}
			}
		}
		
		return false;
	}

	private boolean KiemTraDieuKienTrungLich(LopHocPhan lopHoc, ArrayList<LopHocPhan> dsLopHocDangKy) {
		// TODO Auto-generated method stub
		String[] thoiGian = lopHoc.getThoiGian().trim().split(",");
		String day1 = thoiGian[0].trim();
		int start1 = Integer.parseInt(thoiGian[1].trim().split("-")[0].trim());
		int end1 = Integer.parseInt(thoiGian[1].trim().split("-")[1].trim());

		String[] thoiGian2;
		String day2;
		int start2, end2;

		int count = dsLopHocDangKy.size();
		for (int row = 0; row < count; row++) {
			thoiGian2 = dsLopHocDangKy.get(row).getThoiGian().trim().split(",");
			day2 = thoiGian2[0].trim();
			start2 = Integer.parseInt(thoiGian2[1].trim().split("-")[0].trim());
			end2 = Integer.parseInt(thoiGian2[1].trim().split("-")[1].trim());

			if (day1.equalsIgnoreCase(day2)) {
				if (start1 >= start2 && start1 <= end2) {
					return true;
				}

				if (start2 >= start1 && start2 <= end1) {
					return true;
				}

				System.out.println(start2 >= start1 && start2 <= end1);
			}
		}
		
//		String[] time1 = lopHoc.getThoiGian().split(",");
//		String thu1 = time1[0].trim();
//		String start1 = time1[1].trim().split("-")[0].trim();
//		String end1 = time1[1].trim().split("-")[1].trim();
//		
//		int count = dsLopHocDangKy.size();
//		for (int row = 0; row < count; row++) {
//			String[]time2 = dsLopHocDangKy.get(row).getThoiGian().trim().split(",");
//			String thu2 = time2[0].trim();
//			String start2 = time2[1].trim().split("-")[0].trim();
//			String end2 = time2[1].trim().split("-")[1].trim();
//
//			if (thu1.compareTo(thu2) == 0) {
//				if(start1.compareTo(start2) > 0 && start1.compareTo(end2) < 0){
//					return true;
//				}
//				
//				if(start2.compareTo(start1) > 0 && start2.compareTo(end1) < 0){
//					return true;
//				}
//				System.out.println(end1.compareTo(start2));
//			}
//		}

		return false;
	}

	private void loadTableThoiKhoaBieu(ArrayList<LopHocPhan> dsLopHocDangKy2) {
		// TODO Auto-generated method stub
		/* Sắp xếp lại array list theo thời khóa biểu */
		dsLopHocDangKy2.sort(new Comparator<LopHocPhan>() {

			@Override
			public int compare(LopHocPhan obj1, LopHocPhan obj2) {
				// TODO Auto-generated method stub
				String[] time1 = obj1.getThoiGian().split(",");
				int day1 = Integer.parseInt(time1[0].trim().split("\\s")[1].trim());
				int start1 = Integer.parseInt(time1[1].trim().split("-")[0]);
				int end1 = Integer.parseInt(time1[1].trim().split("-")[1].trim());

				String[] time2 = obj2.getThoiGian().split(",");
				int day2 = Integer.parseInt(time2[0].trim().split("\\s")[1].trim());
				int start2 = Integer.parseInt(time2[1].trim().split("-")[0]);
				int end2 = Integer.parseInt(time2[1].trim().split("-")[1].trim());
				
				if (day1 > day2) {
					return 1;
				} else if (day1 < day2) {
					return -1;
				} else if (day1 == day2){
					if (start1 > start2) {
						return 1;
					} else if (start1 < start2) {
						return -1;
					} else {
						return 0;
					}
				}
				return 0;
				
//				String[] time1 = obj1.getThoiGian().split(",");
//				String thu1 = time1[0];
//				String start1 = time1[1].trim().split("-")[0];
//				String end1 = time1[1].trim().split("-")[1];
//				
//				String[] time2 = obj2.getThoiGian().split(",");
//				String thu2 = time2[0];
//				String start2 = time2[1].trim().split("-")[0];
//				String end2 = time2[1].trim().split("-")[1];
//				
//				if(thu1.compareTo(thu2) > 0){
//					return 1;
//				}else if(thu1.compareTo(thu2) < 0){
//					return -1;
//				}else{
//					if(start1.compareTo(start2) > 0){
//						return 1;
//					}else if(start1.compareTo(start2) < 0){
//						return -1;
//					}else{
//						return 0;
//					}
//				}
				
			}
		});

		/* load dữ liệu vào bảng */
		DefaultTableModel model = (DefaultTableModel) tableTKB.getModel();
		/* Xóa bảng dữ liệu trong bảng cũ */
		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		/* load lại bảng dữ liệu đã được sắp xếp lịch */
		Object[] obj = new Object[5];
		for (int i = 0; i < dsLopHocDangKy2.size(); i++) {
			obj[0] = dsLopHocDangKy2.get(i).getThoiGian().split(",")[0].trim(); // thứ
			obj[1] = dsLopHocDangKy2.get(i).getThoiGian().split(",")[1].trim(); // thời gian
			obj[2] = dsLopHocDangKy2.get(i).getTuanHoc();
			obj[3] = dsLopHocDangKy2.get(i).getPhongHoc();
			obj[4] = dsLopHocDangKy2.get(i).getIdLop();

			model.addRow(obj);
		}

	}

	public void addDataTableDangKy(LopHocPhan lopHoc, String mode) {
		DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
		Object[] obj = new Object[6];
		obj[0] = lopHoc.getIdLop();
		obj[1] = lopHoc.getTenLop();
		obj[2] = lopHoc.getIdHocPhan();
		obj[3] = lopHoc.getLoaiLop();
		obj[5] = false;

		if (mode.equalsIgnoreCase("new")) {
			obj[4] = "Chưa gửi đăng kí";
		}

		if (mode.equalsIgnoreCase("old")) {
			obj[4] = "thành công";
		}

		model.addRow(obj);
	}

	private ArrayList<LopHocPhan> loadDanhSachLopHocPhan(String fileName) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<LopHocPhan> dslopHP = new ArrayList<>();

		/* Đọc file exel dsLopHocPhan */
		FileInputStream fis;
		fis = new FileInputStream(new File(fileName));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		Row row;
		Iterator<Row> itrRow = sheet.iterator();
		/*Bỏ qua tiêu đề*/
		if(itrRow.hasNext()){
			itrRow.next();
		}
		while(itrRow.hasNext()){
			row = itrRow.next();
			
			String hocKy = row.getCell(1).getStringCellValue();
			String idLop = row.getCell(2).getStringCellValue();
			String loaiLop = row.getCell(3).getStringCellValue();
			String maHocPhan = row.getCell(4).getStringCellValue();
			String tenLop = row.getCell(5).getStringCellValue();
			String thoiGian = row.getCell(6).getStringCellValue();
			String tuanHoc = row.getCell(7).getStringCellValue();
			String phongHoc = row.getCell(8).getStringCellValue();
			String tenGiangVien = row.getCell(9).getStringCellValue();
			int soSVMax = (int) row.getCell(10).getNumericCellValue();
			int soSVHienTai = (int) row.getCell(11).getNumericCellValue();
			
			/*Đọc file ds sinh viên*/
			ArrayList<SinhVien> dsSV = loadDanhSachSinhVien(idLop);
			
			LopHocPhan lopHP = new LopHocPhan(hocKy, idLop, loaiLop, maHocPhan, tenLop, thoiGian, tuanHoc, phongHoc, dsSV,
					tenGiangVien, soSVMax, soSVHienTai);
			dslopHP.add(lopHP);
		}
		
		wb.close();
		fis.close();

		return dslopHP;
	}

	private ArrayList<SinhVien> loadDanhSachSinhVien(String idLop) throws IOException {
		// TODO Auto-generated method stub
		String path = "quanlysinhvien/danhsachhocphan/lophocphan/"+idLop+"_dsSV.xlsx";
		File file = new File(path);
		if(!file.exists()){
			return new ArrayList<>();
		}
		ArrayList<SinhVien> dsSVTC = new ArrayList<>();
		
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> itrRow = sheet.iterator();
		Row row;
		
		/*bỏ qua header*/
		if(itrRow.hasNext()){
			itrRow.next();
		}
		
		/*đọc file theo dòng*/
		while(itrRow.hasNext()){
			row = itrRow.next();
			ArrayList<String> dataSV = new ArrayList<>();
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
				dataSV.add(data);
			}
			
			if(dataSV.size() == 0){
				return new ArrayList<>();
			}
			
			SinhVien sinhVien = new SinhVien(dataSV.get(0), dataSV.get(1), dataSV.get(2), dataSV.get(3), dataSV.get(4), dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)));
			dsSVTC.add(sinhVien);
		}
		
		wb.close();
		fis.close();
		
		return dsSVTC;
	}
}
