package Productos;

import BaseDeDatos.Control.GestionProductos;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Tabla.AccionEnJTable;
import Tabla.CeldaPersonalizada;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class CatalogoProductos extends JPanel {

    private JComboBoxCustom cmbFiltro, cmbEstado;
    private JTable tblLista;

    public CatalogoProductos(Dimension d) {
        setSize(d);
        initComponents();
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }
    JTextFieldRounded txtBuscar;

    public void initComponents() {
        JLabel lblTitulo = Recursos.tituloVentana("CÁTALOGO DE PRODUCTOS");
        lblTitulo.setSize(800, 60);
        add(lblTitulo);

        txtBuscar = new JTextFieldRounded("Buscar por nombre, clave, etc...", 20, getFuente(0, 0, 16));
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.setBackground(new Color(239, 239, 239));
        txtBuscar.setBorder(null);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtro = cmbFiltro.getSelectedItem().toString();
                Miscelanea.CargarTabla(GestionProductos.busquedaProducto(txtBuscar.getText(), filtro), tblLista, true);
                setSignoPeso();
            }
        });
        add(txtBuscar);

        JLabel lblFiltro = new JLabel("Filtro:", JLabel.LEFT);
        lblFiltro.setLocation(650, 180);
        lblFiltro.setSize(800, 50);
        lblFiltro.setFont(getFuente(0, 1, 16));
        add(lblFiltro);

        cmbFiltro = new JComboBoxCustom<>();
        cmbFiltro.setFont(getFuente(1, 1, 16));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ninguno", "Clave", "Nombre", "Categoria", "Anfitrión"}));
        cmbFiltro.setLocation(700, 187);
        cmbFiltro.setSize(140, 36);
        cmbFiltro.setBackground(new Color(255, 214, 153));
        cmbFiltro.setColorOver(new Color(240, 200, 120));
        cmbFiltro.setMaximumRowCount(3);
        add(cmbFiltro);

        JLabel lblEstado = new JLabel("Estado:", JLabel.RIGHT);
        lblEstado.setLocation(1120, 180);
        lblEstado.setSize(120, 50);
        lblEstado.setFont(getFuente(0, 1, 16));
        add(lblEstado);

        cmbEstado = new JComboBoxCustom<>();
        cmbEstado.setFont(getFuente(1, 1, 16));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Cualquiera", "Inactivo", "Activo"}));
        cmbEstado.setLocation(1280, 187);
        cmbEstado.setSize(140, 36);
        cmbEstado.setBackground(new Color(255, 214, 153));
        cmbEstado.setColorOver(new Color(240, 200, 120));
        cmbEstado.setMaximumRowCount(3);
        cmbEstado.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (cmbEstado.getSelectedItem().toString().equals("Inactivo") || cmbEstado.getSelectedItem().toString().equals("Activo")) {
                    Miscelanea.CargarTabla(GestionProductos.vistaProductosPorEstado(cmbEstado.getSelectedItem().toString()), tblLista, true);
                } else {
                    Miscelanea.CargarTabla(GestionProductos.vistaProductos(), tblLista, true);
                }
                setSignoPeso();
            }
        });
        add(cmbEstado);

        tabla();

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(100, 680);
        btnAtras.setSize(80, 50);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.panelAnterior(cp.panelActual());
            }
        });
        add(btnAtras);

        JButtonRounded btnMovimientos = new JButtonRounded("VER MOVIMIENTOS", 20, true);
        btnMovimientos.setLocation(730, 675);
        btnMovimientos.setSize(300, 60);
        btnMovimientos.setFont(Recursos.FUENTE_BOTON);
        btnMovimientos.setBorderPainted(false);
        btnMovimientos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movimientos mv = new Movimientos(true, "Movimientos");
            }
        });
        add(btnMovimientos);

        JButtonRounded btnNuevoRegistro = new JButtonRounded("NUEVO REGISTRO", 20, true);
        btnNuevoRegistro.setLocation(1130, 675);
        btnNuevoRegistro.setSize(300, 60);
        btnNuevoRegistro.setFont(Recursos.FUENTE_BOTON);
        btnNuevoRegistro.setBorderPainted(false);
        btnNuevoRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoCyE pcye = new ProductoCyE(true, true, null);
                if (pcye.isCambiosGenerados) {
                    if (cmbEstado.getSelectedItem().toString().equals("Inactivo") || cmbEstado.getSelectedItem().toString().equals("Activo")) {
                        Miscelanea.CargarTabla(GestionProductos.vistaProductosPorEstado(cmbEstado.getSelectedItem().toString()), tblLista, true);
                    } else {
                        Miscelanea.CargarTabla(GestionProductos.vistaProductos(), tblLista, true);
                    }
                    setSignoPeso();
                    txtBuscar.setText("");
                }
            }
        });
        add(btnNuevoRegistro);
    }

    public void tabla() {
        Object[][] datos = {{}};
        String[] cabecera = new String[]{"Clave", "Nombre del producto", "Precio", "Uds.", "Categoría", "Anfitrión", "Estado", ""};
        boolean[] colEditables = {false, false, false, false, false, false, false, true};
        int[] tamColumnas = {10, 300, 40, 30, 80, 80, 25, 80};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 2);

        Miscelanea.CargarTabla(GestionProductos.vistaProductos(), tblLista, true);
        setSignoPeso();
        AccionTabla(tblLista, 2);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setOpaque(false);
        jsp1.setSize(new java.awt.Dimension(getWidth() - 200, 380));
        jsp1.setPreferredSize(new java.awt.Dimension(getWidth() - 200, 380));
        jsp1.setRowHeaderView(null);

        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37 * 9);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(100, 250);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        add(jsp1);
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
                Object[] datos = new Object[tblLista.getColumnCount() - 1];
                for (int i = 0; i < tblLista.getColumnCount() - 1; i++) {
                    datos[i] = tblLista.getValueAt(row, i);
                }
                datos[2] = datos[2].toString().substring(1);
                ProductoCyE pcye = new ProductoCyE(true, false, datos);
                if (pcye.isCambiosGenerados) {
                    if (cmbEstado.getSelectedItem().toString().equals("Inactivo") || cmbEstado.getSelectedItem().toString().equals("Activo")) {
                        Miscelanea.CargarTabla(GestionProductos.vistaProductosPorEstado(cmbEstado.getSelectedItem().toString()), tblLista, true);
                    } else {
                        Miscelanea.CargarTabla(GestionProductos.vistaProductos(), tblLista, true);
                    }
                    setSignoPeso();
                    txtBuscar.setText("");
                }
                tblLista.getSelectionModel().setSelectionInterval(row, row);
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está segura de eliminar el producto " + tblLista.getValueAt(row, 1) + "?", true);
                if (n.getRespuesta()) {
                    if (GestionProductos.eliminarProducto(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                        new Notificacion(0, "El producto se ha eliminado correctamente", false);
                        if (tblLista.isEditing()) {
                            tblLista.getCellEditor().stopCellEditing();
                        }
                        if (cmbEstado.getSelectedItem().toString().equals("Inactivo") || cmbEstado.getSelectedItem().toString().equals("Activo")) {
                            Miscelanea.CargarTabla(GestionProductos.vistaProductosPorEstado(cmbEstado.getSelectedItem().toString()), tblLista, true);
                        } else {
                            Miscelanea.CargarTabla(GestionProductos.vistaProductos(), tblLista, true);
                        }
                        setSignoPeso();
                        txtBuscar.setText("");
                    }
                    tblLista.getSelectionModel().setSelectionInterval(row, row);
                }
            }

            @Override
            public void visualizar(int row) {
            }

            @Override
            public void verHistorial(int row) {
            }

            @Override
            public void ajustar(int row) {
                String datos[] = new String[]{(String) tblLista.getValueAt(row, 0), (String) tblLista.getValueAt(row, 1), (String) tblLista.getValueAt(row, 3)};
                AjustarCantidad ajuste = new AjustarCantidad(true, datos);
                if (ajuste.isAjusteRealizado) {
                    System.out.println("Ajuste realizado");
                    if (cmbEstado.getSelectedItem().toString().equals("Inactivo") || cmbEstado.getSelectedItem().toString().equals("Activo")) {
                        Miscelanea.CargarTabla(GestionProductos.vistaProductosPorEstado(cmbEstado.getSelectedItem().toString()), tblLista, true);
                    } else {
                        Miscelanea.CargarTabla(GestionProductos.vistaProductos(), tblLista, true);
                    }
                    setSignoPeso();
                    txtBuscar.setText("");
                }
                tblLista.getSelectionModel().setSelectionInterval(row, row);
            }
        };
        tblLista.getColumnModel().getColumn(tblLista.getColumnModel().getColumnCount() - 1).setCellEditor(new CeldaPersonalizada(event, tipo, new Color(255, 244, 227)));
    }

    public void setSignoPeso() {
        
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 2), i, 2);
        }
    }
}
