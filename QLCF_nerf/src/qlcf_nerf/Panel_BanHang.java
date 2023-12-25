/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package qlcf_nerf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Panel_BanHang extends javax.swing.JPanel {

    /**
     * Creates new form BanHang_Panel
     */
    private Connection c;
    private PreparedStatement pst;
    private ResultSet rs;
    private String date;
    private int hour = 0;
    private int minute = 0;
    private long tongtien = 0, tienkhach= 0;
    private String manv;
    private int num_hd = 0;
    public Panel_BanHang(Connection c, String username, String manv) {
        initComponents();
        functions f = new functions();
        this.c = c;
        load();
        lbNhanVien.setText(username);
        this.manv = manv;
    }
    private void load(){
        loadMaHD();
        loadBan();
        loadTime();
        loadDate();
        loadLoaiTU();
        loadTU();
        loadDT();
    }
    private void loadBan(){
        cbx_ban.removeAllItems();
        for (int i = 1; i <=20; i++){
            cbx_ban.addItem(String.valueOf(i));
        }
    }
    private void loadMaHD(){
        String query = "SELECT G_HOADON FROM NUMGENERATE LIMIT 1";
        try{
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()){
                num_hd = rs.getInt("G_HOADON");
            }
            lbMaHD.setText("HD" + String.valueOf(num_hd));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void loadTime(){
        LocalTime currentTime = LocalTime.now();
        hour = currentTime.getHour();
        minute = currentTime.getMinute();
        String h = (hour >= 10) ? String.valueOf(hour) : "0"+String.valueOf(hour);
        String m = (minute >= 10) ? String.valueOf(minute) : "0"+String.valueOf(minute);
        lbl_time.setText(h+ ":" + m);
    }
    private void loadDate(){
        LocalDate currentDate = LocalDate.now();
        date = currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth();
        lbl_date.setText(currentDate.getDayOfMonth() + "/" + currentDate.getMonthValue() + "/" + currentDate.getYear());
    }
    private void loadLoaiTU(){
        String query = "SELECT DISTINCT LOAI FROM THUCUONG";
        try{
            int i = 0;
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                cbLoaiNuoc.insertItemAt(rs.getString("LOAI"), i);
                i++;
            }
            cbLoaiNuoc.setSelectedIndex(0);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void loadTU(){
        cbTenNuoc.removeAllItems();
        String query = "SELECT TENTU FROM THUCUONG WHERE LOAI='" + cbLoaiNuoc.getSelectedItem().toString() + "'";
        try {
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()){
                cbTenNuoc.addItem(rs.getString("TENTU"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void loadDT(){
        String query = "SELECT MADT, SOBAN, TGBATDAU, TGKETTHUC FROM DATTRUOC WHERE NGAY='" + date + "'";
        try {
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int n = rsmd.getColumnCount();
            String[] colName = new String[n];
            for (int i = 0; i<n;i++){
                colName[i] = rsmd.getColumnName(i+1);
            }
            DefaultTableModel dfm= (DefaultTableModel)tbl_ban.getModel();
            dfm.setRowCount(0);
            dfm.setColumnIdentifiers(colName);
            while(rs.next()){
                Vector v = new Vector();
                for(int i=1;i<=n;i++){
                    v.add(rs.getString("MADT"));
                    v.add(rs.getString("SOBAN"));
                    v.add(rs.getString("TGBATDAU"));
                    v.add(rs.getString("TGKETTHUC"));
                }
                dfm.addRow(v);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void increaseHD(){
        String query = "UPDATE NUMGENERATE SET G_HOADON=" + String.valueOf(num_hd+1) + " WHERE G_HOADON=" + String.valueOf(num_hd);
        try{
            pst = c.prepareStatement(query);
            pst.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private boolean checkSoLuong(){
        if(tfSoLuong.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập số lượng.");
            return false;
        } 
        if(!isNumeric(tfSoLuong.getText())){
            JOptionPane.showMessageDialog(null, "Nhập sai định dạng số lượng.");
            return false;
        }
        String query = "SELECT SOLUONG FROM THUCUONG WHERE TENTU='" + cbTenNuoc.getSelectedItem().toString() + "'";
        try{
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()){
                if (rs.getInt("SOLUONG") < Integer.parseInt(tfSoLuong.getText())){
                    JOptionPane.showMessageDialog(null, "Không đủ số lượng của thức uống được chọn để bán.");
                    return false;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean checkBan(){
        String query = "SELECT SOBAN, TGBATDAU, TGKETTHUC, NGAY FROM DATTRUOC WHERE SOBAN=" + cbx_ban.getSelectedItem().toString() + " AND NGAY='" + date +"'";
        String temptimestart, temptimeend;
        temptimestart = temptimeend = "";
        String[] tts_p, tte_p;
        try{
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                temptimestart = rs.getString("TGBATDAU");
                temptimeend = rs.getString("TGKETTHUC");
                tts_p = temptimestart.split(":");
                tte_p = temptimeend.split(":");
                if (hour > Integer.parseInt(tts_p[0]) && hour < Integer.parseInt(tte_p[0])) return false;
                if (hour == Integer.parseInt(tts_p[0]) && minute >= Integer.parseInt(tts_p[1])){
                    if (hour < Integer.parseInt(tte_p[0])) return false;
                    if (hour == Integer.parseInt(tte_p[0]) && minute <= Integer.parseInt(tte_p[1])) return false;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    private int getMaDT(){
        String query = "SELECT G_DATTRUOC FROM NUMGENERATE LIMIT 1";
        try{
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()){
                return rs.getInt("G_DATTRUOC");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    private void addDatTruoc(){
        int madt = getMaDT();
        String q = "INSERT INTO DATTRUOC (MADT, SOBAN, TGBATDAU, TGKETTHUC, NGAY) VALUES('"+ "DT" + String.valueOf(madt)+"', '"+ cbx_ban.getSelectedItem().toString()+"', '"+ hour + ":" + minute+"', '"+ "24:00:00"+"', '"+ date +"')";
        try{
            pst = c.prepareStatement(q);
            pst.executeUpdate();
            q = "UPDATE NUMGENERATE SET G_DATTRUOC=" + String.valueOf(madt+1);
            pst = c.prepareStatement(q);
            pst.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private boolean exceptionThem(){
        if(!checkSoLuong()) return false;
        return true;
    }
    private boolean exceptionTinhTien(){
        if (!tf_tiennhan.getText().equals("")){
            tienkhach = Long.parseLong(tf_tiennhan.getText())-tongtien;
            if (tienkhach < 0) {
                JOptionPane.showMessageDialog(null, "Không đủ tiền để trả!");
                return false;
            }  
        } else {
            JOptionPane.showMessageDialog(null, "Không được bỏ trống tiền khách trả.");
            return false;
        }
        if(!checkBan()){
            JOptionPane.showMessageDialog(null, "Bàn bạn đặt không hợp lệ, vui lòng chọn bàn khác.");
            return false;
        }
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbNhanVien = new javax.swing.JLabel();
        lbMaHD = new javax.swing.JLabel();
        lbDate = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbLoaiNuoc = new javax.swing.JComboBox<>();
        cbTenNuoc = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        lbBan = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_tu = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        tfSoLuong = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cbx_ban = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        lbl_time = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_ban = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_tongtien = new javax.swing.JLabel();
        lbl_tienthua = new javax.swing.JLabel();
        tf_tiennhan = new javax.swing.JTextField();
        btn_tinhtien = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(970, 650));
        setPreferredSize(new java.awt.Dimension(970, 650));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel3.setText("Mã hoá đơn:");

        jLabel4.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel4.setText("Thời gian:");

        jLabel5.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel5.setText("Nhân viên:");

        lbl_date.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        lbNhanVien.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        lbNhanVien.setText("Nguyễn Văn A");

        lbMaHD.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        lbDate.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel11.setText("Loại nước:");

        jLabel12.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel12.setText("Tên nước:");

        cbLoaiNuoc.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        cbLoaiNuoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbLoaiNuocItemStateChanged(evt);
            }
        });
        cbLoaiNuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiNuocActionPerformed(evt);
            }
        });

        cbTenNuoc.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        cbTenNuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel13.setText("Số lượng:");

        lbBan.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel9.setText("Danh sách thức uống khách đã đặt:");

        tbl_tu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã TU", "Tên TU", "Loại", "DVT", "Số lượng", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_tu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_tuMouseClicked(evt);
            }
        });
        tbl_tu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbl_tuPropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_tu);

        btnXoa.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/x-mark.png"))); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btn_reset.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reset.png"))); // NOI18N
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnThem, btnXoa});

        tfSoLuong.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        tfSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSoLuongActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel15.setText("Ngày:");

        cbx_ban.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        cbx_ban.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel18.setText("Bàn số:");

        lbl_time.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lbMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel18)
                .addGap(25, 25, 25)
                .addComponent(cbx_ban, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(38, 38, 38)
                .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel15)
                .addGap(36, 36, 36)
                .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbBan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(33, 33, 33)
                .addComponent(lbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(33, 33, 33)
                .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(37, 37, 37)
                .addComponent(cbTenNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_ban, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lbl_time, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbBan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbTenNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel8.setText("Các bàn đã đặt:");

        tbl_ban.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbl_ban);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel7.setText("Tổng tiền:");

        jLabel10.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel10.setText("Tiền nhận của khách:");

        jLabel14.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel14.setText("Tiền thừa:");

        lbl_tongtien.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        lbl_tongtien.setText("0 VND");

        lbl_tienthua.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        lbl_tienthua.setText("0 VNĐ ");

        tf_tiennhan.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        tf_tiennhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_tiennhanActionPerformed(evt);
            }
        });
        tf_tiennhan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tf_tiennhanPropertyChange(evt);
            }
        });

        btn_tinhtien.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btn_tinhtien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hand.png"))); // NOI18N
        btn_tinhtien.setText("Tính tiền");
        btn_tinhtien.setIconTextGap(20);
        btn_tinhtien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tinhtienActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer.png"))); // NOI18N
        jButton2.setText("In hoá đơn");
        jButton2.setIconTextGap(20);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btn_tinhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(135, 135, 135)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(18, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_tiennhan, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_tiennhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tienthua, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tinhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tf_tiennhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_tiennhanActionPerformed
        // TODO add your handling code here:
        tienkhach = 0;
        if (!tf_tiennhan.getText().isEmpty()){
            tienkhach = Long.parseLong(tf_tiennhan.getText())-tongtien;
            if (tienkhach < 0) JOptionPane.showMessageDialog(null, "Không đủ tiền để trả!");
            else {
                lbl_tienthua.setText(String.valueOf(tienkhach) + " VND");
            }
        }
    }//GEN-LAST:event_tf_tiennhanActionPerformed

    private void btn_tinhtienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tinhtienActionPerformed
        // TODO add your handling code here:
        if (exceptionTinhTien()){
            String q1 = "INSERT INTO HOADON VALUES('" +lbMaHD.getText()+ "', '" + lbl_time.getText() + "', '" + date + "', '" + manv + "', '" + cbx_ban.getSelectedItem().toString() + "')";
            String q2 = "INSERT INTO CHITIETHOADON VALUES('" + lbMaHD.getText() + "', '";
            String q3 = "SELECT SOLUONG FROM THUCUONG WHERE MATU='";
            String q4 = "UPDATE THUCUONG SET SOLUONG=";
            String final_q2, final_q3, final_q4;
            int sl = 0;
            try{
                pst = c.prepareStatement(q1);
                pst.executeUpdate();
                increaseHD();

                int rowCount = tbl_tu.getRowCount();
                for (int row=0; row < rowCount; row++){
                    final_q2 = q2 + tbl_tu.getValueAt(row, 0) + "', " + tbl_tu.getValueAt(row, 4) + ", " + tbl_tu.getValueAt(row, 5) + ")";
                    pst = c.prepareStatement(final_q2);
                    pst.executeUpdate();
                    final_q3 = q3 + tbl_tu.getValueAt(row, 0) + "'";
                    pst = c.prepareStatement(final_q3);
                    rs = pst.executeQuery();
                    if (rs.next()){
                        sl = rs.getInt("SOLUONG");
                    }
                    final_q4 = q4 + String.valueOf(sl - Integer.parseInt(tbl_tu.getValueAt(row, 4).toString())) + " WHERE MATU='" + tbl_tu.getValueAt(row, 0) + "'";
                    pst = c.prepareStatement(final_q4);
                    pst.executeUpdate();
                }
                addDatTruoc();
                JOptionPane.showMessageDialog(null, "Tính tiền thành công.");
                load();
            } 
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_tinhtienActionPerformed

    private void tfSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSoLuongActionPerformed

    private void cbLoaiNuocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbLoaiNuocItemStateChanged
        // TODO add your handling code here:
        loadTU();
    }//GEN-LAST:event_cbLoaiNuocItemStateChanged

    private void cbLoaiNuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiNuocActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cbLoaiNuocActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (exceptionThem()){
            String query = "SELECT * FROM THUCUONG WHERE TENTU='" + cbTenNuoc.getSelectedItem().toString() + "'";
            long giatemp = 0;
            try{
                pst = c.prepareStatement(query);
                rs = pst.executeQuery();
                DefaultTableModel dfm= (DefaultTableModel)tbl_tu.getModel();
                if(rs.next()){
                    Vector v = new Vector();

                    v.add(rs.getString("MATU"));
                    v.add(rs.getString("TENTU"));
                    v.add(rs.getString("LOAI"));
                    v.add(rs.getString("DVT"));
                    v.add(tfSoLuong.getText());
                    giatemp = rs.getLong("GIA")*Long.parseLong(tfSoLuong.getText());
                    tongtien += giatemp;
                    lbl_tongtien.setText(String.valueOf(tongtien) + " VND");
                    v.add(giatemp);
                    dfm.addRow(v);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tbl_tuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_tuMouseClicked
        // TODO add your handling code here:
        int id_selected = tbl_tu.getSelectedRow();
        TableModel model = tbl_tu.getModel();
        cbLoaiNuoc.setSelectedItem(model.getValueAt(id_selected, 2).toString());
        cbTenNuoc.setSelectedItem(model.getValueAt(id_selected, 1).toString());
        tfSoLuong.setText(model.getValueAt(id_selected, 4).toString());
    }//GEN-LAST:event_tbl_tuMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dfm = (DefaultTableModel) tbl_tu.getModel();
        tongtien -= (long)tbl_tu.getValueAt(tbl_tu.getSelectedRow(), 5);
        lbl_tongtien.setText(String.valueOf(tongtien) + " VND");
        dfm.removeRow(tbl_tu.getSelectedRow());
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tbl_tuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbl_tuPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_tuPropertyChange

    private void tf_tiennhanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tf_tiennhanPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tf_tiennhanPropertyChange

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        load();
    }//GEN-LAST:event_btn_resetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_tinhtien;
    private javax.swing.JComboBox<String> cbLoaiNuoc;
    private javax.swing.JComboBox<String> cbTenNuoc;
    private javax.swing.JComboBox<String> cbx_ban;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbBan;
    private javax.swing.JLabel lbDate;
    private javax.swing.JLabel lbMaHD;
    private javax.swing.JLabel lbNhanVien;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_tienthua;
    private javax.swing.JLabel lbl_time;
    private javax.swing.JLabel lbl_tongtien;
    private javax.swing.JTable tbl_ban;
    private javax.swing.JTable tbl_tu;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tf_tiennhan;
    // End of variables declaration//GEN-END:variables
}
