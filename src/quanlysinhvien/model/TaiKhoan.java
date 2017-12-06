package quanlysinhvien.model;

public class TaiKhoan {
	private String taiKhoan;
	private String matKhau;
	private String loaiTK;
	
	public TaiKhoan() {
		
	}
	
	public TaiKhoan(String taiKhoan, String matKhau, String loaiTK) {
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		this.loaiTK = loaiTK;
	}
	
	public boolean check(TaiKhoan tk) {
		if(this.taiKhoan.equalsIgnoreCase(tk.getTaiKhoan()) && this.matKhau.equals(tk.getMatKhau())) return true;
		else return false;
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getLoaiTK() {
		return loaiTK;
	}

	public void setLoaiTK(String loaiTK) {
		this.loaiTK = loaiTK;
	}
	
}
