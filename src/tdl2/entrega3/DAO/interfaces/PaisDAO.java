package tdl2.entrega3.DAO.interfaces;

import java.util.List;
import java.sql.SQLException;
import tdl2.entrega3.classes.Pais;

public interface PaisDAO {

	List<Pais> cargar() throws SQLException;

	void guardar(Pais f) throws SQLException;

	void eliminar(Pais f) throws SQLException;

	void editar(Pais f, int id) throws SQLException;

	Pais encontrar(String nombre) throws SQLException;
}
