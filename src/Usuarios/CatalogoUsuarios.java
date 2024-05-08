package Usuarios;

import BaseDeDatos.Control.GestionUsuarios;
import BaseDeDatos.Control.Miscelanea;
import static Principal.Ventana.vta;
import SwingModificado.JButtonRounded;
import SwingModificado.ScrollBarCustom;
import Tabla.AccionEnJTable;
import Tabla.CeldaPersonalizada;
import Utilidades.Notificacion;
import Utilidades.Recursos;
import static Utilidades.Recursos.getFuente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CatalogoUsuarios extends JDialog {

    JPanel pnl;

    public CatalogoUsuarios(boolean modal, String titulo) {
        super(vta, modal);
        pnl = new JPanel();
        pnl.setBackground(Color.white);
        initComponents();
        pnl.setLayout(new BorderLayout());
        setTitle(titulo);
        setSize(800, 500);
        setContentPane(pnl);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {
        JLabel lblTitulo = new JLabel("LISTA DE USUARIOS", JLabel.CENTER);
        lblTitulo.setSize(400, 30);
        lblTitulo.setLocation(200, 30);
        lblTitulo.setFont(getFuente(1, 0, 24));
        lblTitulo.setForeground(Recursos.letraDorada);
        pnl.add(lblTitulo);
        JButtonRounded btnAtras = new JButtonRounded(20, true);
        btnAtras.setLocation(50, 340);
        btnAtras.setSize(60, 40);
        btnAtras.setFont(Recursos.FUENTE_BOTON_2);
        btnAtras.setIcon(new ImageIcon(getClass().getResource("/res/imagenes/iu/atras.png")));
        btnAtras.setIconPosition(0);
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        pnl.add(btnAtras);

        JButtonRounded btnAceptar = new JButtonRounded("ACEPTAR", 20, true);
        btnAceptar.setLocation(450, 340);
        btnAceptar.setSize(100, 40);
        btnAceptar.setFont(Recursos.FUENTE_BOTON_2);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accion = true;
                int row = tblLista.getSelectedRow();
                nombreCliente = tblLista.getValueAt(row, 0).toString();
                dispose();
            }
        });
        pnl.add(btnAceptar);

        tabla();
    }
    String nombreCliente = "";
    boolean accion = false;
    JTable tblLista;

    public boolean getRespuesta() {
        return accion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void tabla() {
        String[] cabecera = {"Clave", "Usuario", "Contraseña", "Administrador", "Estado", ""};
        Object[][] datos = {};
        boolean[] colEditables = {false, false, false, false, false, true};
        int[] tamColumnas = {10, 140, 100, 120, 40, 40};
        tblLista = Recursos.crearTabla(cabecera, datos, tamColumnas, colEditables, 3);
        Miscelanea.CargarTabla(GestionUsuarios.vistaUsuarios(), tblLista, true);
        AccionTabla(tblLista, 3);
        JScrollPane jsp1 = new JScrollPane();
        jsp1.setBackground(new java.awt.Color(255, 255, 255));
        jsp1.setSize(new java.awt.Dimension(700, 300));
        jsp1.setPreferredSize(new java.awt.Dimension(700, 200));
        jsp1.setRowHeaderView(null);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(37);
        sb.setForeground(new Color(180, 180, 180));
        jsp1.setVerticalScrollBar(sb);
        jsp1.setViewportView(tblLista);
        jsp1.setLocation(50, 100);
        jsp1.getViewport().setBackground(tblLista.getBackground());
        pnl.add(jsp1);
    }

    public void AccionTabla(JTable tblLista, int tipo) {
        AccionEnJTable event = new AccionEnJTable() {
            @Override
            public void editar(int row) {
                Object datos[] = new Object[tblLista.getColumnCount() - 1];
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = tblLista.getValueAt(row, i);
                }
                EdicionUsuario ccye = new EdicionUsuario(true, false, datos);
                if (ccye.isAccion()) {
                    Miscelanea.CargarTabla(GestionUsuarios.vistaUsuarios(), tblLista, true);
                }
            }

            @Override
            public void eliminar(int row) {
                Notificacion n = new Notificacion(2, "¿Está seguro que desea eliminar a " + tblLista.getValueAt(row, 1) + "?", true);
                if (n.getRespuesta()) {
                    if (GestionUsuarios.eliminarUsuario(Integer.parseInt(tblLista.getValueAt(row, 0).toString()))) {
                        new Notificacion(0, "El usuario se ha eliminado correctamente", false);
                        if (tblLista.isEditing()) {
                            tblLista.getCellEditor().stopCellEditing();
                        }
                        Miscelanea.CargarTabla(GestionUsuarios.vistaUsuarios(), tblLista, true);
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
