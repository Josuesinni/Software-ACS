package Buscadores;

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

public class BuscarProducto extends JDialog {

    JPanel pnl;
    JTable tblVenta;

    public BuscarProducto(boolean modal, String titulo, JTable tblVenta) {
        super(vta, modal);
        this.tblVenta = tblVenta;
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setTitle(titulo);
        setSize(600, 440);
        setLocationRelativeTo(null);
        setContentPane(pnl);
        setVisible(true);
    }
    JComboBoxCustom cmbFiltro;

    public void initComponents() {
        JTextFieldRounded txtBuscar = new JTextFieldRounded("Clave/Nombre del producto", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(30, 30);
        txtBuscar.setSize(260, 40);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtro = cmbFiltro.getSelectedItem().toString();
                Miscelanea.CargarTabla(GestionProductos.busquedaProducto(txtBuscar.getText(), filtro, true), tblLista, false);
            }
        });
        pnl.add(txtBuscar);

        JLabel lblFiltro = new JLabel("Filtro:", JLabel.RIGHT);
        lblFiltro.setLocation(340, 35);
        lblFiltro.setSize(100, 30);
        lblFiltro.setFont(Recursos.FUENTE_BOTON_2);
        pnl.add(lblFiltro);

        cmbFiltro = new JComboBoxCustom<>();
        cmbFiltro.setFont(getFuente(1, 1, 14));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ninguno", "Clave", "Nombre"}));
        cmbFiltro.setLocation(450, 35);
        cmbFiltro.setSize(100, 30);
        cmbFiltro.setBackground(new Color(255, 214, 153));
        ((JLabel) cmbFiltro.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        pnl.add(cmbFiltro);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(30, 330);
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
        btnAceptar.setLocation(450, 330);
        btnAceptar.setSize(100, 40);
        btnAceptar.setFont(Recursos.FUENTE_BOTON_2);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductoTablaVenta();
                dispose();
            }
        });
        pnl.add(btnAceptar);
        tabla();
    }
    private JTable tblLista;

    public void tabla() {
        String[] cabecera = {"Clave", "Nombre del producto"};
        Object[][] datos = {};
        int[] tamColumna = {60, 440};
        boolean[] colEditables = {false, false};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumna, colEditables, 5);
        Miscelanea.CargarTabla(GestionProductos.vistaBusquedaProductos(), tblLista, false);
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
        jsp1.getViewport().setBackground(tblLista.getBackground());
        pnl.add(jsp1);
    }

    private void addProductoTablaVenta() {
        int row = tblLista.getSelectedRow();
        if (!buscarIgualdades(tblLista.getValueAt(row, 1).toString())) {
            String[] productoSeleccionado = GestionProductos.productoSeleccionado(Integer.parseInt((String) tblLista.getValueAt(row, 0)));
            Object[] datos = new Object[]{productoSeleccionado[0], "$" + Double.valueOf(productoSeleccionado[1]), 1, "$" + 1 * Double.parseDouble(productoSeleccionado[1]), ""};
            DefaultTableModel dft = (DefaultTableModel) tblVenta.getModel();
            dft.addRow(datos);
            tblLista.setPreferredSize(new Dimension(900, dft.getRowCount() * 37));
        }
    }

    private boolean buscarIgualdades(String nombre) {
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            if (tblVenta.getValueAt(i, 0).toString().equals(nombre)) {
                int cantidad = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());
                if (cantidad < GestionProductos.cantidadProductoExistente(tblVenta.getValueAt(i, 0).toString())) {
                    //System.out.println("es aqui");
                    tblVenta.setValueAt(Integer.parseInt(tblVenta.getValueAt(i, 2).toString()) + 1, i, 2);
                    cantidad = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());
                    double precio = Double.parseDouble(tblVenta.getValueAt(i, 1).toString().substring(1));
                    tblVenta.setValueAt("$" + String.format("%.2f", cantidad * precio), i, 3);
                    return true;
                }
                return true;
            }
        }
        return false;
    }
}
