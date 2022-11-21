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
	JButton crearFButton;
	JButton exportCSVButton;
	JButton btnVolver;
	IngresarFutbolista crearFutbView;
	IngresarFutbolista editarFutbView;
	Eliminar eliminarView;
	FutbolistaDAOjdbc futDAO;

	Futbolistas() {
		// Dialog
		this.setTitle("Futbolistas");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);

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
		crearFButton = new JButton("+ NUEVO");
		subPanel.add(crearFButton);
		exportCSVButton = new JButton("EXPORTAR CSV");
		subPanel.add(exportCSVButton);
		btnVolver = new JButton("VOLVER");
		subPanel.add(btnVolver);
		panel.add(subPanel, BorderLayout.NORTH);

		/// Funcionalidad de crearFButton
		crearFButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				crearFutbView = new IngresarFutbolista("NUEVO FUTBOLISTA");
				crearFutbView.setVisible(true);
				// BOTON GUARDAR
				crearFutbView.getBtnGuardar().addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// CONDICIONES DE CAMPOS
						boolean ok = crearFutbView.getTfEmail().getText().contains("@") &&
								crearFutbView.getTfNombre().getText().matches("^[a-zA-Z]+$") &&
								crearFutbView.getTfApellido().getText().matches("^[a-zA-Z]+$") &&
								crearFutbView.getTfTelefono().getText().matches("^[0-9]+$") &&
								crearFutbView.getTfPais().getText().matches("^[a-zA-Z]+$");
						if (ok) {
							try {
								futDAO = FactoryDAO.getFutbolistaDAO();
								// creo nuevo futbolista
								Futbolista f = new Futbolista();
								// edito futbolista con campos
								f.setEmail(crearFutbView.getTfEmail().getText());
								f.setNombre(crearFutbView.getTfNombre().getText());
								f.setApellido(crearFutbView.getTfApellido().getText());
								f.setTelefono(Integer.valueOf(crearFutbView.getTfTelefono().getText()));
								f.setPais("argentina", "español");
								// guardo futbolista en base de datos
								futDAO.guardar(f);
								// agrego fila
								Object[] fila = { f.getID(), f.getNombre(), f.getApellido(), "EDITAR",
										"ELIMINAR" };
								modelo.addRow(fila);
							} catch (Exception err) {
								System.err.println("Error de SQL: " + err.getMessage());
							}
							crearFutbView.setVisible(false);
						}
					}
				});
			}
		});

		// TABLA - CENTRO
		futDAO = FactoryDAO.getFutbolistaDAO();

		try {
			List<Futbolista> lista = futDAO.cargar();
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

		eliminarView = new Eliminar();

		tableButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						// fila y columna seleccionada
						int row = (tabla.getSelectedRow() == -1) ? 0 : tabla.getSelectedRow();
						int column = tabla.getSelectedColumn();
						// obtengo id del futbolista
						String numberIdString = (tabla.getValueAt(row, 0)).toString();
						int numberId = Integer.parseInt(numberIdString);

						// COLUMNA ELIMINAR
						if (column == 4) {
							// activo vista eliminar
							eliminarView.setVisible(true);
							// SELECCIONA: SI
							eliminarView.getBtnYes().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									// desactivo vista eliminar
									eliminarView.setVisible(false);
									// ELIMINO FUTBOLISTA DE BD Y DE LA TABLA
									try {
										futDAO = FactoryDAO.getFutbolistaDAO();
										Futbolista f = futDAO.encontrar(numberId);
										futDAO = FactoryDAO.getFutbolistaDAO();
										futDAO.eliminar(f);
										modelo.removeRow(row);
									} catch (SQLException error) {
										System.err.println("Error de SQL: " + error.getMessage());
									}
								}
							});
							// SELECCIONA: NO
							eliminarView.getBtnNo().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									eliminarView.setVisible(false);
								}
							});

						}
						// COLUMNA EDITAR
						if (column == 3) {
							// FUTBOLISTA SELECCIONADO
							editarFutbView = new IngresarFutbolista("EDITAR FUTBOLISTA");
							try {
								futDAO = FactoryDAO.getFutbolistaDAO();
								Futbolista f = futDAO.encontrar(numberId);
								editarFutbView.getTfNombre().setText(f.getNombre());
								editarFutbView.getTfApellido().setText(f.getApellido());
								editarFutbView.getTfEmail().setText(f.getEmail());
								editarFutbView.getTfTelefono().setText(Integer.toString(f.getTelefono()));
								editarFutbView.getTfPais().setText(f.getPais().getNombre());
							} catch (SQLException error) {
								System.err.println("Error de SQL: " + error.getMessage());
							}
							editarFutbView.setVisible(true);
							// BOTON GUARDAR DE EDITAR JUGADOR
							editarFutbView.getBtnGuardar().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									// CONDICIONES DE CAMPOS
									boolean ok = editarFutbView.getTfEmail().getText().contains("@") &&
											editarFutbView.getTfNombre().getText().matches("^[a-zA-Z]+$") &&
											editarFutbView.getTfApellido().getText().matches("^[a-zA-Z]+$") &&
											editarFutbView.getTfTelefono().getText().matches("^[0-9]+$") &&
											editarFutbView.getTfPais().getText().matches("^[a-zA-Z]+$");
									if (ok) {
										try {
											// obtengo futbolista
											futDAO = FactoryDAO.getFutbolistaDAO();
											Futbolista f = futDAO.encontrar(numberId);
											// edito futbolista con campos
											f.setEmail(editarFutbView.getTfEmail().getText());
											f.setNombre(editarFutbView.getTfNombre().getText());
											f.setApellido(editarFutbView.getTfApellido().getText());
											f.setTelefono(Integer.valueOf(editarFutbView.getTfTelefono().getText()));
											// edito base de datos
											futDAO.editar(f, numberId);
											modelo.removeRow(row);
											Object[] fila = { f.getID(), f.getNombre(), f.getApellido(), "EDITAR",
													"ELIMINAR" };
											modelo.insertRow(row, fila);
										} catch (Exception err) {
											System.err.println("Error de SQL: " + err.getMessage());
										}
										editarFutbView.setVisible(false);
									}
								}
							});
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

	// BOTONES
	public JButton getBtnVolver() {
		return btnVolver;
	}

	public JButton getBtnEliminar() {
		return tableButton;
	}

	public static void main(String[] args) {
		new Futbolistas();
	}
}
