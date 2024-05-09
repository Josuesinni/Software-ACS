package Apartados;

import BaseDeDatos.Control.GestionApartados;
import BaseDeDatos.Control.GestionPagos;
import BaseDeDatos.Control.Miscelanea;
import Menus.MenuFecha;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JPanelRounded;
import SwingModificado.JSeparatorCustom;
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
import static java.awt.Window.Type.POPUP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CatalogoApartados extends JPanel {

    JTextFieldRounded txtCiclo1, txtCiclo2;
    JComboBoxCustom cmbEstado;
    JComboBoxCustom cmbMetodoPago;

    public CatalogoApartados(Dimension d) {
        setSize(d);
        setBackground(Color.white);
        initComponents();
        setLayout(new BorderLayout());
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("LISTA DE APARTADOS");
        lblTitulo.setSize(570, 60);
        add(lblTitulo);

        JTextFieldRounded txtBuscar = new JTextFieldRounded("Buscar por folio, nombre...", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        
        add(txtBuscar);

        String hoy = Calendar.getInstance().get(Calendar.DATE) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.YEAR);
        txtCiclo1 = new JTextFieldRounded(Miscelanea.getFechaMin("fecha_inicio", "apartado"), 20, Recursos.FUENTE_GENERAL);
        txtCiclo1.setLocation(1104, 100);
        txtCiclo1.setSize(120, 40);
        txtCiclo1.setBackground(new Color(239, 232, 232));
        txtCiclo1.setBorder(null);
        txtCiclo1.setHorizontalAlignment(SwingConstants.CENTER);
        txtCiclo1.setEditable(false);

        add(txtCiclo1);

        JLabel lblBarra = new JLabel("/", SwingConstants.CENTER);
        lblBarra.setLocation(1244, 100);
        lblBarra.setSize(40, 40);
        lblBarra.setFont(getFuente(0, 1, 28));
        add(lblBarra);

        txtCiclo2 = new JTextFieldRounded(hoy, 20, Recursos.FUENTE_GENERAL);
        txtCiclo2.setLocation(1304, 100);
        txtCiclo2.setSize(120, 40);
        txtCiclo2.setBackground(new Color(239, 232, 232));
        txtCiclo2.setBorder(null);
        txtCiclo2.setHorizontalAlignment(SwingConstants.CENTER);
        txtCiclo2.setEditable(false);
        add(txtCiclo2);

        MenuFecha mf = new MenuFecha();
        mf.setDialogPosition(760, 250);
        mf.resizeJDialog(240, 170);
        mf.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (e.getOppositeWindow() != null) {
                    if (e.getOppositeWindow().getType() != POPUP) {
                        mf.dispose();
                    }
                } else {
                    mf.dispose();
                }
            }
        });
        mf.setTextCiclos(txtCiclo1, txtCiclo2);
        JButtonRounded btn = new JButtonRounded("Fecha", 20);
        btn.setSize(240, 40);
        btn.setLocation(760, 190);
        btn.setBackground(new Color(255, 214, 153));
        btn.setColorOver(new Color(255, 224, 163));
        btn.setColor(new Color(255, 214, 153));
        btn.setColorClick(new Color(255, 224, 163));
        btn.setFont(Recursos.FUENTE_GENERAL);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mf.showDialog();
                String estado = cmbEstado.getSelectedItem().toString();
                mf.addTablaLista(tblLista, estado);
                mf.setTipo(0);
                limpiar();
            }
        });
        add(btn);

        JLabel lblEstado = new JLabel("Estado", JLabel.LEFT);
        lblEstado.setLocation(600, 172);
        lblEstado.setSize(150, 20);
        lblEstado.setFont(getFuente(1, 1, 12));
        add(lblEstado);

        cmbEstado = new JComboBoxCustom<>();
        cmbEstado.setFont(getFuente(1, 1, 16));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Todos", "Activo", "Pagado", "Cancelado"}));
        cmbEstado.setLocation(600, 190);
        cmbEstado.setSize(140, 40);
        cmbEstado.setBackground(new Color(255, 214, 153));
        ((JLabel) cmbEstado.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbEstado.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String estado = cmbEstado.getSelectedItem().toString();
                if (txtCiclo1.getText().isEmpty() && txtCiclo2.getText().isEmpty()) {
                    Miscelanea.CargarTabla(GestionApartados.buscarApartadoPorEstado(estado), tblLista, true);
                } else {
                    Miscelanea.CargarTabla(GestionApartados.vistaApartadosDe(txtCiclo1.getText(), txtCiclo2.getText(), estado), tblLista, true);
                }
                limpiar();
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

        JButtonRounded btnNuevoApartado = new JButtonRounded("NUEVO APARTADO", 20, true);
        btnNuevoApartado.setLocation(749, 675);
        btnNuevoApartado.setSize(260, 60);
        btnNuevoApartado.setFont(Recursos.FUENTE_BOTON);
        btnNuevoApartado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new NuevoApartado(getSize(), tblLista));
                cp.siguientePanel(cp.panelActual());
            }
        });
        add(btnNuevoApartado);

    }

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"Folio", "Nombre del cliente", "Inicio", "Fin", "Estado", ""};
        boolean[] colEditables = {false, false, false, false, false, true};
        int[] tamColumnas = {10, 300, 40, 40, 40, 100};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, -2);
        GestionApartados.vistaApartados(tblLista);
        AccionTabla(tblLista, -2);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(900, 380));
        jsp1.setPreferredSize(new java.awt.Dimension(900, 380));
        jsp1.setRowHeaderView(null);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37 * 9);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(100, 250);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        add(jsp1);
        tblLista.getSelectionModel().addListSelectionListener((e) -> {
            try {
                txtImporte.setText("0.00");
                int id_apartado = Integer.parseInt(tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString());
                int metodo_pago = GestionPagos.getMetodoPagoUsado(id_apartado);
                cmbMetodoPago.setSelectedIndex(metodo_pago);
                if (metodo_pago == 1) {
                    txtImporte.setEditable(false);
                    txtImporte.setText(lblPdte.getText().substring(1));
                    actualizarCambio();
                } else {
                    txtImporte.setEditable(true);
                }
                cmbMetodoPago.setEnabled(false);
                if (tblLista.getValueAt(tblLista.getSelectedRow(), 4).toString().equals("Cancelado") || (tblLista.getValueAt(tblLista.getSelectedRow(), 4).toString().equals("Pagado"))) {
                    btnCobrar.setEnabled(false);
                    txtImporte.setEnabled(false);
                } else {
                    btnCobrar.setEnabled(true);
                    txtImporte.setEnabled(true);
                }
                actualizacion();
            } catch (ArrayIndexOutOfBoundsException ex) {
                cmbMetodoPago.setEnabled(true);
                cmbMetodoPago.setSelectedIndex(0);
            }
        });
    }
    JLabel lblTotal;
    JLabel lblAnticipo;
    JLabel lblPgdo;
    JLabel lblPdte;
    JTextFieldRounded txtImporte;
    JLabel lblCambio;
    JTable tblLista;
    JButtonRounded btnCobrar;

    public void panelDerecho() {

        JPanelRounded pr = new JPanelRounded(20);
        pr.setSize(330, 240);
        pr.setOpaque(false);
        pr.setLocation(1100, 170);
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
        ((JLabel) cmbMetodoPago.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
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
        pnlImporte.setBackground(new Color(239, 239, 239));

        JLabel lblPeso = new JLabel("$", JLabel.LEFT);
        lblPeso.setLocation(10, 0);
        lblPeso.setSize(20, 40);
        lblPeso.setFont(getFuente(1, 1, 20));
        pnlImporte.add(lblPeso);

        txtImporte = new JTextFieldRounded("0.00", 20, Recursos.FUENTE_GENERAL);
        txtImporte.setHorizontalAlignment(JTextField.LEFT);
        txtImporte.setLocation(30, 0);
        txtImporte.setSize(90, 40);
        txtImporte.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizarCambio();
            }
        });
        Recursos.permitirSoloDobles(txtImporte);
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

        btnCobrar = new JButtonRounded("COBRAR", 20, true);
        btnCobrar.setLocation(1130, 675);
        btnCobrar.setSize(260, 60);
        btnCobrar.setFont(getFuente(0, 0, 24));
        btnCobrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lblTotal.getText().equals("$0.00")) {
                    operacion();
                    actualizacion();
                    txtImporte.setText("0.00");
                    lblCambio.setText("$0.00");
                }
            }
        });

        add(btnCobrar);
    }

    private void limpiar() {
        tblLista.clearSelection();
        lblTotal.setText("$0.00");
        lblAnticipo.setText("$0.00");
        lblPgdo.setText("$0.00");
        lblPdte.setText("$0.00");
        txtImporte.setText("0.00");
        lblCambio.setText("$0.00");
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
                /*cp.cargarPanel(new EditarApartado(getSize(), tblLista.getValueAt(row, 0).toString()));
                cp.siguientePanel(cp.panelActual());*/
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está segura de cancelar el apartado de " + tblLista.getValueAt(row, 1) + "?", true);
                if (n.getRespuesta()) {
                    if (tblLista.getValueAt(row, 4).toString().equals("Activo")) {
                        if (GestionApartados.cancelarApartado(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                            new Notificacion(0, "Se ha cancelado el apartado exitosamente", false);
                            if (tblLista.isEditing()) {
                                tblLista.getCellEditor().stopCellEditing();
                            }
                            GestionApartados.vistaApartados(tblLista);
                        }
                    }else{
                        new Notificacion(1, "Error el apartado ya se ha pagado en su totalidad o se ha cancelado", false);
                    }

                }
            }

            @Override
            public void visualizar(int row) {
                String datos[] = new String[2];
                datos[0] = tblLista.getValueAt(row, 0).toString();
                datos[1] = tblLista.getValueAt(row, 1).toString();
                new LP_Apartados(true, datos);
            }

            @Override
            public void verHistorial(int row) {
                String datos[] = new String[2];
                datos[0] = tblLista.getValueAt(row, 0).toString();
                datos[1] = tblLista.getValueAt(row, 1).toString();
                new HistorialPagos(true, datos);
            }

            @Override
            public void ajustar(int row) {
            }
        };
        tblLista.getColumnModel().getColumn(tblLista.getColumnModel().getColumnCount() - 1).setCellEditor(new CeldaPersonalizada(event, tipo, new Color(255, 244, 227)));
    }

    private void actualizarCambio() {
        double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
        double restante = Double.parseDouble(lblPdte.getText().substring(1));
        if (importe > restante) {
            lblCambio.setText("$" + String.format("%.2f", (importe - restante)));
        } else {
            lblCambio.setText("$0.00");
        }
    }

    private void operacion() {
        double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
        double restante = Double.parseDouble(lblPdte.getText().substring(1));
        double cantidad = restante - importe;
        if (importe > 0.00) {
            if (cantidad >= 0) {
                if (GestionPagos.registrarPago(tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString(), importe, (cmbMetodoPago.getSelectedIndex() != 0))) {
                    new Notificacion(0, "Pago registrado correctamente", false);
                    String folio = tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString();
                    System.out.println((Double.parseDouble(GestionApartados.getTotal(folio)) - Double.parseDouble(GestionPagos.obtenerPagado(folio))));
                    if ((Double.parseDouble(GestionApartados.getTotal(folio)) - Double.parseDouble(GestionPagos.obtenerPagado(folio))) == 0.00) {
                        GestionApartados.cambiarApartadoAVenta(folio);
                    }
                    GestionApartados.vistaApartados(tblLista);
                }
            } else {
                if (GestionPagos.registrarPago(tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString(), restante, (cmbMetodoPago.getSelectedIndex() != 0))) {
                    new Notificacion(0, "Pago registrado correctamente", false);
                }
            }
        } else {
            new Notificacion(1, "Error: El importe ingresado es invalido", false);
        }
    }

    private void actualizacion() {
        String folio = tblLista.getValueAt(tblLista.getSelectedRow(), 0).toString();
        lblTotal.setText("$" + GestionApartados.getTotal(folio));
        lblAnticipo.setText("$" + GestionPagos.obtenerAnticipo(folio));
        lblPgdo.setText("$" + GestionPagos.obtenerPagado(folio));
        double pagado = lblPgdo.getText().isEmpty() ? 0.00 : Double.parseDouble(lblPgdo.getText().substring(1));
        double total = lblTotal.getText().isEmpty() ? 0.00 : Double.parseDouble(lblTotal.getText().substring(1));
        lblPdte.setText("$" + String.format("%.2f", total - pagado));
    }
}
