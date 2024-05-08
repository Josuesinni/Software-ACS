package Buscadores;

import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Utilidades.Recursos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Buscador extends JPanel {

    private javax.swing.JPopupMenu popup;
    private JTextFieldRounded buscar;
    private Color fondo = new Color(239, 239, 239);

    public Buscador() {
        UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
        initComponent();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(null);
        popup.setFocusable(false);
        popup.add(this);
    }
    JList lista;

    public void initComponent() {
        lista = new JList();
        lista.setBackground(new Color(239, 239, 239));
        lista.setBorder(BorderFactory.createEmptyBorder());
        lista.setFont(Recursos.FUENTE_GENERAL_2);
        lista.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });

        lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (lista.getSelectedValue() != null) {
                    buscar.setText(lista.getSelectedValue().toString());
                    popup.setVisible(false);
                }
            }
        });
        lista.setFixedCellHeight(24);
        popup = new javax.swing.JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(fondo);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Area borde = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                Area headear = new Area(new Rectangle2D.Double(0, 0, getWidth(), 20));
                borde.add(headear);
                g2.fill(borde);
            }
        };

        jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(popup.getWidth(), popup.getHeight()));
        jsp1.setPreferredSize(new java.awt.Dimension(popup.getWidth(), popup.getHeight()));
        jsp1.setRowHeaderView(null);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(30);
        sb.setForeground(new Color(255, 214, 153));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(lista);
        jsp1.setLocation(0, 0);
        jsp1.setBorder(BorderFactory.createEmptyBorder());
        add(jsp1);
    }
    JScrollPane jsp1;

    public void setBuscar(JTextFieldRounded buscar) {
        this.buscar = buscar;
        buscar.setEditable(true);
    }
    ResultSet rs;

    public void actualizarLista(ResultSet rs) {
        try {
            DefaultListModel productos = new DefaultListModel();
            while (rs.next()) {
                if (rs != null) {
                    productos.addElement(rs.getString(2));
                }
            }
            lista.setModel(productos);
            showPopup();

        } catch (SQLException ex) {
        }
    }

    public void showPopup() {
        popup.show(buscar, 0, buscar.getHeight() - 6);
    }
    public void hidePopUp(){
        popup.setVisible(false);
    }
    public void changePopupSize(int w, int h) {
        popup.setPopupSize(w, h);
        jsp1.setSize(w, h - 15);
    }

    public void setFondo(Color fondo) {
        this.fondo = fondo;
    }

}
