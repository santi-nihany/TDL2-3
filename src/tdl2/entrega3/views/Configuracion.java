package tdl2.entrega3.views;

import java.awt.*;

import javax.swing.*;

public class Configuracion extends JDialog {

	private JLabel lblUsuario;
	private JLabel lblContra;
	private JTextField tfUsuario;
	private JPasswordField pf;
	private JButton btnGuardar;

	public Configuracion() throws HeadlessException {
		super();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout());
		lblUsuario = new JLabel("        Usuario");
		this.add(lblUsuario);
		tfUsuario = new JTextField("", 14);
		this.add(tfUsuario);
		lblContra = new JLabel("Contraseña");
		this.add(lblContra);
		pf = new JPasswordField("", 14);
		pf.setEchoChar('●');
		this.add(pf);
		btnGuardar = new JButton("Guardar");
		this.add(btnGuardar);
		this.setTitle("Configuracion");
		this.setLocationRelativeTo(null);
		this.setSize(270, 130);
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public JPasswordField getPf() {
		return pf;
	}

}