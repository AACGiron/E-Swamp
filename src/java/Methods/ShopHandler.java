package Methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopHandler {

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
