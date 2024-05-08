package Utilidades;

import SwingModificado.JTextFieldRounded;
import Tabla.JTableBotones;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;

public class Recursos {

    public static final Font FUENTE_TITULO = getFuente(0, 1, 48);
    public static final Font FUENTE_SUBTITULO = getFuente(1, 0, 24);
    public static final Font FUENTE_GENERAL = getFuente(0, 0, 16);
    public static final Font FUENTE_BOTON = getFuente(1, 0, 24);

    public static final Font FUENTE_TITULO_2 = getFuente(0, 1, 24);
    public static final Font FUENTE_GENERAL_2 = getFuente(0, 1, 14);
    public static final Font FUENTE_BOTON_2 = getFuente(1, 0, 14);

    public static final Color fondoGris = new Color(239, 239, 239);
    public static final Color fondoPiel = new Color(255, 244, 227);
    public static final Color letraDorada = new Color(192, 145, 64);
    public static final Color cafeCrema = new Color(229, 209, 169);

    public static Font getFuente(int fuente, int tipo, int size) {
        Font f;
        if (fuente == 0) {
            f = Fuente.fuenteNormal.deriveFont(tipo, size);
        } else {
            f = Fuente.fuenteBold.deriveFont(tipo, size);
        }
        return f;
    }

    public static JLabel tituloVentana(String text) {
        JLabel lblTitulo = new JLabel(text, JLabel.LEFT);
        lblTitulo.setLocation(100, 90);
        lblTitulo.setFont(FUENTE_TITULO);
        return lblTitulo;
    }

    public static JTable crearTabla(String[] cabecera, Object[][] datos, int tamColumna[], boolean[] colEditables, int tipo) {
        JTable tblLista = new JTable();
        tblLista.setFont(getFuente(0, 0, 16));
        tblLista.setModel(new javax.swing.table.DefaultTableModel(datos, cabecera) {
            boolean[] canEdit = colEditables;
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblLista.setSize(new java.awt.Dimension(900, 380));
        tblLista.setMinimumSize(new java.awt.Dimension(900, 380));
        tblLista.setPreferredSize(new java.awt.Dimension(900, 380));
        tblLista.setRowHeight(37);
        tblLista.getTableHeader().setReorderingAllowed(false);
        tblLista.setSelectionBackground(new Color(255, 221, 133));
        tblLista.setBackground(new Color(255, 244, 227));
        if (tblLista.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < tblLista.getColumnModel().getColumnCount(); i++) {
                tblLista.getColumnModel().getColumn(i).setResizable(false);
                tblLista.getColumnModel().getColumn(i).setPreferredWidth(tamColumna[i]);
            }
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < tblLista.getColumnModel().getColumnCount(); i++) {
            tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        if (tipo < 5) {
            tblLista.getColumnModel().getColumn(tblLista.getColumnModel().getColumnCount() - 1).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean bln1, int row, int column) {
                    Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);
                    JTableBotones celdaConBotones = new JTableBotones(tipo, new Color(255, 244, 227));
                    if (isSelected == false && row % 2 == 0) {
                        celdaConBotones.setBackground(new Color(255, 244, 227));
                    } else {
                        celdaConBotones.setBackground(com.getBackground());
                    }
                    return celdaConBotones;
                }
            });
        }

        tblLista.getTableHeader().setBackground(new Color(255, 214, 153));
        tblLista.getTableHeader().setFont(getFuente(0, 1, 14));
        tblLista.getTableHeader().setPreferredSize(new Dimension(570, 30));
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return tblLista;
    }

    public static void permitirSoloNumeros(JTextFieldRounded txt) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') e.consume();
            }
        });
    }

    public static void permitirSoloDobles(JTextFieldRounded txt) {
        AbstractDocument doc = (AbstractDocument) txt.getDocument();
        doc.setDocumentFilter(new DoubleDocumentFilter());
    }
    
    public static String getMesEnEsp(int mes) {
        String[] listaMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return listaMeses[mes];
    }
}
