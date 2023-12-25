/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlcf_nerf;

/**
 *
 * @author Admin
 */
import java.sql.*;



public class functions {
    public Connection connectDB(){
        Connection c = null;
        
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/qlcf_db";
            String username = "root";
            String password = "pass123";
            
            c = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        
        return c;
    }
    
    public static void main(String args[]) {

    }
}
