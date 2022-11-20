package tdl2.entrega3.views;

import javax.swing.table.DefaultTableModel;

public class FutbolistasTableModel extends DefaultTableModel {

    public FutbolistasTableModel(final Object[][] datos, final String[] titulos) {
        super(datos, titulos);
    }

    public Class getColumnClass(final int column) {
        return this.getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // all cells false
        return ((column == 3) || (column == 4));
    }

}
