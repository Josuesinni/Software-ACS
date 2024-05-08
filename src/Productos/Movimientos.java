package Productos;

import BaseDeDatos.Control.GestionProductos;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Movimientos extends JDialog {

    JPanel pnl;

    public Movimientos(boolean modal, String titulo) {
        super(vta, modal);
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setTitle(titulo);
        setSize(800, 440);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(pnl);
        setVisible(true);
    }
    JComboBoxCustom cmbFiltro;

    public void initComponents() {
        JTextFieldRounded txtBuscar = new JTextFieldRounded("Clave/Nombre del producto", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(50, 30);
        txtBuscar.setSize(260, 40);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //String filtro = cmbFiltro.getSelectedItem().toString();
                Miscelanea.CargarTabla(GestionProductos.vistaMovimientosProducto(txtBuscar.getText()), tblLista, false);
            }
        });
        pnl.add(txtBuscar);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(50, 330);
        btnAtras.setSize(60, 40);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnAtras);

        JButtonRounded btnAceptar = new JButtonRounded("ACEPTAR", 20, true);
        btnAceptar.setLocation(650, 330);
        btnAceptar.setSize(100, 40);
        btnAceptar.setFont(Recursos.FUENTE_BOTON_2);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnAceptar);
        tabla();
    }
    private JTable tblLista;

    public void tabla() {
        String[] cabecera = {"Clave", "Nombre del producto","Cantidad","Razon","Fecha"};
        Object[][] datos = {};
        int[] tamColumna = {10, 200, 15, 120, 80};
        boolean[] colEditables = {false, false, false, false, false};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumna, colEditables, 5);
        Miscelanea.CargarTabla(GestionProductos.vistaMovimientos(), tblLista, false);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(700, 200));
        jsp1.setPreferredSize(new java.awt.Dimension(700, 200));
        jsp1.setRowHeaderView(null);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblLista.getColumnModel().getColumnCount(); i++) {
            tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(50, 100);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        pnl.add(jsp1);
    }

}
