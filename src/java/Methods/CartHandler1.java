
package Methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartHandler {
    
    
    public ResultSet getCart(String username, Connection conn) {
        
    ResultSet shoppingcart;

        try {
            
            
            PreparedStatement psgetshoppingcart = conn.prepareStatement("Select * FROM shoppingcart WHERE username = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psgetshoppingcart.setString(1, username);
            shoppingcart = psgetshoppingcart.executeQuery();
                return shoppingcart;
            
        } catch (SQLException ex) {
            Logger.getLogger(CartHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;

    }
    
    
    public void addProduct(String username, String item, String seller, int quantity, double price, Connection conn)
    {
    
        try {
            PreparedStatement psCheckExists;
            String firstAdd = "INSERT INTO shoppingcart (username, item, seller, price, quantity) VALUES (?, ?, ?, ?, ?)";
            String alreadyAdd = "UPDATE shoppingcart SET quantity = quantity + ? WHERE user_name = ? AND item = ?";
            
            psCheckExists = conn.prepareStatement("SELECT * from shoppingcart WHERE item = ? AND username = ? AND seller = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psCheckExists.setString(1, item);
            psCheckExists.setString(2, username);
            psCheckExists.setString(3, seller);
            
            ResultSet checkExists = psCheckExists.executeQuery();

            

                if (checkExists.next()) {
                    PreparedStatement psAddProduct = conn.prepareStatement(alreadyAdd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    psAddProduct.setInt(1, quantity);
                    psAddProduct.setString(2, item);
                    psAddProduct.setString(3, username);
                    psAddProduct.setString(4, seller);


                    psAddProduct.executeUpdate();
                }
                else {
                    PreparedStatement psAddProduct = conn.prepareStatement(firstAdd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    psAddProduct.setString(1, username);
                    psAddProduct.setString(2, item);
                    psAddProduct.setString(3, seller);
                    psAddProduct.setDouble(4, price);
                    psAddProduct.setInt(5, quantity);

                    psAddProduct.executeUpdate();
                }


        } 

        catch (SQLException ex) {

            Logger.getLogger(CartHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void removeProduct(String username, String item, String seller, Connection conn) {
    
        
        try {
            
            PreparedStatement psDeleteProduct = conn.prepareStatement("DELETE FROM shoppingcart WHERE username = ? AND item = ? AND seller = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            psDeleteProduct.setString(1, username);
            psDeleteProduct.setString(2, item);
            psDeleteProduct.setString(3, seller);

            psDeleteProduct.executeUpdate();


        } 

        catch (SQLException ex) {

            Logger.getLogger(CartHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public static ResultSet PageResult(int PageNum, Connection conn) {
        try {
            String query = "SELECT * FROM shoppingcart LIMIT ? OFFSET ?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, 3);                        //constant limit
            ps.setInt(2, (PageNum - 1) * 3);     //bases from page number
            ResultSet records = ps.executeQuery();

            if (records.next()) {
                records.beforeFirst();
                return records;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CartHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public static int ProductCount(Connection conn) {
        try {
            String query = "SELECT COUNT(*) FROM shoppingcart";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet records = ps.executeQuery();
            
            if (records.next()) {
                records.first();
            }
            System.out.println(records.getInt("count(*)"));
        } catch (SQLException ex) {
            Logger.getLogger(CartHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


}