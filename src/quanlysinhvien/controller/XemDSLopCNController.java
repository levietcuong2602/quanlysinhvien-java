package quanlysinhvien.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.view.XemDanhSachLopCNView;

public class XemDSLopCNController {
	private XemDanhSachLopCNView xemDSLopCN;
	private JTable table;
	private ArrayList<LopChuyenNganh> dsLopCN;
	
	public XemDSLopCNController(XemDanhSachLopCNView xemDSLopCN, String idNganh) {
		this.xemDSLopCN = xemDSLopCN;
		this.table = xemDSLopCN.getTable();
		try {
			this.dsLopCN = getLopCN(idNganh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsLopCN = new ArrayList<>();
			e.printStackTrace();
		}
		xemDSLopCN.loadData(table, dsLopCN);
	}
	
	private ArrayList<LopChuyenNganh> getLopCN(String idNganh) throws IOException{
		ArrayList<LopChuyenNganh> dsLopCN = new ArrayList<>();
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
			Cell cell = nextRow.getCell(4);
			String idN = cell.getStringCellValue();
			if(idN.equals(idNganh)) {
				cell = nextRow.getCell(1);
				String idLopChuyenNganh = cell.getStringCellValue();
				cell = nextRow.getCell(2);
				String tenLop = cell.getStringCellValue();
				cell = nextRow.getCell(3);
				String tenChuNhiem = cell.getStringCellValue();
				cell = nextRow.getCell(5);
				String tenNganh = cell.getStringCellValue();
				LopChuyenNganh lopCN = new LopChuyenNganh(null, idLopChuyenNganh, tenLop, tenChuNhiem, idNganh, tenNganh);
				dsLopCN.add(lopCN);
			}
		}

		workbook.close();
		inputStream.close();
		return dsLopCN;
	}
}
