package quanlysinhvien.model;

import java.util.ArrayList;

public class QuanLyHocPhan {
	private int soTCHienTai;
	private ArrayList<HocPhan> dsHocPhan;
	
	public QuanLyHocPhan() {
		// TODO Auto-generated constructor stub
		this.dsHocPhan = new ArrayList<>();
		this.soTCHienTai = 0;
	}
	
	public QuanLyHocPhan(ArrayList<HocPhan> dsHocPhan) {
		super();
		this.dsHocPhan = dsHocPhan;
		this.soTCHienTai = tongTinChi();
	}

	private int tongTinChi() {
		// TODO Auto-generated method stub
		int soTC = 0;
		int count = dsHocPhan.size();
		for(int i = 0; i < count; i++){
			soTC += dsHocPhan.get(i).getSoTinChi();
		}
		return soTC;
	}

	public int getSoTCHienTai() {
		return soTCHienTai;
	}

	public void setSoTCHienTai(int soTCHienTai) {
		this.soTCHienTai = soTCHienTai;
	}

	public ArrayList<HocPhan> getDsHocPhan() {
		return dsHocPhan;
	}

	public void setDsHocPhan(ArrayList<HocPhan> dsHocPhan) {
		this.dsHocPhan = dsHocPhan;
	}
	
	public boolean themHocPhan(HocPhan hp){
		if(checkHocPhan(hp.getIdHocPhan())){
			System.out.println("trung maHocPhan");
			return false;
		}
		
		dsHocPhan.add(hp);
		soTCHienTai += hp.getSoTinChi();
		return true;
	}

	public boolean xoaHocPhan(HocPhan hp){
		int count = dsHocPhan.size();
		for(int i = 0; i < count; i++){
			if(hp.getIdHocPhan().equalsIgnoreCase(dsHocPhan.get(i).getIdHocPhan())){
				dsHocPhan.remove(i);
				soTCHienTai -= hp.getSoTinChi();
				return true;
			}
		}
		System.out.println("không tìm thấy mã học phần");
		return false;
	}
	
	public boolean checkHocPhan(String idHocPhan) {
		// TODO Auto-generated method stub
		int count = dsHocPhan.size();
		for(int i = 0; i < count; i++){
			if(idHocPhan.equalsIgnoreCase(dsHocPhan.get(i).getIdHocPhan())){
				return true;
			}
		}
		return false;
	}

	public HocPhan getHocPhan(String idHocPhan){
		int count = dsHocPhan.size();
		String idHP = "";
		for(int i = 0; i < count; i++){
			idHP = dsHocPhan.get(i).getIdHocPhan();
			if(idHocPhan.equalsIgnoreCase(idHP)){
				return dsHocPhan.get(i);
			}
		}
		
		return null;
	}
}
