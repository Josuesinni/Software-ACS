package InicioSesion;

import Menus.Menu;
import BaseDeDatos.Control.GestionUsuarios;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JLabelImage;
import SwingModificado.JPanelRounded;
import SwingModificado.JPlaceHolder;
import SwingModificado.JTextFieldRounded;
import SwingModificado.JTextFieldTitled;
import SwingModificado.JToggleButtonCustom;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class CrearUsuario extends JPanel {

    private JPanel izq, der;

    public CrearUsuario(Dimension d) {
        setSize(d);
        initComponents();
        setBackground(Color.white);
        setLayout(new BorderLayout());
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }
    private JTextFieldTitled pr;
    private JPanelRounded pr2, pr3;
    private JTextFieldRounded txtUsuario;
    private JPasswordField txtPass, txtPassConfirm;
    private JLabel lblError;

    public void panelIzquierdo() {
        izq = new JPanel();
        izq.setBackground(new Color(255, 255, 255));
        izq.setSize(600, getHeight());
        JLabelImage ie = new JLabelImage("/res/imagenes/iu/logo.png");
        ie.setSize(new Dimension(280, 180));
        ie.setLocation(160, 40);
        izq.add(ie);
        JLabel lblTitulo = new JLabel("CREAR USUARIO", JLabel.LEFT);
        lblTitulo.setLocation(75, 250);
        lblTitulo.setSize(300, 50);
        lblTitulo.setFont(getFuente(1, 0, 32));
        izq.add(lblTitulo);

        txtUsuario = new JTextFieldRounded("Nombre del usuario", 10, getFuente(0, 0, 20));
        pr = new JTextFieldTitled("Usuario", txtUsuario, false, 450, 80);
        pr.setFont(getFuente(0, 2, 20));
        pr.setBackground(Color.white);
        pr.setLocation(75, 320);
        txtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
                if (!pr.isPintarBorde() && !pr2.isPintarBorde() && !pr3.isPintarBorde()) {
                    lblError.setVisible(false);
                }
            }
        });
        izq.add(pr);
        pr.setLayout(new BorderLayout());
        izq.add(pr);

        pr2 = new JPanelRounded(40);
        pr2.setSize(450, 80);
        pr2.setOpaque(false);
        pr2.setLocation(75, 440);

        JLabel lblPass = new JLabel("Contraseña", JLabel.LEFT);
        lblPass.setLocation(20, 10);
        lblPass.setSize(120, 20);
        lblPass.setFont(getFuente(0, 1, 14));
        pr2.add(lblPass);

        txtPass = new JPasswordField("Contraseña");
        txtPass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr2.setPintarBorde(false);
                if (!pr.isPintarBorde() && !pr2.isPintarBorde() && !pr3.isPintarBorde()) {
                    lblError.setVisible(false);
                }
            }
        });
        txtPass = new JPasswordField();
        txtPass.setFont(getFuente(0, 0, 20));
        txtPass.setLocation(20, 25);
        txtPass.setSize(380, 50);
        txtPass.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtPass.setBackground(new Color(239, 232, 232));
        txtPass.setOpaque(false);
        JPlaceHolder ph = new JPlaceHolder("Contraseña", txtPass);
        ph.changeAlpha(0.5f);

        pr2.add(txtPass);
        JToggleButtonCustom btnMostrar = new JToggleButtonCustom();
        btnMostrar.setLocation(400, 40);
        btnMostrar.setSize(30, 30);
        btnMostrar.setIconoHide(new ImageIcon(getClass().getResource("/res/imagenes/iu/hide.png")));
        btnMostrar.setIconoShow(new ImageIcon(getClass().getResource("/res/imagenes/iu/show.png")));
        btnMostrar.addActionListener(mostrar(btnMostrar, txtPass));
        pr2.add(btnMostrar);

        pr2.setLayout(new BorderLayout());
        izq.add(pr2);

        pr3 = new JPanelRounded(40);
        pr3.setSize(450, 80);
        pr3.setOpaque(false);
        pr3.setLocation(75, 560);

        JLabel lblPassConf = new JLabel("Confirmar contraseña", JLabel.LEFT);
        lblPassConf.setLocation(20, 10);
        lblPassConf.setSize(200, 20);
        lblPassConf.setFont(getFuente(0, 1, 14));
        pr3.add(lblPassConf);

        txtPassConfirm = new JPasswordField();
        txtPassConfirm.setFont(getFuente(0, 0, 20));
        txtPassConfirm.setLocation(20, 25);
        txtPassConfirm.setSize(380, 50);
        txtPassConfirm.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtPassConfirm.setBackground(new Color(239, 232, 232));
        txtPassConfirm.setOpaque(false);
        JPlaceHolder ph2 = new JPlaceHolder("Confirmar contraseña", txtPassConfirm);
        ph2.changeAlpha(0.5f);

        pr3.add(txtPassConfirm);
        JToggleButtonCustom btnMostrar2 = new JToggleButtonCustom();
        btnMostrar2.setLocation(400, 40);
        btnMostrar2.setSize(30, 30);
        btnMostrar2.setIconoHide(new ImageIcon(getClass().getResource("/res/imagenes/iu/hide.png")));
        btnMostrar2.setIconoShow(new ImageIcon(getClass().getResource("/res/imagenes/iu/show.png")));
        btnMostrar2.addActionListener(mostrar(btnMostrar2, txtPassConfirm));
        pr3.add(btnMostrar2);

        pr3.setLayout(new BorderLayout());
        izq.add(pr3);

        lblError = new JLabel("", JLabel.RIGHT);
        lblError.setSize(300, 40);
        lblError.setFont(getFuente(0, 0, 12));
        lblError.setForeground(Color.red);
        lblError.setLocation(pr3.getX() + 140, pr3.getY() + 80);
        lblError.setVisible(false);
        izq.add(lblError);

        JButtonRounded btnSiguiente = new JButtonRounded(40, true);
        btnSiguiente.setLocation(375, 680);
        btnSiguiente.setSize(100, 60);
        btnSiguiente.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/siguiente.png")));
        btnSiguiente.setIconPosition(0);
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    String usuario = txtUsuario.getText();
                    String pass = new String(txtPass.getPassword());
                    if (GestionUsuarios.registarUsuario(usuario, pass)) {
                        cp.cargarPanel(new Menu(getSize()));
                        cp.siguientePanel(cp.panelActual());
                    }
                } else {
                    lblError.setVisible(true);
                }
            }
        });
        izq.add(btnSiguiente);

        JButtonRounded btnAtras = new JButtonRounded(40, true);
        btnAtras.setLocation(100, 680);
        btnAtras.setSize(100, 60);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.panelAnterior(cp.panelActual());
            }
        });
        izq.add(btnAtras);

        izq.setLayout(new BorderLayout());
        add(izq);
        limpiar();
    }

    public void panelDerecho() {
        der = new JPanel();
        der.setBackground(new Color(229, 209, 169));
        der.setSize(getWidth() - 600, getHeight());
        der.setLocation(600, 0);
        add(der);
    }

    public boolean validar() {
        String usuario = txtUsuario.getText();
        String pass = new String(txtPass.getPassword());
        String passConf = new String(txtPassConfirm.getPassword());

        if (usuario.isEmpty() && pass.isEmpty() && passConf.isEmpty()) {
            pr.setPintarBorde(true);
            pr2.setPintarBorde(true);
            pr3.setPintarBorde(true);
            lblError.setText("Campos requeridos vacios");
        } else if (usuario.isEmpty()) {
            pr.setPintarBorde(true);
            lblError.setText("Campo de usuario vacio");
        } else if (!pass.equals(passConf)) {
            pr2.setPintarBorde(true);
            pr3.setPintarBorde(true);
            lblError.setText("Las constraseñas ingresadas no coinciden");
        } else {
            return true;
        }
        return false;
    }

    public void limpiar() {
        txtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
            }
        });
        txtPass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr2.setPintarBorde(false);
            }
        });
        txtPassConfirm.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr3.setPintarBorde(false);
            }
        });
    }

    public ActionListener mostrar(JToggleButtonCustom btn, JPasswordField txt) {
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btn.isSelected()) {
                    txt.setEchoChar((char) 0);
                } else {
                    txt.setEchoChar('*');
                }
            }
        };
        return a;
    }
}
