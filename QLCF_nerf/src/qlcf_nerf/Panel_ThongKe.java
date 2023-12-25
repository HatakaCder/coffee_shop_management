/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package qlcf_nerf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class Panel_ThongKe extends javax.swing.JPanel {
    private Connection c;
    private PreparedStatement pst;
    private ResultSet rs;
    /**
     * Creates new form ThongKe_Panel
     */
    public Panel_ThongKe() {
        initComponents();
        functions f = new functions();
        c = f.connectDB();
        load();
    }
    private void load(){
       rad_ngay.setSelected(true);
    }
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private boolean checkNgay(){
        String[] s;
        try{
            s = tf_date.getText().split("-");
            if (s.length != 3) return false;
            int year=Integer.parseInt(s[0]);
            int month=Integer.parseInt(s[1]);
            int day=Integer.parseInt(s[2]);
            if(year < 0 || month <= 0 || day <= 0) return false;
            if(month > 12) return false;
            if (day > 31) return false;
            if((month == 4|| month==6 || month==9 || month==11) && day > 30) return false;
            if (month == 2 && year % 4 == 0 && day > 29) return false;
            if (month == 2 && year % 4 !=0 && day > 28) return false;
            
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    private boolean checkThang(){
        String[] s;
        try{
            s = tf_date.getText().split("/");
            if (s.length != 2) return false;
            int year=Integer.parseInt(s[1]);
            int month=Integer.parseInt(s[0]);
            if(year < 0 || month <= 0) return false;
            if(month > 12) return false;
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    private boolean checkNam(){
        try{
            int year=Integer.parseInt(tf_date.getText());
            if(year < 0) return false;
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    private boolean check(){
        if(rad_ngay.isSelected()){
            return checkNgay();
        } else if (rad_thang.isSelected()){
            return checkThang();
        }
        return checkNam();
    }
    private float getDoanhThu(){
        int rowCount = tableTK.getRowCount();
        int columnCount = tableTK.getColumnCount();
        int doanhthu = 0;
        for (int i = 0; i < rowCount; i++){
            float value = Float.parseFloat(tableTK.getValueAt(i, columnCount-1).toString());
            doanhthu+=value;
        }
        return doanhthu;
    }
    private void showTKNgay(){
        String query="SELECT HOADON.MAHD, HOADON.MANV, HOADON.THOIGIAN, HOADON.NGAY, SUM(CHITIETHOADON.GIA) AS TONGGIATRIHOADON FROM HOADON JOIN CHITIETHOADON ON CHITIETHOADON.MAHD = HOADON.MAHD WHERE HOADON.NGAY='"+ tf_date.getText() + "' GROUP BY HOADON.MAHD";
        try{
            pst=c.prepareStatement(query);
            rs=pst.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int n = rsmd.getColumnCount();
            String[] colName = new String[n];
            for (int i = 0; i<n;i++){
                colName[i] = rsmd.getColumnName(i+1);
            }
            DefaultTableModel dfm= (DefaultTableModel)tableTK.getModel();
            dfm.setRowCount(0);
            dfm.setColumnIdentifiers(colName);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString("MAHD"));
                v.add(rs.getString("MANV"));
                v.add(rs.getString("THOIGIAN"));
                v.add(rs.getString("NGAY"));
                v.add(rs.getString("TONGGIATRIHOADON"));
                dfm.addRow(v);
            }
            lbl_doanhthu.setText("Tổng tiền thu về: " + String.valueOf(getDoanhThu()) + " VND");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void showTKThang(){
        String[] s = tf_date.getText().split("/");
        String month = s[0];
        String year = s[1];
        String query = "SELECT DATE(T.NGAY) AS NGAY, SUM(TONGGIATRIHOADON) AS DOANHTHU FROM (SELECT HOADON.MAHD, HOADON.MANV, HOADON.THOIGIAN, HOADON.NGAY, SUM(CHITIETHOADON.GIA) AS TONGGIATRIHOADON FROM HOADON JOIN CHITIETHOADON ON CHITIETHOADON.MAHD = HOADON.MAHD GROUP BY HOADON.MAHD) AS T WHERE MONTH(NGAY)='"+ s[0] + "' AND YEAR(NGAY)='" + s[1] +"' GROUP BY NGAY";
        try{
            pst=c.prepareStatement(query);
            rs=pst.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int n = rsmd.getColumnCount();
            String[] colName = new String[n];
            for (int i = 0; i<n;i++){
                colName[i] = rsmd.getColumnName(i+1);
            }
            DefaultTableModel dfm= (DefaultTableModel)tableTK.getModel();
            dfm.setRowCount(0);
            dfm.setColumnIdentifiers(colName);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString("NGAY"));
                v.add(rs.getString("DOANHTHU"));
                dfm.addRow(v);
            }
            lbl_doanhthu.setText("Tổng tiền thu về: " + String.valueOf(getDoanhThu()) + " VND");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void showTKNam(){
        String query = "SELECT YEAR(T.NGAY) AS NAM, SUM(TONGGIATRIHOADON) AS DOANHTHU FROM (SELECT HOADON.MAHD, HOADON.MANV, HOADON.THOIGIAN, HOADON.NGAY, SUM(CHITIETHOADON.GIA) AS TONGGIATRIHOADON FROM HOADON JOIN CHITIETHOADON ON CHITIETHOADON.MAHD = HOADON.MAHD GROUP BY HOADON.MAHD) AS T WHERE YEAR(NGAY)='" + tf_date.getText() +"' GROUP BY NAM";
        try{
            pst=c.prepareStatement(query);
            rs=pst.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int n = rsmd.getColumnCount();
            String[] colName = new String[n];
            for (int i = 0; i<n;i++){
                colName[i] = rsmd.getColumnName(i+1);
            }
            DefaultTableModel dfm= (DefaultTableModel)tableTK.getModel();
            dfm.setRowCount(0);
            dfm.setColumnIdentifiers(colName);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString("NAM"));
                v.add(rs.getString("DOANHTHU"));
                dfm.addRow(v);
            }
            lbl_doanhthu.setText("Tổng tiền thu về: " + String.valueOf(getDoanhThu()) + " VND");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rad_ngay = new javax.swing.JRadioButton();
        rad_thang = new javax.swing.JRadioButton();
        rad_nam = new javax.swing.JRadioButton();
        tf_date = new javax.swing.JTextField();
        btn_ok = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTK = new javax.swing.JTable();
        lbl_doanhthu = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(970, 650));
        setPreferredSize(new java.awt.Dimension(970, 650));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        jLabel1.setText("Thống kê theo...:");

        rad_ngay.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rad_ngay);
        rad_ngay.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        rad_ngay.setText("Theo ngày");

        rad_thang.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rad_thang);
        rad_thang.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        rad_thang.setText("Theo tháng");

        rad_nam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rad_nam);
        rad_nam.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        rad_nam.setText("Theo năm");

        tf_date.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        tf_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_dateActionPerformed(evt);
            }
        });

        btn_ok.setBackground(new java.awt.Color(204, 204, 204));
        btn_ok.setFont(new java.awt.Font("Inter", 1, 16)); // NOI18N
        btn_ok.setText("OK");
        btn_ok.setBorderPainted(false);
        btn_ok.setRolloverEnabled(false);
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rad_ngay)
                        .addGap(68, 68, 68)
                        .addComponent(rad_thang)
                        .addGap(73, 73, 73)
                        .addComponent(rad_nam)
                        .addGap(97, 97, 97)
                        .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_ok, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rad_ngay)
                    .addComponent(rad_thang)
                    .addComponent(rad_nam)
                    .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ok))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        jLabel2.setText("Thống kê doanh thu của quán + date + time:");

        tableTK.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableTK);

        lbl_doanhthu.setFont(new java.awt.Font("Inter", 1, 16)); // NOI18N
        lbl_doanhthu.setText("Tổng tiền thu về: 0 VND");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 563, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_doanhthu)))
                .addGap(11, 11, 11))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_doanhthu)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        // TODO add your handling code here:
        boolean check = check();
        if (check){
            if (rad_ngay.isSelected()){           
                showTKNgay();
            } else if (rad_thang.isSelected()){
                showTKThang();
            } else if (rad_nam.isSelected()){
                showTKNam();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Định dạng không hợp lệ!");
        }
    }//GEN-LAST:event_btn_okActionPerformed

    private void tf_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_dateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ok;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_doanhthu;
    private javax.swing.JRadioButton rad_nam;
    private javax.swing.JRadioButton rad_ngay;
    private javax.swing.JRadioButton rad_thang;
    private javax.swing.JTable tableTK;
    private javax.swing.JTextField tf_date;
    // End of variables declaration//GEN-END:variables
}
