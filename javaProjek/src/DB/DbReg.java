package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbReg { 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DB = "perentalan";
    private static final String MYCONN = "jdbc:mysql://localhost:3306/"+DB; // Pastikan menambahkan port 3306

    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(MYCONN, USERNAME, PASSWORD);
            System.out.println("Koneksi Berhasil");
        } catch (SQLException ex) {
            Logger.getLogger(DbReg.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Koneksi Gagal");
        }

        return conn;
    }
}
