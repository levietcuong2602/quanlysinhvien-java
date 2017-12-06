package quanlysinhvien.model;

import java.util.ArrayList;

public class QuanLyLopHocPhan {
	ArrayList<LopHocPhan> dsLopHocPhan;
	
	public QuanLyLopHocPhan() {
		// TODO Auto-generated constructor stub
		this.dsLopHocPhan = new ArrayList<>();
	}
	
	public QuanLyLopHocPhan(ArrayList<LopHocPhan> dsLopHP){
		this.dsLopHocPhan = dsLopHP;
	}
	
	
	
	public ArrayList<LopHocPhan> getDsLopHocPhan() {
		return dsLopHocPhan;
	}

	public void setDsLopHocPhan(ArrayList<LopHocPhan> dsLopHocPhan) {
		this.dsLopHocPhan = dsLopHocPhan;
	}

	public boolean themLopHocPhan(LopHocPhan lopHP){
		/*nếu đã có mã lớp trong ds lớp*/
		if(checkLopHocPhan(lopHP.getIdLop())){
			return false;
		}
		
		/*nếu không có trong ds lớp thì thêm lớp*/
		dsLopHocPhan.add(lopHP);
		return true;
	}

	public boolean checkLopHocPhan(String idLop) {
		// TODO Auto-generated method stub
		int count = dsLopHocPhan.size();
		String maLop = "";
		for(int i = 0; i < count; i++){
			maLop = dsLopHocPhan.get(i).getIdLop();
			if(idLop.equalsIgnoreCase(maLop)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkMaHocPHan(String idHocPhan){
		int count = dsLopHocPhan.size();
		String maHP = "";
		for(int i = 0; i < count; i++){
			maHP = dsLopHocPhan.get(i).getIdHocPhan();
			if(idHocPhan.equalsIgnoreCase(maHP)){
				return true;
			}
		}
		return false;
	}
	
	public boolean xoaLopHocPhan(String idLop){
		int count = dsLopHocPhan.size();
		String maLop = "";
		for(int i = 0; i < count; i++){
			maLop = dsLopHocPhan.get(i).getIdLop();
			if(idLop.equalsIgnoreCase(maLop)){
				dsLopHocPhan.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public LopHocPhan getLopPhan(String idLop){
		int count = dsLopHocPhan.size();
		String maLop = "";
		for(int i = 0; i < count; i++){
			maLop = dsLopHocPhan.get(i).getIdLop();
			if(idLop.equalsIgnoreCase(maLop)){
				return dsLopHocPhan.get(i);
			}
		}
		return null;
	}
	

}
