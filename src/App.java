import java.sql.Connection;

import db.DBConnection;

public class App {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DBConnection.getConnection();
            MainMenu.run(); // Delegated to a controller class

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn);
        }
    }
}
