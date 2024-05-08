package Clientes;

import BaseDeDatos.Control.GestionClientes;
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

public class ClienteCyE extends JDialog {

    JPanel pnlPrincipal;
    boolean tipo, accion;
    Object[] datos;
    JTextFieldTitled pr, pr2, pr3, pr4;
    JTextFieldRounded txtNombre, txtInstagram, txtTelefono, txtDireccion;
    JLabel lblError;
    JComboBoxCustom cmbEstado;
    
    public ClienteCyE(boolean modal, boolean tipo, Object[] datos) {
        super(vta, modal);
        pnlPrincipal = new JPanel();
        pnlPrincipal.setBackground(Color.white);
        this.tipo = tipo;
        this.datos = datos;
        initComponents();
        if (!tipo) {
            setDatosProducto();
        }
        pnlPrincipal.setLayout(new BorderLayout());
        String titulo = (tipo) ? "Productos - Registrar Cliente" : "Productos - Editar Cliente";
        setTitle(titulo);
        if (!tipo) {
            setSize(825, 560);
        }else{
            setSize(825, 480);
        }
        setContentPane(pnlPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        String titulo = (tipo) ? "NUEVO CLIENTE" : "EDITAR CLIENTE";
        JLabel lblTitulo = new JLabel(titulo, JLabel.LEFT);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(50, 30);
        lblTitulo.setFont(getFuente(1, 0, 32));
        pnlPrincipal.add(lblTitulo);

        txtNombre = new JTextFieldRounded("Nombre del cliente", 0, getFuente(0, 0, 18));
        txtNombre.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (!(e.getKeyChar()>='a'&&e.getKeyChar()<='z'||e.getKeyChar()>='A'&&e.getKeyChar()<='Z'||e.getKeyChar()==' ')) {
                    e.consume();
                }
            }
        });
        pr = new JTextFieldTitled("Nombre del cliente", txtNombre, false, 300, 80);
        pr.setLocation(75, 100);//300
        pnlPrincipal.add(pr);
        txtInstagram = new JTextFieldRounded("Cuenta de instagram o correo electronico", 0, getFuente(0, 0, 18));
        txtInstagram.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if (e.getKeyChar()==32) {
                    e.consume();
                }
            }
        });
        pr2 = new JTextFieldTitled("Instagram/Correo Electronico", txtInstagram, false, 300, 80);
        pr2.setLocation(75, 220);
        pnlPrincipal.add(pr2);
        txtTelefono = new JTextFieldRounded("Número de télefono", 0, getFuente(0, 0, 18));
        pr3 = new JTextFieldTitled("Télefono", txtTelefono, false, 300, 80);
        pr3.setLocation(450, 100);
        pnlPrincipal.add(pr3);
        txtDireccion = new JTextFieldRounded("Dirección", 0, getFuente(0, 0, 18));
        pr4 = new JTextFieldTitled("Dirección", txtDireccion, false, 300, 80);
        pr4.setLocation(450, 220);
        pnlPrincipal.add(pr4);
        
        if (!tipo) {
            JLabel lblEstado = new JLabel("Estado", JLabel.LEFT);
            lblEstado.setLocation(450, 300);
            lblEstado.setSize(150, 40);
            lblEstado.setFont(getFuente(1, 1, 14));
            pnlPrincipal.add(lblEstado);
            cmbEstado = new JComboBoxCustom<>();
            cmbEstado.setFont(getFuente(1, 1, 16));
            cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Inactivo", "Activo"}));
            cmbEstado.setLocation(450, 340);
            cmbEstado.setSize(300, 40);
            cmbEstado.setBackground(Recursos.fondoGris);
            cmbEstado.setColorOver(Color.lightGray);
            ((JLabel) cmbEstado.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
            cmbEstado.setMaximumRowCount(3);
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
        btnAtras.setLocation(75, tipo?345:420);
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
        btnGuardar.setLocation(tipo ? 570 : 490, tipo?345:420);
        btnGuardar.setSize(tipo ? 180 : 260, 50);
        btnGuardar.setFont(Recursos.FUENTE_BOTON);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    if (operacion()) {
                        accion = true;
                        new Notificacion(0, "El cliente se ha " + ((tipo) ? "registrado" : "editado") + " correctamente", false);
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
        String telefono = txtTelefono.getText();
        String instagram = txtInstagram.getText();
        if (nombre.isEmpty()) {
            pr.setPintarBorde(true);
        }
        boolean telefonoValido=true;
        if (telefono.isEmpty()) {
            pr3.setPintarBorde(true);
            telefonoValido=false;
        } else {
            if (!telefono.matches("[0-9]{10}")) {
                lblError.setText("El número debe contener 10 digitos");
                pr3.setPintarBorde(true);
                telefonoValido=false;
            }
        }
        boolean instagramValido=true;
        if (!instagram.isEmpty()) {
            if (!instagram.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+$")) {
                if (!instagram.matches("^\\w+[\\.\\w\\d_]+")) {
                    lblError.setText("Datos ingresados invalidos");
                    pr2.setPintarBorde(true);
                    instagramValido=false;
                }
            }
        }
        if (!pr.isPintarBorde() && !pr2.isPintarBorde() && !pr3.isPintarBorde()) {
            lblError.setText("Campos requeridos vacios");
            lblError.setVisible(false);
        }
        return !nombre.isEmpty() && instagramValido && telefonoValido;
    }

    public void setDatosProducto() {
        txtNombre.setText(datos[1].toString());
        txtTelefono.setText(datos[2].toString());
        txtInstagram.setText(datos[3].toString());
        txtDireccion.setText(datos[4].toString());
        cmbEstado.setSelectedItem(datos[5].toString());
    }

    public void limpiar() {
        txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
            }
        });
        txtInstagram.addFocusListener(new FocusAdapter() {
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
    }

    public boolean operacion() {
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String instagram = txtInstagram.getText();
        String direccion = txtDireccion.getText();
        if (tipo) {
            return GestionClientes.registrarCliente(nombre,instagram , telefono, direccion);
        } else {
            boolean estado=cmbEstado.getSelectedIndex() != 0;
            return GestionClientes.actualizarCliente(Integer.parseInt(datos[0].toString()), nombre, instagram,telefono,direccion,estado);
        }
    }

    public boolean isAccion() {
        return accion;
    }

}
