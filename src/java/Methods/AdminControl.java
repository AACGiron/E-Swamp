/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Methods;

import Other.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acer
 */
public class AdminControl {
    public static ResultSet AdminKeys(Connection conn){
        try {
            String query = "SELECT * FROM adminkey";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet records = ps.executeQuery();

            if (records.next()) {
                records.beforeFirst();
                return records;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean AdminKeyCheck (String inputKey1, String inputKey2, Connection conn){
        try {
            ResultSet adminKeys = AdminKeys(conn);
            adminKeys.first();
            
            String decryptKey1 = Security.decrypt(adminKeys.getString("key1"));
            String decryptKey2 = Security.decrypt(adminKeys.getString("key2"));
            
            if(decryptKey1.equals(inputKey1) && decryptKey2.equals(inputKey2)){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void changeAdminKey (String key1, String key2, Connection conn){
        try {
            key1 = Security.encrypt(key1);
            key2 = Security.encrypt(key2);
            String query = "UPDATE adminkey SET key1 = ? AND key2 = ?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, key1);
            ps.setString(2, key2);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
