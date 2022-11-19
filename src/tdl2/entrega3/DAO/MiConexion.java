package tdl2.entrega3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class MiConexion {

	private static Connection con = null;
	private MiConexion(String usr, String passw) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mundial_futbol_2022",usr,passw);
		} catch (java.sql.SQLException e) {
					System.out.println("Error de SQL: "+e.getMessage());
		}
	}
	
	public static Connection getCon(String usr, String passw) {
		if (con == null) {	
			new MiConexion(usr,passw);
		}
		return con;
	}

}
