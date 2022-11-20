package tdl2.entrega3.DAO.implJDBC;

import java.sql.*;
import java.util.*;

import tdl2.entrega3.DAO.interfaces.PaisDAO;
import tdl2.entrega3.classes.Pais;
import tdl2.entrega3.sql.MiConexion;

public class PaisDAOjdbc implements PaisDAO {

	public PaisDAOjdbc() {
		super();
	}

	public List<Pais> cargar() {
		List<Pais> lista = new LinkedList<Pais>();
		Connection con = null;

		return null;
	}

	public void eliminar(Pais p) {

	}

	public Pais encontrar(int id) {
		Pais p = null;
		try {
			Connection con = MiConexion.getCon("root", "");
			PreparedStatement pst = con.prepareStatement("SELECT * FROM pais WHERE idpais = ?");
			pst.clearParameters();
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next() == true) {
				p = new Pais();
				p.setIdioma(rs.getString(1));
				p.setNombre(rs.getString(2));
			}
			rs.close();
			pst.close();
			con.close();
		} catch (java.sql.SQLException e) {
			System.out.println("Error de SQL: " + e.getMessage());
		}
		return p;
	}

	public Pais encontrar(String n) {
		return null;
	}

	public Pais guardar(Pais p) {
		return null;
	}

}
