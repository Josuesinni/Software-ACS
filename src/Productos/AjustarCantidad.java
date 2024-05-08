package Productos;

import BaseDeDatos.Control.GestionProductos;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JPanelRounded;
import SwingModificado.JTextFieldRounded;
import SwingModificado.JToggleButtonCustom;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AjustarCantidad extends JDialog {

    JPanel pnl;
    String id;
    String nombreProducto;
    String cantidad;
    boolean isAjusteRealizado;

    public AjustarCantidad(boolean modal, String datos[]) {
        super(vta, modal);
        pnl = new JPanel();
        this.id = datos[0];
        this.nombreProducto = datos[1];
        this.cantidad = datos[2];
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setTitle("Ajustar Cantidad");
        setSize(480, 400);
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    JTextFieldRounded txtCantidad;
    JComboBoxCustom cmbRazon;

    public void initComponents() {
        JLabel lblNombreProducto = new JLabel(nombreProducto, JLabel.LEFT);
        lblNombreProducto.setLocation(30, 20);
        lblNombreProducto.setSize(420, 30);
        lblNombreProducto.setFont(getFuente(1, 0, 18));
        pnl.add(lblNombreProducto);

        JPanelRounded pnlInventario = new JPanelRounded(20);
        pnlInventario.setSize(150, 30);
        pnlInventario.setLocation(40, 60);
        pnlInventario.setBackground(Recursos.fondoPiel);

        JLabel cantInventario = new JLabel(cantidad + " en inventario", JLabel.CENTER);
        cantInventario.setSize(150, 30);
        cantInventario.setFont(getFuente(1, 0, 14));
        cantInventario.setForeground(Recursos.letraDorada);
        pnlInventario.add(cantInventario);
        pnlInventario.setLayout(new BorderLayout());
        pnl.add(pnlInventario);

        JPanelRounded pnlAjustado = new JPanelRounded(20);
        pnlAjustado.setSize(120, 30);
        pnlAjustado.setLocation(200, 60);
        pnlAjustado.setBackground(Recursos.fondoPiel);

        JLabel cantAjustada = new JLabel("0 ajustado", JLabel.CENTER);
        cantAjustada.setSize(120, 30);
        cantAjustada.setFont(getFuente(1, 0, 14));
        pnlAjustado.add(cantAjustada);
        pnlAjustado.setLayout(new BorderLayout());
        pnl.add(pnlAjustado);

        JPanelRounded pnlCantidad = new JPanelRounded(40);
        pnlCantidad.setSize(180, 70);
        pnlCantidad.setOpaque(false);
        pnlCantidad.setLocation(240, 120);

        JLabel lblCantidad = new JLabel("Cantidad", JLabel.LEFT);
        lblCantidad.setLocation(20, 10);
        lblCantidad.setSize(280, 20);
        lblCantidad.setFont(Recursos.FUENTE_GENERAL_2);
        pnlCantidad.add(lblCantidad);

        JLabel lblSimbolo = new JLabel("+", JLabel.LEFT);
        lblSimbolo.setLocation(20, 40);
        lblSimbolo.setSize(10, 20);
        lblSimbolo.setFont(Recursos.FUENTE_GENERAL_2);
        pnlCantidad.add(lblSimbolo);

        txtCantidad = new JTextFieldRounded("0", 20, getFuente(0, 0, 18));
        txtCantidad.setLocation(40, 30);
        txtCantidad.setSize(120, 40);
        txtCantidad.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtCantidad.setBackground(Recursos.fondoGris);
        txtCantidad.setOpaque(false);
        Recursos.permitirSoloNumeros(txtCantidad);
        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cantAjustada.setText((txtCantidad.getText().isEmpty() ? "0" : txtCantidad.getText()) + " ajustado");
            }
        });
        pnlCantidad.add(txtCantidad);

        pnlCantidad.setLayout(new BorderLayout());
        pnl.add(pnlCantidad);

        JPanelRounded pnlTipoOperacion = new JPanelRounded(20);
        pnlTipoOperacion.setSize(170, 70);
        pnlTipoOperacion.setOpaque(false);
        pnlTipoOperacion.setLocation(40, 120);
        pnlTipoOperacion.setBackground(Recursos.fondoPiel);

        ButtonGroup btg = new ButtonGroup();

        JToggleButtonCustom tbtnMas = new JToggleButtonCustom();
        tbtnMas.setText("+");
        tbtnMas.setSize(60, 40);
        tbtnMas.setRadio(20);
        tbtnMas.setDibujarFondo(true);
        tbtnMas.setBackground(new Color(255, 214, 153));
        tbtnMas.setSelectedBackground(new Color(235, 194, 133));
        tbtnMas.setLocation(15, 15);
        tbtnMas.setFont(Recursos.FUENTE_BOTON);
        tbtnMas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblSimbolo.setText("+");
                cmbRazon.setSelectedIndex(0);
            }
        });
        tbtnMas.setSelected(true);
        btg.add(tbtnMas);
        pnlTipoOperacion.add(tbtnMas);

        JToggleButtonCustom tbtnMenos = new JToggleButtonCustom();
        tbtnMenos.setText("-");
        tbtnMenos.setSize(60, 40);
        tbtnMenos.setRadio(20);
        tbtnMenos.setDibujarFondo(true);
        tbtnMenos.setBackground(new Color(255, 214, 153));
        tbtnMenos.setSelectedBackground(new Color(235, 194, 133));
        tbtnMenos.setLocation(95, 15);
        tbtnMenos.setFont(Recursos.FUENTE_BOTON);
        tbtnMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblSimbolo.setText("-");
                cmbRazon.setSelectedIndex(1);
            }
        });
        btg.add(tbtnMenos);
        pnlTipoOperacion.add(tbtnMenos);

        pnlTipoOperacion.setLayout(new BorderLayout());
        pnl.add(pnlTipoOperacion);

        JLabel lblRazon = new JLabel("Razón:", JLabel.LEFT);
        lblRazon.setLocation(80, 220);
        lblRazon.setSize(60, 30);
        lblRazon.setFont(Recursos.FUENTE_GENERAL_2);
        pnl.add(lblRazon);

        cmbRazon = new JComboBoxCustom<>();
        cmbRazon.setFont(getFuente(1, 1, 14));
        cmbRazon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Entrada de productos", "Salida de productos"}));
        cmbRazon.setLocation(180, 220);
        cmbRazon.setSize(200, 30);
        cmbRazon.setBackground(new Color(255, 214, 153));
        ((JLabel) cmbRazon.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbRazon.setMaximumRowCount(2);
        cmbRazon.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (cmbRazon.getSelectedIndex() == 0) {
                    tbtnMas.setSelected(true);
                    lblSimbolo.setText("+");
                } else {
                    tbtnMenos.setSelected(true);
                    lblSimbolo.setText("-");
                }
            }
        });
        pnl.add(cmbRazon);

        JButtonRounded btnCancelar = new JButtonRounded("CANCELAR", 20, false);
        btnCancelar.setLocation(120, 290);
        btnCancelar.setSize(140, 40);
        btnCancelar.setFont(getFuente(1, 0, 18));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnCancelar);
        JButtonRounded btnAjustar = new JButtonRounded("AJUSTAR", 20);
        btnAjustar.setLocation(280, 290);
        btnAjustar.setSize(140, 40);
        btnAjustar.setFont(getFuente(1, 0, 18));
        btnAjustar.setBackground(new Color(16, 155, 249));
        btnAjustar.setForeground(new Color(255, 255, 255));
        btnAjustar.setColor(new Color(16, 155, 249));
        btnAjustar.setColorOver(new Color(16, 135, 229));
        btnAjustar.setColorClick(new Color(16, 115, 209));
        btnAjustar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nuevaCantidad = txtCantidad.getText().isEmpty() ? 0 : Integer.parseInt(txtCantidad.getText());
                if (nuevaCantidad > 0) {
                    if (tbtnMenos.isSelected() && nuevaCantidad <= Integer.parseInt(cantidad)) {
                        if (GestionProductos.ajustarCantidadProducto(Integer.parseInt(id), -nuevaCantidad, cmbRazon.getSelectedIndex())) {
                            isAjusteRealizado = true;
                            new Notificacion(0, "Ajustes realizados correctamente", false);
                            dispose();
                        }
                    } else if (tbtnMas.isSelected()) {
                        if (GestionProductos.ajustarCantidadProducto(Integer.parseInt(id), nuevaCantidad, cmbRazon.getSelectedIndex())) {
                            isAjusteRealizado = true;
                            new Notificacion(0, "Ajustes realizados correctamente", false);
                            dispose();
                        }
                    } else {
                        new Notificacion(4, "Error: La cantidad ingresada supera la disponible", true);
                    }
                } else {
                    new Notificacion(4, "Error: La cantidad ingresada no es valida", true);
                }
            }
        });
        pnl.add(btnAjustar);
    }

    public boolean isIsAjusteRealizado() {
        return isAjusteRealizado;
    }

}
