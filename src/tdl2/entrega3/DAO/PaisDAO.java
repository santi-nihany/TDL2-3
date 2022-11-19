package tdl2.entrega3.DAO;

import java.util.List;
import tdl2.entrega3.classes.Pais;

public interface PaisDAO {
	List<Pais> cargar();
	void guardar(Pais p);
	void eliminar(Pais p);
	void editar(Pais p);
	Pais encontrar(String nombre);
}
