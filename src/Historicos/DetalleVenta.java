package Historicos;

import BaseDeDatos.Control.GestionVentas;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JPanelRounded;
import SwingModificado.ScrollBarCustom;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DetalleVenta extends JDialog {

    JPanel pnl;
    String folio;
    String cliente;
    String fecha;
    JLabel lblTotal;

    public DetalleVenta(boolean mode, String folio, String cliente, String fecha) {
        super(vta, mode);
        this.folio = folio;
        this.cliente = cliente;
        this.fecha = fecha;
        this.pnl = new JPanel();
        setTitle("Detalle de venta");
        setSize(900, 550);
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        JLabel lblTitulo = new JLabel("DETALLE DE VENTA", JLabel.LEFT);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(50, 30);
        lblTitulo.setFont(getFuente(1, 0, 32));
        pnl.add(lblTitulo);

        JLabel lblFecha = new JLabel(fecha, JLabel.RIGHT);
        lblFecha.setSize(200, 30);
        lblFecha.setLocation(600, 30);
        lblFecha.setFont(getFuente(1, 0, 18));
        pnl.add(lblFecha);

        JLabel lblFolio = new JLabel("Folio #" + folio, JLabel.LEFT);
        lblFolio.setSize(400, 30);
        lblFolio.setLocation(50, 80);
        lblFolio.setFont(getFuente(1, 0, 18));
        pnl.add(lblFolio);

        JLabel lblCliente = new JLabel("Cliente: " + cliente, JLabel.LEFT);
        lblCliente.setSize(400, 30);
        lblCliente.setLocation(50, 120);
        lblCliente.setFont(getFuente(1, 0, 18));
        pnl.add(lblCliente);

        JPanelRounded pr = new JPanelRounded(20);
        pr.setSize(300, 60);
        pr.setLocation(500, 420);
        pr.setBackground(new Color(255, 244, 227));
        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(20, 15);
        lblTotalTxt.setSize(80, 30);
        lblTotalTxt.setFont(getFuente(1, 0, 26));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pr.add(lblTotalTxt);

        lblTotal = new JLabel("$0.00", JLabel.RIGHT);
        lblTotal.setLocation(100, 15);
        lblTotal.setSize(180, 30);
        lblTotal.setFont(getFuente(1, 1, 24));
        pr.add(lblTotal);

        pr.setLayout(new BorderLayout());
        pnl.add(pr);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(50, 420);
        btnAtras.setSize(80, 60);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnAtras);

        tabla();
        setTotal();
    }
    JTable tblLista;

    public void tabla() {
        String[] cabecera = {"Nombre del producto", "Uds.", "Precio", "Subtotal"};
        Object[][] datos = {};
        int[] tamColumna = {270, 10, 30, 30, 30};
        boolean[] colEditables = {false, false, false, false};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumna, colEditables, 5);
        
        Miscelanea.CargarTabla(GestionVentas.vistaTicket(folio), tblLista, false);
        setSignoPeso();
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(getWidth() - 100, 220));
        jsp1.setPreferredSize(new java.awt.Dimension(getWidth() - 100, 400));
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
        jsp1.setLocation(50, 170);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        pnl.add(jsp1);
    }

    public void setSignoPeso() {
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 2), i, 2);
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 3), i, 3);
        }
    }

    public void setTotal() {
        double total = 0.00;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            total += Double.parseDouble(tblLista.getValueAt(i, 3).toString().substring(1));
        }
        lblTotal.setText("$" + total);
    }
}
