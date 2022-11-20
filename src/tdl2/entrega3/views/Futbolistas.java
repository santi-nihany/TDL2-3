package tdl2.entrega3.views;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import tdl2.entrega3.DAO.FactoryDAO;
import tdl2.entrega3.DAO.implJDBC.FutbolistaDAOjdbc;
import tdl2.entrega3.classes.Futbolista;

public class Futbolistas extends JDialog {

	JPanel panel = new JPanel();

	private JTable tabla = new JTable();
	private String[] titulos = { "ID", "NOMBRE", "APELLIDO", "EDITAR", "ELIMINAR" };
	private Object[][] datos = {};
	FutbolistasTableModel modelo = new FutbolistasTableModel(datos, titulos);
	JButton tableButton = new JButton();
	JButton optionsButton;
	JButton btnVolver;
	IngresarFutbolista crearFutbView;
	IngresarFutbolista editarFutbView;
	FutbolistaDAOjdbc futDAO;

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
		/// Options Buttons
		optionsButton = new JButton("+ NUEVO");
		crearFutbView = new IngresarFutbolista("NUEVO FUTBOLISTA");
		optionsButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				crearFutbView.setVisible(true);
			}
		});
		subPanel.add(optionsButton);
		optionsButton = new JButton("EXPORTAR CSV");
		subPanel.add(optionsButton);
		btnVolver = new JButton("VOLVER");
		subPanel.add(btnVolver);
		panel.add(subPanel, BorderLayout.NORTH);

		// Tabla - CENTRO
		futDAO = FactoryDAO.getFutbolistaDAO();

		try {
			List<Futbolista> lista = futDAO.cargar();
			System.out.println(lista);
			for (Futbolista f : lista) {
				Object[] fila = { f.getID(), f.getNombre(), f.getApellido(), "EDITAR", "ELIMINAR" };
				modelo.addRow(fila);
			}
		} catch (SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
		}
		tabla.setModel(modelo);
		tabla.getColumn("EDITAR").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("EDITAR").setCellEditor(new ButtonEditor(new JCheckBox()));

		tabla.getColumn("ELIMINAR").setCellEditor(new ButtonEditor(new JCheckBox()));
		tabla.getColumn("ELIMINAR").setCellRenderer(new ButtonRenderer());

		subPanel = new JPanel();
		subPanel.add(new JScrollPane(tabla));
		panel.add(subPanel, BorderLayout.CENTER);

		editarFutbView = new IngresarFutbolista("EDITAR FUTBOLISTA");

		tableButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						if (tabla.getSelectedColumn() == 4) {
							String numberIdString = (tabla.getValueAt(tabla.getSelectedRow(), 0)).toString();
							int numberId = Integer.parseInt(numberIdString);
							try {
								Futbolista f = futDAO.encontrar(numberId);
								futDAO.eliminar(f);
							} catch (SQLException e) {
								System.err.println("Error de SQL: " + e.getMessage());

							}

						}
						if (tabla.getSelectedColumn() == 3) {
							editarFutbView.setVisible(true);
						}
					}
				});

		this.add(panel);

	}

	// CONFIGURACION DEL BOTON DE LA TABLA
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "Modify" : value.toString());
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		private String label;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value,
				boolean isSelected, int row, int column) {
			label = (value == null) ? "Modify" : value.toString();
			tableButton.setText(label);
			return tableButton;
		}

		public Object getCellEditorValue() {
			return new String(label);
		}
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public static void main(String[] args) {
		new Futbolistas();
	}
}
