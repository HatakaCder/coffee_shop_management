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
import javax.swing.table.TableModel;
import java.time.*;

/**
 *
 * @author Admin
 */
public class Panel_DatBan extends javax.swing.JPanel {
    private String tratruoc="Co";
    private Connection c;
    private PreparedStatement pst;
    private ResultSet rs;
    private String date;
    /**
     * Creates new form DatBan_Panel
     */
    public Panel_DatBan() {
        initComponents();
        functions f = new functions();
        c = f.connectDB();
        rad_no.setSelected(true);
        LocalDate currentDate = LocalDate.now();
        date = currentDate.getYear() + "-" + currentDate.getMonthValue() + "-" + currentDate.getDayOfMonth();
        addBan();
        showDatTruoc();
    }
    private void addBan(){
        cbx_ban.removeAllItems();
        for (int i = 1; i <=20; i++){
            cbx_ban.addItem(String.valueOf(i));
        }
    }
    private int getMaDatTruoc(){
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
    private void showDatTruoc(){
        int madt = getMaDatTruoc();
        tf_madt.setText("DT" + String.valueOf(madt));
        String query="SELECT * FROM DATTRUOC WHERE TENKH IS NOT NULL AND NGAY >= '" + date + "' ORDER BY NGAY ASC, TGBATDAU ASC";
        try{
            pst=c.prepareStatement(query);
            rs=pst.executeQuery();
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
                Vector v=new Vector();
                for(int i=1;i<=n;i++){
                    v.add(rs.getString("MADT"));
                    v.add(rs.getString("TENKH"));
                    v.add(rs.getString("SDT"));
                    v.add(rs.getString("SOBAN"));
                    v.add(rs.getString("TGBATDAU"));
                    v.add(rs.getString("TGKETTHUC"));
                    v.add(rs.getString("NGAY"));
                    v.add(rs.getString("THANHTOAN"));
                    v.add(rs.getString("GHICHU"));
                }
                dfm.addRow(v);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private boolean checkTime(int h, int m){
        if (h > 23) return false;
        if (m > 59) return false;
        return true;
    }
    private boolean checkAdv(){
        try{
            String[] tgbd = tf_tgbatdau.getText().split(":");
            String[] tgkt = tf_tgketthuc.getText().split(":");
            if (Integer.parseInt(tgbd[0]) > Integer.parseInt(tgkt[0])){
                return false;
            }
            if (!checkTime(Integer.parseInt(tgbd[0]), Integer.parseInt(tgbd[1])) || !checkTime(Integer.parseInt(tgkt[0]), Integer.parseInt(tgkt[1])))return false;
            if (Integer.parseInt(tgbd[0]) == Integer.parseInt(tgkt[0])){
                if(Integer.parseInt(tgbd[1]) > Integer.parseInt(tgkt[1]))return false;
            }
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    private boolean checkNull(){
        if(tf_madt.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập mã đặt trước!");
            return false;
        }
       
        if(tf_tenkh.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập họ tên khách hàng!");
            return false;
        }
        
        if(tf_sdt.getText().equals("") || !isNumeric(tf_sdt.getText())){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập số điện thoại khách hàng hoặc nhập không đúng định dạng!");
            return false;
        }
           
        if(tf_tgbatdau.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập thời gian bắt đầu!");
            return false;
        }
          
        if(tf_tgketthuc.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập thời gian kết thúc!");
            return false;
        }
        if (!checkAdv()) return false;
        if(tf_date.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập ngày!");
            return false;
        }
        
        if(!rad_no.isSelected() && !rad_yes.isSelected()){
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn phương thức thanh toán!");
            return false;
        }
        return true;
    }
    private void search(){
        String query = "";
        if (tf_search.getText().startsWith("DT")){
            query = "SELECT * FROM DATTRUOC WHERE MADT='" + tf_search.getText() + "'";
        }
        else {
            query = "SELECT * FROM DATTRUOC WHERE TENKH='" + tf_search.getText() + "'";
        }
        try{
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
                Vector v=new Vector();
                for(int i=1;i<=n;i++){
                    v.add(rs.getString("MADT"));
                    v.add(rs.getString("TENKH"));
                    v.add(rs.getString("SDT"));
                    v.add(rs.getString("SOBAN"));
                    v.add(rs.getString("TGBATDAU"));
                    v.add(rs.getString("TGKETTHUC"));
                    v.add(rs.getString("NGAY"));
                    v.add(rs.getString("THANHTOAN"));
                    v.add(rs.getString("GHICHU"));
                }
                dfm.addRow(v);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void increaseNumDT(){
        int madt = getMaDatTruoc();
        String q = "UPDATE NUMGENERATE SET G_DATTRUOC=" + String.valueOf(madt+1);
        try{
            pst = c.prepareStatement(q);
            pst.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private boolean kiemTraTonTaiDT(String id) {
        String query = "SELECT COUNT(*) FROM DATTRUOC WHERE MADT = ?";
        try {
            pst = c.prepareStatement(query);
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            return rowCount > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tf_tenkh = new javax.swing.JTextField();
        tf_sdt = new javax.swing.JTextField();
        cbx_ban = new javax.swing.JComboBox<>();
        tf_tgbatdau = new javax.swing.JTextField();
        tf_tgketthuc = new javax.swing.JTextField();
        tf_date = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        tf_madt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_note = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        rad_no = new javax.swing.JRadioButton();
        rad_yes = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_ban = new javax.swing.JTable();
        tf_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();

        setBackground(new java.awt.Color(196, 164, 132));
        setMinimumSize(new java.awt.Dimension(970, 650));
        setPreferredSize(new java.awt.Dimension(970, 650));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        jLabel1.setText("Thông tin đặt bàn");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel2.setText("Tên khách hàng:");

        jLabel3.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel3.setText("Số điện thoại:");

        jLabel4.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel4.setText("Bàn số:");

        jLabel5.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel5.setText("Thời gian:");

        jLabel6.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel6.setText("Ngày:");

        tf_tenkh.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        tf_sdt.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        cbx_ban.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        cbx_ban.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tf_tgbatdau.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        tf_tgketthuc.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        tf_date.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        tf_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_dateActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel10.setText("Mã đặt trước:");

        tf_madt.setEditable(false);
        tf_madt.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(89, 89, 89)
                        .addComponent(tf_sdt))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(tf_tgbatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addComponent(tf_tgketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tf_tenkh)
                            .addComponent(tf_madt)
                            .addComponent(tf_date)
                            .addComponent(cbx_ban, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_madt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_tenkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbx_ban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_tgketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_tgbatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel8.setText("Ghi chú:");

        txt_note.setColumns(20);
        txt_note.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        txt_note.setRows(5);
        jScrollPane1.setViewportView(txt_note);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        btn_them.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btn_them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_them.setText("Thêm");
        btn_them.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_them.setIconTextGap(20);
        btn_them.setMargin(new java.awt.Insets(2, 50, 3, 14));
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });
        jPanel10.add(btn_them);

        btn_xoa.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/x-mark.png"))); // NOI18N
        btn_xoa.setText("Xoá");
        btn_xoa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_xoa.setIconTextGap(20);
        btn_xoa.setMargin(new java.awt.Insets(2, 50, 3, 14));
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });
        jPanel10.add(btn_xoa);

        btn_sua.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit-button.png"))); // NOI18N
        btn_sua.setText("Sửa");
        btn_sua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_sua.setIconTextGap(20);
        btn_sua.setMargin(new java.awt.Insets(2, 50, 3, 14));
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });
        jPanel10.add(btn_sua);

        btn_reset.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reset.png"))); // NOI18N
        btn_reset.setText("Reset");
        btn_reset.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_reset.setIconTextGap(20);
        btn_reset.setMargin(new java.awt.Insets(2, 50, 3, 14));
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        jPanel10.add(btn_reset);

        jLabel7.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        jLabel7.setText("Trả trước:");

        rad_no.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rad_no);
        rad_no.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        rad_no.setText("Không");
        rad_no.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rad_noStateChanged(evt);
            }
        });

        rad_yes.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rad_yes);
        rad_yes.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        rad_yes.setText("Đã thanh toán");
        rad_yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rad_yesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(64, 64, 64)
                        .addComponent(rad_no)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rad_yes)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rad_no)
                    .addComponent(rad_yes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Inter", 1, 18)); // NOI18N
        jLabel9.setText("Danh sách các bàn đã được đặt");

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
        tbl_ban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_banMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_ban);

        tf_search.setFont(new java.awt.Font("Inter", 0, 16)); // NOI18N
        tf_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_searchActionPerformed(evt);
            }
        });

        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btn_search.setBorder(null);
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(208, 208, 208)
                        .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tf_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_dateActionPerformed

    private void rad_yesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rad_yesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rad_yesActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        if (kiemTraTonTaiDT(tf_madt.getText())) {
            JOptionPane.showMessageDialog(null, "Mã đặt trước " + tf_madt.getText() + " đã tồn tại. Không thể thêm mới.");
            return;
        }
        String q = "INSERT INTO DATTRUOC VALUES('"+tf_madt.getText()+"', '"+ tf_tenkh.getText()+"', '"+ tf_sdt.getText()+"', '"+cbx_ban.getSelectedItem().toString()+"', '"+ tf_tgbatdau.getText()+"', '"+tf_tgketthuc.getText()+"', '"+tf_date.getText()+ "', '" + tratruoc +"', '" + txt_note.getText() +"')";
        try{
            pst = c.prepareStatement(q);
            pst.executeUpdate();
            showDatTruoc();
            JOptionPane.showMessageDialog(null, "Đặt trước thành công!");
            increaseNumDT();
            showDatTruoc();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
        String q = "UPDATE DATTRUOC SET TENKH='" + tf_tenkh.getText() +"', SDT='"+ tf_sdt.getText() +"', SOBAN='" + cbx_ban.getSelectedItem().toString() + "', TGBATDAU='" + tf_tgbatdau.getText() + "', TGKETTHUC='" + tf_tgketthuc.getText() + "', NGAY='" + tf_date.getText() + "', THANHTOAN='" + tratruoc + "', GHICHU='" + txt_note.getText() + "' WHERE MADT='" + tf_madt.getText() + "'";
        try{
            pst = c.prepareStatement(q);
            pst.executeUpdate();
            showDatTruoc();
            JOptionPane.showMessageDialog(null, "Sửa thành công!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void rad_noStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rad_noStateChanged
        // TODO add your handling code here:
        if(rad_no.isSelected()) tratruoc="Khong";
        else tratruoc="Co";
    }//GEN-LAST:event_rad_noStateChanged

    private void tbl_banMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_banMouseClicked
        // TODO add your handling code here:
        int id_selected = tbl_ban.getSelectedRow();
        TableModel model = tbl_ban.getModel();
        tf_madt.setText(model.getValueAt(id_selected, 0).toString());
        tf_tenkh.setText(model.getValueAt(id_selected, 1).toString());
        tf_sdt.setText(model.getValueAt(id_selected, 2).toString());
        cbx_ban.setSelectedItem(model.getValueAt(id_selected, 3).toString());
        tf_tgbatdau.setText(model.getValueAt(id_selected, 4).toString());
        tf_tgketthuc.setText(model.getValueAt(id_selected, 5).toString());
        tf_date.setText(model.getValueAt(id_selected, 6).toString());
        if(model.getValueAt(id_selected, 7).toString().equals("Co")){
            rad_yes.setSelected(true);
            tratruoc = "Co";
        } else {
            rad_no.setSelected(true);
            tratruoc = "Khong";
        }
        txt_note.setText(model.getValueAt(id_selected, 8).toString());
    }//GEN-LAST:event_tbl_banMouseClicked

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        // TODO add your handling code here:
        int option = JOptionPane.showConfirmDialog(null,"Bạn có chắc chắn muốn xoá?", "Xoá", 2);
        if (option==JOptionPane.YES_OPTION){
            String q = "DELETE FROM DATTRUOC WHERE MADT='" + tf_madt.getText() + "'";
            try{
                pst = c.prepareStatement(q);
                pst.executeUpdate();
                showDatTruoc();
                JOptionPane.showMessageDialog(null, "Xoá thành công!");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void tf_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_searchActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_tf_searchActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        showDatTruoc();
    }//GEN-LAST:event_btn_resetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbx_ban;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rad_no;
    private javax.swing.JRadioButton rad_yes;
    private javax.swing.JTable tbl_ban;
    private javax.swing.JFormattedTextField tf_date;
    private javax.swing.JTextField tf_madt;
    private javax.swing.JTextField tf_sdt;
    private javax.swing.JTextField tf_search;
    private javax.swing.JTextField tf_tenkh;
    private javax.swing.JTextField tf_tgbatdau;
    private javax.swing.JTextField tf_tgketthuc;
    private javax.swing.JTextArea txt_note;
    // End of variables declaration//GEN-END:variables
}
