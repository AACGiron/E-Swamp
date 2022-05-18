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
public class AccountHandler {

    public static void Register(String username, String pass, String role, Connection conn) {
        try {
            String query = "INSERT INTO accounts (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, username);
            ps.setString(2, pass);
            ps.setString(3, role);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet AccInfo(String username, Connection conn) {
        try {
            String query = "SELECT * FROM accounts WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, username);
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

    public static boolean UsernameCheck(String username, Connection conn) {
        ResultSet info = AccInfo(username, conn);
        if (info == null) {
            System.out.println("Unique Username");
            return true;
        }
        return false;
    }

    public static String AccountCheck(String username, String password, Connection conn) {
        try {
            ResultSet info = AccInfo(username, conn);
            if (info != null) {
                info.first();
                String encrypted = info.getString("password");
                String decrypted = Security.decrypt(encrypted);

                if (decrypted.equals(password)) {
                    return "success";   //both correct
                } else {
                    return "password";  //wrong passsword
                }
            } else {
                return "username";      //wrong username
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
