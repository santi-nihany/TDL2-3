package tdl2.entrega3.views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MainFrame extends JFrame {

	private JPanel panel;
	private JButton btnConf;
	private JButton btnFutbolista;
	private JButton btnPais;

	public MainFrame() {
		// Frame
		super("Mundial Qatar 2022");
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		// Otras ventanas
		Configuracion confView = new Configuracion();
		Futbolistas futbolistasView = new Futbolistas();
		confView.getBtnGuardar().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				confView.setVisible(false);
			}
		});
		futbolistasView.getBtnVolver().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				futbolistasView.setVisible(false);
			}
		});
		// Panel General NORTE-CENTRO
		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// subPanel NORTE
		JPanel subPanel = new JPanel();
		GridLayout grid = new GridLayout(2, 4);
		grid.setHgap(15);
		subPanel.setLayout(grid);
		subPanel.add(new JPanel());
		subPanel.add(new JPanel());
		/// BOTON DE CONFIGURACION
		btnConf = new JButton("Configuracion");
		btnConf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				confView.setVisible(true);
			}
		});
		subPanel.add(btnConf);
		/// BOTON
		subPanel.add(new JButton("lala"));
		subPanel.add(new JPanel());
		subPanel.add(new JPanel());
		subPanel.add(new JPanel());
		subPanel.add(new JPanel());
		panel.add(subPanel, BorderLayout.NORTH);

		// subPanel CENTRO
		subPanel = new JPanel();
		grid = new GridLayout(2, 3);
		grid.setHgap(20);
		grid.setVgap(20);
		subPanel.setLayout(grid);
		// BOTON FUTBOLISTA
		btnFutbolista = new JButton("FUTBOLISTA");
		btnFutbolista.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				futbolistasView.setVisible(true);
			}
		});
		subPanel.add(btnFutbolista);
		// BOTON PAIS
		subPanel.add(new JButton("PAIS"));
		subPanel.add(new JButton("SIN DEFINIR"));
		subPanel.add(new JButton("SIN DEFINIR"));
		subPanel.add(new JButton("SIN DEFINIR"));
		subPanel.add(new JButton("SIN DEFINIR"));
		panel.add(subPanel, BorderLayout.CENTER);

		this.add(panel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
