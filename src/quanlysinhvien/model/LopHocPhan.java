package quanlysinhvien.model;

import java.util.ArrayList;

public class LopHocPhan {
	private String hocKy;
	private String idLop;
	private String loaiLop;
	private String idHocPhan;
	private String tenLop;
	private String thoiGian;
	private String tuanHoc;
	private String phongHoc;
	private ArrayList<SinhVien> dsSinhVien;
	private String tenGiangVien;
	private int soSVMax;
	private int soSVHienTai;
	
	
	
	public LopHocPhan() {
		dsSinhVien = new ArrayList<>();
		this.soSVMax = 80;
		this.soSVHienTai = dsSinhVien.size();
	}
	
	public LopHocPhan(String hocKy, String idLop, String loaiLop, String idHocPhan, String tenLop, String thoiGian, String tuanHoc, String phongHoc, String tenGiangVien, int soSVMax, int soSVHienTai) {
		this.hocKy = hocKy;
		this.idLop = idLop;
		this.loaiLop = loaiLop;
		this.idHocPhan = idHocPhan;
		this.tenLop = tenLop;
		this.thoiGian = thoiGian;
		this.tuanHoc = tuanHoc;
		this.phongHoc = phongHoc;
		this.tenGiangVien = tenGiangVien;
		this.soSVMax = soSVMax;
		this.soSVHienTai = soSVHienTai;
		this.dsSinhVien = new ArrayList<>();
	}
	
	
	public LopHocPhan(String hocKy, String idLop, String loaiLop, String idHocPhan, String tenLop, String thoiGian, String tuanHoc, String phongHoc, ArrayList<SinhVien> dsSinhVien, String tenGiangVien, int soSVMax, int soSVHienTai) {
		this.hocKy = hocKy;
		this.idLop = idLop;
		this.loaiLop = loaiLop;
		this.idHocPhan = idHocPhan;
		this.tenLop = tenLop;
		this.thoiGian = thoiGian;
		this.tuanHoc = tuanHoc;
		this.phongHoc = phongHoc;
		this.dsSinhVien = dsSinhVien;
		this.tenGiangVien = tenGiangVien;
		this.soSVMax = soSVMax;
		this.soSVHienTai = soSVHienTai;
	}
	
	public boolean themSinhVien(SinhVien sv) {
		if(kiemTraTrungSV(sv) == true){
			return false;
		}
		
		if(soSVHienTai < soSVMax) {
			this.dsSinhVien.add(sv);
			soSVHienTai++;
			return true;
		}else {
			return false;
		}
	}
	
	public boolean kiemTraTrungSV(SinhVien sv) {
		// TODO Auto-generated method stub
		int count = dsSinhVien.size();
		for(int i = 0; i < count; i++){
			if(sv.idSinhVien.equalsIgnoreCase(dsSinhVien.get(i).getIdSinhVien())){
				return true;
			}
		}
		
		return false;
	}

	public boolean xoaSinhVien(String idSinhVien) {
		for(int i = 0; i < dsSinhVien.size(); i++) {
			if(dsSinhVien.get(i).getIdSinhVien().equals(idSinhVien)) {
				dsSinhVien.remove(i);
				return true;
			}
		}
		return false;
	}
	

	public String getIdLop() {
		return idLop;
	}

	public void setIdLop(String idLop) {
		this.idLop = idLop;
	}

	public ArrayList<SinhVien> getDsSinhVien() {
		return dsSinhVien;
	}

	public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
		this.dsSinhVien = dsSinhVien;
	}

	public String getTenGiangVien() {
		return tenGiangVien;
	}

	public void setTenGiangVien(String tenGiangVien) {
		this.tenGiangVien = tenGiangVien;
	}

	public int getSoSVMax() {
		return soSVMax;
	}

	public void setSoSVMax(int soSVMax) {
		this.soSVMax = soSVMax;
	}

	public int getSoSVHienTai() {
		return soSVHienTai;
	}

	public void setSoSVHienTai(int soSVHienTai) {
		this.soSVHienTai = soSVHienTai;
	}

	public String getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(String thoiGian) {
		this.thoiGian = thoiGian;
	}

	public String getTuanHoc() {
		return tuanHoc;
	}

	public void setTuanHoc(String tuanHoc) {
		this.tuanHoc = tuanHoc;
	}

	public String getPhongHoc() {
		return phongHoc;
	}

	public void setPhongHoc(String phongHoc) {
		this.phongHoc = phongHoc;
	}

	public String getIdHocPhan() {
		return idHocPhan;
	}

	public void setIdHocPhan(String idHocPhan) {
		this.idHocPhan = idHocPhan;
	}

	public String getHocKy() {
		return hocKy;
	}

	public void setHocKy(String hocKy) {
		this.hocKy = hocKy;
	}

	public String getLoaiLop() {
		return loaiLop;
	}

	public void setLoaiLop(String loaiLop) {
		this.loaiLop = loaiLop;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
	
}
