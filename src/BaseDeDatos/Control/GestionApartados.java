package BaseDeDatos.Control;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionApartados {

    public static void vistaApartados(JTable tblLista) {
        //String sql = "CALL vistaApartados(" + null + "," + null + "," + null + ")";
        String sql = "SELECT * FROM historial_apartados";
        ResultSet rs = Miscelanea.procedimiento(sql);
        DefaultTableModel model = (DefaultTableModel) tblLista.getModel();
        String datos[] = new String[tblLista.getColumnCount() - 1];
        Miscelanea.LimpiarTabla(tblLista);
        try {
            while (rs.next()) {
                for (int i = 0; i < datos.length; i++) {
                    if (i != 4) {
                        datos[i] = rs.getString(i + 1);
                    } else {
                        datos[i] = rs.getString(i + 3);
                    }
                }
                model.addRow(datos);
            }
            tblLista.setPreferredSize(new Dimension(900, model.getRowCount() * 37));
            tblLista.setModel(model);
        } catch (SQLException ex) {
        }
    }

    public static String[] totales(String folio) {
        String[] totales = new String[3];
        String sql = "SELECT total,total_restante FROM folio=" + folio;
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                totales[0] = rs.getString(1);
                totales[1] = rs.getString(2);
                totales[2] = String.format(".2f", Double.parseDouble(totales[0]) - Double.parseDouble(totales[1]));
            }
        } catch (SQLException ex) {
        }
        return totales;
    }

    public static boolean registrarApartado(String nombre) {
        if (nombre.isEmpty()) {
            return false;
        }
        String sql = "CALL registrarApartado('" + nombre + "')";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean registarProductoApartado(JTable tblLista, int inApartado) {
        String[] datos = new String[tblLista.getColumnCount() - 1];
        String sql;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            for (int j = 0; j < datos.length; j++) {
                datos[j] = tblLista.getValueAt(i, j).toString();
            }
            sql = "CALL registrarDetalleApartado(" + inApartado + ",'" + datos[0] + "'," + datos[1].substring(1) + "," + datos[2] + "," + datos[3].substring(1) + ")";
            if (Miscelanea.ejecucionActualizacion(sql) == 0) {
                sql = "DELETE FROM detalle_apartado WHERE id_apartado=" + inApartado;
                Miscelanea.ejecucionActualizacion(sql);
                sql = "DELETE FROM apartado WHERE id_apartado=" + inApartado;
                Miscelanea.ejecucionActualizacion(sql);
                return false;
            }
        }
        return true;
    }

    public static String getFolio() {
        try {
            String sql = "SELECT max(id_apartado) FROM apartado";
            ResultSet rs = Miscelanea.procedimiento(sql);
            while (rs.next()) {
                return "" + (rs.getInt(1) + 1);
            }
        } catch (SQLException ex) {
        }
        return "1";
    }

    public static ResultSet getListaProductosApartados(String folio) {
        String sql = "call ticketDeApartados(" + folio + ")";
        return Miscelanea.procedimiento(sql);
    }

    public static String getTotal(String folio) {
        String sql = "SELECT total FROM historial_apartados WHERE folio=" + folio;
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
        }
        return "0.00";
    }

    public static ResultSet vistaApartadosDe(String nombre, String fechaIni, String fechaFin, String estado) {
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
            sql = "SELECT Folio,Cliente,Fecha_Inicio,Fecha_Fin,Estado FROM historial_apartados WHERE"
                    + " (Fecha_Inicio >='" + inicioFN + "' OR '" + inicioFN + "' IS NULL)"
                    + " AND (Fecha_Inicio <='" + finFN + "'OR '" + finFN + "' IS NULL)"
                    + " AND Cliente LIKE '%" + nombre + "%'";
            if (!estado.equals("Todos")) {
                sql += " AND (" + estado + " IS NULL OR Estado =" + estado + ")";
            }
            sql += " ORDER BY Folio";
            //System.out.println(sql);
        } catch (ParseException ex) {
        }
        if (sql.isEmpty()) {
            sql = "call vistaApartados(null , null, null)";
        }
        //System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet vistaApartadosDe(String fechaIni, String fechaFin, String estado) {
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
            if (!estado.equals("Todos")) {
                sql = "call vistaApartados('" + inicioFN + "', '" + finFN + "','" + estado + "')";
            } else {
                sql = "call vistaApartados('" + inicioFN + "', '" + finFN + "'," + null + ")";
            }
        } catch (ParseException ex) {
        }
        if (sql.isEmpty()) {
            sql = "call vistaApartados(null , null, null)";
        }
        //System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet buscarApartadoPorEstado(String estado) {
        String sql = "call vistaApartados( null , null,";
        if (!estado.equals("Todos")) {
            sql += "'" + estado + "')";
        } else {
            sql += "null)";
        }

        //System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    public static boolean cancelarApartado(int folio) {
        String sql = "CALL actualizarApartado(" + folio + ",null,null,false)";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean editarApartado() {
        String sql = "CALL actualizarDetalleApartado(null,null,false)";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean cancelarProductoApartado(String folio, String nombre) {
        String sql = "CALL actualizarDetalleApartado(" + folio + ",'" + nombre + "',null,false)";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static void cambiarApartadoAVenta(String folio, String nombre, boolean metodoPago) {
        String sql = "call registrarVenta(1,'" + nombre + "'," + metodoPago + ")";
        System.out.println(sql);
        if (Miscelanea.ejecucionActualizacion(sql) != 0) {
            sql = "SELECT * FROM detalle_apartado where id_apartado=" + folio + " AND estado=1";
            System.out.println(sql);
            ResultSet rs = Miscelanea.procedimiento(sql);
            try {
                while (rs.next()) {
                    //System.out.println("item");
                    String sentencia = "call registrarDetalleVenta(" + (Integer.parseInt(GestionVentas.getFolio()) - 1) + ",'" + rs.getInt(2) + "'," + rs.getDouble(4) + "," + rs.getInt(3) + "," + rs.getDouble(5) + ")";
                    System.out.println(sentencia);
                    Miscelanea.ejecucionActualizacion(sentencia);
                }
            } catch (SQLException ex) {
            }
        }

    }
}
