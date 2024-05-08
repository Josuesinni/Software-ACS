package Apartados;

import BaseDeDatos.Control.GestionApartados;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JPanelRounded;
import SwingModificado.ScrollBarCustom;
import Utilidades.Recursos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LP_Apartados extends JDialog {

    JPanel pnl;
    JTable tblLista;
    String datos[];
    JLabel lblTotal;

    public LP_Apartados(boolean modal, String[] datos) {
        super(vta, modal);
        this.datos = datos;
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        setSize(new Dimension(600, 440));
        initComponents();
        Miscelanea.CargarTabla(GestionApartados.getListaProductosApartados(datos[0]), tblLista, false);
        addSignoTabla();
        calcularTotal();
        pnl.setLayout(new BorderLayout());
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        JLabel lblTitulo = new JLabel("APARTADO #" + datos[0], JLabel.LEFT);
        lblTitulo.setLocation(30, 20);
        lblTitulo.setSize(300, 30);
        lblTitulo.setFont(Recursos.FUENTE_TITULO_2);
        pnl.add(lblTitulo);

        JLabel lblNombreCliente = new JLabel("A nombre de: " + datos[1], JLabel.LEFT);
        lblNombreCliente.setLocation(30, 60);
        lblNombreCliente.setSize(300, 20);
        lblNombreCliente.setFont(Recursos.getFuente(0, 1, 14));
        lblNombreCliente.setForeground(new Color(192, 145, 64));
        pnl.add(lblNombreCliente);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(30, 330);
        btnAtras.setSize(60, 40);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                dispose();
            }
        });
        pnl.add(btnAtras);

        tabla();

        JPanelRounded pnlFondo = new JPanelRounded(20);
        pnlFondo.setSize(240, 40);
        pnlFondo.setLocation(310, 330);
        pnlFondo.setBackground(new Color(255, 244, 227));

        JLabel lblTotalTxt = new JLabel("TOTAL", JLabel.LEFT);
        lblTotalTxt.setLocation(10, 0);
        lblTotalTxt.setSize(60, 40);
        lblTotalTxt.setFont(Recursos.getFuente(1, 0, 18));
        lblTotalTxt.setForeground(new Color(192, 145, 64));
        pnlFondo.add(lblTotalTxt);

        lblTotal = new JLabel("$0.00", JLabel.RIGHT);
        lblTotal.setLocation(70, 0);
        lblTotal.setSize(160, 40);
        lblTotal.setFont(Recursos.getFuente(1, 0, 18));
        pnlFondo.add(lblTotal);

        pnlFondo.setLayout(new BorderLayout(10, 10));
        pnl.add(pnlFondo);
    }

    public void tabla() {
        Object[][] datos = new Object[][]{{"Delineador", "$129.99", "1", "$129.99",}, {"Mascarilla", "$50.00", "2", "$100.00",}};
        String[] cabecera = new String[]{"Nombre del producto", "Precio", "Uds.", "Subtotal"};
        boolean[] colEditables = {false, false, false, false};
        int[] tamColumnas = {250, 10, 20, 40};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 5);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(500, 200));
        jsp1.setPreferredSize(new java.awt.Dimension(500, 200));
        jsp1.setRowHeaderView(null);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(50, 100);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        pnl.add(jsp1);
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
}
