package quanlysinhvien.model;

public class HocPhan {
	private String idHocPhan, ngayDangKy;
	private String tenHP;
	private int soTinChi;
	private double soTCHocPhi;
	private String idNganh;
	private double trongSo;
	
	
	
	public HocPhan() {
		
	}
	
	public HocPhan(String idHocPhan, String tenHP, int soTinChi, double soTCHocPhi, String idNganh, double trongSo) {
		this.idHocPhan = idHocPhan;
		this.tenHP = tenHP;
		this.soTinChi = soTinChi;
		this.soTCHocPhi = soTCHocPhi;
		this.idNganh = idNganh;
		this.trongSo = trongSo;
	}
	
	public HocPhan(String idHocPhan, String tenHP, int soTinChi, double soTCHocPhi, String idNganh, double trongSo, String ngayDangKy) {
		this.idHocPhan = idHocPhan;
		this.tenHP = tenHP;
		this.soTinChi = soTinChi;
		this.soTCHocPhi = soTCHocPhi;
		this.idNganh = idNganh;
		this.trongSo = trongSo;
		this.ngayDangKy = ngayDangKy;
	}
	
	public HocPhan(String idHocPhan, String tenHP, int soTinChi, String ngayDangKy) {
		this.idHocPhan = idHocPhan;
		this.tenHP = tenHP;
		this.soTinChi = soTinChi;
		this.ngayDangKy = ngayDangKy;
	}
	

	public String getIdHocPhan() {
		return idHocPhan;
	}

	public void setIdHocPhan(String idHocPhan) {
		this.idHocPhan = idHocPhan;
	}

	public String getTenHP() {
		return tenHP;
	}

	public void setTenHP(String tenHP) {
		this.tenHP = tenHP;
	}

	public int getSoTinChi() {
		return soTinChi;
	}

	public void setSoTinChi(int soTinChi) {
		this.soTinChi = soTinChi;
	}

	public double getSoTCHocPhi() {
		return soTCHocPhi;
	}

	public void setSoTCHocPhi(double soTCHocPhi) {
		this.soTCHocPhi = soTCHocPhi;
	}

	public String getIdNganh() {
		return idNganh;
	}

	public void setIdNganh(String idNganh) {
		this.idNganh = idNganh;
	}

	public double getTrongSo() {
		return trongSo;
	}

	public void setTrongSo(double trongSo) {
		this.trongSo = trongSo;
	}

	public String getNgayDangKy() {
		return ngayDangKy;
	}

	public void setNgayDangKy(String ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}
	
}
