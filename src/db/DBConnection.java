package db;

import java.sql.*;

public class DBConnection {
    private static Connection conn;
    private static final String URL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/mjain22";
    private static final String USER = "mjain22";
    private static final String PASS = "200596188";

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try { ac.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
