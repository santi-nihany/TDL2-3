package tdl2.entrega3.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Configuracion extends JDialog {

	private JLabel lblUsuario;
	private JLabel lblContra;
	private JTextField tfUsuario;
	private JPasswordField pf;
	public JButton btnGuardar;
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
		/*btnGuardar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	this.setVisible(false);
                }
		});*/
		this.add(btnGuardar);
		this.setTitle("Configuracion");
		this.setLocationRelativeTo(null);
		this.setSize(270,130);
		
	}

}