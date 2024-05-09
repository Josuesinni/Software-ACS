package BaseDeDatos.Control;

import BaseDeDatos.ConexionBD;
import Calendario.DateChooser;
import Principal.Ventana;
import SwingModificado.JComboBoxCustom;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Miscelanea {

    public static void CargarTabla(ResultSet rs, JTable tblDatos, boolean normal) {
        DefaultTableModel model = (DefaultTableModel) tblDatos.getModel();
        String datos[] = new String[tblDatos.getColumnCount() - (normal ? 1 : 0)];
        LimpiarTabla(tblDatos);
        try {
            while (rs.next()) {
                for (int i = 0; i < tblDatos.getColumnCount() - (normal ? 1 : 0); i++) {
                    datos[i] = rs.getString(i + 1);
                }
                model.addRow(datos);
            }
        } catch (SQLException ex) {
        }
        tblDatos.setPreferredSize(new Dimension(900, model.getRowCount() * 37));
        tblDatos.setModel(model);
    }

    public static void CargarComboBox(ResultSet rs, JComboBoxCustom cmb) {
        ArrayList<String> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                lista.add(rs.getString(1));
            }
            cmb.setModel(new javax.swing.DefaultComboBoxModel<>(lista.toArray()));
        } catch (SQLException ex) {
        }
    }

    public static void LimpiarTabla(JTable tblDatos) {
        DefaultTableModel model = (DefaultTableModel) tblDatos.getModel();
        int a = model.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public static ResultSet procedimiento(String sql) {
        Connection con = ConexionBD.conectar(Ventana.usuario, Ventana.pass);
        if (con != null) {
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                return rs;
            } catch (SQLException ex) {
                ConexionBD.cerrar();
            }
        }
        return null;
    }

    public static int ejecucionActualizacion(String sql) {
        Connection con = ConexionBD.conectar(Ventana.usuario, Ventana.pass);
        if (con != null) {
            try {
                Statement st = con.createStatement();
                int resultado = st.executeUpdate(sql);
                if (resultado != 0) {
                    ConexionBD.cerrar();
                    return resultado;
                }
                return 0;
            } catch (SQLException ex) {
                //System.out.println("Error en la BD");
                //ex.printStackTrace();
                ConexionBD.cerrar();
            }
        }
        return 0;
    }

    public static String[] formatoFechas(String fechaIni, String fechaFin) {
        String OLD_FORMAT = "dd-MM-yyyy";
        String NEW_FORMAT = "yyyy-MM-dd";
        String inicioFN = null, finFN = null;
        String sql = "";
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date inicio, fin;
        try {
            inicio = sdf.parse(fechaIni);
            fin = sdf.parse(fechaFin);
            sdf.applyPattern(NEW_FORMAT);
            inicioFN = sdf.format(inicio);
            finFN = sdf.format(fin);
        } catch (ParseException ex) {
        }
        return new String[]{inicioFN, finFN};
    }
    public static String getFechaMin(String columna,String tabla) {
        DateChooser dc = new DateChooser();
        try {
            String sql = "select min("+columna+") from "+tabla;
            ResultSet rs = Miscelanea.procedimiento(sql);
            while (rs.next()) {
                String fecha = rs.getString(1);
                String y = fecha.substring(0, 4);
                String m = fecha.substring(5, 7);
                String d = fecha.substring(8, 10);
                return d + "-" + m + "-" + y;
            }
        } catch (SQLException ex) {
        }
        return dc.getSelectedDate().getDay() + "-" + dc.getSelectedDate().getMonth() + "-" + dc.getSelectedDate().getYear();
    }
}
