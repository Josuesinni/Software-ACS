package Ventas;

import BaseDeDatos.Control.GestionClientes;
import BaseDeDatos.Control.GestionProductos;
import BaseDeDatos.Control.GestionVentas;
import BaseDeDatos.Control.Miscelanea;
import Buscadores.Buscador;
import Buscadores.BuscarCliente;
import Buscadores.BuscarProducto;
import Imprimir.Control.ControladorTicket;
import Imprimir.Modelo.ParametrosTicket;
import Imprimir.Modelo.ProductoVendido;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JPanelRounded;
import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Tabla.AccionEnJTable;
import Tabla.CantidadCellEditor;
import Tabla.EventCellInputChange;
import Tabla.CeldaPersonalizada;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Venta extends JPanel {

    public Venta(Dimension d) {
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }

    public void initComponents() {
        ControladorTicket.getInstance().compilarTicket();
        panelIzquierdo();
        panelDerecho();
    }
    JTextFieldRounded txtBuscar;
    Buscador buscadorProductos, buscadorClientes;
    JTable tblLista;
    JLabel lblCambio;
    JLabel lblTotal;
    JTextFieldRounded txtImporte;
    String folio;
    JLabel lblTotalTarjeta;
    JTextFieldRounded txtCliente;
    JLabel lblFolio;
    JLabel lblFecha;
    JComboBoxCustom cmbMetodoPago;

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("VENTA");
        lblTitulo.setSize(240, 60);
        add(lblTitulo);
        txtBuscar = new JTextFieldRounded("Clave/Nombre del producto", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 10) {
                    String producto = txtBuscar.getText();
                    if (GestionProductos.existeProducto(producto)) {
                        if (!buscarIgualdades(producto)) {
                            GestionProductos.addProductoLista(producto, tblLista);
                            tblLista.setPreferredSize(new Dimension(900, 333));
                            if (tblLista.getRowCount() > 9) {
                                tblLista.setPreferredSize(new Dimension(900, 333 + tblLista.getRowCount() * 37));
                            }
                        } else {
                            actualizarSubtotal();
                        }
                        actualizarTotal();
                        actualizarCambio();
                        txtBuscar.setText("");
                    }
                }
            }

            @Override
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
                //c.cancelCellEditing();
                //tblLista.clearSelection();
                BuscarProducto bp = new BuscarProducto(true, "Venta - Buscar producto", tblLista);
                actualizarTotal();
                if (cmbMetodoPago.getSelectedItem().toString().equals("Transferencia")) {
                    txtImporte.setText(lblTotal.getText().substring(1));
                }
                actualizarCambio();
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

        JButtonRounded btnCancelar = new JButtonRounded("CANCELAR", 20, false);
        btnCancelar.setLocation(240, 680);
        btnCancelar.setSize(180, 50);
        btnCancelar.setFont(Recursos.FUENTE_BOTON);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
        add(btnCancelar);

    }
    CantidadCellEditor c;

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"Nombre del producto", "Precio", "Uds.", "Subtotal", ""};
        boolean[] colEditables = {false, false, true, false, true};
        int[] tamColumnas = {500, 40, 20, 40, 10};
        c = new CantidadCellEditor(new EventCellInputChange() {
            public void inputChanged() {
                if (!eliminando) {
                    actualizarTotal();
                    actualizarCambio();
                }
            }
        });
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 4);
        tblLista.getColumnModel().getColumn(2).setCellEditor(c);
        tblLista.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
        tblLista.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    //
                    /*if (!eliminando) {
                        if (tblLista.getSelectedRow() != -1) {
                            c.setMaximumInput(GestionProductos.cantidadProductoExistente(tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString()));
                        }
                    }*/
                    if (tblLista.getSelectedRow() != -1) {
                        c.setMaximumInput(GestionProductos.cantidadProductoExistente(tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString()));
                    }
                    //tblLista.clearSelection();
                    /*if (tblLista.getRowCount() > 0) {
                        
                    }*/
                }
            }
        });
        AccionTabla(tblLista, 4);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(900, 380));
        jsp1.setPreferredSize(new java.awt.Dimension(900, 333));
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

    public void panelDerecho() {

        folio = GestionVentas.getFolio();
        lblFolio = new JLabel("Folio #" + folio, JLabel.LEFT);
        lblFolio.setLocation(1100, 100);
        lblFolio.setSize(330, 50);
        lblFolio.setFont(getFuente(0, 1, 24));
        add(lblFolio);

        lblFecha = new JLabel(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime()), JLabel.RIGHT);
        lblFecha.setLocation(1265, 80);
        lblFecha.setSize(165, 20);
        lblFecha.setFont(getFuente(0, 1, 16));
        add(lblFecha);

        fecha();

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
                BuscarCliente bp = new BuscarCliente(true, "Venta - Buscar cliente");
                txtCliente.setText(bp.getNombreCliente());
            }
        });
        add(btnBuscarCliente);

        JPanelRounded pr = new JPanelRounded(20);
        pr.setSize(330, 70);
        pr.setOpaque(false);
        pr.setLocation(1100, 260);
        pr.setBackground(new Color(255, 244, 227));

        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(20, 0);
        lblTotalTxt.setSize(80, 70);
        lblTotalTxt.setFont(getFuente(1, 1, 24));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pr.add(lblTotalTxt);

        lblTotal = new JLabel("$0.00", JLabel.RIGHT);
        lblTotal.setLocation(100, 0);
        lblTotal.setSize(210, 70);
        lblTotal.setFont(getFuente(1, 1, 24));
        pr.add(lblTotal);

        pr.setLayout(new BorderLayout(10, 10));
        add(pr);

        JButtonRounded btnTarjetaRegalo = new JButtonRounded(20);
        btnTarjetaRegalo.setLocation(1100, 360);
        btnTarjetaRegalo.setSize(200, 40);
        btnTarjetaRegalo.setBackground(new Color(161, 238, 208));
        btnTarjetaRegalo.setColor(new Color(161, 238, 208));
        btnTarjetaRegalo.setColorOver(new Color(151, 228, 198));
        btnTarjetaRegalo.setColorClick(new Color(131, 208, 178));
        btnTarjetaRegalo.setFont(getFuente(0, 1, 12));
        btnTarjetaRegalo.setForeground(new Color(47, 158, 114));
        btnTarjetaRegalo.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/tarjeta_regalo.png")));
        btnTarjetaRegalo.setBorderPainted(false);
        btnTarjetaRegalo.setIconPosition(1);
        btnTarjetaRegalo.setText("TARJETA DE REGALO");
        btnTarjetaRegalo.setHorizontalAlignment(4);
        btnTarjetaRegalo.setPresionado(false);
        add(btnTarjetaRegalo);

        lblTotalTarjeta = new JLabel("$0.00", JLabel.RIGHT);
        lblTotalTarjeta.setLocation(1300, 360);
        lblTotalTarjeta.setSize(110, 40);
        lblTotalTarjeta.setFont(getFuente(1, 1, 20));
        add(lblTotalTarjeta);

        JLabel lblMetodoPago = new JLabel("Método de pago", JLabel.LEFT);
        lblMetodoPago.setLocation(1110, 420);
        lblMetodoPago.setSize(150, 40);
        lblMetodoPago.setFont(getFuente(1, 1, 14));
        add(lblMetodoPago);

        cmbMetodoPago = new JComboBoxCustom<>();
        cmbMetodoPago.setFont(getFuente(1, 1, 16));
        cmbMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Efectivo", "Transferencia"}));
        cmbMetodoPago.setLocation(1100, 460);
        cmbMetodoPago.setSize(150, 40);
        cmbMetodoPago.setBackground(new Color(255, 214, 153));
        cmbMetodoPago.setColorOver(new Color(255, 224, 163));
        ((JLabel) cmbMetodoPago.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ItemListener listener = (e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getSource() == cmbMetodoPago) {
                    if (e.getItem().toString().equals("Transferencia")) {
                        txtImporte.setEditable(false);
                        txtImporte.setText(lblTotal.getText().substring(1));
                        actualizarCambio();
                    } else {
                        txtImporte.setEditable(true);
                    }
                }
            }
        };
        cmbMetodoPago.addItemListener(listener);
        add(cmbMetodoPago);

        JLabel lblImporte = new JLabel("Importe", JLabel.LEFT);
        lblImporte.setLocation(1290, 420);
        lblImporte.setSize(150, 40);
        lblImporte.setFont(getFuente(1, 1, 14));
        add(lblImporte);

        JPanelRounded pnlImporte = new JPanelRounded(20);
        pnlImporte.setOpaque(false);
        pnlImporte.setLocation(1280, 460);
        pnlImporte.setSize(150, 40);
        pnlImporte.setBackground(new Color(239, 232, 232));

        JLabel lblPeso = new JLabel("$", JLabel.LEFT);
        lblPeso.setLocation(10, 0);
        lblPeso.setSize(20, 40);
        lblPeso.setFont(getFuente(1, 1, 20));
        pnlImporte.add(lblPeso);

        txtImporte = new JTextFieldRounded("0.00", 0, Recursos.FUENTE_GENERAL);
        txtImporte.setHorizontalAlignment(JTextField.LEFT);
        txtImporte.setLocation(30, 0);
        txtImporte.setSize(90, 40);
        Recursos.permitirSoloDobles(txtImporte);
        txtImporte.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizarCambio();
            }
        });
        pnlImporte.add(txtImporte);

        pnlImporte.setLayout(new BorderLayout());
        add(pnlImporte);

        JPanelRounded pr2 = new JPanelRounded(20);
        pr2.setSize(330, 70);
        pr2.setOpaque(false);
        pr2.setLocation(1100, 560);
        pr2.setBackground(new Color(255, 244, 227));

        JLabel lblCambioTxt = new JLabel("CAMBIO", JLabel.LEFT);
        lblCambioTxt.setLocation(20, 0);
        lblCambioTxt.setSize(100, 70);
        lblCambioTxt.setFont(getFuente(1, 1, 24));
        lblCambioTxt.setForeground(new Color(192, 145, 64));
        pr2.add(lblCambioTxt);

        lblCambio = new JLabel("$0.00", JLabel.RIGHT);
        lblCambio.setLocation(120, 0);
        lblCambio.setSize(190, 70);
        lblCambio.setFont(getFuente(1, 1, 24));
        pr2.add(lblCambio);

        pr2.setLayout(new BorderLayout(10, 10));
        add(pr2);

        JButtonRounded btnCobrar = new JButtonRounded("COBRAR", 20, true);
        btnCobrar.setLocation(1130, 675);
        btnCobrar.setSize(260, 60);
        btnCobrar.setFont(getFuente(0, 0, 24));
        btnCobrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tblLista.getRowCount() > 0) {
                    double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
                    double total = Double.parseDouble(lblTotal.getText().replace('$', ' ').trim());
                    if (importe < total) {
                        new Notificacion(1, "Error al registrar la venta. El importe es menor al total.", false);
                    } else {
                        String cliente = txtCliente.getText();
                        if (cliente.isEmpty()) {
                            cliente = "Publico General";
                        }
                        if (GestionClientes.existeCliente(txtCliente.getText())) {
                            if (GestionVentas.registrarVenta(1, cliente, cmbMetodoPago.getSelectedIndex())) {
                                if (GestionVentas.registarProductoVenta(tblLista, Integer.parseInt(folio))) {
                                    Notificacion n = new Notificacion(3, "¿Desea imprimir el ticket de la venta?", true);
                                    if (n.getRespuesta()) {
                                        List<ProductoVendido> listaVenta = new ArrayList<>();
                                        for (int i = 0; i < tblLista.getRowCount(); i++) {
                                            String datos[] = new String[tblLista.getColumnCount() - 1];
                                            for (int j = 0; j < tblLista.getColumnCount() - 1; j++) {
                                                datos[j] = tblLista.getValueAt(i, j).toString();
                                            }
                                            ProductoVendido pv = new ProductoVendido(datos[0], datos[2], datos[1], datos[3]);
                                            listaVenta.add(pv);
                                        }
                                        String metodo = cmbMetodoPago.getSelectedItem().toString();
                                        InputStream logo = getClass().getResourceAsStream("/res/imagenes/iu/logo.png");
                                        InputStream qr = getClass().getResourceAsStream("/qr-code.png");
                                        ParametrosTicket pt = new ParametrosTicket("#" + folio, cliente, lblTotal.getText(), "$" + txtImporte.getText(), lblCambio.getText(), metodo, lblFecha.getText(), qr, logo, listaVenta);
                                        ControladorTicket.getInstance().imprimirTicket(pt);
                                        //Se manda a llamar para imprimir el ticket   
                                    } else {
                                        new Notificacion(0, "Venta registrada exitosamente.", false);
                                    }
                                    limpiar();
                                } else {
                                    new Notificacion(1, "Error al registrar la venta. Ha ocurrido un problema con la base de datos.", false);
                                }
                            } else {
                                new Notificacion(1, "Error al registrar la venta. Ha ocurrido un problema con la base de datos.", false);
                            }
                        } else {
                            new Notificacion(1, "Error al registrar la venta. El cliente no existe", false);
                        }
                    }
                } else {
                    new Notificacion(1, "Error no hay elementos a vender.", false);
                }
            }

        });
        add(btnCobrar);
    }
    boolean eliminando = false;

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está seguro que desea remover el producto " + tblLista.getValueAt(row, 0) + " de la venta?", true);
                if (n.getRespuesta()) {
                    eliminando = true;
                    if (tblLista.isEditing()) {
                        tblLista.getCellEditor().stopCellEditing();
                    }
                    DefaultTableModel dft = (DefaultTableModel) tblLista.getModel();
                    //c.eliminarItem();
                    dft.removeRow(row);
                    eliminando = false;
                    actualizarTotal();
                    if (cmbMetodoPago.getSelectedIndex()==1) {
                        txtImporte.setText(lblTotal.getText().substring(1));
                    }
                    actualizarCambio();
                    
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

    private boolean actualizarSubtotal() {
        for (int row = 0; row < tblLista.getRowCount(); row++) {
            int cantidad = Integer.parseInt(tblLista.getValueAt(row, 2).toString());
            double precio = Double.parseDouble(tblLista.getValueAt(row, 1).toString().substring(1));
            tblLista.setValueAt("$" + String.format("%.2f", cantidad * precio), row, 3);
            return true;
        }
        return false;
    }

    private void actualizarTotal() {
        //Al ingresar un nuevo producto en la venta se actualiza el total
        double total = 0.00;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            total += Double.parseDouble(tblLista.getValueAt(i, 3).toString().substring(1).replace(",", ""));
        }
        lblTotal.setText("$" + String.format("%.2f", total));
    }

    private void fecha() {
        //La fecha se actualiza cada minuto
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            lblFecha.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime()));
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void actualizarCambio() {
        //Al ingresar un valor en el importe se actualiza el cambio
        double total = Double.parseDouble(lblTotal.getText().substring(1).replace(",", ""));
        double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
        if (importe - total < 0) {
            lblCambio.setText("$" + String.format("%.2f", (importe - total)));
            lblCambio.setForeground(Color.red);
        } else {
            lblCambio.setText("$" + String.format("%.2f", (importe - total)));
            lblCambio.setForeground(Color.black);
        }
    }

    private void limpiar() {
        c.cancelCellEditing();
        tblLista.clearSelection();
        Miscelanea.LimpiarTabla(tblLista);
        actualizarTotal();
        actualizarCambio();
        txtCliente.setText("");
        txtBuscar.setText("");
        lblTotalTarjeta.setText("$0.00");
        txtImporte.setText("0.00");
        lblCambio.setText("$0.00");
        lblCambio.setForeground(Color.black);
        cmbMetodoPago.setSelectedIndex(0);
        folio = GestionVentas.getFolio();
        lblFolio.setText("Folio #" + folio);
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
}
