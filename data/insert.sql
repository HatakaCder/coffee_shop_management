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

INSERT INTO NUMGENERATE VALUES (1, 6, 10);

INSERT INTO THUCUONG (MATU, TENTU, LOAI, DVT, SOLUONG, GIA)
VALUES
    ('TC001', 'Cà Phê Đen', 'Cafe', 'Ly', 50, 15000),
    ('TC002', 'Sinh Tố Dưa Hấu', 'Sinh Tố', 'Ly', 30, 25000),
    ('TC003', 'Nước Ngọt Coca Cola', 'Nước Giải Khát', 'Lon', 100, 12000),
    ('TC004', 'Trà Chanh', 'Trà', 'Ly', 40, 18000),
    ('TC005', 'Nước Ép Cam', 'Nước Giải Khát', 'Chai', 25, 22000),
    ('TC006', 'Cà Phê Sữa Đá', 'Cafe', 'Ly', 60, 18000),
    ('TC007', 'Sinh Tố Bơ', 'Sinh Tố', 'Ly', 35, 28000),
    ('TC008', 'Nước Ngọt Pepsi', 'Nước Giải Khát', 'Lon', 80, 13000),
    ('TC009', 'Trà Oolong', 'Trà', 'Ly', 45, 20000),
    ('TC010', 'Nước Ép Dưa Lưới', 'Nước Giải Khát', 'Chai', 20, 25000),
    ('TC011', 'Cà Phê Sữa Nóng', 'Cafe', 'Ly', 40, 16000),
    ('TC012', 'Sinh Tố Dừa', 'Sinh Tố', 'Ly', 25, 30000),
    ('TC013', 'Nước Ngọt Sprite', 'Nước Giải Khát', 'Lon', 60, 14000),
    ('TC014', 'Trà Đào', 'Trà', 'Ly', 50, 21000),
    ('TC015', 'Nước Ép Lựu', 'Nước Giải Khát', 'Chai', 30, 27000),
    ('TC016', 'Cà Phê Phin', 'Cafe', 'Ly', 55, 19000),
    ('TC017', 'Sinh Tố Xoài', 'Sinh Tố', 'Ly', 40, 26000),
    ('TC018', 'Nước Ngọt Fanta', 'Nước Giải Khát', 'Lon', 70, 15000),
    ('TC019', 'Trà Xanh', 'Trà', 'Ly', 35, 22000),
    ('TC020', 'Nước Ép Lê', 'Nước Giải Khát', 'Chai', 18, 23000),
    ('TC021', 'Cà Phê Americano', 'Cafe', 'Ly', 48, 17000),
    ('TC022', 'Sinh Tố Dâu', 'Sinh Tố', 'Ly', 32, 28000),
    ('TC023', 'Nước Ngọt Mirinda', 'Nước Giải Khát', 'Lon', 90, 16000),
    ('TC024', 'Trà Lài', 'Trà', 'Ly', 42, 20000),
    ('TC025', 'Nước Ép Táo', 'Nước Giải Khát', 'Chai', 22, 26000),
    ('TC026', 'Cà Phê Espresso', 'Cafe', 'Ly', 50, 20000),
    ('TC027', 'Sinh Tố Kiwi', 'Sinh Tố', 'Ly', 28, 30000),
    ('TC028', 'Nước Ngọt 7UP', 'Nước Giải Khát', 'Lon', 65, 14000),
    ('TC029', 'Trà Ô Long', 'Trà', 'Ly', 38, 21000),
    ('TC030', 'Nước Ép Dừa', 'Nước Giải Khát', 'Chai', 15, 28000);