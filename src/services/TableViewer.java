package services;

import db.DBConnection;
import java.sql.*;
import java.util.Scanner;

public class TableViewer {

    public static void displayTable() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the table name you want to see: ");
        String table = sc.next();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 1; i <= colCount; i++)
                System.out.print((i > 1 ? " | " : "") + meta.getColumnName(i));
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= colCount; i++)
                    System.out.print((i > 1 ? " | " : "") + rs.getString(i));
                System.out.println();
            }

        } catch (Throwable e) {
            System.err.println("Error displaying table data:");
            e.printStackTrace();
        }
    }
}
