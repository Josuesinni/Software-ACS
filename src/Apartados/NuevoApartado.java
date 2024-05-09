package Apartados;

import Menus.MenuAjustarAnticipo;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NuevoApartado extends JPanel {

    private MenuAjustarAnticipo menu;
    private JLabel lblTxtAnticipo;
    private JLabel lblTotal;
    private JLabel lblAnticipo;
    private JLabel lblPdte;
    private JTable tblLista;
    private JTextFieldRounded txtImporte;
    private JTextFieldRounded txtCliente;
    private JLabel lblFolio;
    private String folio;
    private JLabel lblCambio;
    Buscador buscadorProductos, buscadorClientes;
    private JTable tblListaApartados;

    public NuevoApartado(Dimension d, JTable tblListaApartados) {
        this.tblListaApartados = tblListaApartados;
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }
    JTextFieldRounded txtBuscar;
    JComboBoxCustom cmbMetodoPago;

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("NUEVO APARTADO");
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
                        actualizarCambio();
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
                BuscarProducto bp = new BuscarProducto(true, "Apartado - Buscar producto", tblLista);
                actualizarTotal();
                actualizarCambio();
            }
        });
        add(btnBuscar);

        menu = new MenuAjustarAnticipo();

        JButtonRounded btnAjuste = new JButtonRounded(20);
        btnAjuste.setLocation(1230, 350);
        btnAjuste.setSize(30, 30);
        btnAjuste.setBackground(new Color(255, 244, 227));
        btnAjuste.setColor(new Color(255, 244, 227));
        btnAjuste.setColorOver(new Color(255, 250, 230));
        btnAjuste.setColorClick(new Color(240, 220, 210));
        btnAjuste.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/ajuste.png")));
        btnAjuste.setIconPosition(0);
        btnAjuste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setAnticipo(lblAnticipo);
                menu.setImporte(txtImporte);
                menu.setCambio(lblCambio);
                menu.setValorAnticipo(lblAnticipo.getText().substring(1));

            }
        });

        menu.setBotonDeReferencia(btnAjuste);
        menu.changePopupSize(330, 120);
        add(btnAjuste);

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
        int[] tamColumnas = {450, 40, 20, 40, 10};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 4);
        c = new CantidadCellEditor(new EventCellInputChange() {
            @Override
            public void inputChanged() {
                if (!eliminando) {
                    actualizarTotal();
                    actualizarCambio();
                }
            }
        });
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
                    if (!eliminando) {
                        c.setMaximumInput(GestionProductos.cantidadProductoExistente(tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString()));
                    }
                }
            }
        });
        AccionTabla(tblLista, 4);
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
                    dft.removeRow(row);
                    eliminando = false;
                    actualizarTotal();
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

    public void panelDerecho() {
        JLabel lblFecha = new JLabel(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime()), JLabel.RIGHT);
        lblFecha.setLocation(1265, 80);
        lblFecha.setSize(165, 20);
        lblFecha.setFont(getFuente(0, 1, 16));
        add(lblFecha);
        folio = GestionApartados.getFolio();
        lblFolio = new JLabel("Folio #" + folio, JLabel.LEFT);
        lblFolio.setLocation(1100, 120);
        lblFolio.setSize(330, 50);
        lblFolio.setFont(getFuente(0, 1, 24));
        add(lblFolio);

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
        pr.setSize(330, 160);
        pr.setOpaque(false);
        pr.setLocation(1100, 250);
        pr.setBackground(new Color(255, 244, 227));

        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(20, 20);
        lblTotalTxt.setSize(80, 40);
        lblTotalTxt.setFont(getFuente(1, 1, 24));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pr.add(lblTotalTxt);

        lblTotal = new JLabel("$0.00", JLabel.RIGHT);
        lblTotal.setLocation(100, 20);
        lblTotal.setSize(210, 40);
        lblTotal.setFont(getFuente(1, 1, 24));
        pr.add(lblTotal);

        lblTxtAnticipo = new JLabel("ANTICIPO", JLabel.LEFT);
        lblTxtAnticipo.setLocation(20, 80);
        lblTxtAnticipo.setSize(130, 70);
        lblTxtAnticipo.setFont(getFuente(1, 1, 24));
        lblTxtAnticipo.setForeground(new Color(192, 145, 64));
        pr.add(lblTxtAnticipo);

        lblAnticipo = new JLabel("$0.00", JLabel.RIGHT);
        lblAnticipo.setLocation(100, 80);
        lblAnticipo.setSize(210, 70);
        lblAnticipo.setFont(getFuente(1, 1, 24));
        pr.add(lblAnticipo);

        //Horizontal
        JSeparatorCustom jsHorizontal = new JSeparatorCustom(0, 4);
        jsHorizontal.setForeground(new Color(229, 209, 169));
        jsHorizontal.setBackground(Recursos.fondoPiel);
        jsHorizontal.setSize(300, 4);
        jsHorizontal.setLocation(15, 85);
        pr.add(jsHorizontal);

        JLabel lblPdteTxt = new JLabel("PENDIENTE", JLabel.LEFT);
        lblPdteTxt.setLocation(20, 160);
        lblPdteTxt.setSize(160, 70);
        lblPdteTxt.setFont(getFuente(1, 1, 20));
        lblPdteTxt.setForeground(new Color(192, 145, 64));
        pr.add(lblPdteTxt);

        lblPdte = new JLabel("$0.00", JLabel.RIGHT);
        lblPdte.setLocation(100, 160);
        lblPdte.setSize(210, 70);
        lblPdte.setFont(getFuente(1, 1, 24));
        pr.add(lblPdte);

        pr.setLayout(new BorderLayout(10, 10));
        add(pr);

        JLabel lblMetodoPago = new JLabel("Método de pago", JLabel.LEFT);
        lblMetodoPago.setLocation(1110, 420);
        lblMetodoPago.setSize(150, 40);
        lblMetodoPago.setFont(getFuente(1, 1, 14));
        add(lblMetodoPago);
        //Con este comando se elimna el fondo de seleccion del combobox
        UIManager.put("ComboBox.selectionBackground", new Color(0, 0, 0, 0));
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

        txtImporte = new JTextFieldRounded("0.00", 20, Recursos.FUENTE_GENERAL);
        txtImporte.setHorizontalAlignment(JTextField.LEFT);
        txtImporte.setLocation(30, 0);
        txtImporte.setSize(90, 40);
        txtImporte.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

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

        JButtonRounded btnCrearApartado = new JButtonRounded("CREAR APARTADO", 20, true);
        btnCrearApartado.setLocation(1120, 675);
        btnCrearApartado.setSize(300, 60);
        btnCrearApartado.setFont(Recursos.FUENTE_BOTON);
        btnCrearApartado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tblLista.getRowCount() > 0) {
                    operacion();
                } else {
                    new Notificacion(1, "Error: No hay productos apartados", false);
                }
            }
        });
        add(btnCrearApartado);
    }

    private void actualizarTotal() {
        double total = 0.00;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            total += Double.parseDouble(tblLista.getValueAt(i, 3).toString().substring(1));
        }
        lblTotal.setText("$" + String.format("%.2f", total));
        lblAnticipo.setText("$" + String.format("%.2f", total * 0.3));
    }

    private void actualizarCambio() {
        double anticipo = Double.parseDouble(lblAnticipo.getText().substring(1));
        double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
        if (importe - anticipo >= 0) {
            lblCambio.setForeground(Color.black);
            lblCambio.setText("$" + (String.format("%.2f", (importe - anticipo))));
        } else {
            lblCambio.setForeground(Color.red);
            lblCambio.setText("$" + (String.format("%.2f", (importe - anticipo))));
        }
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

    private void operacion() {
        String cliente = txtCliente.getText();
        if (GestionApartados.registrarApartado(cliente)) {
            if (GestionApartados.registarProductoApartado(tblLista, Integer.parseInt(folio))) {
                double anticipo = lblAnticipo.getText().isEmpty() ? 0.00 : Double.parseDouble(lblAnticipo.getText().substring(1));
                if (GestionPagos.registrarPago(folio, anticipo, (cmbMetodoPago.getSelectedIndex() != 0))) {
                    GestionApartados.vistaApartados(tblListaApartados);
                    new Notificacion(0, "Apartado creado exitosamente", false);
                    cp.panelAnterior(cp.panelActual());
                } else {
                    new Notificacion(1, "Error en la base de datos al registrar el pago", false);
                }
            } else {
                new Notificacion(1, "Error en la base de datos al registrar detalle apartado", false);
            }
        } else {
            new Notificacion(1, "Error: Cliente no encontrado, favor de registrarlo primero", false);
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
        txtImporte.setText("0.00");
        lblCambio.setText("$0.00");
        lblCambio.setForeground(Color.black);
        folio = GestionApartados.getFolio();
        lblFolio.setText("Folio #" + folio);
    }
}
