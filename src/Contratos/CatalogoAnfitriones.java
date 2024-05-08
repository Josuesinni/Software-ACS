package Contratos;

import BaseDeDatos.Control.GestionAnfitriones;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.cp;
import SwingModificado.JButtonRounded;
import SwingModificado.JComboBoxCustom;
import SwingModificado.JTextFieldRounded;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CatalogoAnfitriones extends JPanel {

    public CatalogoAnfitriones(Dimension d) {
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }
    JComboBoxCustom cmbFiltro;

    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("GESTIÓN DE ANFITRIONES");
        lblTitulo.setSize(800, 60);
        add(lblTitulo);

        JTextFieldRounded txtBuscar = new JTextFieldRounded("Nombre/Usuario de Instagram", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtro = cmbFiltro.getSelectedItem().toString();
                Miscelanea.CargarTabla(GestionAnfitriones.busquedaAnfitrion(txtBuscar.getText(), filtro), tblLista, true);
            }
        });
        add(txtBuscar);

        JLabel lblFiltro = new JLabel("Filtro:", JLabel.LEFT);
        lblFiltro.setLocation(650, 195);
        lblFiltro.setSize(800, 20);
        lblFiltro.setFont(getFuente(0, 1, 16));
        add(lblFiltro);

        cmbFiltro = new JComboBoxCustom<>();
        cmbFiltro.setFont(getFuente(1, 1, 16));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ninguno", "Nombre_Anfitrion", "Nombre_Representante"}));
        cmbFiltro.setLocation(700, 185);
        cmbFiltro.setSize(300, 40);
        cmbFiltro.setBackground(new Color(255, 214, 153));
        cmbFiltro.setColorOver(new Color(240, 200, 120));
        add(cmbFiltro);

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
        String[] cabecera = new String[]{"Clave", "Nombre del Anfitrion", "Nombre del represente","Teléfono", "Correo","Instagram", "Estado", ""};
        boolean[] colEditables = {false, false, false,false, false, false, false, true};
        int[] tamColumnas = {10, 140, 140,50, 100, 120, 40, 40};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 3);
        Miscelanea.CargarTabla(GestionAnfitriones.vistaAnfitriones(), tblLista, true);
        AccionTabla(tblLista, 3);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(1320, 380));
        jsp1.setPreferredSize(new java.awt.Dimension(900, 380));
        jsp1.setRowHeaderView(null);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(100, 250);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        add(jsp1);
    }

    public void panelDerecho() {
        JButtonRounded btnNuevoAnfitrion = new JButtonRounded("NUEVO ANFITRIÓN", 20, true);
        btnNuevoAnfitrion.setLocation(1120, 675);
        btnNuevoAnfitrion.setSize(300, 60);
        btnNuevoAnfitrion.setFont(Recursos.FUENTE_BOTON);
        btnNuevoAnfitrion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnfitrionCyE c = new AnfitrionCyE(true, true, null);
                if (c.isAccion()) {
                    Miscelanea.CargarTabla(GestionAnfitriones.vistaAnfitriones(), tblLista, true);
                }
            }
        });
        add(btnNuevoAnfitrion);
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
                Object datos[] = new Object[tblLista.getColumnCount()];
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = tblLista.getValueAt(row, i);
                }
                AnfitrionCyE ccye=new AnfitrionCyE(true, false, datos);
                if (ccye.isAccion()) {
                    Miscelanea.CargarTabla(GestionAnfitriones.vistaAnfitriones(), tblLista, true);
                    tblLista.getSelectionModel().setSelectionInterval(row, row);
                }
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está seguro que desea eliminar a " + tblLista.getValueAt(row, 1) + "?", true);
                if (n.getRespuesta()) {
                    if (GestionAnfitriones.esPosibleEliminarAnfitrion(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                        if (GestionAnfitriones.eliminarAnfitrion(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                            new Notificacion(0, "El cliente se ha eliminado correctamente", false);
                            if (tblLista.isEditing()) {
                                tblLista.getCellEditor().stopCellEditing();
                            }
                            Miscelanea.CargarTabla(GestionAnfitriones.vistaAnfitriones(), tblLista, true);
                        }
                    } else {
                        new Notificacion(1, "Error: No es posible al anfitrion eliminar debido a que hay contratos en progreso", false);
                    }
                    tblLista.getSelectionModel().setSelectionInterval(row, row);
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
