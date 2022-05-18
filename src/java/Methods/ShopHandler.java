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
    public void addProduct(String username, String item, String description, double price, Connection conn)
    {
    

            
        try {
            
            PreparedStatement psAddProduct = conn.prepareStatement("INSERT INTO products (username, item, price, description) VALUES (?, ?, ?, ?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psAddProduct.setString(1, username);
            psAddProduct.setString(2, item);
            psAddProduct.setDouble(3, price);
            psAddProduct.setString(4, description);

            psAddProduct.executeUpdate();

            Statement statement = conn.createStatement();

        } 

        catch (SQLException ex) {

            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ResultSet getProducts(String username, Connection conn) {

    ResultSet products;


        try {
            PreparedStatement psgetProducts = conn.prepareStatement("Select * FROM products WHERE user_name = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psgetProducts.setString(1, username);
            products = psgetProducts.executeQuery();
                return products;
            
        } catch (SQLException ex) {
            Logger.getLogger(ShopHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;

    } 



}

