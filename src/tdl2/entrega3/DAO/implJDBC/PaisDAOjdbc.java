package tdl2.entrega3.DAO.implJDBC;

import java.sql.*;
import java.util.*;

import tdl2.entrega3.DAO.interfaces.PaisDAO;
import tdl2.entrega3.classes.Pais;
import tdl2.entrega3.sql.MyConnection;

public class PaisDAOjdbc implements PaisDAO {

	public PaisDAOjdbc() {
		super();
	}

	@Override
	public void guardar(Pais p) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			if (this.encontrar(p.getNombre()) != null) {
				System.out.println("Ya existe el pais");
			} else {
				con = MyConnection.getCon("root", "");
				pst = con.prepareStatement(
						"INSERT INTO pais (nombre,idioma) VALUES(?,?)");
				pst.clearParameters();
				pst.setString(1, p.getNombre());
				pst.setString(2, p.getIdioma());
				pst.executeUpdate();
				System.out.println("Pais agregado con exito.");
			}

		} catch (SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
		}

	}

	@Override
	public void eliminar(Pais p) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MyConnection.getCon("root", "");
			pst = con.prepareStatement("DELETE FROM pais WHERE nombre= ?");
			pst.clearParameters();
			pst.setString(1, p.getNombre());
			pst.executeUpdate();
			System.out.println("Eliminado exitosamente");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error de SQL: " + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
		}
	}

	@Override
	public void editar(Pais p, int id) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MyConnection.getCon("root", "");
			pst = con.prepareStatement(
					"UPDATE Pais SET nombre=? ,  idioma=? WHERE idpais=?");
			pst.clearParameters();
			pst.setString(1, p.getNombre());
			pst.setString(2, p.getIdioma());
			pst.setInt(3, id);
			pst.executeUpdate();
			System.out.println("Editado exitosamente.");
		} catch (SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
		}
	}

	@Override
	public Pais encontrar(String nombre) throws SQLException {
		Pais p = null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = MyConnection.getCon("root", "");
			pst = con.prepareStatement("SELECT * FROM pais WHERE nombre=?");
			pst.clearParameters();
			pst.setString(1, nombre);
			rs = pst.executeQuery();

			if (rs.next()) {
				p = new Pais();
				p.setNombre(rs.getString("nombre"));
				p.setIdioma(rs.getString("idioma"));
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
		}
		return p;
	}

	@Override
	public List<Pais> cargar() throws SQLException {
		List<Pais> lista = new LinkedList<Pais>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = MyConnection.getCon("root", "");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM pais");
			while (rs.next()) {
				Pais p = new Pais(rs.getString("nombre"), rs.getString("idioma"));
				lista.add(p);
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return lista;
	}

}
