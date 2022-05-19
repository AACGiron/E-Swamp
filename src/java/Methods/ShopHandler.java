/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexa
 */
public class ShopHandler {
    
    
    public ResultSet getUserproducts(String username, Connection conn) {
        
    ResultSet products;

        try {
            
            
            PreparedStatement psgetproducts = conn.prepareStatement("Select * FROM products WHERE username = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psgetproducts.setString(1, username);
            products = psgetproducts.executeQuery();
                return products;
            
        } catch (SQLException ex) {
            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;

    }
    
    
    public void addProduct(String username, String item, String description, double price, Connection conn)
    {
    
        try {
            
            PreparedStatement psAddProduct = conn.prepareStatement("INSERT INTO products (username, item, price, description) VALUES (?, ?, ?, ?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psAddProduct.setString(1, username);
            psAddProduct.setString(2, item);
            psAddProduct.setDouble(3, price);
            psAddProduct.setString(4, description);

            psAddProduct.executeUpdate();


        } 

        catch (SQLException ex) {

            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void editProduct(String username, String item, String editing, String newValue, Connection conn)
    {
    
        try {
            
            PreparedStatement psAddProduct = conn.prepareStatement("UPDATE products SET ? = ? WHERE username = ?, item = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psAddProduct.setString(1, editing);
            
            if ("price".equals(editing)) {
            
                psAddProduct.setDouble(2, Double.parseDouble(newValue));
            }
            
            else {
            
                psAddProduct.setString(2, newValue);
            }
            
            psAddProduct.setString(3, username);
            psAddProduct.setString(4, item);

            psAddProduct.executeUpdate();


        } 

        catch (SQLException ex) {

            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void removeProduct(String username, String item, Connection conn) {
    
        
        try {
            
            PreparedStatement psDeleteProduct = conn.prepareStatement("DELETE FROM products WHERE username = ? AND item = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psDeleteProduct.setString(1, username);
            psDeleteProduct.setString(2, item);

            psDeleteProduct.executeUpdate();


        } 

        catch (SQLException ex) {

            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public static ResultSet PageResult(int PageNum, Connection conn) {
        try {
            String query = "SELECT * FROM products LIMIT ? OFFSET ?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, 3);                        //constant limit
            ps.setInt(2, (PageNum - 1) * 3);     //bases from page number
            ResultSet records = ps.executeQuery();

            if (records.next()) {
                records.beforeFirst();
                return records;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static int ProductCount(Connection conn) {
        try {
            String query = "SELECT COUNT(*) FROM products";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet records = ps.executeQuery();
            
            if (records.next()) {
                records.first();
            }
            System.out.println(records.getInt("count(*)"));
        } catch (SQLException ex) {
            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


}

