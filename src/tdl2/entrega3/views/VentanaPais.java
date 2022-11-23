package tdl2.entrega3.views;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import tdl2.entrega3.DAO.FactoryDAO;
import tdl2.entrega3.DAO.implJDBC.PaisDAOjdbc;
import tdl2.entrega3.classes.Pais;

public class VentanaPais extends JDialog {

	private JPanel panel;
	private JButton btnNuevo;
	private JButton btnVolver;
	private JButton tableButton = new JButton();
	private JTable tabla = new JTable();
	private JScrollPane scrollPane;
	private Object[][] datos = {};
	private String[] nombreCol = { "ID", "Nombre", "EDITAR", "ELIMINAR" };
	private Eliminar eliminarView;
	private IngresarPais ingresarPaisView;
	private IngresarPais editarPaisView;
	private PaisDAOjdbc paisDAO;
	private final VentanaPais v = this;

	public VentanaPais() {
		super();
		this.setTitle("Pais");
		this.setLocationRelativeTo(null);
		this.setSize(550, 400);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		// Agregar botones
		panel = new JPanel();
		btnVolver = new JButton("Volver");
		panel.setLayout(new GridLayout(1, 4));
		panel.add(new JPanel());
		this.setBtnNuevo();// check
		panel.add(btnVolver);
		panel.add(new JPanel());
		this.add(panel, BorderLayout.NORTH);
		// Tabla
		setTablaPaises(datos, nombreCol);

		scrollPane = new JScrollPane(tabla);
		this.add(scrollPane, BorderLayout.CENTER);

		setTableButton();

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

	// setTablaPaises
	public void setTablaPaises(Object[][] datos, String[] nombreCol) {
		PaisTableModel m = new PaisTableModel(datos, nombreCol);
		try {
			List<Pais> lista = FactoryDAO.getPaisDAO().cargar();
			for (Pais p : lista) {
				Object[] fila = { p.getId(), p.getNombre(), "EDITAR", "ELIMINAR" };
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

	public void setBtnNuevo() {
		btnNuevo = new JButton("+ Nuevo");
		btnNuevo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ingresarPaisView = new IngresarPais("Ingresar pais nuevo");
				ingresarPaisView.setLocationRelativeTo(null);
				ingresarPaisView.setVisible(true);
				ingresarPaisView.getBtnGuardar().addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Pais p = new Pais();
						p.setNombre(ingresarPaisView.getTfNombre().getText());
						p.setIdioma(ingresarPaisView.getTfIdioma().getText());
						boolean ok = ingresarPaisView.getTfNombre().getText().matches("^[a-zA-Z]+$") &&
								ingresarPaisView.getTfIdioma().getText().matches("^[a-zA-Z]+$");
						try {
							if ((ok) && (FactoryDAO.getPaisDAO().encontrar(p.getNombre()) == null)) {
								FactoryDAO.getPaisDAO().guardar(p);
								setTablaPaises(datos, nombreCol);
								ingresarPaisView.setVisible(false);
							} else {
								ingresarPaisView.getLblError().setForeground(Color.RED);
								if (!ok)
									ingresarPaisView.getLblError().setText("Nombre invalido");
								if (FactoryDAO.getPaisDAO().encontrar(p.getNombre()) != null)
									JOptionPane.showMessageDialog(null, "El pais ya existe");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
		});
		panel.add(btnNuevo);
	}

	private void setTableButton() {
		tableButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						// fila y columna seleccionada
						int row = (tabla.getSelectedRow() == -1) ? 0 : tabla.getSelectedRow();
						int column = tabla.getSelectedColumn();
						// obtengo id del futbolista
						System.out.println("ROW: " + row);
						String nombrePais = (tabla.getValueAt(row, 1)).toString();
						System.out.println("nombrePais: " + nombrePais);

						// COLUMNA ELIMINAR- check
						if (column == 3) {
							// activo vista eliminar
							eliminarView = new Eliminar();
							eliminarView.setVisible(true);
							// SELECCIONA: SI
							eliminarView.getBtnYes().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									// desactivo vista eliminar
									eliminarView.setVisible(false);
									// ELIMINO PAIS DE BD
									try {
										// encuentro pais
										paisDAO = FactoryDAO.getPaisDAO();
										Pais p = paisDAO.encontrar(nombrePais);
										// elimino pais de la base de datos
										paisDAO = FactoryDAO.getPaisDAO();
										paisDAO.eliminar(p);
										setTablaPaises(datos, nombreCol);
									} catch (SQLException error) {
										System.err.println("Error de SQL: " + error.getMessage());
									}
								}
							});
							// SELECCIONA: NO
							eliminarView.getBtnNo().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									setTablaPaises(datos, nombreCol);
									eliminarView.setVisible(false);
								}
							});
						}
						// COLUMNA EDITAR
						if (column == 2) {
							editarPaisView = new IngresarPais("EDITAR PAIS");
							try {
								paisDAO = FactoryDAO.getPaisDAO();
								Pais p = paisDAO.encontrar(nombrePais);
								// cargar vista de editar Pais con sus campos
								editarPaisView.getTfNombre().setText(p.getNombre());
								editarPaisView.getTfIdioma().setText(p.getIdioma());
							} catch (SQLException error) {
								System.err.println("Error de SQL: " + error.getMessage());
							}
							editarPaisView.setVisible(true);
							// BOTON GUARDAR DE EDITAR PAIS
							editarPaisView.getBtnGuardar().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									// CONDICIONES DE CAMPOS
									boolean ok = editarPaisView.getTfNombre().getText().matches("^[a-zA-Z]+$") &&
											editarPaisView.getTfIdioma().getText().matches("^[a-zA-Z]+$");
									if (ok) {
										try {
											// obtengo pais
											paisDAO = FactoryDAO.getPaisDAO();
											Pais p = paisDAO.encontrar(nombrePais);
											paisDAO = FactoryDAO.getPaisDAO();
											// edito Pais con campos
											p.setNombre(editarPaisView.getTfNombre().getText());
											p.setIdioma(editarPaisView.getTfIdioma().getText());
											// edito base de datos
											paisDAO.editar(p, p.getId());
											// cargo modelo y seteo tabla nuevamente
											setTablaPaises(datos, nombreCol);
										} catch (Exception err) {
											System.err.println("Error de SQL: " + err.getMessage());
										}
										editarPaisView.setVisible(false);
									}
								}
							});
						}
					}
				});

	}

}
