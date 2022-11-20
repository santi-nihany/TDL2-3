package tdl2.entrega3.DAO.interfaces;

import java.util.List;
import tdl2.entrega3.classes.Pais;

public interface PaisDAO {

	public List<Pais> cargar();

	public void eliminar(Pais p);

	public Pais encontrar(int id);

	public Pais encontrar(String n);

	public Pais guardar(Pais p);
}
