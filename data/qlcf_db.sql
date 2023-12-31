CREATE DATABASE qlcf_db;
USE qlcf_db;
DROP DATABASE qlcf_db;
CREATE TABLE NHANVIEN (
	MANV varchar(10) NOT NULL PRIMARY KEY,
    HOTEN varchar(50),
    GIOITINH enum('Nam', 'Nu'),
    NGAYSINH date,
    SDT varchar(15),
    DIACHI varchar(255)
);

CREATE TABLE THUCUONG (
	MATU varchar(10) NOT NULL PRIMARY KEY,
    TENTU varchar(50),
    LOAI enum("Cafe", "Sinh Tố", "Nước Giải Khát", "Trà", "Đồ Uống"),
    DVT enum('Chai', 'Lon', 'Ly'),
    SOLUONG int,
    GIA long
);


CREATE TABLE HOADON (
	MAHD varchar(10) NOT NULL PRIMARY KEY,
    THOIGIAN time,
    NGAY date,
    MANV varchar(10),
    SOBAN varchar(2)
);

CREATE TABLE CHITIETHOADON (
	MAHD varchar(10) NOT NULL,
    MATU varchar(10),
    SOLUONG int,
    GIA long
);

CREATE TABLE ACCOUNT (
	TAIKHOAN varchar(50) NOT NULL PRIMARY KEY,
    MATKHAU varchar(50) NOT NULL,
    MANV varchar(10),
    LOAI enum("Nhan vien", "Admin")
);

CREATE TABLE DATTRUOC (	
	MADT varchar(10) NOT NULL PRIMARY KEY,
    TENKH varchar(50),
    SDT varchar(15),
    SOBAN varchar(2),
    TGBATDAU time,
    TGKETTHUC time,
    NGAY date,
    THANHTOAN enum("Co", "Khong"),
    GHICHU varchar(256)
);

CREATE TABLE NUMGENERATE (
	G_HOADON int,
    G_NHANVIEN int,
    G_DATTRUOC int,
    G_THUCUONG int
);

ALTER TABLE CHITIETHOADON
ADD FOREIGN KEY (MAHD) REFERENCES HOADON(MAHD),
ADD FOREIGN KEY (MATU) REFERENCES THUCUONG(MATU);

ALTER TABLE HOADON
ADD FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV);

ALTER TABLE ACCOUNT
ADD FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV) ON DELETE CASCADE;
