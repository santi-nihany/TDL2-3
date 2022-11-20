package tdl2.entrega3.sql;

import java.sql.*;

public class MyConnection {
    private static Connection con = null;

    public static Connection getCon(String usr, String passwd) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mundial_futbol_2022", usr, passwd);
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return con;
    }
}
