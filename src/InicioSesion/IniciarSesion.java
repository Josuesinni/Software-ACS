package InicioSesion;

import BaseDeDatos.ConexionBD;
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
import Usuarios.Administracion;
import Utilidades.Notificacion;
import Utilidades.Recursos;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.Timer;

public class IniciarSesion extends JPanel {

    private JPanel izq, der;

    public IniciarSesion(Dimension d) {

        setSize(d);
        initComponents();
        setBackground(Color.white);
        setLayout(new BorderLayout());
        carrusel();
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }
    JLabel lblError;
    JTextFieldTitled pr;
    JPanelRounded pr2;
    JTextFieldRounded txtUsuario;
    JPasswordField txtPass;

    public void panelIzquierdo() {
        izq = new JPanel();
        izq.setBackground(new Color(255, 255, 255));
        izq.setSize(600, getHeight());
        JLabelImage ie = new JLabelImage("/res/imagenes/iu/logo.png");
        ie.setSize(new Dimension(280, 180));
        ie.setLocation(160, 40);
        izq.add(ie);
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN", JLabel.LEFT);
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
                if (!pr.isPintarBorde() && !pr2.isPintarBorde()) {
                    lblError.setVisible(false);
                }
            }
        });
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
        btnMostrar.setSize(30, 30);
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
        izq.add(pr2);

        lblError = new JLabel("", JLabel.RIGHT);
        lblError.setSize(300, 40);
        lblError.setFont(getFuente(0, 0, 12));
        lblError.setForeground(Color.red);
        lblError.setLocation(pr2.getX() + 140, pr2.getY() + 80);
        lblError.setVisible(false);
        izq.add(lblError);

        JButtonRounded btnSiguiente = new JButtonRounded(40, true);
        btnSiguiente.setLocation(240, 560);
        btnSiguiente.setSize(120, 80);
        btnSiguiente.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/siguiente.png")));
        btnSiguiente.setIconPosition(0);
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aqui se comprobaría que el nombre de usuario y la contraseña coincidan
                String nombre = txtUsuario.getText();
                String pass = txtPass.getText();
                if (!nombre.isEmpty() && !pass.isEmpty()) {
                    if (GestionUsuarios.consultarUsuario(nombre, pass)) {
                        limpiar();
                        btnSiguiente.restaurar();
                        cp.cargarPanel(new Menu(getSize()));
                        cp.siguientePanel(cp.panelActual());
                        timer.stop();
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

        izq.add(btnSiguiente);

        JLabel lblCrearCuenta = new JLabel("Crear cuenta");
        lblCrearCuenta.setLocation(250, 680);
        lblCrearCuenta.setSize(100, 24);
        lblCrearCuenta.setFont(Recursos.FUENTE_GENERAL);
        lblCrearCuenta.setForeground(Color.lightGray);
        lblCrearCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCrearCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                lblCrearCuenta.setForeground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                lblCrearCuenta.setForeground(Color.lightGray);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                cp.cargarPanel(new CrearUsuario(getSize()));
                cp.siguientePanel(2);
                limpiar();
            }
        });
        izq.add(lblCrearCuenta);

        JButtonRounded btnAdministracion = new JButtonRounded(40);
        btnAdministracion.setBackground(Color.white);
        btnAdministracion.setLocation(50, 700);
        btnAdministracion.setSize(40, 40);
        btnAdministracion.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/ajuste.png")));
        btnAdministracion.setIconPosition(0);
        btnAdministracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administracion(true, "Administración");
            }
        });
        izq.add(btnAdministracion);

        JButtonRounded btnAjusteBD = new JButtonRounded(40);
        btnAjusteBD.setBackground(Color.white);
        btnAjusteBD.setLocation(500, 700);
        btnAjusteBD.setSize(40, 40);
        btnAjusteBD.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/crud/ajuste.png")));
        btnAjusteBD.setIconPosition(0);
        btnAjusteBD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AjusteBD(true, "Administración de BD");
            }
        });
        izq.add(btnAjusteBD);

        izq.setLayout(new BorderLayout());
        add(izq);
    }

    public void panelDerecho() {
        der = new JPanel();
        //BufferedImage bi=new BufferedImage[5];
        //der.setBackground(new Color(229, 209, 169));
        der.setSize(getWidth() - 600, getHeight());
        der.setLocation(600, 0);
        JButtonRounded btns[] = new JButtonRounded[5];
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButtonRounded(40, true);
            btns[i].setSize(40, 40);
            btns[i].setLocation(620 + 50 * i, 700);
            der.add(btns[i]);
        }
        btns[0].addActionListener((ActionEvent e) -> {
            carrusel.setUrl(urls[0]);
            time = 0;
        });
        btns[1].addActionListener((ActionEvent e) -> {
            carrusel.setUrl(urls[1]);
            time = 1;
        });
        btns[2].addActionListener((ActionEvent e) -> {
            carrusel.setUrl(urls[2]);
            time = 2;
        });
        btns[3].addActionListener((ActionEvent e) -> {
            carrusel.setUrl(urls[3]);
            time = 3;
        });
        btns[4].addActionListener((ActionEvent e) -> {
            carrusel.setUrl(urls[4]);
            time = 4;
        });
        urls = new String[5];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = "/res/imagenes/carrusel/imagen_" + (i + 1) + ".jpg";
        }
        carrusel = new JLabelImage();
        carrusel.setUrl(urls[time]);
        carrusel.setLocation(0, 0);
        carrusel.setSize(der.getSize());
        der.add(carrusel);
        der.setLayout(new BorderLayout());
        add(der);
    }
    JLabelImage carrusel;
    String urls[];
    private int time = 0;
    Timer timer;

    private void carrusel() {
        timer = new Timer(4000, (ActionEvent e) -> {
            //System.out.println("tic-tac");
            time++;
            if (time > 4) {
                time = 0;
            }
            carrusel.setUrl(urls[time]);
        });
        timer.setRepeats(true);
        timer.start();
    }

    public void limpiar() {
        pr.setPintarBorde(false);
        pr2.setPintarBorde(false);
        lblError.setVisible(false);
        txtPass.setText("");
        txtUsuario.setText("");
    }
}
