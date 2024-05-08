package Contratos;

import BaseDeDatos.Control.GestionAnfitriones;
import BaseDeDatos.Control.GestionApartados;
import BaseDeDatos.Control.GestionContratos;
import BaseDeDatos.Control.Miscelanea;
import Menus.MenuFecha;
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
import static java.awt.Window.Type.POPUP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class CatalogoContratos extends JPanel {

    public CatalogoContratos(Dimension d) {
        initComponents();
        setSize(d);
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }

    public void initComponents() {
        panelIzquierdo();
        panelDerecho();
    }
    JComboBoxCustom cmbEstado;
    JTextFieldRounded txtCiclo1,txtCiclo2;
    public void panelIzquierdo() {
        JLabel lblTitulo = Recursos.tituloVentana("GESTIÓN DE CONTRATOS");
        lblTitulo.setSize(600, 60);
        add(lblTitulo);

        JTextFieldRounded txtBuscar = new JTextFieldRounded("Nombre del anfitrión", 20, Recursos.FUENTE_GENERAL);
        txtBuscar.setLocation(100, 180);
        txtBuscar.setSize(460, 50);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Miscelanea.CargarTabla(GestionContratos.busquedaContrato(txtBuscar.getText()), tblLista, true);
            }
        });
        add(txtBuscar);

        String hoy = Calendar.getInstance().get(Calendar.DATE) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.YEAR);
        txtCiclo1 = new JTextFieldRounded(hoy, 20, Recursos.FUENTE_GENERAL);
        txtCiclo1.setLocation(1104, 100);
        txtCiclo1.setSize(120, 40);
        txtCiclo1.setBackground(new Color(239, 232, 232));
        txtCiclo1.setBorder(null);
        txtCiclo1.setHorizontalAlignment(SwingConstants.CENTER);
        txtCiclo1.setEditable(false);

        add(txtCiclo1);

        JLabel lblBarra = new JLabel("/", SwingConstants.CENTER);
        lblBarra.setLocation(1244, 100);
        lblBarra.setSize(40, 40);
        lblBarra.setFont(getFuente(0, 1, 28));
        add(lblBarra);

        txtCiclo2 = new JTextFieldRounded(hoy, 20, Recursos.FUENTE_GENERAL);
        txtCiclo2.setLocation(1304, 100);
        txtCiclo2.setSize(120, 40);
        txtCiclo2.setBackground(new Color(239, 232, 232));
        txtCiclo2.setBorder(null);
        txtCiclo2.setHorizontalAlignment(SwingConstants.CENTER);
        txtCiclo2.setEditable(false);
        add(txtCiclo2);

        MenuFecha mf = new MenuFecha();
        mf.setDialogPosition(760, 250);
        mf.resizeJDialog(240, 170);
        mf.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (e.getOppositeWindow() != null) {
                    if (e.getOppositeWindow().getType() != POPUP) {
                        mf.dispose();
                    }
                } else {
                    mf.dispose();
                }
            }
        });
        mf.setTextCiclos(txtCiclo1, txtCiclo2);
        JButtonRounded btn = new JButtonRounded("Fecha", 20);
        btn.setSize(240, 40);
        btn.setLocation(760, 190);
        btn.setBackground(new Color(255, 214, 153));
        btn.setColorOver(new Color(255, 224, 163));
        btn.setColor(new Color(255, 214, 153));
        btn.setColorClick(new Color(255, 224, 163));
        btn.setFont(Recursos.FUENTE_GENERAL);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mf.showDialog();
                String estado = cmbEstado.getSelectedItem().toString();
                int estado2 = cmbEstado.getSelectedIndex();
                mf.addTablaLista(tblLista, estado);
                mf.setTipo(1);
                mf.setEstado2(estado2);
                //limpiar();
            }
        });
        add(btn);

        JLabel lblEstado = new JLabel("Estado", JLabel.LEFT);
        lblEstado.setLocation(600, 172);
        lblEstado.setSize(150, 20);
        lblEstado.setFont(getFuente(1, 1, 12));
        add(lblEstado);

        cmbEstado = new JComboBoxCustom<>();
        cmbEstado.setFont(getFuente(1, 1, 16));
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Todos", "Cancelado", "Activo", "Finalizado"}));
        cmbEstado.setLocation(600, 190);
        cmbEstado.setSize(140, 40);
        cmbEstado.setBackground(new Color(255, 214, 153));
        ((JLabel) cmbEstado.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbEstado.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int estado = cmbEstado.getSelectedIndex();
                if (txtCiclo1.getText().isEmpty() && txtCiclo2.getText().isEmpty()) {
                    Miscelanea.CargarTabla(GestionContratos.buscarContratoPorEstado(estado), tblLista, true);
                    setSignoPeso();
                } else {
                    Miscelanea.CargarTabla(GestionContratos.vistaContratosDe(txtCiclo1.getText(), txtCiclo2.getText(), estado), tblLista, true);
                    setSignoPeso();
                }
                //limpiar();
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
        String[] cabecera = new String[]{"Clave", "Nombre del Anfitrion", "Fecha_Inicio", "Fecha_Final", "Total", "Estado", ""};
        boolean[] colEditables = {false, false, false, false, false, false, true};
        int[] tamColumnas = {10, 140, 100, 100, 40, 40, 40};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 3);
        Miscelanea.CargarTabla(GestionContratos.vistaContratos(), tblLista, true);
        AccionTabla(tblLista, 3);
        setSignoPeso();
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
    JButtonRounded btnNuevoContrato;

    public void panelDerecho() {
        JButtonRounded bntAnfitriones = new JButtonRounded("VER ANFITRIONES", 20, true);
        bntAnfitriones.setLocation(760, 675);
        bntAnfitriones.setSize(300, 60);
        bntAnfitriones.setFont(Recursos.FUENTE_BOTON);
        bntAnfitriones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.cargarPanel(new CatalogoAnfitriones(getSize()));
                cp.siguientePanel(cp.panelActual());
                bntAnfitriones.restaurar();
            }
        });
        add(bntAnfitriones);
        btnNuevoContrato = new JButtonRounded("NUEVO CONTRATO", 20, true);
        btnNuevoContrato.setLocation(1120, 675);
        btnNuevoContrato.setSize(300, 60);
        btnNuevoContrato.setFont(Recursos.FUENTE_BOTON);
        btnNuevoContrato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GestionAnfitriones.hayAnfitrionesDisponibles()) {
                    ContratoCyE c = new ContratoCyE(true, true, null);
                    if (c.isAccion()) {
                        Miscelanea.CargarTabla(GestionContratos.vistaContratos(), tblLista, true);
                        setSignoPeso();
                    }
                } else {
                    new Notificacion(1,"No hay anfitriones disponibles para hacer un nuevo contrato",false);
                }
            }
        });
        add(btnNuevoContrato);
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
                Object datos[] = new Object[tblLista.getColumnCount() - 1];
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = tblLista.getValueAt(row, i);
                }
                ContratoCyE ccye = new ContratoCyE(true, false, datos);
                if (ccye.isAccion()) {
                    Miscelanea.CargarTabla(GestionContratos.vistaContratos(), tblLista, true);
                    setSignoPeso();
                }
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está seguro que desea eliminar a " + tblLista.getValueAt(row, 1) + "?", true);
                if (n.getRespuesta()) {
                    if (GestionContratos.eliminarContrato(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                        new Notificacion(0, "El cliente se ha eliminado correctamente", false);
                        if (tblLista.isEditing()) {
                            tblLista.getCellEditor().stopCellEditing();
                        }
                        Miscelanea.CargarTabla(GestionContratos.vistaContratos(), tblLista, true);
                        setSignoPeso();
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

    public void setSignoPeso() {
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.setValueAt("$" + tblLista.getValueAt(i, 4), i, 4);
        }
    }
}
