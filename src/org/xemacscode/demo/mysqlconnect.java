/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class mysqlconnect {
       
    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/projetpi","root","");
           // JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    
    }
    
    public static ObservableList<Seance> getDatausers(){
        Connection conn = ConnectDb();
        ObservableList<Seance> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seance");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){   
                list.add(new Seance(Integer.parseInt(rs.getString("ID_seance")), rs.getInt("ID_formation"), rs.getString("lien"), rs.getString("description"), rs.getString("date_seance")));               
            }
        } catch (Exception e) {
        }
        return list;
    }
    
}
