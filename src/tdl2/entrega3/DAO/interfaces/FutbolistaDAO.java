package tdl2.entrega3.DAO.interfaces;

import java.sql.SQLException;
import java.util.List;
import tdl2.entrega3.classes.Futbolista;

public interface FutbolistaDAO {
	List<Futbolista> cargar() throws SQLException;

	void guardar(Futbolista f) throws SQLException;

	void eliminar(Futbolista f) throws SQLException;

	void editar(Futbolista f, int id) throws SQLException;

	Futbolista encontrar(int x) throws SQLException;
}
