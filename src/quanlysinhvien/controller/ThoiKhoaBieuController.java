package quanlysinhvien.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.view.PanelTKBView;

class SapXepTKB implements Comparator<LopHocPhan>{

	@Override
	public int compare(LopHocPhan o1, LopHocPhan o2) {
		// TODO Auto-generated method stub
		String thoiGian1 = o1.getThoiGian();
		String thoiGian2 = o2.getThoiGian();
		if(thoiGian1.compareTo(thoiGian2) > 0){
			   return 1;
		}else if(thoiGian1.compareTo(thoiGian2) == 0){
			   return 0;
		}else{
			   return -1;
		}
	}
	
}

public class ThoiKhoaBieuController {
	private PanelTKBView tkb;
	private JTable table;
	private JLabel labStatus;
	private ArrayList<LopHocPhan> dsLopHP;
	
	public ThoiKhoaBieuController(PanelTKBView tkb, String fileName) {
		this.tkb = tkb;
		this.table = tkb.getTable();
		this.labStatus = tkb.getLabStatus();
		try {
			dsLopHP = getDSLopHP(fileName);
			if(dsLopHP.size() != 0) Collections.sort(dsLopHP, new SapXepTKB());
			if(dsLopHP.size() == 0) labStatus.setText("Sinh viên không có thời khóa biểu kì này");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsLopHP = new ArrayList<>();
			labStatus.setText("Sinh viên không có thời khóa biểu kì này");
			System.out.println("Error tkb: " + e);
		}
		this.tkb.loadData(table, dsLopHP);
	}
	
	private ArrayList<LopHocPhan> getDSLopHP(String fileName) throws IOException{
		ArrayList<LopHocPhan> dsLopHP = new ArrayList<>();
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
			ArrayList<String> dataLopHP = new ArrayList<>();
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
				dataLopHP.add(data);
				if(dataLopHP.size() < 1) return null;
			}
			if(dataLopHP.size() > 0) {
				LopHocPhan lopHocPhan = new LopHocPhan(dataLopHP.get(0), dataLopHP.get(1), dataLopHP.get(2), dataLopHP.get(3), dataLopHP.get(4), 
						dataLopHP.get(5), dataLopHP.get(6), dataLopHP.get(7), null, dataLopHP.get(8), (int) Double.parseDouble(dataLopHP.get(9)), (int) Double.parseDouble(dataLopHP.get(10)));
				dsLopHP.add(lopHocPhan);
			}
		}

		workbook.close();
		inputStream.close();
		return dsLopHP;
	}
	
}
