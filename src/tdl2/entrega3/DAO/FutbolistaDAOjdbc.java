package tdl2.entrega3.DAO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import tdl2.entrega3.classes.Futbolista;
import tdl2.entrega3.classes.Pais;

public class FutbolistaDAOjdbc implements FutbolistaDAO {

	public FutbolistaDAOjdbc() {
		super();
	}

	@Override
	public void guardar(Futbolista f) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		try {
			if (this.encontrar(f.getTeléfono()) != null) {
				System.out.println("Ya existe el futbolista");
			} else {
				con = MiConexion.getCon("root","");
				// FALTA AGREGAR EL PAIS SI NO EXISTE!
				pst2 = con.prepareStatement("SELECT idpais,nombre FROM pais WHERE nombre =?");
				pst2.clearParameters();
				pst2.setString(1,f.getPaís().getNombre());
				rs = pst2.executeQuery();
				rs.next();
				int paisID = rs.getInt("idpais");
				pst = con.prepareStatement("INSERT INTO futbolista (nombre,apellido,docIdentidad,telefono,email,idpais) VALUES(?,?,?,?,?,?)");
				pst.setString(1,f.getNombre());
				pst.setString(2,f.getApellido());
				pst.setInt(3,f.getDocId());
				pst.setInt(4,f.getTeléfono());
				pst.setString(5,f.getEmail());
				pst.setInt(6,paisID);
				pst.executeUpdate();
				System.out.println("Futbolista agregado con exito.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error de SQL: "+e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (pst2 != null) {
				pst2.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		
	}

	@Override
	public void eliminar(Futbolista f) throws SQLException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MiConexion.getCon("root","");
			pst = con.prepareStatement("DELETE FROM futbolista WHERE telefono = ?");
			pst.clearParameters();
			pst.setInt(1,f.getTeléfono());
			pst.executeUpdate();
			System.out.println("Eliminado exitosamente");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error de SQL: "+e.getMessage());
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
	public void editar(Futbolista f, int id) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		try {
			con = MiConexion.getCon("root","");
			pst = con.prepareStatement("SELECT * FROM pais WHERE nombre =?");
			pst.clearParameters();
			pst.setString(1,f.getPaís().getNombre());
			rs= pst.executeQuery();
			rs.next();
			pst2 = con.prepareStatement("UPDATE futbolista SET nombre=? , apellido=? , docIdentidad=? , telefono=? , email=? , idpais=? WHERE idfutbolista=?");
			pst2.clearParameters();
			pst2.setString(1,f.getNombre());
			pst2.setString(2,f.getApellido());
			pst2.setInt(3,f.getDocId());
			pst2.setInt(4,f.getTeléfono());
			pst2.setString(5,f.getEmail());
			pst2.setInt(6,rs.getInt("idpais"));
			pst2.setInt(7,id);
			pst2.executeUpdate();
			System.out.println("Editado exitosamente.");
		} catch (SQLException e) {
			System.err.println("Error de SQL: "+e.getMessage());
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
			if (pst2 != null) {
				pst2.close();
			}
		}
	}

	@Override
	public Futbolista encontrar(int x) throws SQLException {
		Futbolista f=null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs2 = null;
		try{
			con = MiConexion.getCon("root","");
			pst = con.prepareStatement("SELECT * FROM futbolista WHERE telefono =?");
			pst.clearParameters();
			pst.setInt(1,x);
			rs= pst.executeQuery();
			rs.next();
				if (rs.getInt("telefono")==x) {
					f = new Futbolista();
					f.setNombre(rs.getString("nombre"));
					f.setApellido(rs.getString("apellido"));
					f.setDocId(rs.getInt("docIdentidad"));
					f.setTeléfono(rs.getInt("telefono"));
					f.setEmail(rs.getString("email"));
					int idpais = rs.getInt("idpais");
					pst2 = con.prepareStatement("SELECT * FROM pais WHERE idpais = ?");
					pst2.clearParameters();
					pst2.setInt(1,idpais);
					rs2 = pst2.executeQuery();
					rs2.next();
					f.setPaís(rs2.getString("nombre"),rs2.getString("idioma"));
				}
		} catch (java.sql.SQLException e) {
			System.err.println("Error de SQL: "+e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (pst2 != null) {
				pst2.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (rs2 != null) {
				rs2.close();
			}
		}
		return f;
	}

	@Override
	public List<Futbolista> cargar() throws SQLException {
		List<Futbolista> lista = new LinkedList<Futbolista> ();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		ResultSet rs2 = null;
		try{
			con = MiConexion.getCon("root","");
			st = con.createStatement();
			rs= st.executeQuery("SELECT * FROM futbolista");
			while (rs.next()) {
				int idpais = rs.getInt("idpais");
				pst = con.prepareStatement("SELECT * FROM pais WHERE idpais = ?");
				pst.clearParameters();
				pst.setInt(1,idpais);
				rs2 = pst.executeQuery();
				rs2.next();
				Pais p = new Pais(rs2.getString("nombre"),rs2.getString("idioma"));
				Futbolista f = new Futbolista(rs.getString("nombre"),rs.getString("apellido"),rs.getInt("docIdentidad"),rs.getInt("telefono"),rs.getString("email"),p);
				lista.add(f);
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Error de SQL: "+e.getMessage());
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
			if (rs2 != null) {
				rs2.close();
			}
		}
		return lista;
	}

}
