package Usuarios;

import BaseDeDatos.Control.GestionUsuarios;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JPanelRounded;
import SwingModificado.JPlaceHolder;
import SwingModificado.JTextFieldRounded;
import SwingModificado.JTextFieldTitled;
import SwingModificado.JToggleButtonCustom;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class Administracion extends JDialog {

    JPanel pnl;
    JLabel lblError;
    JTextFieldTitled pr;
    JPanelRounded pr2;
    JTextFieldRounded txtUsuario;
    JPasswordField txtPass;

    public Administracion(boolean modal, String titulo) {
        super(vta, modal);
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setTitle(titulo);
        setSize(600, 440);
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void initComponents() {
        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN", JLabel.CENTER);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(100, 30);
        lblTitulo.setFont(getFuente(1, 0, 28));
        lblTitulo.setForeground(Recursos.letraDorada);
        pnl.add(lblTitulo);
        JLabel lblTitulo2 = new JLabel("DE USUARIOS", JLabel.CENTER);
        lblTitulo2.setSize(400, 30);
        lblTitulo2.setLocation(100, 60);
        lblTitulo2.setFont(getFuente(1, 0, 28));
        lblTitulo2.setForeground(Recursos.letraDorada);
        pnl.add(lblTitulo2);
        txtUsuario = new JTextFieldRounded("Cuenta de administrador", 10, getFuente(0, 0, 16));
        pr = new JTextFieldTitled("Usuario", txtUsuario, false, 450, 70);
        pr.setFont(getFuente(0, 2, 20));
        pr.setBackground(Color.white);
        pr.setLocation(75, 120);
        txtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
                if (!pr.isPintarBorde() && !pr2.isPintarBorde()) {
                    lblError.setVisible(false);
                }
            }
        });
        pnl.add(pr);

        pr2 = new JPanelRounded(40);
        pr2.setSize(450, 70);
        pr2.setOpaque(false);
        pr2.setLocation(75, 210);

        JLabel lblPass = new JLabel("Contraseña", JLabel.LEFT);
        lblPass.setLocation(20, 10);
        lblPass.setSize(120, 20);
        lblPass.setFont(getFuente(0, 1, 14));
        pr2.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setFont(getFuente(0, 0, 16));
        txtPass.setLocation(20, 25);
        txtPass.setSize(380, 50);
        txtPass.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtPass.setBackground(new Color(239, 232, 232));
        txtPass.setOpaque(false);

        JPlaceHolder ph = new JPlaceHolder("Contraseña", txtPass);
        ph.changeAlpha(0.5f);

        pr2.add(txtPass);
        txtPass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr2.setPintarBorde(false);
                if (!pr.isPintarBorde() && !pr2.isPintarBorde()) {
                    lblError.setVisible(false);
                }
            }
        });
        JToggleButtonCustom btnMostrar = new JToggleButtonCustom();
        btnMostrar.setLocation(400, 40);
        btnMostrar.setSize(24, 24);
        btnMostrar.setIconoHide(new ImageIcon(getClass().getResource("/res/imagenes/iu/hide.png")));
        btnMostrar.setIconoShow(new ImageIcon(getClass().getResource("/res/imagenes/iu/show.png")));
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnMostrar.isSelected()) {
                    txtPass.setEchoChar((char) 0);
                } else {
                    txtPass.setEchoChar('*');
                }
            }
        });
        pr2.add(btnMostrar);

        pr2.setLayout(new BorderLayout());
        pnl.add(pr2);

        lblError = new JLabel("", JLabel.RIGHT);
        lblError.setSize(280, 20);
        lblError.setFont(getFuente(0, 0, 12));
        lblError.setForeground(Color.red);
        lblError.setLocation(pr2.getX() + 140, pr2.getY() + 80);
        lblError.setVisible(false);
        pnl.add(lblError);
        
        JButtonRounded btnSiguiente = new JButtonRounded(40, true);
        btnSiguiente.setLocation(240, 310);
        btnSiguiente.setSize(100, 60);
        btnSiguiente.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/siguiente.png")));
        btnSiguiente.setIconPosition(0);
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aqui se comprobaría que el nombre de usuario y la contraseña coincidan
                String nombre = txtUsuario.getText();
                String pass = txtPass.getText();
                if (!nombre.isEmpty() && !pass.isEmpty()) {
                    if (GestionUsuarios.esAdmin(nombre, pass)) {
                        dispose();
                        new CatalogoUsuarios(true,"Usuarios");
                    } else {
                        pr.setPintarBorde(true);
                        pr2.setPintarBorde(true);
                        lblError.setVisible(true);
                        lblError.setText("Usuario y/o contraseña incorrectos");
                    }
                } else {
                    pr.setPintarBorde(true);
                    pr2.setPintarBorde(true);
                    lblError.setText("Campos requeridos vacios");
                    lblError.setVisible(true);
                }
            }
        });

        pnl.add(btnSiguiente);
        
    }
}