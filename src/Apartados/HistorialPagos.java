package Apartados;

import BaseDeDatos.Control.GestionPagos;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.ScrollBarCustom;
import Utilidades.Recursos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HistorialPagos extends JDialog {

    JPanel pnl;
    JTable tblLista;
    String datos[];

    public HistorialPagos(boolean modal, String datos[]) {
        super(vta, modal);
        this.datos = datos;
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        setSize(600, 440);
        setTitle("Historial de pagos");
        initComponents();
        GestionPagos.vistaPagos(tblLista, datos[0]);
        addSignoTabla();
        pnl.setLayout(new BorderLayout());
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        JLabel lblTitulo = new JLabel("HISTORIAL DE PAGOS", JLabel.LEFT);
        lblTitulo.setLocation(30, 20);
        lblTitulo.setSize(300, 30);
        lblTitulo.setFont(Recursos.FUENTE_TITULO_2);
        pnl.add(lblTitulo);

        JLabel lblNombreCliente = new JLabel("A nombre de: "+datos[1], JLabel.LEFT);
        lblNombreCliente.setLocation(30, 60);
        lblNombreCliente.setSize(300, 20);
        lblNombreCliente.setFont(Recursos.FUENTE_GENERAL_2);
        lblNombreCliente.setForeground(new Color(192, 145, 64));
        pnl.add(lblNombreCliente);

        JLabel lblFolioApartado = new JLabel("Apartado #"+datos[0], JLabel.RIGHT);
        lblFolioApartado.setLocation(450, 20);
        lblFolioApartado.setSize(100, 30);
        lblFolioApartado.setFont(Recursos.FUENTE_GENERAL_2);
        pnl.add(lblFolioApartado);

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
    }

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"#No", "Abono", "Fecha"};
        boolean[] colEditables = {false, false, false};
        int[] tamColumnas = {10, 20, 20};
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
    private void addSignoTabla(){
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$"+tblLista.getValueAt(i, 1), i, 1);
        }
    }
}
