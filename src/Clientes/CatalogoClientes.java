package Clientes;

import BaseDeDatos.Control.GestionClientes;
import BaseDeDatos.Control.GestionProductos;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JTextFieldRounded;
import SwingModificado.ScrollBarCustom;
import Tabla.AccionEnJTable;
import Tabla.CeldaPersonalizada;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CatalogoClientes extends JPanel {

    public CatalogoClientes(Dimension d) {
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }
    JComboBoxCustom cmbFiltro,cmbEstado;

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("GESTIÓN DE CLIENTES");
        lblTitulo.setSize(600, 60);
        add(lblTitulo);

        JTextFieldRounded txtBuscar = new JTextFieldRounded("Nombre/Usuario de Instagram", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtro = cmbFiltro.getSelectedItem().toString();
                Miscelanea.CargarTabla(GestionClientes.busquedaCliente(txtBuscar.getText(), filtro), tblLista, true);
            }
        });
        add(txtBuscar);

        JLabel lblFiltro = new JLabel("Filtro:", JLabel.LEFT);
        lblFiltro.setLocation(650, 195);
        lblFiltro.setSize(200, 20);
        lblFiltro.setFont(getFuente(0, 1, 16));
        add(lblFiltro);

        cmbFiltro = new JComboBoxCustom<>();
        cmbFiltro.setFont(getFuente(1, 1, 16));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ninguno", "Nombre", "Instagram"}));
        cmbFiltro.setLocation(700, 185);
        cmbFiltro.setSize(120, 40);
        cmbFiltro.setBackground(new Color(255, 214, 153));
        cmbFiltro.setColorOver(new Color(240, 200, 120));
        add(cmbFiltro);
            
        JLabel lblEstado = new JLabel("Estado:", JLabel.RIGHT);
        lblEstado.setLocation(1120, 180);
        lblEstado.setSize(120, 50);
        lblEstado.setFont(getFuente(0, 1, 16));
        add(lblEstado);
        
        cmbEstado = new JComboBoxCustom<>();
        cmbEstado.setFont(getFuente(1, 1, 16));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Cualquiera", "Inactivo", "Activo"}));
        cmbEstado.setLocation(1280, 187);
        cmbEstado.setSize(140, 36);
        cmbEstado.setBackground(new Color(255, 214, 153));
        cmbEstado.setColorOver(new Color(240, 200, 120));
        cmbEstado.setMaximumRowCount(3);
        cmbEstado.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                
                if (cmbEstado.getSelectedItem().toString().equals("Inactivo") || cmbEstado.getSelectedItem().toString().equals("Activo")) {
                    Miscelanea.CargarTabla(GestionClientes.vistaClientesporEstado(cmbEstado.getSelectedItem().toString()), tblLista, true);
                } else {
                    Miscelanea.CargarTabla(GestionClientes.vistaClientes(), tblLista, true);
                }
            }
        });
        add(cmbEstado);
        
        tabla();

        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(100, 680);
        btnAtras.setSize(80, 50);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.panelAnterior(cp.panelActual());
            }
        });
        add(btnAtras);
    }
    JTable tblLista;

    public void tabla() {
        Object[][] datos = new Object[][]{};
        String[] cabecera = new String[]{"Clave", "Nombre", "Teléfono", "Usuario de IG", "Dirección", "Estado", ""};
        boolean[] colEditables = {false, false, false, false, false, false, true};
        int[] tamColumnas = {10, 140, 50, 100, 120, 40, 40};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 3);
        Miscelanea.CargarTabla(GestionClientes.vistaClientes(), tblLista, true);
        AccionTabla(tblLista, 3);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(1320, 380));
        jsp1.setPreferredSize(new java.awt.Dimension(900, 380));
        jsp1.setRowHeaderView(null);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(100, 250);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        add(jsp1);
    }

    public void panelDerecho() {
        JButtonRounded btnNuevoCliente = new JButtonRounded("NUEVO CLIENTE", 20, true);
        btnNuevoCliente.setLocation(1120, 675);
        btnNuevoCliente.setSize(300, 60);
        btnNuevoCliente.setFont(Recursos.FUENTE_BOTON);
        btnNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteCyE c = new ClienteCyE(true, true, null);
                if (c.isAccion()) {
                    Miscelanea.CargarTabla(GestionClientes.vistaClientes(), tblLista, true);
                }
            }
        });
        add(btnNuevoCliente);
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
                Object datos[] = new Object[tblLista.getColumnCount() - 1];
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = tblLista.getValueAt(row, i);
                }
                ClienteCyE ccye=new ClienteCyE(true, false, datos);
                if (ccye.isAccion()) {
                    Miscelanea.CargarTabla(GestionClientes.vistaClientes(), tblLista, true);
                }
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está seguro que desea eliminar a " + tblLista.getValueAt(row, 1) + "?", true);
                if (n.getRespuesta()) {
                    if (GestionClientes.esPosibleEliminarCliente(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                        if (GestionClientes.eliminarCliente(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                            new Notificacion(0, "El cliente se ha eliminado correctamente", false);
                            if (tblLista.isEditing()) {
                                tblLista.getCellEditor().stopCellEditing();
                            }
                            Miscelanea.CargarTabla(GestionClientes.vistaClientes(), tblLista, true);
                        }
                    } else {
                        new Notificacion(1, "Error: El cliente a eliminar tiene uno o varios apartados pendientes", false);
                    }
                }
            }

            @Override
            public void visualizar(int row) {
            }

            @Override
            public void verHistorial(int row) {
            }

            @Override
            public void ajustar(int row) {
            }
        };
        tblLista.getColumnModel().getColumn(tblLista.getColumnModel().getColumnCount() - 1).setCellEditor(new CeldaPersonalizada(event, tipo, new Color(255, 244, 227)));

    }
}
