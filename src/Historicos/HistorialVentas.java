package Historicos;

import BaseDeDatos.Control.GestionReportes;
import BaseDeDatos.Control.GestionVentas;
import BaseDeDatos.Control.Miscelanea;
import Calendario.DateChooser;
import Imprimir.Modelo.VentaRealizada;
import Imprimir.Plantilla.HistorialDeVentas_PDF;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HistorialVentas extends JPanel {

    private JComboBoxCustom cmbPeriodo;
    private JLabel lblFechaIni, lblFechaFin;
    JTable tblLista;

    public HistorialVentas(Dimension d) {
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        lblFechaIni.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Miscelanea.CargarTabla(GestionVentas.vistaVentasDe(lblFechaIni.getText(), lblFechaFin.getText()), tblLista, true);
                setSignoPeso();
                lblTotal.setText("$" + GestionReportes.totalVentas(lblFechaIni.getText(), lblFechaFin.getText()));
            }
        });
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();

    }
    MenuFecha mf = new MenuFecha();

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("HISTORIAL DE VENTAS");
        lblTitulo.setSize(600, 60);
        add(lblTitulo);
        JTextFieldRounded txtBuscar = new JTextFieldRounded("Folio, Nombre del cliente", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        add(txtBuscar);
        mf.setDialogPosition(710, 240);
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
        JButtonRounded btn = new JButtonRounded("Fecha", 20);
        btn.setBackground(new Color(255, 214, 153));
        btn.setColorOver(new Color(255, 224, 163));
        btn.setColor(new Color(255, 214, 153));
        btn.setColorClick(new Color(255, 224, 163));
        btn.setSize(240, 40);
        btn.setLocation(710, 180);
        btn.setFont(Recursos.FUENTE_GENERAL);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mf.showDialog();
                mf.addTablaLista(tblLista, "");
                mf.setTipo(2);
            }
        });
        add(btn);

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
    JLabel lblTotal;

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"Folio", "Nombre del cliente", "Fecha", "Subtotal", "Metodo de Pago", "Estado", ""};
        boolean[] colEditables = {false, false, false, false, false, false, true};
        int[] tamColumnas = {10, 240, 40, 30, 20, 10, 80};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, -1);
        AccionTabla(tblLista, -1);
        Miscelanea.CargarTabla(GestionVentas.vistaVentas(), tblLista, true);
        setSignoPeso();
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

    public void panelDerecho() {
        JPanelRounded pr = new JPanelRounded(20);
        pr.setSize(330, 190);
        pr.setOpaque(false);
        pr.setLocation(1100, 260);
        pr.setBackground(new Color(255, 244, 227));

        JLabel lblIGenerados = new JLabel("INGRESOS GENERADOS", JLabel.CENTER);
        lblIGenerados.setLocation(0, 20);
        lblIGenerados.setSize(330, 30);
        lblIGenerados.setFont(getFuente(0, 1, 24));
        lblIGenerados.setForeground(new Color(192, 145, 64));
        pr.add(lblIGenerados);

        JSeparatorCustom separador1 = new JSeparatorCustom(0, 4);
        separador1.setForeground(new Color(229, 209, 169));
        separador1.setBackground(Recursos.fondoPiel);
        separador1.setSize(300, 4);
        separador1.setLocation(15, 60);
        pr.add(separador1);
        DateChooser dc = new DateChooser();
        lblFechaIni = new JLabel(GestionVentas.getFechaMin(), JLabel.CENTER);
        lblFechaIni.setLocation(0, 80);
        lblFechaIni.setSize(150, 30);
        lblFechaIni.setFont(getFuente(1, 0, 22));
        lblFechaIni.setForeground(new Color(192, 145, 64));
        pr.add(lblFechaIni);

        lblFechaFin = new JLabel(dc.getSelectedDate().getDay() + "-" + dc.getSelectedDate().getMonth() + "-" + dc.getSelectedDate().getYear(), JLabel.CENTER);
        lblFechaFin.setLocation(180, 80);
        lblFechaFin.setSize(150, 30);
        lblFechaFin.setFont(getFuente(1, 0, 22));
        lblFechaFin.setForeground(new Color(192, 145, 64));
        pr.add(lblFechaFin);
        mf.setTextCiclos(lblFechaIni, lblFechaFin);

        JSeparatorCustom separador2 = new JSeparatorCustom(0, 4);
        separador2.setForeground(new Color(229, 209, 169));
        separador2.setBackground(Recursos.fondoPiel);
        separador2.setSize(300, 4);
        separador2.setLocation(15, 120);
        pr.add(separador2);

        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(20, 140);
        lblTotalTxt.setSize(80, 30);
        lblTotalTxt.setFont(getFuente(1, 0, 26));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pr.add(lblTotalTxt);

        lblTotal = new JLabel("$0.00", JLabel.RIGHT);
        lblTotal.setLocation(100, 140);
        lblTotal.setSize(210, 30);
        lblTotal.setFont(getFuente(1, 1, 24));
        pr.add(lblTotal);

        pr.setLayout(new BorderLayout());
        add(pr);

        JButtonRounded btnReporte = new JButtonRounded("CREAR REPORTE", 20, true);
        btnReporte.setLocation(1130, 480);
        btnReporte.setSize(260, 60);
        btnReporte.setFont(Recursos.FUENTE_BOTON);
        btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new ReporteDeVentas(getSize(), lblFechaIni.getText(), lblFechaFin.getText()));
                cp.siguientePanel(cp.panelActual());
            }
        });
        add(btnReporte);

        JButtonRounded btnPDF = new JButtonRounded("GENERAR PDF", 20, true);
        btnPDF.setLocation(1130, 570);
        btnPDF.setSize(260, 60);
        btnPDF.setFont(Recursos.FUENTE_BOTON);
        btnPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List l = listaVenta();
                HistorialDeVentas_PDF r = new HistorialDeVentas_PDF(lblFechaIni.getText(), lblFechaFin.getText(), l);
                r.crearReporte();
            }
        });
        add(btnPDF);
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está segura de cancelar la venta #" + tblLista.getValueAt(row, 0) + "?", true);
                if (n.getRespuesta()) {
                    if (GestionReportes.cancelarVenta(tblLista.getValueAt(row, 0).toString())) {
                        new Notificacion(0, "Se ha cancelado la venta exitosamente", false);
                        if (tblLista.isEditing()) {
                            tblLista.getCellEditor().stopCellEditing();
                        }
                        Miscelanea.CargarTabla(GestionVentas.vistaVentas(), tblLista, true);
                        setSignoPeso();
                        lblTotal.setText("$" + GestionReportes.totalVentas(lblFechaIni.getText(), lblFechaFin.getText()));
                    }
                }
            }

            @Override
            public void visualizar(int row) {
                String folio = tblLista.getValueAt(row, 0).toString();
                String cliente = tblLista.getValueAt(row, 1).toString();
                String fecha = tblLista.getValueAt(row, 2).toString();
                new DetalleVenta(true, folio, cliente, fecha);
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

    public void setSignoPeso() {
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 3), i, 3);
        }
    }

    public List<VentaRealizada> listaVenta() {
        List<VentaRealizada> lista = new ArrayList<VentaRealizada>();
        String[] infoVenta = new String[tblLista.getColumnCount() - 2];
        VentaRealizada pv;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            if (tblLista.getValueAt(i, 5).toString().equals("Activo")) {
                for (int j = 0; j < infoVenta.length; j++) {
                    infoVenta[j] = tblLista.getValueAt(i, j).toString();
                }
                pv = new VentaRealizada(infoVenta[0], infoVenta[1], infoVenta[2], infoVenta[3].substring(1), infoVenta[4].toString());
                lista.add(pv);
            }
        }

        return lista;
    }
}
