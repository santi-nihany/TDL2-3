package tdl2.entrega3.views;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import tdl2.entrega3.DAO.FactoryDAO;
import tdl2.entrega3.DAO.implJDBC.FutbolistaDAOjdbc;
import tdl2.entrega3.DAO.implJDBC.PaisDAOjdbc;
import tdl2.entrega3.classes.Futbolista;

public class Futbolistas extends JDialog {

	JPanel panel = new JPanel();

	private JTable tabla = new JTable();
	private String[] titulos = { "ID", "NOMBRE", "APELLIDO", "EDITAR", "ELIMINAR" };
	private Object[][] datos = {};
	private FutbolistasTableModel modelo;
	private JButton tableButton = new JButton();
	private JButton crearFButton;
	private JButton exportCSVButton;
	private JButton btnVolver;
	private IngresarFutbolista crearFutbView;
	private IngresarFutbolista editarFutbView;
	private Eliminar eliminarView;
	private FutbolistaDAOjdbc futDAO;
	private PaisDAOjdbc paisDAO;

	Futbolistas() {
		// Dialog
		this.setTitle("Futbolistas");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

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
		setCrearFButton();

		// TABLA - CENTRO
		setTablaJugadores(datos, titulos);

		subPanel = new JPanel();
		subPanel.add(new JScrollPane(tabla));
		panel.add(subPanel, BorderLayout.CENTER);

		// setear evento table button
		setTableButton();

		this.add(panel);

	}

	// BOTON CREAR JUGADOR
	private void setCrearFButton() {
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
								crearFutbView.getTfTelefono().getText().matches("^[0-9]+$");
						if (ok) {
							try {
								futDAO = FactoryDAO.getFutbolistaDAO();
								paisDAO = FactoryDAO.getPaisDAO();
								// creo nuevo futbolista
								Futbolista f = new Futbolista();
								// edito futbolista con campos
								f.setEmail(crearFutbView.getTfEmail().getText());
								f.setNombre(crearFutbView.getTfNombre().getText());
								f.setApellido(crearFutbView.getTfApellido().getText());
								f.setTelefono(Integer.valueOf(crearFutbView.getTfTelefono().getText()));
								String nombrePais = (String) crearFutbView.getCbPaises().getSelectedItem();
								f.setPais(paisDAO.encontrar(nombrePais));
								// guardo futbolista en base de datos
								futDAO.guardar(f);
								// cargo nuevo modelo y seteo tabla
								setTablaJugadores(datos, titulos);
							} catch (Exception err) {
								System.err.println("Error de SQL: " + err.getMessage());
							}
							crearFutbView.setVisible(false);
						} else {
							crearFutbView.getLblError().setForeground(Color.RED);
							crearFutbView.getLblError().setText("Campos inválidos");
						}
					}
				});
			}
		});

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

	// setTabla
	private void setTablaJugadores(Object[][] datos, String[] titulos) {
		FutbolistasTableModel m = new FutbolistasTableModel(datos, titulos);
		FutbolistaDAOjdbc futDAO = FactoryDAO.getFutbolistaDAO();

		try {
			List<Futbolista> lista = futDAO.cargar();
			for (Futbolista f : lista) {
				Object[] fila = { f.getID(), f.getNombre(), f.getApellido(), "EDITAR", "ELIMINAR" };
				m.addRow(fila);
			}
		} catch (SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage());
		}
		tabla.setModel(m);
		tabla.getColumn("EDITAR").setCellRenderer(new ButtonRenderer());
		tabla.getColumn("EDITAR").setCellEditor(new ButtonEditor(new JCheckBox()));

		tabla.getColumn("ELIMINAR")
				.setCellEditor(new ButtonEditor(new JCheckBox()));
		tabla.getColumn("ELIMINAR").setCellRenderer(new ButtonRenderer());
	}

	// set Table Button
	private void setTableButton() {
		tableButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						// fila y columna seleccionada
						int row = (tabla.getSelectedRow() == -1) ? 0 : tabla.getSelectedRow();
						int column = tabla.getSelectedColumn();
						// obtengo id del futbolista
						System.out.println("ROW: " + row);
						String numberIdString = (tabla.getValueAt(row, 0)).toString();
						int numberId = Integer.valueOf(numberIdString);
						System.out.println("numberID: " + numberId);

						// COLUMNA ELIMINAR
						if (column == 4) {
							// activo vista eliminar
							eliminarView = new Eliminar();
							eliminarView.setVisible(true);
							// SELECCIONA: SI
							eliminarView.getBtnYes().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									// desactivo vista eliminar
									eliminarView.setVisible(false);
									// ELIMINO FUTBOLISTA DE BD
									try {
										// encuentro futbolista
										futDAO = FactoryDAO.getFutbolistaDAO();
										Futbolista f = futDAO.encontrar(numberId);
										// elimino futbolista de la base de datos
										futDAO = FactoryDAO.getFutbolistaDAO();
										futDAO.eliminar(f);
										setTablaJugadores(datos, titulos);
									} catch (SQLException error) {
										System.err.println("Error de SQL: " + error.getMessage());
									}
								}
							});
							// SELECCIONA: NO
							eliminarView.getBtnNo().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									setTablaJugadores(datos, titulos);
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
								// cargar vista de editar futbolista con sus campos
								editarFutbView.getTfNombre().setText(f.getNombre());
								editarFutbView.getTfApellido().setText(f.getApellido());
								editarFutbView.getTfEmail().setText(f.getEmail());
								editarFutbView.getTfTelefono().setText(Integer.toString(f.getTelefono()));
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
											editarFutbView.getTfTelefono().getText().matches("^[0-9]+$");
									if (ok) {
										try {
											// obtengo futbolista
											futDAO = FactoryDAO.getFutbolistaDAO();
											Futbolista f = futDAO.encontrar(numberId);
											paisDAO = FactoryDAO.getPaisDAO();
											// edito futbolista con campos
											f.setEmail(editarFutbView.getTfEmail().getText());
											f.setNombre(editarFutbView.getTfNombre().getText());
											f.setApellido(editarFutbView.getTfApellido().getText());
											f.setTelefono(Integer.valueOf(editarFutbView.getTfTelefono().getText()));
											String nombrePais = (String) editarFutbView.getCbPaises().getSelectedItem();
											f.setPais(paisDAO.encontrar(nombrePais));
											// edito base de datos
											futDAO.editar(f, numberId);
											// cargo modelo y seteo tabla nuevamente
											setTablaJugadores(datos, titulos);
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
