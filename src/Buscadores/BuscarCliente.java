package Buscadores;

import BaseDeDatos.Control.GestionClientes;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class BuscarCliente extends JDialog {

    JPanel pnl;

    public BuscarCliente(boolean modal, String titulo) {
        super(vta, modal);
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setTitle(titulo);
        setSize(600, 440);
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    JComboBoxCustom cmbFiltro;
    public void initComponents() {
        JTextFieldRounded txtBuscar = new JTextFieldRounded("Nombre/Cuenta de Instagram", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(30, 30);
        txtBuscar.setSize(260, 40);
        txtBuscar.setBackground(new Color(239, 232, 232));
        txtBuscar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String filtro=cmbFiltro.getSelectedItem().toString();
                GestionClientes.busquedaCliente(txtBuscar.getText(),tblLista,filtro);
            }

        });
        pnl.add(txtBuscar);

        JLabel lblFiltro = new JLabel("Filtro:", JLabel.RIGHT);
        lblFiltro.setLocation(300, 35);
        lblFiltro.setSize(100, 30);
        lblFiltro.setFont(getFuente(1, 0, 14));
        pnl.add(lblFiltro);

        cmbFiltro = new JComboBoxCustom<>();
        cmbFiltro.setFont(getFuente(1, 1, 14));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ninguno", "Nombre", "Instagram"}));
        cmbFiltro.setLocation(410, 35);
        cmbFiltro.setSize(140, 30);
        cmbFiltro.setBackground(new Color(255, 214, 153));
        ((JLabel) cmbFiltro.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        pnl.add(cmbFiltro);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(30, 330);
        btnAtras.setSize(60, 40);
        btnAtras.setFont(Recursos.FUENTE_BOTON_2);
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
        btnAceptar.setLocation(450, 330);
        btnAceptar.setSize(100, 40);
        btnAceptar.setFont(Recursos.FUENTE_BOTON_2);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion = true;
                int row = tblLista.getSelectedRow();
                nombreCliente = tblLista.getValueAt(row, 0).toString();
                dispose();
            }
        });
        pnl.add(btnAceptar);

        tabla();
    }
    String nombreCliente = "";
    boolean accion = false;
    JTable tblLista;

    public boolean getRespuesta() {
        return accion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void tabla() {
        String[] cabecera = {"Nombre del cliente", "Cuenta de Instagram"};
        Object[][] datos = {};
        int[] tamColumna = {320, 180};
        boolean[] colEditables = {false, false};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumna, colEditables, 5);
        GestionClientes.vistaClientes(tblLista);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(500, 200));
        jsp1.setPreferredSize(new java.awt.Dimension(500, 200));
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
        pnl.add(jsp1);
    }
}