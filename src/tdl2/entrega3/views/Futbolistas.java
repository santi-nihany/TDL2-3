package tdl2.entrega3.views;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import tdl2.entrega3.DAO.implJDBC.FutbolistaDAOjdbc;
import tdl2.entrega3.classes.Futbolista;

public class Futbolistas extends JDialog {

	JPanel panel = new JPanel();

	private JTable tabla = new JTable();
	private Object[] titulos = { "ID", "NOMBRE", "APELLIDO", "EDITAR", "ELIMINAR" };
	private DefaultTableModel modelo = new DefaultTableModel(titulos, 0);

	Futbolistas() {
		// Dialog
		this.setTitle("Futbolistas");
		this.setSize(800, 400);

		// Panel principal
		panel.setLayout(new BorderLayout());

		// NORTE
		JPanel subPanel = new JPanel();
		GridLayout grid = new GridLayout(0, 4);
		grid.setHgap(15);
		grid.setVgap(50);
		subPanel.setLayout(grid);
		subPanel.add(new JPanel());
		subPanel.add(new JButton("+ NUEVO"));
		subPanel.add(new JButton("EXPORTAR CSV"));
		subPanel.add(new JButton("VOLVER"));
		panel.add(subPanel, BorderLayout.NORTH);

		// Tabla - CENTRO

		Object[] fila = { "hola1", "hola2" };
		modelo.addRow(fila);
		tabla.setModel(modelo);
		subPanel = new JPanel();
		subPanel.add(new JScrollPane(tabla));
		panel.add(subPanel, BorderLayout.CENTER);

		this.add(panel);
	}

	public static void main(String[] args) {
		new Futbolistas();
	}
}
