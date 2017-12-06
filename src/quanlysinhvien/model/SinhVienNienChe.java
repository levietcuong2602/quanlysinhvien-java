package quanlysinhvien.model;

public class SinhVienNienChe extends SinhVien{
	private int tongSoKy;
	
	
	
	public SinhVienNienChe() {
		super();
	}
	
	public SinhVienNienChe(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, int tongSoKy) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.tongSoKy = tongSoKy;
	}

	
	public int getTongSoKy() {
		return tongSoKy;
	}

	public void setTongSoKy(int tongSoKy) {
		this.tongSoKy = tongSoKy;
	}
	
}
