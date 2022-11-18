package tdl2.entrega3.components;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Futbolistas extends JDialog{
	
	JPanel panel = new JPanel();
	
	private JTable tabla= new JTable();
	private Object []titulos = {"Hola", "Hola2"};
	private DefaultTableModel modelo = new DefaultTableModel(titulos, 4);
	
	private Futbolistas () {
		 // Dialog
		 this.setTitle("Futbolistas");
		 this.setSize(400,200);
		 
		 // Panel principal
		 panel.setLayout(new BorderLayout());
		 
		// Tabla - CENTRO
		 tabla.setModel(modelo);
		 JPanel subPanel = new JPanel();
		 subPanel.add(new JScrollPane(tabla));
		 panel.add(subPanel, BorderLayout.CENTER); 
		 
		 this.add(panel);
		 this.setVisible(true);
	}
	
	public static void main (String[] args) {
		Futbolistas fulbo = new Futbolistas();
	}
}
