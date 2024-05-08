package Menus;

import SwingModificado.JButtonRounded;
import SwingModificado.JTextFieldRounded;
import SwingModificado.JTextFieldTitled;
import Utilidades.Recursos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuAjustarAnticipo extends JPanel {

    private javax.swing.JPopupMenu popup;
    private JButtonRounded boton;
    private JLabel lblAnticipo,lblCambio;
    private JTextFieldRounded txtImporte;
    public MenuAjustarAnticipo() {
        initComponent();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(null);
        popup.add(this);
    }
    JTextFieldRounded tfr;
    boolean guardar;

    public void initComponent() {
        tfr = new JTextFieldRounded("0.00", 20, Recursos.FUENTE_GENERAL);
        tfr.setSize(140, 30);
        JTextFieldTitled tt = new JTextFieldTitled("Nuevo anticipo", tfr, true, 170, 80);
        tt.setLocation(15, 20);
        add(tt);
        JButtonRounded brGuardar = new JButtonRounded("GUARDAR", 20, true);
        brGuardar.setSize(104, 35);
        brGuardar.setLocation(206, 20);
        brGuardar.setFont(Recursos.FUENTE_BOTON_2);
        brGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblAnticipo.setText("$" + String.format("%.2f", Double.parseDouble(tfr.getText().isEmpty() ? "0" : tfr.getText())));
                actualizarCambio();
                popup.setVisible(false);
                brGuardar.restaurar();
            }
        });
        add(brGuardar);
        JButtonRounded brCancelar = new JButtonRounded("CANCELAR", 20, false);
        brCancelar.setSize(104, 35);
        brCancelar.setLocation(206, 65);
        brCancelar.setFont(Recursos.FUENTE_BOTON_2);
        brCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.setVisible(false);
                brCancelar.restaurar();
            }
        });
        add(brCancelar);
        popup = new javax.swing.JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(255, 244, 227));
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Area borde = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
                Area headear = new Area(new Rectangle2D.Double(0, 0, getWidth(), 40));
                borde.add(headear);
                g2.fill(borde);
            }
        };
    }

    public void setBotonDeReferencia(JButtonRounded btn) {
        this.boton = btn;
        this.boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btn.isEnabled()) {
                    showPopup();
                }
            }
        });
    }

    public void showPopup() {
        popup.show(boton, -130, boton.getHeight());
    }

    public void changePopupSize(int w, int h) {
        popup.setPopupSize(w, h);
    }

    public String getValor() {
        return tfr.getText();
    }

    public boolean accion() {
        return guardar;
    }

    public void hidePopup() {
        popup.setVisible(false);
    }

    public void setAnticipo(JLabel lblAnticipo) {
        this.lblAnticipo = lblAnticipo;
    }
    public void setImporte(JTextFieldRounded txtImporte){
        this.txtImporte=txtImporte;
    }
    public void setCambio(JLabel lblCambio){
        this.lblCambio = lblCambio;
    }
    public void setValorAnticipo(String text) {
        tfr.setText(text);
    }
    private void actualizarCambio() {
        double anticipo = Double.parseDouble(lblAnticipo.getText().substring(1));
        double importe = txtImporte.getText().isEmpty() ? 0.00 : Double.parseDouble(txtImporte.getText());
        if (importe - anticipo > 0) {
            lblCambio.setForeground(Color.black);
            lblCambio.setText("$" + (String.format("%.2f", (importe - anticipo))));
        } else {
            lblCambio.setForeground(Color.red);
            lblCambio.setText("$" + (String.format("%.2f", (importe - anticipo))));
        }
    }
}
