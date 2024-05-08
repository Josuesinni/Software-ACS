package Tabla;

import Tabla.AccionEnJTable;
import Tabla.JTableBotones;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;


public class CeldaPersonalizada extends DefaultCellEditor {

    private AccionEnJTable event;
    private int tipo;
    private Color bg;

    public CeldaPersonalizada(AccionEnJTable event, int tipo, Color c) {
        super(new JCheckBox());
        this.event = event;
        this.tipo = tipo;
        bg = c;
    }

    @Override
    public Component getTableCellEditorComponent(JTable tbl, Object o, boolean bln, int row, int column) {
        JTableBotones celdaBotones = new JTableBotones(tipo, bg);
        celdaBotones.initEvent(event, row);
        celdaBotones.setBackground(tbl.getSelectionBackground());
        return celdaBotones;
    }
}
