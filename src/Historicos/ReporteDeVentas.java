package Historicos;

import BaseDeDatos.Control.GestionAnfitriones;
import BaseDeDatos.Control.GestionReportes;
import BaseDeDatos.Control.Miscelanea;
import Buscadores.Buscador;
import Imprimir.Modelo.ProductoVendido;
import Imprimir.Plantilla.ReporteDeVentas_PDF;
import static Principal.Ventana.cp;
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
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ReporteDeVentas extends JPanel {

    JTable tblLista;
    JComboBoxCustom cmbFiltro, cmbAnfitrion;
    Buscador buscador;
    String fechaIni;
    String fechaFin;

    public ReporteDeVentas(Dimension d, String fechaIni, String fechaFin) {
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);

    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("REPORTE DE VENTAS");
        lblTitulo.setSize(500, 60);
        add(lblTitulo);

        JTextFieldRounded txtBuscar = new JTextFieldRounded("Clave/Nombre del anfitrión", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!txtBuscar.getText().isEmpty()) {
                    if (e.getKeyCode() != 10) {
                        buscador.actualizarLista(GestionReportes.buscadorReporte(cmbFiltro.getSelectedIndex(), txtBuscar.getText()));
                        Miscelanea.CargarTabla(GestionReportes.busquedaVentas(cmbFiltro.getSelectedItem().toString(), txtBuscar.getText(), fechaIni, fechaFin), tblLista, false);
                        setSignoPeso();
                    }
                } else {
                    Miscelanea.CargarTabla(GestionReportes.busquedaVentas(cmbFiltro.getSelectedItem().toString(), txtBuscar.getText(), fechaIni, fechaFin), tblLista, false);
                    buscador.hidePopUp();
                    setSignoPeso();
                }
            }

        });

        add(txtBuscar);
        buscador = new Buscador();
        buscador.changePopupSize(460, 100);
        buscador.setBuscar(txtBuscar);

        cmbFiltro = new JComboBoxCustom<>();
        cmbFiltro.setFont(getFuente(1, 1, 16));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ninguno", "Nombre", "Categoria"}));
        cmbFiltro.setLocation(700, 187);
        cmbFiltro.setSize(140, 36);
        cmbFiltro.setBackground(new Color(255, 214, 153));
        cmbFiltro.setColorOver(new Color(240, 200, 120));
        cmbFiltro.setMaximumRowCount(3);
        add(cmbFiltro);

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

        JLabel lblEstado = new JLabel("Anfitrion:", JLabel.LEFT);
        lblEstado.setLocation(900, 180);
        lblEstado.setSize(150, 40);
        lblEstado.setFont(getFuente(1, 1, 14));
        add(lblEstado);

        cmbAnfitrion = new JComboBoxCustom<>();
        cmbAnfitrion.setFont(getFuente(1, 1, 16));
        Miscelanea.CargarComboBox(GestionAnfitriones.cargarAnfitrionesParaReporte(fechaIni, fechaFin), cmbAnfitrion);
        cmbAnfitrion.addItem("Todos");
        cmbAnfitrion.setLocation(1000, 187);
        cmbAnfitrion.setSize(180, 36);
        cmbAnfitrion.setBackground(new Color(255, 214, 153));
        ((JLabel) cmbAnfitrion.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbAnfitrion.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String anfitrion = cmbAnfitrion.getSelectedItem().toString();
                if (anfitrion.equals("Todos")) {
                    tablaMode(true);
                    Miscelanea.CargarTabla(GestionReportes.ventasRealizadasEn(fechaIni, fechaFin), tblLista, false);
                    setSignoPeso();
                } else {
                    tablaMode(false);
                    Miscelanea.CargarTabla(GestionReportes.ventasRealizadasEn(fechaIni, fechaFin, anfitrion), tblLista, false);
                    setSignoPeso();
                }
            }
        });
        cmbAnfitrion.setSelectedItem("Todos");
        add(cmbAnfitrion);
    }

    private void tablaMode(boolean tipo) {
        DefaultTableModel dtm;
        String[] cabecera;
        int[] tamColumnas;
        boolean[] colEditables = {false, false, false, false, false, false, false};

        if (tipo) {
            cabecera = new String[]{"Clave", "Nombre del producto", "Precio", "Uds.", "Subtotal", "Anfitrión", "Categoria"};
            tamColumnas = new int[]{10, 300, 20, 10, 20, 160, 120};
        } else {
            cabecera = new String[]{"Clave", "Nombre del producto", "Precio", "Uds.", "Subtotal", "Categoria"};
            tamColumnas = new int[]{10, 300, 20, 10, 20, 120};
        }
        dtm = new DefaultTableModel(new Object[][]{}, cabecera) {
            boolean[] canEdit = colEditables;

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblLista.setModel(dtm);
        if (tblLista.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < tblLista.getColumnModel().getColumnCount(); i++) {
                tblLista.getColumnModel().getColumn(i).setResizable(false);
                tblLista.getColumnModel().getColumn(i).setPreferredWidth(tamColumnas[i]);
            }
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 2; i < 5; i++) {
            tblLista.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblLista.setAutoCreateColumnsFromModel(true);
    }

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"Clave", "Nombre del producto", "Precio", "Uds.", "Subtotal", "Anfitrión", "Categoria"};
        boolean[] colEditables = {false, false, false, false, false, false, false};
        int[] tamColumnas = {20, 100, 40, 20, 40, 100, 100};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 5);
        Miscelanea.CargarTabla(GestionReportes.ventasRealizadasEn(fechaIni, fechaFin), tblLista, false);
        setSignoPeso();
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(1320, 380));
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
        JLabel lblFechaIni = new JLabel("Del: " + fechaIni, JLabel.RIGHT);
        lblFechaIni.setLocation(1100, 60);
        lblFechaIni.setSize(165, 20);
        lblFechaIni.setFont(getFuente(0, 1, 16));
        add(lblFechaIni);
        JLabel lblFechaFin = new JLabel("Al:" + fechaFin, JLabel.RIGHT);
        lblFechaFin.setLocation(1265, 60);
        lblFechaFin.setSize(165, 20);
        lblFechaFin.setFont(getFuente(0, 1, 16));
        add(lblFechaFin);
        
        JButtonRounded btnGenerarPdf = new JButtonRounded("GENERAR PDF", 20, true);
        btnGenerarPdf.setLocation(1120, 675);
        btnGenerarPdf.setSize(300, 60);
        btnGenerarPdf.setFont(Recursos.FUENTE_BOTON);
        btnGenerarPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String anfitrion = cmbAnfitrion.getSelectedItem().toString();
                List l= listaProductos(anfitrion.equals("Todos"));
                ReporteDeVentas_PDF r = new ReporteDeVentas_PDF(fechaIni, fechaFin, anfitrion, l,anfitrion.equals("Todos"));
                r.crearReporte();
            }
        });
        add(btnGenerarPdf);
    }

    public List<ProductoVendido> listaProductos(boolean tipo) {
        List<ProductoVendido> lista = new ArrayList<ProductoVendido>();
        String[] infoVenta = new String[tblLista.getColumnCount()];
        ProductoVendido pv;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            for (int j = 0; j < tblLista.getColumnCount(); j++) {
                infoVenta[j] = tblLista.getValueAt(i, j).toString();
            }
            if (tipo) {
                pv = new ProductoVendido(infoVenta[0], infoVenta[1], infoVenta[2].substring(1), infoVenta[3], infoVenta[4].substring(1), infoVenta[5], infoVenta[6]);
            }else{
                pv = new ProductoVendido(infoVenta[0], infoVenta[1], infoVenta[2].substring(1), infoVenta[3], infoVenta[4].substring(1), infoVenta[5]);
            }
            lista.add(pv);
        }

        return lista;
    }
    public void actualizarTotal(){
        
    }
    public void setSignoPeso() {
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 2), i, 2);
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 4), i, 4);
        }
    }
}