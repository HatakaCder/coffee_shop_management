USE qlcf_db;

INSERT INTO NHANVIEN (MANV, HOTEN, GIOITINH, NGAYSINH, SDT, DIACHI)
VALUES
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
('admin', 'admin', NULL, 'Admin');

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

INSERT INTO THUCUONG (MATU, TEN, LOAI, DVT, SOLUONG, GIA) VALUES
('TC001', 'Cà phê đen', 'Đồ uống', 'Ly', 100, 15000),
('TC002', 'Trà chanh', 'Đồ uống', 'Ly', 80, 12000),
('TC003', 'Nước suối', 'Đồ uống', 'Chai', 50, 10000),
('TC004', 'Bia Larue', 'Bia', 'Lon', 120, 25000),
('TC005', 'Soda', 'Nước ngọt', 'Chai', 60, 18000),
('TC006', 'Nước cam', 'Nước trái cây', 'Chai', 70, 22000),
('TC007', 'Bạc xỉu', 'Đồ uống', 'Ly', 90, 18000),
('TC008', 'Coca Cola', 'Nước ngọt', 'Chai', 110, 20000),
('TC009', 'Bia Tiger', 'Bia', 'Lon', 100, 28000),
('TC010', 'Trà đào', 'Nước trái cây', 'Chai', 75, 21000),
('TC011', 'Cà phê sữa đá', 'Đồ uống', 'Ly', 85, 20000),
('TC012', 'Nước dừa', 'Nước trái cây', 'Chai', 40, 25000),
('TC013', 'Bia Hà Nội', 'Bia', 'Lon', 95, 26000),
('TC014', 'Nước lọc', 'Đồ uống', 'Chai', 120, 8000),
('TC015', 'Pepsi', 'Nước ngọt', 'Chai', 70, 19000),
('TC016', 'Sinh tố xoài', 'Nước trái cây', 'Ly', 65, 23000),
('TC017', 'Bia Saigon', 'Bia', 'Lon', 80, 27000),
('TC018', 'Nước ép cam', 'Nước trái cây', 'Chai', 55, 20000),
('TC019', 'Trà sen', 'Đồ uống', 'Ly', 75, 16000),
('TC020', 'Nước táo', 'Nước trái cây', 'Chai', 85, 22000),
('TC021', 'Bia 333', 'Bia', 'Lon', 110, 29000),
('TC022', 'Nước măng cụt', 'Nước trái cây', 'Chai', 50, 24000),
('TC023', 'Cà phê cappuccino', 'Đồ uống', 'Ly', 60, 25000),
('TC024', 'Nước mâm xôi', 'Nước trái cây', 'Chai', 45, 26000),
('TC025', 'Bia Budweiser', 'Bia', 'Lon', 105, 30000),
('TC026', 'Nước lựu', 'Nước trái cây', 'Chai', 80, 21000),
('TC027', 'Trà bưởi', 'Nước trái cây', 'Ly', 70, 17000),
('TC028', 'Bia Sapporo', 'Bia', 'Lon', 95, 28000),
('TC029', 'Nước mơ', 'Nước trái cây', 'Chai', 65, 19000),
('TC030', 'Cà phê phin', 'Đồ uống', 'Ly', 75, 18000);