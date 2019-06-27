package com.example.demo.model;

public class Recruitment {
	private int iD;
	private String maCV;
	private String hunter;
	private String hoVaTen;
	private String namSinh;
	private String soDT;
	private String email;
	private String truong;
	private String nganh;
	private String doiTuong;
	private String viTri;
	private String donViPV;
	private String nguoiPV;
	private String nguonCV;
	private String nguoiGT;
	private String ngayPVVong1;
	private boolean ketQuaV1;
	private String ngayPVVong2;
	private boolean ketQuaV2;
	private int ketQuaCuoi;
	private String nhanXet;
	private String note;

	public Recruitment() {
	}

	public Recruitment(String maCV, String hoVaTen, String namSinh, String soDT, String doiTuong, String viTri) {
		this.maCV = maCV;
		this.hoVaTen = hoVaTen;
		this.namSinh = namSinh;
		this.soDT = soDT;
		this.doiTuong = doiTuong;
		this.viTri = viTri;
	}

	public Recruitment(String maCV, String hunter, String hoVaTen, String namSinh, String soDT, String email, String truong, String nganh, String doiTuong, String viTri, String donViPV, String nguoiPV, String nguonCV, String nguoiGT, String ngayPVVong1, boolean ketQuaV1, String ngayPVVong2, boolean ketQuaV2, int ketQuaCuoi, String nhanXet, String note) {
		this.maCV = maCV;
		this.hunter = hunter;
		this.hoVaTen = hoVaTen;
		this.namSinh = namSinh;
		this.soDT = soDT;
		this.email = email;
		this.truong = truong;
		this.nganh = nganh;
		this.doiTuong = doiTuong;
		this.viTri = viTri;
		this.donViPV = donViPV;
		this.nguoiPV = nguoiPV;
		this.nguonCV = nguonCV;
		this.nguoiGT = nguoiGT;
		this.ngayPVVong1 = ngayPVVong1;
		this.ketQuaV1 = ketQuaV1;
		this.ngayPVVong2 = ngayPVVong2;
		this.ketQuaV2 = ketQuaV2;
		this.ketQuaCuoi = ketQuaCuoi;
		this.nhanXet = nhanXet;
		this.note = note;
	}

	public int getiD() {
		return iD;
	}

	public String getHunter() {
		return hunter;
	}

	public void setHunter(String hunter) {
		this.hunter = hunter;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public String getMaCV() {
		return maCV;
	}

	public void setMaCV(String maCV) {
		this.maCV = maCV;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(String namSinh) {
		this.namSinh = namSinh;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTruong() {
		return truong;
	}

	public void setTruong(String truong) {
		this.truong = truong;
	}

	public String getNganh() {
		return nganh;
	}

	public void setNganh(String nganh) {
		this.nganh = nganh;
	}

	public String getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}

	public String getViTri() {
		return viTri;
	}

	public void setViTri(String viTri) {
		this.viTri = viTri;
	}

	public String getDonViPV() {
		return donViPV;
	}

	public void setDonViPV(String donViPV) {
		this.donViPV = donViPV;
	}

	public String getNguoiPV() {
		return nguoiPV;
	}

	public void setNguoiPV(String nguoiPV) {
		this.nguoiPV = nguoiPV;
	}

	public String getNguonCV() {
		return nguonCV;
	}

	public void setNguonCV(String nguonCV) {
		this.nguonCV = nguonCV;
	}

	public String getNguoiGT() {
		return nguoiGT;
	}

	public void setNguoiGT(String nguoiGT) {
		this.nguoiGT = nguoiGT;
	}

	public String getNgayPVVong1() {
		return ngayPVVong1;
	}

	public void setNgayPVVong1(String ngayPVVong1) {
		this.ngayPVVong1 = ngayPVVong1;
	}

	public boolean isKetQuaV1() {
		return ketQuaV1;
	}

	public void setKetQuaV1(boolean ketQuaV1) {
		this.ketQuaV1 = ketQuaV1;
	}

	public String getNgayPVVong2() {
		return ngayPVVong2;
	}

	public void setNgayPVVong2(String ngayPVVong2) {
		this.ngayPVVong2 = ngayPVVong2;
	}

	public boolean isKetQuaV2() {
		return ketQuaV2;
	}

	public void setKetQuaV2(boolean ketQuaV2) {
		this.ketQuaV2 = ketQuaV2;
	}

	public String getNhanXet() {
		return nhanXet;
	}

	public void setNhanXet(String nhanXet) {
		this.nhanXet = nhanXet;
	}

	public int getKetQuaCuoi() {
		return ketQuaCuoi;
	}

	public void setKetQuaCuoi(int ketQuaCuoi) {
		this.ketQuaCuoi = ketQuaCuoi;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Recruitment{" +
				"iD=" + iD +
				", maCV='" + maCV + '\'' +
				", hunter='" + hunter + '\'' +
				", hoVaTen='" + hoVaTen + '\'' +
				", namSinh='" + namSinh + '\'' +
				", soDT='" + soDT + '\'' +
				", email='" + email + '\'' +
				", truong='" + truong + '\'' +
				", nganh='" + nganh + '\'' +
				", doiTuong='" + doiTuong + '\'' +
				", viTri='" + viTri + '\'' +
				", donViPV='" + donViPV + '\'' +
				", nguoiPV='" + nguoiPV + '\'' +
				", nguonCV='" + nguonCV + '\'' +
				", nguoiGT='" + nguoiGT + '\'' +
				", ngayPVVong1='" + ngayPVVong1 + '\'' +
				", ketQuaV1=" + ketQuaV1 +
				", ngayPVVong2='" + ngayPVVong2 + '\'' +
				", ketQuaV2=" + ketQuaV2 +
				", ketQuaCuoi=" + ketQuaCuoi +
				", nhanXet='" + nhanXet + '\'' +
				", note='" + note + '\'' +
				'}';
	}
}