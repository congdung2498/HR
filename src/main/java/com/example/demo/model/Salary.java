package com.example.demo.model;

public class Salary {
    private int iD;
    private String hoVaTen;
    private String maNV;
    private String thang;
    private double luongChucDanh;
    private double luongThamNien;
    private double luongLePhep;
    private double boiDuongTruc;
    private double dieuChinhBoSung;
    private double phuCapDoanThe;
    private double phuCapAnCa;
    private double phuCapDienThoai;
    private double tongThuNhap;
    private double BHXH;
    private double BHYT;
    private double BHTN;
    private double kinhPhiCongDoan;
    private double thueTNCN;
    private double tongKhauTru;
    private double soTienChuyenKhoan;
    private double congCheDo;
    private double congThucTe;
    private double ngayNghiLePhep;
    private double ngayTruc;
    private String KI;

    public Salary() {
    }

    public Salary(int id,String hoVaTen, String thang, double luongChucDanh, double luongThamNien, double luongLePhep, double boiDuongTruc, double dieuChinhBoSung, double phuCapDoanThe, double phuCapAnCa, double phuCapDienThoai, double tongThuNhap, double BHXH, double BHYT, double BHTN, double kinhPhiCongDoan, double thueTNCN, double tongKhauTru, double soTienChuyenKhoan, double congCheDo, double congThucTe, double ngayNghiLePhep, double ngayTruc, String KI) {
        this.iD = id;
        this.hoVaTen = hoVaTen;
        this.thang = thang;
        this.luongChucDanh = luongChucDanh;
        this.luongThamNien = luongThamNien;
        this.luongLePhep = luongLePhep;
        this.boiDuongTruc = boiDuongTruc;
        this.dieuChinhBoSung = dieuChinhBoSung;
        this.phuCapDoanThe = phuCapDoanThe;
        this.phuCapAnCa = phuCapAnCa;
        this.phuCapDienThoai = phuCapDienThoai;
        this.tongThuNhap = tongThuNhap;
        this.BHXH = BHXH;
        this.BHYT = BHYT;
        this.BHTN = BHTN;
        this.kinhPhiCongDoan = kinhPhiCongDoan;
        this.thueTNCN = thueTNCN;
        this.tongKhauTru = tongKhauTru;
        this.soTienChuyenKhoan = soTienChuyenKhoan;
        this.congCheDo = congCheDo;
        this.congThucTe = congThucTe;
        this.ngayNghiLePhep = ngayNghiLePhep;
        this.ngayTruc = ngayTruc;
        this.KI = KI;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public double getLuongChucDanh() {
        return luongChucDanh;
    }

    public void setLuongChucDanh(double luongChucDanh) {
        this.luongChucDanh = luongChucDanh;
    }

    public double getLuongThamNien() {
        return luongThamNien;
    }

    public void setLuongThamNien(double luongThamNien) {
        this.luongThamNien = luongThamNien;
    }

    public double getLuongLePhep() {
        return luongLePhep;
    }

    public void setLuongLePhep(double luongLePhep) {
        this.luongLePhep = luongLePhep;
    }

    public double getBoiDuongTruc() {
        return boiDuongTruc;
    }

    public void setBoiDuongTruc(double boiDuongTruc) {
        this.boiDuongTruc = boiDuongTruc;
    }

    public double getDieuChinhBoSung() {
        return dieuChinhBoSung;
    }

    public void setDieuChinhBoSung(double dieuChinhBoSung) {
        this.dieuChinhBoSung = dieuChinhBoSung;
    }

    public double getPhuCapDoanThe() {
        return phuCapDoanThe;
    }

    public void setPhuCapDoanThe(double phuCapDoanThe) {
        this.phuCapDoanThe = phuCapDoanThe;
    }

    public double getPhuCapAnCa() {
        return phuCapAnCa;
    }

    public void setPhuCapAnCa(double phuCapAnCa) {
        this.phuCapAnCa = phuCapAnCa;
    }

    public double getPhuCapDienThoai() {
        return phuCapDienThoai;
    }

    public void setPhuCapDienThoai(double phuCapDienThoai) {
        this.phuCapDienThoai = phuCapDienThoai;
    }

    public double getTongThuNhap() {
        return tongThuNhap;
    }

    public void setTongThuNhap(double tongThuNhap) {
        this.tongThuNhap = tongThuNhap;
    }

    public double getBHXH() {
        return BHXH;
    }

    public void setBHXH(double BHXH) {
        this.BHXH = BHXH;
    }

    public double getBHYT() {
        return BHYT;
    }

    public void setBHYT(double BHYT) {
        this.BHYT = BHYT;
    }

    public double getBHTN() {
        return BHTN;
    }

    public void setBHTN(double BHTN) {
        this.BHTN = BHTN;
    }

    public double getKinhPhiCongDoan() {
        return kinhPhiCongDoan;
    }

    public void setKinhPhiCongDoan(double kinhPhiCongDoan) {
        this.kinhPhiCongDoan = kinhPhiCongDoan;
    }

    public double getThueTNCN() {
        return thueTNCN;
    }

    public void setThueTNCN(double thueTNCN) {
        this.thueTNCN = thueTNCN;
    }

    public double getTongKhauTru() {
        return tongKhauTru;
    }

    public void setTongKhauTru(double tongKhauTru) {
        this.tongKhauTru = tongKhauTru;
    }

    public double getSoTienChuyenKhoan() {
        return soTienChuyenKhoan;
    }

    public void setSoTienChuyenKhoan(double soTienChuyenKhoan) {
        this.soTienChuyenKhoan = soTienChuyenKhoan;
    }

    public double getCongCheDo() {
        return congCheDo;
    }

    public void setCongCheDo(double congCheDo) {
        this.congCheDo = congCheDo;
    }

    public double getCongThucTe() {
        return congThucTe;
    }

    public void setCongThucTe(double congThucTe) {
        this.congThucTe = congThucTe;
    }

    public double getNgayNghiLePhep() {
        return ngayNghiLePhep;
    }

    public void setNgayNghiLePhep(double ngayNghiLePhep) {
        this.ngayNghiLePhep = ngayNghiLePhep;
    }

    public double getNgayTruc() {
        return ngayTruc;
    }

    public void setNgayTruc(double ngayTruc) {
        this.ngayTruc = ngayTruc;
    }

    public String getKI() {
        return KI;
    }

    public void setKI(String KI) {
        this.KI = KI;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "iD=" + iD +
                ", thang='" + thang + '\'' +
                ", luongChucDanh=" + luongChucDanh +
                ", luongThamNien=" + luongThamNien +
                ", luongLePhep=" + luongLePhep +
                ", boiDuongTruc=" + boiDuongTruc +
                ", dieuChinhBoSung=" + dieuChinhBoSung +
                ", phuCapDoanThe=" + phuCapDoanThe +
                ", phuCapAnCa=" + phuCapAnCa +
                ", phuCapDienThoai=" + phuCapDienThoai +
                ", tongThuNhap=" + tongThuNhap +
                ", BHXH=" + BHXH +
                ", BHYT=" + BHYT +
                ", BHTN=" + BHTN +
                ", kinhPhiCongDoan=" + kinhPhiCongDoan +
                ", thueTNCN=" + thueTNCN +
                ", tongKhauTru=" + tongKhauTru +
                ", soTienChuyenKhoan=" + soTienChuyenKhoan +
                ", congCheDo=" + congCheDo +
                ", congThucTe=" + congThucTe +
                ", ngayNghiLePhep=" + ngayNghiLePhep +
                ", ngayTruc=" + ngayTruc +
                ", KI='" + KI + '\'' +
                ", maNV='" + maNV + '\'' +
                '}';
    }
}