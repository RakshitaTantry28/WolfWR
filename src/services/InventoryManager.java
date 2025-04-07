package services;

import db.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class InventoryManager {
    static Scanner sc = new Scanner(System.in);

    public static void transferInventory() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter product ID: ");
            int productID = sc.nextInt();

            System.out.print("Enter source store ID: ");
            int fromStore = sc.nextInt();

            System.out.print("Enter destination store ID: ");
            int toStore = sc.nextInt();

            System.out.print("Enter quantity to transfer: ");
            int transferQty = sc.nextInt();

            conn.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE productId = " + productID + " AND storeId = " + fromStore);
            if (!rs.next()) {
                System.out.println("Product not found in source store.");
                conn.rollback();
                return;
            }

            int availableQty = rs.getInt("quantityInStock");
            String productName = rs.getString("productName");

            if (availableQty < transferQty) {
                System.out.println("Not enough quantity to transfer.");
                conn.rollback();
                return;
            }

            stmt.executeUpdate(String.format(
                "UPDATE Product SET quantityInStock = quantityInStock - %d WHERE productId = %d AND storeId = %d",
                transferQty, productID, fromStore));

            rs = stmt.executeQuery("SELECT productId FROM Product WHERE productName = '" + productName + "' AND storeId = " + toStore);
            if (rs.next()) {
                int destProductId = rs.getInt("productId");
                stmt.executeUpdate(String.format(
                    "UPDATE Product SET quantityInStock = quantityInStock + %d WHERE productId = %d AND storeId = %d",
                    transferQty, destProductId, toStore));
            } else {
                stmt.executeUpdate(String.format("""
                    INSERT INTO Product (productName, supplierId, buyPrice, marketPrice, productionDate, expiryDate, discountId, quantityInStock, storeId)
                    SELECT productName, supplierId, buyPrice, marketPrice, productionDate, expiryDate, discountId, %d, %d
                    FROM Product WHERE productId = %d AND storeId = %d LIMIT 1
                    """, transferQty, toStore, productID, fromStore));
            }

            conn.commit();
            System.out.println("Product transferred successfully.");

        } catch (Throwable err) {
            System.out.println("Error occurred. Rolling back...");
            err.printStackTrace();
        }
    }

    // Issue 3: add method addNewInventoryForSupplierWithTransaction and returnItems similarly
}
