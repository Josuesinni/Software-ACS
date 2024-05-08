package Productos;

import BaseDeDatos.Control.GestionAnfitriones;
import BaseDeDatos.Control.GestionCategoria;
import BaseDeDatos.Control.GestionProductos;
import BaseDeDatos.Control.Miscelanea;
import Buscadores.Buscador;
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
import javax.swing.text.AbstractDocument;

public class ProductoCyE extends JDialog {

    JPanel pnlPrincipal;
    boolean tipo;
    Object[] datos;
    JTextFieldRounded txtNombreProducto;
    JTextFieldRounded txtCantidad;
    JTextFieldRounded txtPrecio;
    JComboBoxCustom cmbAnfitrion, cmbEstado;
    JTextFieldRounded txtCategoria;
    JTextFieldTitled pr, pr2, pr3, pr4;
    JLabel lblError;
    Buscador buscadorCategoria;

    public ProductoCyE(boolean modal, boolean tipo, Object[] datos) {
        super(vta, modal);
        pnlPrincipal = new JPanel();
        pnlPrincipal.setBackground(Color.white);
        this.tipo = tipo;
        this.datos = datos;
        initComponents();
        categoria();
        if (!tipo) {
            setDatosProducto();
        }
        pnlPrincipal.setLayout(new BorderLayout());
        String titulo = (tipo) ? "Productos - Registrar Psroducto" : "Productos - Editar Producto";
        setTitle(titulo);
        setSize(900, 550);
        setContentPane(pnlPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void initComponents() {
        String titulo = (tipo) ? "NUEVO PRODUCTO" : "EDITAR PRODUCTO";
        JLabel lblTitulo = new JLabel(titulo, JLabel.LEFT);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(50, 30);
        lblTitulo.setFont(getFuente(1, 0, 32));
        pnlPrincipal.add(lblTitulo);

        txtNombreProducto = new JTextFieldRounded("Nombre del producto", 0, getFuente(0, 0, 18));
        pr = new JTextFieldTitled("Nombre del producto", txtNombreProducto, false, 320, 80);
        pr.setLocation(75, 100);
        pnlPrincipal.add(pr);
        txtCantidad = new JTextFieldRounded("0", 0, getFuente(0, 0, 18));
        pr2 = new JTextFieldTitled("Cantidad", txtCantidad, false, 320, 80);
        Recursos.permitirSoloNumeros(txtCantidad);
        pr2.setLocation(75, 200);
        pnlPrincipal.add(pr2);
        txtPrecio = new JTextFieldRounded("0.00", 0, getFuente(0, 0, 18));
        pr3 = new JTextFieldTitled("Precio", txtPrecio, true, 320, 80);
        Recursos.permitirSoloDobles(txtPrecio);

        pr3.setLocation(75, 300);
        pnlPrincipal.add(pr3);
        txtCategoria = new JTextFieldRounded("Nombre de la categoria", 0, getFuente(0, 0, 18));
        pr4 = new JTextFieldTitled("Categoria", txtCategoria, false, 320, 80);
        pr4.setLocation(500, 100);
        pnlPrincipal.add(pr4);

        JLabel lblAnfitrion = new JLabel("Anfitrión", JLabel.LEFT);
        lblAnfitrion.setLocation(500, 200);
        lblAnfitrion.setSize(150, 40);
        lblAnfitrion.setFont(getFuente(1, 1, 14));
        pnlPrincipal.add(lblAnfitrion);

        cmbAnfitrion = new JComboBoxCustom<>();
        cmbAnfitrion.setFont(getFuente(1, 1, 16));
        Miscelanea.CargarComboBox(GestionAnfitriones.cargarAnfitriones(), cmbAnfitrion);
        cmbAnfitrion.setLocation(500, 240);
        cmbAnfitrion.setSize(300, 40);
        cmbAnfitrion.setBackground(Recursos.fondoGris);
        cmbAnfitrion.setColorOver(Color.lightGray);
        ((JLabel) cmbAnfitrion.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbAnfitrion.setMaximumRowCount(3);
        pnlPrincipal.add(cmbAnfitrion);
        if (!tipo) {
            JLabel lblEstado = new JLabel("Estado", JLabel.LEFT);
            lblEstado.setLocation(500, 300);
            lblEstado.setSize(150, 40);
            lblEstado.setFont(getFuente(1, 1, 14));
            pnlPrincipal.add(lblEstado);
            cmbEstado = new JComboBoxCustom<>();
            cmbEstado.setFont(getFuente(1, 1, 16));
            cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Inactivo", "Activo"}));
            cmbEstado.setLocation(500, 340);
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
        btnAtras.setLocation(100, 420);
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
        btnGuardar.setLocation(tipo ? 620 : 540, 420);
        btnGuardar.setSize(tipo ? 180 : 260, 50);
        btnGuardar.setFont(Recursos.FUENTE_BOTON);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    if (operacion()) {
                        isCambiosGenerados = true;
                        new Notificacion(0, "El producto se ha " + ((tipo) ? "registrado" : "editado") + " correctamente", false);
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
    boolean isCambiosGenerados = false;

    public boolean isIsCambiosGenerados() {
        return isCambiosGenerados;
    }

    public boolean validar() {
        String nombre = txtNombreProducto.getText();
        int cantidad = Integer.parseInt(txtCantidad.getText().isEmpty() ? "0" : txtCantidad.getText());
        double precio = Double.parseDouble(txtPrecio.getText().isEmpty() ? "0.00" : txtPrecio.getText());
        String categoria = txtCategoria.getText();
        if (nombre.isEmpty()) {
            pr.setPintarBorde(true);
        }
        if (cantidad == 0) {
            pr2.setPintarBorde(true);
        }
        if (precio == 0.00) {
            pr3.setPintarBorde(true);
        }
        if (categoria.isEmpty()) {
            pr4.setPintarBorde(true);
        }
        if (!pr.isPintarBorde() && !pr2.isPintarBorde() && ! !pr3.isPintarBorde() && !pr4.isPintarBorde()) {
            lblError.setVisible(false);
        }
        return !nombre.isEmpty() && cantidad != 0 && precio != 0.00 && !categoria.isEmpty();
    }

    public void setDatosProducto() {
        txtNombreProducto.setText(datos[1].toString());
        txtPrecio.setText(datos[2].toString());
        txtCantidad.setText(datos[3].toString());
        txtCategoria.setText(datos[4].toString());
        cmbAnfitrion.setSelectedItem(datos[5].toString());
        cmbEstado.setSelectedItem(datos[6].toString());
    }

    public void limpiar() {
        txtNombreProducto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr.setPintarBorde(false);
            }
        });
        txtCantidad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr2.setPintarBorde(false);
            }
        });
        txtPrecio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr3.setPintarBorde(false);
            }
        });
        txtCategoria.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pr4.setPintarBorde(false);
            }
        });
    }

    public boolean operacion() {
        String nombre = txtNombreProducto.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        int cantidad = Integer.parseInt(txtCantidad.getText());
        String anfitrion = cmbAnfitrion.getSelectedItem().toString();
        String categoria = txtCategoria.getText();
        if (tipo) {
            if (!GestionCategoria.isCategoriaRegistrada(categoria)) {
                GestionCategoria.registrarCategoria(categoria);
            }
            return GestionProductos.registarProducto(nombre, cantidad, precio, anfitrion, categoria);
        } else {
            int estado = cmbEstado.getSelectedIndex();
            return GestionProductos.actualizarProducto(Integer.parseInt(datos[0].toString()), nombre, cantidad, precio, anfitrion, categoria, estado);
        }
    }

    public void categoria() {
        txtCategoria.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!txtCategoria.getText().isEmpty()) {
                    if (e.getKeyCode() != 10) {
                        buscadorCategoria.actualizarLista(GestionCategoria.busquedaCategoria(txtCategoria.getText()));
                    }
                }
            }
        });
        buscadorCategoria = new Buscador();
        buscadorCategoria.changePopupSize(300, 60);
        buscadorCategoria.setBackground(Color.red);
        buscadorCategoria.setBuscar(txtCategoria);
    }
}
