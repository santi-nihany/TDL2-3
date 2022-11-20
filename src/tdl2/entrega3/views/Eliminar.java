package tdl2.entrega3.views;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.*;

public class Eliminar extends JDialog {
    private JButton btnYes;
    private JButton btnNo;

    public Eliminar() {
        super();
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Desea eliminar ?"));
        btnYes = new JButton("Si");
        this.add(btnYes);
        btnNo = new JButton("No");
        this.add(btnNo);
        this.setLocationRelativeTo(null);
        this.setSize(270, 130);
    }

    public JButton getBtnYes() {
        return btnYes;
    }

    public JButton getBtnNo() {
        return btnNo;
    }

    public static void main(String[] args) {
        new Eliminar();
    }
}
