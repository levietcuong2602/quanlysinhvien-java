package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.view.PanelDangKiTotNghiepView;

public class DangKiTotNghiepController {
	private JLabel labDiemTB, labTongSoTC, labSoTCNo, labTongSoKy, labDiemTB1;
	private JButton btnDangKy;
	private PanelDangKiTotNghiepView dkTotNghiep;
	private String idSV;
	public DangKiTotNghiepController(PanelDangKiTotNghiepView dkTN, String idSV) {
		this.dkTotNghiep = dkTN;
		this.labDiemTB = dkTN.getLabDiemTB();
		this.labTongSoTC = dkTN.getLabTongSoTC();
		this.labSoTCNo = dkTN.getLabSoTCNo();
		this.labTongSoKy = dkTN.getLabTongSoKy();
		this.labDiemTB1 = dkTN.getLabDiemTB1();
		this.btnDangKy = dkTN.getBtnDangKy();
		this.idSV = idSV;
		
		addEvents();
	}
	
	private void addEvents() {
		// TODO Auto-generated method stub
		btnDangKy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ArrayList<DiemHocPhan> dsDiem;
				try {
					dsDiem = loadBangDiemHocPhan();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					dsDiem = new ArrayList<>();
					e.printStackTrace();
				}
				XetTotNghiep(dsDiem);
			}
		});
	}

	public ArrayList<DiemHocPhan> loadBangDiemHocPhan() throws IOException{
		ArrayList<DiemHocPhan>dsDiem = new ArrayList<DiemHocPhan>();
		
		File file = new File ("quanlysinhvien/sinhvientinchi/"+idSV+"/diem.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i <= rowCount; i++) {
			DiemHocPhan diem = new DiemHocPhan();
			Row row = sheet.getRow(i);
			Cell cell;
			
			cell = row.getCell(1);
			diem.setHocKy(cell.getStringCellValue());
			
			cell = row.getCell(2);
			diem.setIdHocPhan(cell.getStringCellValue());
			
			cell = row.getCell(3);
			diem.setTenHP(cell.getStringCellValue());
			
			cell = row.getCell(4);
			diem.setTinChi((int) cell.getNumericCellValue());
			
			//lop hoc
			cell = row.getCell(5);
			
			cell = row.getCell(6);
			diem.setDiemQT((double) cell.getNumericCellValue());
			
			cell = row.getCell(7);
			diem.setDiemThi((double) cell.getNumericCellValue());
			
			cell = row.getCell(8);
			diem.setDiemChu(cell.getStringCellValue());
			
			switch (diem.getDiemChu()) {
			case "A+":
				diem.setDiemThang4(4);
				break;
			case "A":
				diem.setDiemThang4(4);
				break;
			case "B+":
				diem.setDiemThang4(3.5);
				break;
			case "B":
				diem.setDiemThang4(3);
				break;
			case "C+":
				diem.setDiemThang4(2.5);
				break;
			case "C":
				diem.setDiemThang4(2);
				break;
			case "D+":
				diem.setDiemThang4(1.5);
				break;
			case "D":
				diem.setDiemThang4(1);
				break;
			case "F":
				diem.setDiemThang4(0);
				break;

			default:
				break;
			}
			
			dsDiem.add(diem);
		}
		
		return dsDiem;
	}
	
	private boolean XetTotNghiep(ArrayList<DiemHocPhan> dsDiem) {
		int i = 0;
		float tong = 0;
		int TCTichLuy = 0;
		int TCNo = 0;
		int TCDK = 0;
		float trinhDo = 1;
		float CPA = 0;
		while (true) {
			if (i >= dsDiem.size()) break;
			int begin = i;
			trinhDo += 1;
			String hocky = dsDiem.get(i).getHocKy();
			float GPA = 0;
			int TCQua = 0;
			
			for (int j = i+1; j < dsDiem.size(); j++) {
				if (dsDiem.get(j).getHocKy().equals(dsDiem.get(i).getHocKy())) i++; 
			}
			for (int j = begin; j <= i; j++) {
				if (dsDiem.get(j).getHocKy().equals(hocky)) {
					GPA += dsDiem.get(j).getDiemThang4()*dsDiem.get(j).getTinChi();
					tong += dsDiem.get(j).getDiemThang4()*dsDiem.get(j).getTinChi();
					TCQua += dsDiem.get(j).getTinChi();
					TCTichLuy += dsDiem.get(j).getTinChi();
					TCDK += dsDiem.get(j).getTinChi();
				}
				else {
					tong += dsDiem.get(j).getDiemThang4()*dsDiem.get(j).getTinChi();
					TCTichLuy += dsDiem.get(j).getTinChi();
					TCDK += dsDiem.get(j).getTinChi();
				}

			}
			
			GPA = GPA/TCQua;
			CPA = tong/TCTichLuy;
			
			i++;
		}
		
		/*Xét điều kiện tốt nghiệp*/
		if(TCTichLuy >= 165 && TCNo == 0 && CPA >= 2.0){
			if(CPA >= 3.8){
				JOptionPane.showMessageDialog(null, "Bạn đã đăng kí tốt nghiệp thành công\n."
						+ "Xếp loại XUẤT SẮC");
			}else if(CPA >= 3.5){
				JOptionPane.showMessageDialog(null, "Bạn đã đăng kí tốt nghiệp thành công\n."
						+ "Xếp loại GIỎI");
			}else if(CPA >= 2.5){
				JOptionPane.showMessageDialog(null, "Bạn đã đăng kí tốt nghiệp thành công\n."
						+ "Xếp loại KHÁ");
			}else if(CPA >= 2.0){
				JOptionPane.showMessageDialog(null, "Bạn đã đăng kí tốt nghiệp thành công\n."
						+ "Xếp loại TRUNG BÌNH");
			}
			
			return true;
		}else{
			JOptionPane.showMessageDialog(null, "Số tín chỉ tích lũy = "+TCTichLuy
					+"\nTín chỉ nợ = "+TCNo+"\n CPA = "+CPA+"\nBạn không đủ điều kiện tốt nghiệp\n."
					+ "TC tích lũy >= 165, TC nợ = 0 , CPA >= 2.0");
			return false;
		}
	}
}
