package Contratos;

import BaseDeDatos.Control.GestionAnfitriones;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JTextFieldRounded;
import SwingModificado.JTextFieldTitled;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnfitrionCyE extends JDialog {

    JPanel pnlPrincipal;
    boolean tipo, accion;
    Object[] datos;
    JTextFieldTitled pr, pr2, pr3, pr4, pr5;
    JTextFieldRounded txtNombreAnfitrion, txtNombreRepresentante, txtTelefono, txtInstagram, txtCorreo;
    JComboBoxCustom cmbEstado;
    JLabel lblError;

    public AnfitrionCyE(boolean modal, boolean tipo, Object[] datos) {
        super(vta, modal);
        pnlPrincipal = new JPanel();
        pnlPrincipal.setBackground(Color.white);
        this.tipo = tipo;
        this.datos = datos;
        initComponents();
        if (!tipo) {
            setDatosAnfitrion();
        }
        pnlPrincipal.setLayout(new BorderLayout());
        String titulo = "Gestion Anfitriones - " + ((tipo) ? "Registrar Anfitrión" : "Editar Anfitrión");
        setTitle(titulo);
        setSize(825, 550);
        setContentPane(pnlPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        String titulo = ((tipo) ? "NUEVO" : "EDITAR") + " ANFITRIÓN";
        JLabel lblTitulo = new JLabel(titulo, JLabel.LEFT);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(50, 30);
        lblTitulo.setFont(getFuente(1, 0, 32));
        pnlPrincipal.add(lblTitulo);

        txtNombreAnfitrion = new JTextFieldRounded("Nombre del anfitrión", 0, getFuente(0, 0, 18));
        txtNombreAnfitrion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z' || e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyChar() >= 'á' && e.getKeyChar() <= 'ú' || e.getKeyChar() >= 'Á' && e.getKeyChar() <= 'Ú' || e.getKeyChar() == ' ')) {
                    e.consume();
                }
            }
        });
        pr = new JTextFieldTitled("Nombre del anfitrión", txtNombreAnfitrion, false, 300, 80);
        pr.setLocation(75, 100);
        pnlPrincipal.add(pr);

        txtNombreRepresentante = new JTextFieldRounded("Nombre del representante", 0, getFuente(0, 0, 18));
        txtNombreRepresentante.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z' || e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyChar() >= 'á' && e.getKeyChar() <= 'ú' || e.getKeyChar() >= 'Á' && e.getKeyChar() <= 'Ú' || e.getKeyChar() == ' ')) {
                    e.consume();
                }
            }
        });

        pr2 = new JTextFieldTitled("Nombre del representante", txtNombreRepresentante, false, 300, 80);
        pr2.setLocation(75, 200);
        pnlPrincipal.add(pr2);

        txtTelefono = new JTextFieldRounded("Número de télefono", 0, getFuente(0, 0, 18));
        Recursos.permitirSoloNumeros(txtTelefono);
        pr3 = new JTextFieldTitled("Télefono", txtTelefono, false, 300, 80);
        pr3.setLocation(75, 300);
        pnlPrincipal.add(pr3);

        txtCorreo = new JTextFieldRounded("Correo", 0, getFuente(0, 0, 18));
        pr4 = new JTextFieldTitled("Correo", txtCorreo, false, 300, 80);
        pr4.setLocation(450, 100);
        pnlPrincipal.add(pr4);

        txtInstagram = new JTextFieldRounded("Usuario de instagram", 0, getFuente(0, 0, 18));
        txtInstagram.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 32) {
                    e.consume();
                }
            }
        });
        pr5 = new JTextFieldTitled("Instagram", txtInstagram, false, 300, 80);
        pr5.setLocation(450, 200);
        pnlPrincipal.add(pr5);
        
        if (!tipo) {
            cmbEstado = new JComboBoxCustom<>();
            cmbEstado.setFont(getFuente(1, 1, 16));
            cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Inactivo", "Activo"}));
            cmbEstado.setLocation(450, 300);
            cmbEstado.setSize(300, 40);
            cmbEstado.setBackground(Recursos.fondoGris);
            cmbEstado.setColorOver(Color.lightGray);
            pnlPrincipal.add(cmbEstado);
        }
        
        lblError = new JLabel("Campos requeridos vacios", JLabel.RIGHT);
        lblError.setSize(280, 40);
        lblError.setFont(getFuente(0, 0, 12));
        lblError.setForeground(Color.red);
        lblError.setLocation(pr3.getX(), pr3.getY() + 80);
        lblError.setVisible(false);
        pnlPrincipal.add(lblError);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(75, 420);
        btnAtras.setSize(80, 50);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnlPrincipal.add(btnAtras);

        JButtonRounded btnGuardar = new JButtonRounded(tipo ? "REGISTRAR" : "GUARDAR CAMBIOS", 20, true);
        btnGuardar.setLocation(tipo ? 570 : 490, 420);
        btnGuardar.setSize(tipo ? 180 : 260, 50);
        btnGuardar.setFont(Recursos.FUENTE_BOTON);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    if (operacion()) {
                        accion = true;
                        new Notificacion(0, "El anfitrión se ha " + ((tipo) ? "registrado" : "editado") + " correctamente", false);
                        dispose();
                    }
                } else {
                    lblError.setVisible(true);
                }
            }
        });
        pnlPrincipal.add(btnGuardar);
        limpiar();
    }

    public boolean validar() {
        String nombre = txtNombreAnfitrion.getText();
        String telefono = txtTelefono.getText();
        String instagram = txtInstagram.getText();
        String correo = txtCorreo.getText();
        if (nombre.isEmpty()) {
            pr.setPintarBorde(true);
        }
        boolean telefonoValido = true;
        if (telefono.isEmpty()) {
            pr3.setPintarBorde(true);
            telefonoValido = false;
        } else {
            if (!telefono.matches("[0-9]{10}")) {
                pr3.setPintarBorde(true);
                telefonoValido = false;
            }
        }
        boolean correoValido = true;
        if (!correo.isEmpty()) {
            if (!correo.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+$")) {
                lblError.setText("El correo escrito no es valido");
                pr4.setPintarBorde(true);
                correoValido = false;
            }
        }
        boolean instagramValido = true;
        if (!instagram.isEmpty()) {
            if (!instagram.matches("^\\w+[\\.\\w\\d_]+")) {
                lblError.setText("El usuario escrito no es valido");
                pr5.setPintarBorde(true);
                instagramValido = false;
            }
        }
        if (!instagramValido && !correoValido) {
            lblError.setText("El usuario y correo escritos no son validos");
        } else {
            lblError.setText("Campos requeridos vacios");
        }
        if (!pr.isPintarBorde() && !pr2.isPintarBorde() && !pr3.isPintarBorde() && !pr4.isPintarBorde() && !pr5.isPintarBorde()) {
            lblError.setVisible(false);
        }
        return !nombre.isEmpty() && instagramValido && telefonoValido && correoValido;
    }

    public void setDatosAnfitrion() {
        txtNombreAnfitrion.setText(datos[1].toString());
        txtNombreRepresentante.setText(datos[2] == null ? "" : datos[2].toString());
        txtTelefono.setText(datos[3].toString());
        txtCorreo.setText(datos[4] == null ? "" : datos[4].toString());
        txtInstagram.setText(datos[5] == null ? "" : datos[5].toString());
        cmbEstado.setSelectedItem(datos[6].toString());
    }

    public void limpiar() {
        txtNombreAnfitrion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
            }
        });
        txtNombreRepresentante.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr2.setPintarBorde(false);
            }
        });
        txtTelefono.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr3.setPintarBorde(false);
            }
        });
        txtCorreo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr4.setPintarBorde(false);
            }
        });
        txtInstagram.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr5.setPintarBorde(false);
            }
        });
    }

    public boolean operacion() {
        String nombre = txtNombreAnfitrion.getText();
        String nombreRepresentante = txtNombreRepresentante.getText();
        String telefono = txtTelefono.getText();
        String instagram = txtInstagram.getText();
        String correo = txtCorreo.getText();
        if (tipo) {
            return GestionAnfitriones.registrarAnfitrion(nombre, nombreRepresentante, correo, telefono, instagram);
        } else {
            int estado = cmbEstado.getSelectedIndex();
            if (estado == 0) {
                if (GestionAnfitriones.esPosibleEliminarAnfitrion(Integer.parseInt(datos[0].toString()))) {
                    return GestionAnfitriones.actualizarAnfitrion(Integer.parseInt(datos[0].toString()), nombre, nombreRepresentante, correo, telefono, instagram, estado);
                } else {
                    new Notificacion(1, "Error: No es posible cambiar de estado al anfitrion debido a que hay contratos en progreso", false);
                    return false;
                }
            }
            return GestionAnfitriones.actualizarAnfitrion(Integer.parseInt(datos[0].toString()), nombre, nombreRepresentante, correo, telefono, instagram, estado);
        }
    }

    public boolean isAccion() {
        return accion;
    }
}