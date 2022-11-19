package tdl2.entrega3.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame {

	private JPanel panel;
	private JButton btnConf;
	
	public MainFrame() {
		 // Frame
		 super("Mundial Qatar 2022");
		 this.setSize(400,200);
		 this.setLocationRelativeTo(null);
		 // Otras ventanas
		 Configuracion conf = new Configuracion();
		 conf.getBtnGuardar().addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	conf.setVisible(false);
	                }
		 });
		 // Panel General NORTE-CENTRO
		 panel = new JPanel();
		 panel.setLayout(new BorderLayout());
		 
		// subPanel NORTE
		JPanel subPanel = new JPanel();
		GridLayout grid= new GridLayout(2,4);
		grid.setHgap(15);
		subPanel.setLayout(grid);
		subPanel.add(new JPanel());
		subPanel.add(new JPanel());
		btnConf = new JButton("Configuracion");
		btnConf.addMouseListener(new MouseAdapter() {
	           public void mouseClicked(MouseEvent e) {
	               conf.setVisible(true);
	               }
		});
		/*try {
            InputStream inputStream = getClass().getResourceAsStream("/recursos/iconos/conf.png");
            Image img = ImageIO.read(inputStream);
            ImageIcon i = new ImageIcon(img);
            btnConf.setIcon(i);
        } catch (IOException e){
            System.err.println("Ocurrió un error durante la lectura del ícono");
            e.printStackTrace();
        } catch (IllegalArgumentException | NullPointerException e){
            System.err.println("El ícono no se encuentra en el directorio especificado");
            e.printStackTrace();
        }*/
		 subPanel.add(btnConf);
		 subPanel.add(new JButton("lala"));
		 subPanel.add(new JPanel());
		 subPanel.add(new JPanel());
		 subPanel.add(new JPanel());
		 subPanel.add(new JPanel());
		 panel.add(subPanel, BorderLayout.NORTH); 
		 
		 
		 // subPanel CENTRO
		 subPanel = new JPanel();
		 grid= new GridLayout(2,3);
		 grid.setHgap(20);
		 grid.setVgap(20);
		 subPanel.setLayout(grid);
		 subPanel.add(new JButton("FUTBOLISTA"));
		 subPanel.add(new JButton("PAIS"));
		 subPanel.add(new JButton("SIN DEFINIR"));
		 subPanel.add(new JButton("SIN DEFINIR"));
		 subPanel.add(new JButton("SIN DEFINIR"));
		 subPanel.add(new JButton("SIN DEFINIR"));
		 panel.add(subPanel, BorderLayout.CENTER);
		 this.add(panel);
	} 

}
