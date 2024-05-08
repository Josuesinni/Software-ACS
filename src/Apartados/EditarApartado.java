package Apartados;

import BaseDeDatos.Control.GestionApartados;
import BaseDeDatos.Control.GestionClientes;
import BaseDeDatos.Control.GestionPagos;
import BaseDeDatos.Control.GestionProductos;
import BaseDeDatos.Control.Miscelanea;
import Buscadores.Buscador;
import Buscadores.BuscarCliente;
import Buscadores.BuscarProducto;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JPanelRounded;
import SwingModificado.JSeparatorCustom;
import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Tabla.AccionEnJTable;
import Tabla.CantidadCellEditor;
import Tabla.CeldaPersonalizada;
import Tabla.EventCellInputChange;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class EditarApartado extends JPanel {

    JTable tblLista;
    JLabel lblPdte;
    JLabel lblTotal;
    JLabel lblPgdo;
    JLabel lblAnticipo;
    String folio;
    JTextFieldRounded txtBuscar;
    JTextFieldRounded txtCliente;
    Buscador buscadorProductos, buscadorClientes;

    public EditarApartado(Dimension d, String folio) {
        this.folio = folio;
        setSize(d);
        initComponents();
        addSignoTabla();
        calcularTotal();
        calcularAnticipo();
        calcularPagado();
        calcularPendiente();
        setLayout(new BorderLayout());
        setBackground(Color.white);
        productosEliminados = new String[tblLista.getRowCount()];
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("EDITAR APARTADO");
        lblTitulo.setSize(500, 60);
        add(lblTitulo);

        txtBuscar = new JTextFieldRounded("Clave/Nombre del producto", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 10) {
                    String producto = txtBuscar.getText();
                    if (GestionProductos.existeProducto(producto)) {
                        if (!buscarIgualdades(producto)) {
                            GestionProductos.addProductoLista(producto, tblLista);
                            tblLista.setPreferredSize(new Dimension(900, tblLista.getRowCount() * 37));
                        } else {
                            actualizarSubtotal();
                        }
                        actualizarTotal();
                        //actualizarCambio();
                        txtBuscar.setText("");
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (!txtBuscar.getText().isEmpty()) {
                    if (e.getKeyCode() != 10) {
                        buscadorProductos.actualizarLista(GestionProductos.busquedaProducto(txtBuscar.getText()));
                    }
                } else {
                    buscadorProductos.hidePopUp();
                }

            }
        });
        add(txtBuscar);

        buscadorProductos = new Buscador();
        buscadorProductos.changePopupSize(460, 100);
        buscadorProductos.setBuscar(txtBuscar); 
/* public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 10) {
                    String producto = txtBuscar.getText();
                    if (GestionProductos.existeProducto(producto)) {
                        if (!buscarIgualdades(producto)) {
                            GestionProductos.addProductoLista(producto, tblLista);
                            tblLista.setPreferredSize(new Dimension(900, tblLista.getRowCount() * 37));
                        } else {
                            actualizarSubtotal();
                        }
                        actualizarTotal();
                        txtBuscar.setText("");
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (!txtBuscar.getText().isEmpty()) {
                    if (e.getKeyCode() != 10) {
                        buscadorProductos.actualizarLista(GestionProductos.busquedaProducto(txtBuscar.getText()));
                    }
                } else {
                    buscadorProductos.hidePopUp();
                }

            }
        });
        add(txtBuscar);

        buscadorProductos = new Buscador();
        buscadorProductos.changePopupSize(460, 100);
        buscadorProductos.setBuscar(txtBuscar); */

        JButtonRounded btnBuscar = new JButtonRounded(20);
        btnBuscar.setLocation(560, 180);
        btnBuscar.setSize(50, 50);
        btnBuscar.setBackground(new Color(255, 244, 227));
        btnBuscar.setColor(new Color(255, 244, 227));
        btnBuscar.setColorOver(new Color(255, 250, 230));
        btnBuscar.setColorClick(new Color(240, 220, 210));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/buscar_producto.png")));
        btnBuscar.setIconPosition(0);
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarProducto bp = new BuscarProducto(true, "Editar Apartado - Buscar producto", tblLista);
                actualizarTotal();
            }
        });
        add(btnBuscar);

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
    }

    public void panelDerecho() {

        txtCliente = new JTextFieldRounded("Nombre del cliente", 20, Recursos.FUENTE_GENERAL);
        txtCliente.setLocation(1100, 180);
        txtCliente.setSize(280, 50);
        txtCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!txtCliente.getText().isEmpty()) {
                    buscadorClientes.actualizarLista(GestionClientes.busquedaCliente(txtCliente.getText(), "nombre"));
                }
                if (txtCliente.getText().isEmpty()) {
                    buscadorProductos.hidePopUp();
                }
            }
        });
        add(txtCliente);
        buscadorClientes = new Buscador();
        buscadorClientes.changePopupSize(280, 60);
        buscadorClientes.setBuscar(txtCliente);

        JButtonRounded btnBuscarCliente = new JButtonRounded(20);
        btnBuscarCliente.setLocation(1380, 180);
        btnBuscarCliente.setSize(50, 50);
        btnBuscarCliente.setBackground(new Color(255, 244, 227));
        btnBuscarCliente.setColor(new Color(255, 244, 227));
        btnBuscarCliente.setColorOver(new Color(255, 250, 230));
        btnBuscarCliente.setColorClick(new Color(240, 220, 210));
        btnBuscarCliente.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/buscar_clientes.png")));
        btnBuscarCliente.setIconPosition(0);
        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarCliente bp = new BuscarCliente(true, "Apartado - Buscar cliente");
                txtCliente.setText(bp.getNombreCliente());
            }
        });
        add(btnBuscarCliente);

        JPanelRounded pr = new JPanelRounded(20);
        pr.setSize(330, 240);
        pr.setOpaque(false);
        pr.setLocation(1100, 250);
        pr.setBackground(new Color(255, 244, 227));

        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(20, 10);
        lblTotalTxt.setSize(80, 50);
        lblTotalTxt.setFont(getFuente(1, 0, 24));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pr.add(lblTotalTxt);

        lblTotal = new JLabel("$0.00", JLabel.RIGHT);
        lblTotal.setLocation(100, 10);
        lblTotal.setSize(210, 50);
        lblTotal.setFont(getFuente(1, 0, 24));
        pr.add(lblTotal);

        JLabel lblTxtAnticipo = new JLabel("ABONADO", JLabel.LEFT);
        lblTxtAnticipo.setLocation(20, 60);
        lblTxtAnticipo.setSize(130, 50);
        lblTxtAnticipo.setFont(getFuente(1, 0, 20));
        lblTxtAnticipo.setForeground(new Color(192, 145, 64));
        pr.add(lblTxtAnticipo);

        lblAnticipo = new JLabel("$0.00", JLabel.RIGHT);
        lblAnticipo.setLocation(100, 60);
        lblAnticipo.setSize(210, 50);
        lblAnticipo.setFont(getFuente(1, 0, 20));
        pr.add(lblAnticipo);
        //Horizontal
        JSeparatorCustom jsHorizontal = new JSeparatorCustom(0, 4);
        jsHorizontal.setForeground(new Color(229, 209, 169));
        jsHorizontal.setBackground(Recursos.fondoPiel);
        jsHorizontal.setSize(300, 4);
        jsHorizontal.setLocation(15, 120);
        pr.add(jsHorizontal);
        //Vertical
        JSeparatorCustom jsVertical = new JSeparatorCustom(1, 4);
        jsVertical.setForeground(new Color(229, 209, 169));
        jsVertical.setBackground(Recursos.fondoPiel);
        jsVertical.setSize(4, 80);
        jsVertical.setLocation(330 / 2 - 2, 140);
        pr.add(jsVertical);

        JLabel lblPgdoTxt = new JLabel("Pagado", JLabel.LEFT);
        lblPgdoTxt.setLocation(25, 140);
        lblPgdoTxt.setSize(160, 20);
        lblPgdoTxt.setFont(getFuente(1, 0, 16));
        pr.add(lblPgdoTxt);

        lblPgdo = new JLabel("$0.00", JLabel.LEFT);
        lblPgdo.setLocation(30, 170);
        lblPgdo.setSize(160, 30);
        lblPgdo.setFont(getFuente(1, 0, 22));
        pr.add(lblPgdo);

        JLabel lblPdteTxt = new JLabel("Pendiente", JLabel.LEFT);
        lblPdteTxt.setLocation(190, 140);
        lblPdteTxt.setSize(160, 20);
        lblPdteTxt.setFont(getFuente(1, 0, 16));
        pr.add(lblPdteTxt);

        lblPdte = new JLabel("$0.00", JLabel.LEFT);
        lblPdte.setLocation(195, 170);
        lblPdte.setSize(160, 30);
        lblPdte.setFont(getFuente(1, 0, 22));
        pr.add(lblPdte);

        pr.setLayout(new BorderLayout(10, 10));
        add(pr);

        JLabel lblEstado = new JLabel("Estado", JLabel.LEFT);
        lblEstado.setLocation(1140, 520);
        lblEstado.setSize(100, 40);
        lblEstado.setFont(Recursos.FUENTE_BOTON);
        add(lblEstado);

        JComboBoxCustom cmbEstado = new JComboBoxCustom<>();
        cmbEstado.setFont(getFuente(1, 1, 16));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Activo", "Cancelado"}));
        cmbEstado.setLocation(1250, 520);
        cmbEstado.setSize(140, 40);
        cmbEstado.setBackground(new Color(255, 214, 153));
        cmbEstado.setColorOver(new Color(255, 224, 163));
        ((JLabel) cmbEstado.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        add(cmbEstado);

        JButtonRounded btnGuardarCambios = new JButtonRounded("GUARDAR CAMBIOS", 20, true);
        btnGuardarCambios.setLocation(1120, 675);
        btnGuardarCambios.setSize(300, 60);
        btnGuardarCambios.setFont(Recursos.FUENTE_BOTON);
        btnGuardarCambios.setBorderPainted(false);
        btnGuardarCambios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operacion()) {
                    new Notificacion(0, "Cambios guardados exitosamente", false);
                } else {
                    new Notificacion(1, "Error en la base de datos al realizar cambios", false);
                }
            }
        });

        add(btnGuardarCambios);
    }

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"Nombre del producto", "Precio", "Uds.", "Subtotal", ""};
        boolean[] colEditables = {false, false, true, false, true};
        int[] tamColumnas = {450, 40, 20, 40, 10};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 4);
        tblLista.getColumnModel().getColumn(2).setCellEditor(new CantidadCellEditor(new EventCellInputChange() {
            @Override
            public void inputChanged() {
                //actualizarTotal();
                //actualizarCambio();
            }
        }));
        tblLista.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        AccionTabla(tblLista, 4);
        Miscelanea.CargarTabla(GestionApartados.getListaProductosApartados(folio), tblLista, true);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(900, 380));
        jsp1.setPreferredSize(new java.awt.Dimension(900, 380));
        jsp1.setRowHeaderView(null);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(100, 250);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        add(jsp1);
    }

    private void addSignoTabla() {
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 1), i, 1);
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 3), i, 3);
        }
    }

    private void calcularTotal() {
        double total = 0.00;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            total += Double.parseDouble(tblLista.getValueAt(i, 3).toString().substring(1));
        }
        lblTotal.setText("$" + String.format("%.2f", total));
    }

    private void calcularAnticipo() {
        //En teoria el anticipo es el primer pago así que no hay problema con eso, pendiente menos
        lblAnticipo.setText("$" + GestionPagos.obtenerAnticipo(folio));
    }

    private void calcularPagado() {
        lblPgdo.setText("$" + GestionPagos.obtenerPagado(folio));
    }

    private void calcularPendiente() {
        double pagado = lblPgdo.getText().isEmpty() ? 0.00 : Double.parseDouble(lblPgdo.getText().substring(1));
        double total = lblTotal.getText().isEmpty() ? 0.00 : Double.parseDouble(lblTotal.getText().substring(1));
        lblPdte.setText("$" + String.format("%.2f", total - pagado));
    }
    String[] productosEliminados;
    int prod = 0;

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está seguro que desea cancelar el producto " + tblLista.getValueAt(row, 0) + " del apartado?", true);
                if (n.getRespuesta()) {
                    productosEliminados[prod++] = tblLista.getValueAt(row, 0).toString();
                    if (tblLista.isEditing()) {
                        tblLista.getCellEditor().stopCellEditing();
                    }
                    DefaultTableModel dft = (DefaultTableModel) tblLista.getModel();
                    dft.removeRow(row);
                    System.out.println(productosEliminados[row]);
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
            }
        };
        tblLista.getColumnModel().getColumn(tblLista.getColumnModel().getColumnCount() - 1).setCellEditor(new CeldaPersonalizada(event, tipo, new Color(255, 244, 227)));
    }

    private void limpiar() {
        Miscelanea.LimpiarTabla(tblLista);
        actualizarTotal();
        txtCliente.setText("");
        txtBuscar.setText("");
    }

    private void actualizarTotal() {
        double total = 0.00;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            total += Double.parseDouble(tblLista.getValueAt(i, 3).toString().substring(1));
        }
        lblTotal.setText("$" + String.format("%.2f", total));
        lblAnticipo.setText("$" + String.format("%.2f", total * 0.3));
    }

    private boolean actualizarSubtotal() {
        for (int row = 0; row < tblLista.getRowCount(); row++) {
            int cantidad = Integer.parseInt(tblLista.getValueAt(row, 2).toString());
            double precio = Double.parseDouble(tblLista.getValueAt(row, 1).toString().substring(1));
            tblLista.setValueAt("$" + String.format("%.2f", cantidad * precio), row, 3);
            return true;
        }
        return false;
    }

    private boolean buscarIgualdades(String nombre) {
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            if (tblLista.getValueAt(i, 0).toString().equals(nombre)) {
                tblLista.setValueAt(Integer.parseInt(tblLista.getValueAt(i, 2).toString()) + 1, i, 2);
                return true;
            }
        }
        return false;
    }

    public boolean operacion() {
        for (String productosEliminado : productosEliminados) {
            if (productosEliminado != null) {
                if (!GestionApartados.cancelarProductoApartado(folio, productosEliminado)) {
                    return false;
                }
            }
        }
        return true;
    }
}
