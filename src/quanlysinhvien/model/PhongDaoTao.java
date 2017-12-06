package quanlysinhvien.model;

import java.util.ArrayList;

public class PhongDaoTao {
	private ArrayList<HocPhan> dsHocPhan;
	private ArrayList<LopHocPhan> dsLopHocPhan;
	private ArrayList<Khoa_Vien> dsKhoa_Vien;
	private ArrayList<LopChuyenNganh> dsLopChuyenNganh;
	
	
	
	public PhongDaoTao() {
		this.dsHocPhan = new ArrayList<>();
		this.dsLopHocPhan = new ArrayList<>();
		this.dsKhoa_Vien = new ArrayList<>();
	}
	
	public PhongDaoTao(ArrayList<HocPhan> dsHocPhan, ArrayList<LopHocPhan> dsLopHocPhan, 
			ArrayList<Khoa_Vien> dsKhoa_Vien, ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsHocPhan = dsHocPhan;
		this.dsLopHocPhan = dsLopHocPhan;
		this.dsKhoa_Vien = dsKhoa_Vien;
		this.dsLopChuyenNganh = dsLopChuyenNganh;
	}

	
	public ArrayList<HocPhan> getDsHocPhan() {
		return dsHocPhan;
	}

	public void setDsHocPhan(ArrayList<HocPhan> dsHocPhan) {
		this.dsHocPhan = dsHocPhan;
	}

	public ArrayList<LopHocPhan> getDsLopHocPhan() {
		return dsLopHocPhan;
	}

	public void setDsLopHocPhan(ArrayList<LopHocPhan> dsLopHocPhan) {
		this.dsLopHocPhan = dsLopHocPhan;
	}

	public ArrayList<Khoa_Vien> getDsKhoa_Vien() {
		return dsKhoa_Vien;
	}

	public void setDsKhoa_Vien(ArrayList<Khoa_Vien> dsKhoa_Vien) {
		this.dsKhoa_Vien = dsKhoa_Vien;
	}

	public ArrayList<LopChuyenNganh> getDsLopChuyenNganh() {
		return dsLopChuyenNganh;
	}

	public void setDsLopChuyenNganh(ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsLopChuyenNganh = dsLopChuyenNganh;
	}
	
}
