package services;

import db.DBConnection;
import java.sql.*;

public class ReportGenerator {

    public static void getSalesByDay(String date) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT SUM(TotalPrice) AS Total_Sales FROM CustomerTransaction WHERE PurchaseDate = '" + date + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Total sales on " + date + ": $" + rs.getDouble("Total_Sales"));
            } else {
                System.out.println("No sales data found for " + date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Issue 4: Additional report methods: getSalesGrowth, getMerchandiseStockReport, getCustomerGrowth, etc. are pending
}
