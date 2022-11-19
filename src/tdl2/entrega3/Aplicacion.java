package tdl2.entrega3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tdl2.entrega3.DAO.FutbolistaDAOjdbc;
import tdl2.entrega3.DAO.MiConexion;
import tdl2.entrega3.classes.Futbolista;
import tdl2.entrega3.classes.Pais;
import tdl2.entrega3.components.*;

public class Aplicacion {

	public static void main(String[] args) {
		//Probando interfaces
		//Configuracion conf = new Configuracion();
		//conf.setVisible(true);
		
		/*MainFrame panel = new MainFrame();
		panel.setVisible(true);
		*/
		FutbolistaDAOjdbc fd = new FutbolistaDAOjdbc();
		Futbolista f = new Futbolista("pepe","perez",44,909090,"asd@asd",new Pais("argentina","esp"));
		try {
			/*List<Futbolista> l = fd.cargar();
			while (!l.isEmpty()) {
				System.out.println(l.get(0).getNombre());
				l.remove(0);
			}*/
			//fd.guardar(f);
			//if (fd.encontrar(f.getTeléfono()) != null) 
			//	System.out.println("Se encontro!");
			//fd.editar(f, 1);
			fd.eliminar(f);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
