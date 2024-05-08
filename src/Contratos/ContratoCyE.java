package Contratos;

import BaseDeDatos.Control.GestionAnfitriones;
import BaseDeDatos.Control.GestionContratos;
import BaseDeDatos.Control.Miscelanea;
import Calendario.DateChooser;
import Imprimir.Modelo.ContratoRealizado;
import Imprimir.Plantilla.Contrato_PDF;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JPanelRounded;
import SwingModificado.JTextFieldRounded;
import SwingModificado.JTextFieldTitled;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ContratoCyE extends JDialog {

    JPanel pnlPrincipal;
    boolean tipo, accion;
    Object[] datos;
    JTextFieldTitled pr2;
    JTextFieldRounded txtAPartirDe;
    JComboBoxCustom cmbTiempo, cmbAnfitrion;
    int tiempo=1;

    public ContratoCyE(boolean modal, boolean tipo, Object[] datos) {
        super(vta, modal);
        pnlPrincipal = new JPanel();
        pnlPrincipal.setBackground(Color.white);
        this.tipo = tipo;
        this.datos = datos;
        initComponents();
        if (!tipo) {
            setDatosContrato();
        }
        pnlPrincipal.setLayout(new BorderLayout());
        String titulo = "Gestion Contratos - " + ((tipo) ? "Nuevo Contrato" : "Editar Contrato");
        setTitle(titulo);
        setSize(430, 640);
        setContentPane(pnlPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    DateChooser fApartirDe;

    public void initComponents() {
        String titulo = ((tipo) ? "NUEVO" : "EDITAR") + " CONTRATO";
        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setSize(410, 30);
        lblTitulo.setLocation(0, 30);
        lblTitulo.setFont(getFuente(1, 0, 32));
        pnlPrincipal.add(lblTitulo);

        JLabel lblAnfitrion = new JLabel("Nombre del Anfitrión", JLabel.LEFT);
        lblAnfitrion.setLocation(40, 100);
        lblAnfitrion.setSize(330, 40);
        lblAnfitrion.setFont(getFuente(1, 1, 14));
        pnlPrincipal.add(lblAnfitrion);

        cmbAnfitrion = new JComboBoxCustom<>();
        cmbAnfitrion.setFont(getFuente(1, 1, 16));
        if (GestionAnfitriones.hayAnfitrionesDisponibles()) {
            Miscelanea.CargarComboBox(GestionAnfitriones.anfitrionesDisponibles(), cmbAnfitrion);
        } else {
            if (!tipo) {
                cmbAnfitrion.setModel(new javax.swing.DefaultComboBoxModel<>());
                cmbAnfitrion.addItem(datos[1].toString());
            }
        }
        cmbAnfitrion.setLocation(40, 140);
        cmbAnfitrion.setSize(330, 40);
        cmbAnfitrion.setBackground(Recursos.fondoGris);
        cmbAnfitrion.setColorOver(Color.lightGray);
        ((JLabel) cmbAnfitrion.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
        cmbAnfitrion.setMaximumRowCount(3);
        pnlPrincipal.add(cmbAnfitrion);

        txtAPartirDe = new JTextFieldRounded("", 0, getFuente(0, 0, 18));
        pr2 = new JTextFieldTitled("A partir de:", txtAPartirDe, false, 160, 80);
        pr2.setLocation(40, 200);
        pnlPrincipal.add(pr2);
        fApartirDe = new DateChooser();
        fApartirDe.setTextRefernce(txtAPartirDe);

        JLabel lblDurante = new JLabel("Durante", JLabel.LEFT);
        lblDurante.setSize(120, 20);
        lblDurante.setLocation(230, 210);
        lblDurante.setFont(getFuente(1, 0, 14));
        pnlPrincipal.add(lblDurante);
        cmbTiempo = new JComboBoxCustom<>();
        cmbTiempo.setFont(getFuente(1, 1, 16));
        cmbTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1 mes", "2 meses", "3 meses"}));
        cmbTiempo.setLocation(230, 230);
        cmbTiempo.setSize(140, 40);
        cmbTiempo.setBackground(Recursos.fondoGris);
        cmbTiempo.setColorOver(Color.lightGray);
        cmbTiempo.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                tiempo = ((cmbTiempo.getSelectedIndex() + 1));
                lblTotal.setText("$" + (350 *(double) ((cmbTiempo.getSelectedIndex() + 1) )));
            }
        });
        pnlPrincipal.add(cmbTiempo);

        ticket();

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(40, 520);
        btnAtras.setSize(80, 50);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnlPrincipal.add(btnAtras);

        JButtonRounded btnGuardar = new JButtonRounded(tipo ? "REGISTRAR" : "GUARDAR", 20, true);
        btnGuardar.setLocation(190, 520);
        btnGuardar.setSize(180, 50);
        btnGuardar.setFont(Recursos.FUENTE_BOTON);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    if (operacion()) {
                        accion = true;
                        new Notificacion(0, "El contrato se ha " + ((tipo) ? "registrado" : "editado") + " correctamente", false);
                        dispose();
                    }
                } else {
                    new Notificacion(1, "Error al registrar la venta. El importe es menor al total.", false);
                }
            }
        });
        pnlPrincipal.add(btnGuardar);
    }

    public boolean validar() {
        double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
        double total = Double.parseDouble(lblTotal.getText().replace('$', ' ').trim());
        return importe >= total;
    }

    public void setDatosContrato() {
        cmbAnfitrion.setSelectedItem(datos[1].toString());
        lblTotal.setText(datos[4].toString());
        txtImporte.setText(datos[4].toString().substring(1));
        LocalDate fechaIni = LocalDate.parse(datos[2].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate fechaFin = LocalDate.parse(datos[3].toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int mes = (fechaFin.getMonthValue() - fechaIni.getMonthValue()) - 1;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date dIni = null;
        try {
            dIni = formato.parse(datos[3].toString());
        } catch (ParseException ex) {
        }
        fApartirDe.setSelectedDate(dIni);
        cmbTiempo.setSelectedIndex(mes);
        txtImporte.setEnabled(false);
        cmbTiempo.setEnabled(false);
        txtAPartirDe.setEnabled(false);
        cmbMetodoPago.setEnabled(false);
    }

    public boolean operacion() {
        String nombre = cmbAnfitrion.getSelectedItem().toString();
        String total = lblTotal.getText().substring(1);
        String aPartirDe = txtAPartirDe.getText();
        LocalDate fechaIni = LocalDate.parse(aPartirDe, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate fechaFin = fechaIni.plusMonths(tiempo);
        String fechaInicio = fechaIni.getYear() + "-" + fechaIni.getMonthValue() + "-" + fechaIni.getDayOfMonth();
        String fechaFinal = fechaFin.getYear() + "-" + fechaFin.getMonthValue() + "-" + fechaFin.getDayOfMonth();
        if (tipo) {
            ContratoRealizado cr=new ContratoRealizado(GestionContratos.getFolio(),nombre,aPartirDe,cmbTiempo.getSelectedItem().toString(),total);
            Contrato_PDF c=new Contrato_PDF(cr);
            c.crearReporte();
            //System.out.println("NO OLVIDAR DESCOMENTAR");
            //return true;
            return GestionContratos.registrarContrato(nombre, total, fechaInicio, fechaFinal);
        } else {
            return GestionContratos.actualizarContrato(Integer.parseInt(datos[0].toString()), nombre, total, fechaInicio, fechaFinal);
        }
    }

    public boolean isAccion() {
        return accion;
    }
    JLabel lblTotal, lblCambio;
    JComboBoxCustom cmbMetodoPago;
    JTextFieldRounded txtImporte;

    public void ticket() {
        JPanelRounded pr3 = new JPanelRounded(20);
        pr3.setSize(330, 40);
        pr3.setOpaque(false);
        pr3.setLocation(40, 300);
        pr3.setBackground(new Color(255, 244, 227));

        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(20, 0);
        lblTotalTxt.setSize(80, 40);
        lblTotalTxt.setFont(getFuente(1, 1, 18));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pr3.add(lblTotalTxt);

        lblTotal = new JLabel("$350", JLabel.RIGHT);
        lblTotal.setLocation(100, 0);
        lblTotal.setSize(210, 40);
        lblTotal.setFont(getFuente(1, 1, 18));
        pr3.add(lblTotal);
        pr3.setLayout(new BorderLayout());
        pnlPrincipal.add(pr3);

        JLabel lblMetodoPago = new JLabel("Método de pago", JLabel.LEFT);
        lblMetodoPago.setLocation(40, 350);
        lblMetodoPago.setSize(150, 40);
        lblMetodoPago.setFont(getFuente(1, 1, 14));
        pnlPrincipal.add(lblMetodoPago);

        cmbMetodoPago = new JComboBoxCustom<>();
        cmbMetodoPago.setFont(getFuente(1, 1, 16));
        cmbMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Efectivo", "Transferencia"}));
        cmbMetodoPago.setLocation(40, 390);
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
        pnlPrincipal.add(cmbMetodoPago);

        JLabel lblImporte = new JLabel("Importe", JLabel.LEFT);
        lblImporte.setLocation(230, 350);
        lblImporte.setSize(150, 40);
        lblImporte.setFont(getFuente(1, 1, 14));
        pnlPrincipal.add(lblImporte);

        JPanelRounded pnlImporte = new JPanelRounded(20);
        pnlImporte.setOpaque(false);
        pnlImporte.setLocation(230, 390);
        pnlImporte.setSize(140, 40);
        pnlImporte.setBackground(new Color(239, 232, 232));

        JLabel lblPeso = new JLabel("$", JLabel.LEFT);
        lblPeso.setLocation(10, 0);
        lblPeso.setSize(20, 40);
        lblPeso.setFont(getFuente(1, 1, 20));
        pnlImporte.add(lblPeso);
        txtImporte = new JTextFieldRounded("0.00", 0, Recursos.FUENTE_GENERAL);
        txtImporte.setHorizontalAlignment(JTextField.LEFT);
        txtImporte.setLocation(30, 0);
        txtImporte.setSize(70, 40);
        Recursos.permitirSoloDobles(txtImporte);
        txtImporte.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizarCambio();
            }
        });
        pnlImporte.add(txtImporte);
        pnlImporte.setLayout(new BorderLayout());
        pnlPrincipal.add(pnlImporte);

        JPanelRounded pr4 = new JPanelRounded(20);
        pr4.setSize(330, 40);
        pr4.setOpaque(false);
        pr4.setLocation(40, 450);
        pr4.setBackground(new Color(255, 244, 227));

        JLabel lblCambioTxt = new JLabel("CAMBIO", JLabel.LEFT);
        lblCambioTxt.setLocation(20, 0);
        lblCambioTxt.setSize(100, 40);
        lblCambioTxt.setFont(getFuente(1, 1, 18));
        lblCambioTxt.setForeground(new Color(192, 145, 64));
        pr4.add(lblCambioTxt);

        lblCambio = new JLabel("$0.00", JLabel.RIGHT);
        lblCambio.setLocation(120, 0);
        lblCambio.setSize(190, 40);
        lblCambio.setFont(getFuente(1, 1, 18));
        pr4.add(lblCambio);
        pr4.setLayout(new BorderLayout());
        pnlPrincipal.add(pr4);
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
}
