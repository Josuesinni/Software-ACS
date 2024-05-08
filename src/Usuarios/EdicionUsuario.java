package Usuarios;

import BaseDeDatos.Control.GestionClientes;
import BaseDeDatos.Control.GestionUsuarios;
import BaseDeDatos.Control.Miscelanea;
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

public class EdicionUsuario extends JDialog {

    JPanel pnlPrincipal;
    boolean accion;
    Object[] datos;
    JTextFieldTitled pr, pr2;
    JTextFieldRounded txtNombre, txtPassword;
    JComboBoxCustom cmbEstado, cmbAdministrador;
    JLabel lblError;

    public EdicionUsuario(boolean modal, boolean tipo, Object[] datos) {
        super(vta, modal);
        pnlPrincipal = new JPanel();
        pnlPrincipal.setBackground(Color.white);
        this.datos = datos;
        initComponents();
        setDatosProducto();
        pnlPrincipal.setLayout(new BorderLayout());
        String titulo = "Usuarios - Editar Usuario";
        setTitle(titulo);
        setSize(825, 480);
        setContentPane(pnlPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        String titulo = "EDITAR USUARIO";
        JLabel lblTitulo = new JLabel(titulo, JLabel.LEFT);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(50, 30);
        lblTitulo.setFont(getFuente(1, 0, 32));
        pnlPrincipal.add(lblTitulo);

        txtNombre = new JTextFieldRounded("Usuario", 0, getFuente(0, 0, 18));
        pr = new JTextFieldTitled("Usuario", txtNombre, false, 300, 80);
        pr.setLocation(75, 100);//300
        pnlPrincipal.add(pr);
        txtPassword = new JTextFieldRounded("Constraseña", 0, getFuente(0, 0, 18));
        pr2 = new JTextFieldTitled("Constraseña", txtPassword, false, 300, 80);
        pr2.setLocation(75, 220);
        pnlPrincipal.add(pr2);//450
        
        JLabel lblAdministrador = new JLabel("Administrador", JLabel.LEFT);
        lblAdministrador.setLocation(450, 100);
        lblAdministrador.setSize(150, 40);
        lblAdministrador.setFont(getFuente(1, 1, 14));
        pnlPrincipal.add(lblAdministrador);
        cmbAdministrador = new JComboBoxCustom<>();
        cmbAdministrador.setFont(getFuente(1, 1, 16));
        cmbAdministrador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"No", "Si"}));
        cmbAdministrador.setLocation(450, 140);
        cmbAdministrador.setSize(300, 40);
        cmbAdministrador.setBackground(Recursos.fondoGris);
        cmbAdministrador.setColorOver(Color.lightGray);
        ((JLabel) cmbAdministrador.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbAdministrador.setMaximumRowCount(2);
        pnlPrincipal.add(cmbAdministrador);
        
        JLabel lblEstado = new JLabel("Estado", JLabel.LEFT);
        lblEstado.setLocation(450, 220);
        lblEstado.setSize(150, 40);
        lblEstado.setFont(getFuente(1, 1, 14));
        pnlPrincipal.add(lblEstado);
        cmbEstado = new JComboBoxCustom<>();
        cmbEstado.setFont(getFuente(1, 1, 16));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Inactivo", "Activo"}));
        cmbEstado.setLocation(450, 260);
        cmbEstado.setSize(300, 40);
        cmbEstado.setBackground(Recursos.fondoGris);
        cmbEstado.setColorOver(Color.lightGray);
        ((JLabel) cmbEstado.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbEstado.setMaximumRowCount(2);
        pnlPrincipal.add(cmbEstado);

        lblError = new JLabel("Campos requeridos vacios", JLabel.RIGHT);
        lblError.setSize(280, 40);
        lblError.setFont(getFuente(0, 0, 12));
        lblError.setForeground(Color.red);
        lblError.setLocation(cmbEstado.getX(), cmbEstado.getY() + 80);
        lblError.setVisible(false);
        pnlPrincipal.add(lblError);

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(100, 360);
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

        JButtonRounded btnGuardar = new JButtonRounded("GUARDAR CAMBIOS", 20, true);
        btnGuardar.setLocation(490, 360);
        btnGuardar.setSize(260, 50);
        btnGuardar.setFont(Recursos.FUENTE_BOTON);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    if (operacion()) {
                        accion = true;
                        new Notificacion(0, "El cliente se ha editado correctamente", false);
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
        String nombre = txtNombre.getText();
        String password = txtPassword.getText();
        if (nombre.isEmpty()) {
            pr.setPintarBorde(true);
        }
        if (password.isEmpty()) {
            pr2.setPintarBorde(true);
        }
        if (!pr.isPintarBorde() && !pr2.isPintarBorde()) {
            lblError.setText("Campos requeridos vacios");
            lblError.setVisible(false);
        }
        return !nombre.isEmpty() && !password.isEmpty();
    }

    public void setDatosProducto() {
        txtNombre.setText(datos[1].toString());
        txtPassword.setText(datos[2].toString());
        cmbAdministrador.setSelectedItem(datos[3].toString());
        cmbEstado.setSelectedItem(datos[4].toString());
    }

    public void limpiar() {
        txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
            }
        });
        txtPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr2.setPintarBorde(false);
            }
        });
    }

    public boolean operacion() {
        String nombre = txtNombre.getText();
        String password = txtPassword.getText();
        boolean is_admin=cmbAdministrador.getSelectedIndex() != 0;
        boolean estado=cmbEstado.getSelectedIndex() != 0;
        return GestionUsuarios.actualizarUsuario(Integer.parseInt(datos[0].toString()),nombre, password, is_admin, estado);
    }

    public boolean isAccion() {
        return accion;
    }

}
