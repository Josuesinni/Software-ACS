/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menus;

import SwingModificado.JCheckBoxCustom;
import SwingModificado.JTextFieldRounded;
import Utilidades.Recursos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Home
 */
public class MenuMostrar extends JDialog {

    //private JDialog dialog;
    private JPanel pnlFondo;
    private JTable tblLista;

    public MenuMostrar() {
        initComponents();
        pnlFondo.setLayout(new BorderLayout());
        setUndecorated(true);
        setContentPane(pnlFondo);
        setBackground(new Color(0, 0, 0, 0));
    }
    JCheckBoxCustom anfitrion, categoria, id;

    private void initComponents() {
        pnlFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                //g2.setColor(Color.white);
                g2.setColor(new Color(255, 224, 163));
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Area borde = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
                Area headear = new Area(new Rectangle2D.Double(0, 0, getWidth(), 40));
                borde.add(headear);
                g2.fill(borde);
            }
        };
        pnlFondo.setBackground(new Color(255, 224, 163));
        //pnlFondo.setBackground(Color.white);
        id = new JCheckBoxCustom("Id", Recursos.FUENTE_GENERAL);
        id.setLocation(20, 10);
        id.setSize(150, 30);
        id.setBackground(pnlFondo.getBackground());
        id.setSelected(true);
        id.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
            }
        });
        pnlFondo.add(id);
        categoria = new JCheckBoxCustom("Categoria", Recursos.FUENTE_GENERAL);
        categoria.setLocation(20, 40);
        categoria.setSize(150, 30);
        categoria.setBackground(pnlFondo.getBackground());
        categoria.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
            }
        });
        pnlFondo.add(categoria);
        anfitrion = new JCheckBoxCustom("Anfitrion", Recursos.FUENTE_GENERAL);
        anfitrion.setLocation(20, 70);
        anfitrion.setSize(150, 30);
        anfitrion.setBackground(pnlFondo.getBackground());
        anfitrion.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
            }
        });
        pnlFondo.add(anfitrion);
    }

    public void setTablaLista(JTable tblLista) {
        this.tblLista = tblLista;
    }
}
