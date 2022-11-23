package tdl2.entrega3.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class IngresarPais extends JDialog {
    private JLabel lblNombre;
    private JLabel lblIdioma;
    private JLabel lblError;
    private JTextField tfNombre;
    private JTextField tfIdioma;
    private JButton btnGuardar;
    private JPanel panelCentral;

    public IngresarPais(String titulo) {
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
        this.setSize(220, 135);

        panelCentral = new JPanel(new GridLayout(2, 3));
        lblNombre = new JLabel("Nombre: ");
        lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIdioma = new JLabel("Idioma: ");
        lblIdioma.setHorizontalAlignment(SwingConstants.RIGHT);
        tfNombre = new JTextField();
        tfIdioma = new JTextField();

        panelCentral.add(lblNombre);
        panelCentral.add(tfNombre);
        panelCentral.add(lblIdioma);
        panelCentral.add(tfIdioma);

        this.add(panelCentral, BorderLayout.CENTER);
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JTextField getTfIdioma() {
        return tfIdioma;
    }

    public void setTfIdioma(JTextField tfIdioma) {
        this.tfIdioma = tfIdioma;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JLabel getLblError() {
        return lblError;
    }

}
