package services;

import db.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class BillingAndTransactionManager {
    static Scanner sc = new Scanner(System.in);

    public static void generateSupplierBillBySupplierId() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter Supplier ID to view transactions: ");
            int supplierId = sc.nextInt();

            String txnQuery = "SELECT * FROM SupplierTransaction WHERE SupplierId = " + supplierId;
            ResultSet txnRs = stmt.executeQuery(txnQuery);

            if (!txnRs.isBeforeFirst()) {
                System.out.println("No transactions found for this supplier.");
                return;
            }

            System.out.println("\n=== Transactions for Supplier ID: " + supplierId + " ===");
            while (txnRs.next()) {
                int txnId = txnRs.getInt("TransactionId");
                int storeId = txnRs.getInt("StoreId");
                double total = txnRs.getDouble("TotalPrice");
                Date date = txnRs.getDate("PurchaseDate");
                System.out.printf("Transaction ID: %d | Store ID: %d | Total: $%.2f | Date: %s%n",
                        txnId, storeId, total, date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Issue 1: Additional methods for makeCustomerTransaction, generateRewardChecks etc. are pending
}
