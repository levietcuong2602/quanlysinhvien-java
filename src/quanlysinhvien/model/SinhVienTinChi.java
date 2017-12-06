package quanlysinhvien.model;

public class SinhVienTinChi extends SinhVien{
	private int soTCQua;
	private int soTCNo;
	
	
	
	public SinhVienTinChi() {
		super();
	}
	
	public SinhVienTinChi(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, int soTCQua, int soTCNo) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.soTCQua = soTCQua;
		this.soTCNo = soTCNo;
	}

	public int getSoTCQua() {
		return soTCQua;
	}

	public void setSoTCQua(int soTCQua) {
		this.soTCQua = soTCQua;
	}

	public int getSoTCNo() {
		return soTCNo;
	}

	public void setSoTCNo(int soTCNo) {
		this.soTCNo = soTCNo;
	}

}
