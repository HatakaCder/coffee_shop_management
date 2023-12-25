USE qlcf_db;

INSERT INTO NHANVIEN (MANV, HOTEN, GIOITINH, NGAYSINH, SDT, DIACHI)
VALUES
('admin', null, null, null, null, null),
('NV001', 'Nguyễn Văn A', 'Nam', '1990-01-15', '0901234567', '123 Đường ABC, Quận 1'),
('NV002', 'Trần Thị B', 'Nu', '1995-05-20', '0987654321', '456 Đường XYZ, Quận 2'),
('NV003', 'Lê Văn C', 'Nam', '1988-12-10', '0912345678', '789 Đường MNO, Quận 3'),
('NV004', 'Phạm Thị D', 'Nu', '1992-07-25', '0978123456', '101 Đường PQR, Quận 4'),
('NV005', 'Hoàng Văn E', 'Nam', '1998-03-05', '0965432109', '202 Đường LMN, Quận 5');

INSERT INTO ACCOUNT (TAIKHOAN, MATKHAU, MANV, LOAI)
VALUES
('account_nv001', 'password_nv001', 'NV001', 'Nhan vien'),
('account_nv002', 'password_nv002', 'NV002', 'Nhan vien'),
('account_nv003', 'password_nv003', 'NV003', 'Nhan vien'),
('account_nv004', 'password_nv004', 'NV004', 'Nhan vien'),
('account_nv005', 'password_nv005', 'NV005', 'Nhan vien'),
('admin', 'admin', 'admin', 'Admin');

INSERT INTO DATTRUOC (MADT, TENKH, SDT, SOBAN, TGBATDAU, TGKETTHUC, NGAY, THANHTOAN, GHICHU)
VALUES 
('DT001', 'Nguyen Van A', '0123456789', '1', '12:00:00', '14:00:00', '2023-01-01', 'Co', 'Ghi chu 1'),
('DT002', 'Tran Thi B', '0987654321', '3', '18:30:00', '20:30:00', '2023-02-15', 'Khong', 'Ghi chu 2'),
('DT003', 'Le Van C', '0369852147', '5', '15:45:00', '17:45:00', '2023-03-10', 'Co', 'Ghi chu 3'),
('DT004', 'Pham Thi D', '0712345678', '2', '20:00:00', '22:00:00', '2023-04-05', 'Khong', 'Ghi chu 4'),
('DT005', 'Hoang Van E', '0932145678', '1', '14:30:00', '16:30:00', '2023-05-20', 'Co', 'Ghi chu 5'),
('DT006', 'Do Thi F', '0765432198', '3', '17:00:00', '19:00:00', '2023-06-12', 'Khong', 'Ghi chu 6'),
('DT007', 'Nguyen Van G', '0854321765', '3', '19:30:00', '21:30:00', '2023-07-18', 'Co', 'Ghi chu 7'),
('DT008', 'Tran Van H', '0943215678', '2', '16:15:00', '18:15:00', '2023-08-23', 'Khong', 'Ghi chu 8'),
('DT009', 'Le Thi I', '0654789321', '1', '13:45:00', '15:45:00', '2023-09-30', 'Co', 'Ghi chu 9'),
('DT010', 'Pham Van J', '0321547896', '4', '21:00:00', '23:00:00', '2023-10-15', 'Khong', 'Ghi chu 10');

INSERT INTO NUMGENERATE VALUES (1, 11, 11, 31);

INSERT INTO THUCUONG (MATU, TENTU, LOAI, DVT, SOLUONG, GIA)
VALUES
    ('TU001', 'Cà Phê Đen', 'Cafe', 'Ly', 50, 15000),
    ('TU002', 'Sinh Tố Dưa Hấu', 'Sinh Tố', 'Ly', 30, 25000),
    ('TU003', 'Nước Ngọt Coca Cola', 'Nước Giải Khát', 'Lon', 100, 12000),
    ('TU004', 'Trà Chanh', 'Trà', 'Ly', 40, 18000),
    ('TU005', 'Nước Ép Cam', 'Nước Giải Khát', 'Chai', 25, 22000),
    ('TU006', 'Cà Phê Sữa Đá', 'Cafe', 'Ly', 60, 18000),
    ('TU007', 'Sinh Tố Bơ', 'Sinh Tố', 'Ly', 35, 28000),
    ('TU008', 'Nước Ngọt Pepsi', 'Nước Giải Khát', 'Lon', 80, 13000),
    ('TU009', 'Trà Oolong', 'Trà', 'Ly', 45, 20000),
    ('TU010', 'Nước Ép Dưa Lưới', 'Nước Giải Khát', 'Chai', 20, 25000),
    ('TU011', 'Cà Phê Sữa Nóng', 'Cafe', 'Ly', 40, 16000),
    ('TU012', 'Sinh Tố Dừa', 'Sinh Tố', 'Ly', 25, 30000),
    ('TU013', 'Nước Ngọt Sprite', 'Nước Giải Khát', 'Lon', 60, 14000),
    ('TU014', 'Trà Đào', 'Trà', 'Ly', 50, 21000),
    ('TU015', 'Nước Ép Lựu', 'Nước Giải Khát', 'Chai', 30, 27000),
    ('TU016', 'Cà Phê Phin', 'Cafe', 'Ly', 55, 19000),
    ('TU017', 'Sinh Tố Xoài', 'Sinh Tố', 'Ly', 40, 26000),
    ('TU018', 'Nước Ngọt Fanta', 'Nước Giải Khát', 'Lon', 70, 15000),
    ('TU019', 'Trà Xanh', 'Trà', 'Ly', 35, 22000),
    ('TU020', 'Nước Ép Lê', 'Nước Giải Khát', 'Chai', 18, 23000),
    ('TU021', 'Cà Phê Americano', 'Cafe', 'Ly', 48, 17000),
    ('TU022', 'Sinh Tố Dâu', 'Sinh Tố', 'Ly', 32, 28000),
    ('TU023', 'Nước Ngọt Mirinda', 'Nước Giải Khát', 'Lon', 90, 16000),
    ('TU024', 'Trà Lài', 'Trà', 'Ly', 42, 20000),
    ('TU025', 'Nước Ép Táo', 'Nước Giải Khát', 'Chai', 22, 26000),
    ('TU026', 'Cà Phê Espresso', 'Cafe', 'Ly', 50, 20000),
    ('TU027', 'Sinh Tố Kiwi', 'Sinh Tố', 'Ly', 28, 30000),
    ('TU028', 'Nước Ngọt 7UP', 'Nước Giải Khát', 'Lon', 65, 14000),
    ('TU029', 'Trà Ô Long', 'Trà', 'Ly', 38, 21000),
    ('TU030', 'Nước Ép Dừa', 'Nước Giải Khát', 'Chai', 15, 28000);
    
INSERT INTO HOADON (MAHD, THOIGIAN, NGAY, MANV, SOBAN)
VALUES
    ('HD001', '12:30:00', '2023-01-01', 'NV001', '1'),
    ('HD002', '13:45:00', '2023-01-02', 'NV002', '2'),
    ('HD003', '15:20:00', '2023-01-03', 'NV003', '3'),
    ('HD004', '18:10:00', '2023-01-04', 'NV001', '4'),
    ('HD005', '20:00:00', '2023-01-05', 'NV002', '5'),
    ('HD006', '14:30:00', '2023-01-06', 'NV003', '6'),
    ('HD007', '16:45:00', '2023-01-07', 'NV001', '7'),
    ('HD008', '19:15:00', '2023-01-08', 'NV002', '8'),
    ('HD009', '21:30:00', '2023-01-09', 'NV003', '9'),
    ('HD010', '12:00:00', '2023-01-10', 'NV001', '10');

INSERT INTO CHITIETHOADON (MAHD, MATU, SOLUONG, GIA)
VALUES
    ('HD001', 'TU001', 2, 30000),
    ('HD001', 'TU002', 1, 25000),
    ('HD001', 'TU005', 3, 22000),
    ('HD002', 'TU003', 2, 12000),
    ('HD002', 'TU006', 1, 18000),
    ('HD002', 'TU009', 1, 20000),
    ('HD003', 'TU007', 3, 28000),
    ('HD003', 'TU010', 2, 25000),
    ('HD003', 'TU013', 1, 14000),
    ('HD004', 'TU014', 2, 21000),
    ('HD004', 'TU017', 1, 26000),
    ('HD004', 'TU020', 3, 23000),
    ('HD005', 'TU021', 2, 17000),
    ('HD005', 'TU024', 1, 20000),
    ('HD005', 'TU027', 1, 30000),
    ('HD006', 'TU025', 3, 26000),
    ('HD006', 'TU028', 2, 14000),
    ('HD006', 'TU030', 1, 28000),
    ('HD007', 'TU002', 2, 25000),
    ('HD007', 'TU005', 1, 22000),
    ('HD007', 'TU008', 3, 13000),
    ('HD008', 'TU010', 2, 25000),
    ('HD008', 'TU013', 1, 14000),
    ('HD008', 'TU016', 1, 19000),
    ('HD009', 'TU018', 3, 15000),
    ('HD009', 'TU021', 2, 21000),
    ('HD009', 'TU024', 1, 20000),
    ('HD009', 'TU026', 1, 20000),
    ('HD010', 'TU030', 2, 28000),
    ('HD010', 'TU001', 1, 15000),
    ('HD010', 'TU007', 2, 28000);

SELECT MONTH(T.NGAY) AS MONTH, SUM(TONGGIATRIHOADON) AS TONGGIATRIHOADON
FROM (
	SELECT HOADON.MAHD, HOADON.MANV, HOADON.THOIGIAN, HOADON.NGAY, SUM(CHITIETHOADON.GIA) AS TONGGIATRIHOADON 
    FROM HOADON 
    JOIN CHITIETHOADON ON CHITIETHOADON.MAHD = HOADON.MAHD
    GROUP BY HOADON.MAHD
) AS T
GROUP BY MONTH