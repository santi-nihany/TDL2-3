package tdl2.entrega3.views;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import tdl2.entrega3.DAO.FactoryDAO;
import tdl2.entrega3.DAO.implJDBC.PaisDAOjdbc;
import tdl2.entrega3.classes.Pais;

public class IngresarFutbolista extends JDialog {
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JLabel lblPais;
    private JLabel lblError;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfEmail;
    private JTextField tfTelefono;
    private JTextField tfPais;
    private JButton btnGuardar;
    private JPanel panelCentral;
    private JComboBox<String> cbPaises;
    private String nombresPaises[];

    public IngresarFutbolista(String titulo) {
        super();
        this.setLocationRelativeTo(null);
        this.setTitle(titulo);
        this.setLayout(new BorderLayout());
        lblError = new JLabel("Ingrese los datos");
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblError, BorderLayout.NORTH);
        this.add(new JPanel(), BorderLayout.EAST);
        this.add(new JPanel(), BorderLayout.WEST);
        btnGuardar = new JButton("Guardar");
        this.add(btnGuardar, BorderLayout.SOUTH);

        panelCentral = new JPanel(new GridLayout(5, 2));
        lblNombre = new JLabel("Nombre: ");
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblApellido = new JLabel("Apellido: ");
        lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEmail = new JLabel("Email: ");
        lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTelefono = new JLabel("Telefono: ");
        lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPais = new JLabel("Pais: ");
        lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
        tfNombre = new JTextField();
        tfApellido = new JTextField();
        tfEmail = new JTextField();
        tfTelefono = new JTextField();
        tfPais = new JTextField();
        nombresPaises = cargarPaises();
        cbPaises = new JComboBox<String>(nombresPaises);

        panelCentral.add(lblNombre);
        panelCentral.add(tfNombre);
        panelCentral.add(lblApellido);
        panelCentral.add(tfApellido);
        panelCentral.add(lblEmail);
        panelCentral.add(tfEmail);
        panelCentral.add(lblTelefono);
        panelCentral.add(tfTelefono);
        panelCentral.add(lblPais);
        panelCentral.add(cbPaises);

        this.add(panelCentral, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setSize(300, 200);
    }

    public String[] cargarPaises() {
        ArrayList<String> listaPaises = new ArrayList<String>();
        PaisDAOjdbc paisDAO = FactoryDAO.getPaisDAO();
        try {
            List<Pais> lista = paisDAO.cargar();
            for (Pais p : lista) {
                listaPaises.add(p.getNombre());
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
        String[] arregloPaises = {};
        arregloPaises = listaPaises.toArray(arregloPaises);
        return arregloPaises;
    }

    public JComboBox<String> getCbPaises() {
        return cbPaises;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JTextField getTfApellido() {
        return tfApellido;
    }

    public void setTfApellido(JTextField tfApellido) {
        this.tfApellido = tfApellido;
    }

    public JTextField getTfEmail() {
        return tfEmail;
    }

    public void setTfEmail(JTextField tfEmail) {
        this.tfEmail = tfEmail;
    }

    public JTextField getTfTelefono() {
        return tfTelefono;
    }

    public void setTfTelefono(JTextField tfTelefono) {
        this.tfTelefono = tfTelefono;
    }

    public JTextField getTfPais() {
        return tfPais;
    }

    public void setTfPais(JTextField tfPais) {
        this.tfPais = tfPais;
    }

    public JLabel getLblError() {
        return lblError;
    }
}