package quanlysinhvien.model;

public class SinhVien {
	protected String idSinhVien;
	protected String hoTen;
	protected String khoa;
	protected String tenLop;
	protected String ngaySinh;
	protected String gioiTinh;
	protected String email;
	protected String soDT;
	protected String diaChi;
	protected double diemTB;
	
	
	
	public SinhVien() {
		
	}
	
	public SinhVien(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB) {
		this.idSinhVien = idSinhVien;
		this.hoTen = hoTen;
		this.khoa = khoa;
		this.tenLop = tenLop;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.soDT = soDT;
		this.diaChi = diaChi;
		this.diemTB = diemTB;
	}

	
	public String getIdSinhVien() {
		return idSinhVien;
	}

	public void setIdSinhVien(String idSinhVien) {
		this.idSinhVien = idSinhVien;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getKhoa() {
		return khoa;
	}

	public void setKhoa(String khoa) {
		this.khoa = khoa;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public double getDiemTB() {
		return diemTB;
	}

	public void setDiemTB(double diemTB) {
		this.diemTB = diemTB;
	}
	
}
