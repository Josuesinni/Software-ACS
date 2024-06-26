package BaseDeDatos.Control;

import Calendario.DateChooser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;

public class GestionVentas {

    public static boolean registrarVenta(int usuario, String cliente, int metodoPago) {
        if (cliente.isEmpty()) {
            cliente = "Publico General";
        }
        String sql = "CALL registrarVenta(" + usuario + ",'" + cliente + "'," + metodoPago + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean registarProductoVenta(JTable tblLista, int inVenta) {
        String[] datos = new String[tblLista.getColumnCount() - 1];
        String sql;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            for (int j = 0; j < datos.length; j++) {
                datos[j] = tblLista.getValueAt(i, j).toString();
            }
            sql = "CALL registrarDetalleVenta(" + inVenta + ",'" + datos[0] + "'," + datos[1].substring(1) + "," + datos[2] + "," + datos[3].substring(1) + ")";
            if (Miscelanea.ejecucionActualizacion(sql) == 0) {
                sql = "DELETE FROM detalle_venta WHERE id_venta=" + inVenta;
                Miscelanea.ejecucionActualizacion(sql);
                sql = "DELETE FROM venta WHERE id_venta=" + inVenta;
                Miscelanea.ejecucionActualizacion(sql);
                return false;
            }
        }
        return true;
    }

    public static boolean actualizarVenta(int idCliente, String nombre, String instagram, String telefono, String direccion) {
        String sql = "CALL actualizarCliente(" + idCliente + ",'" + nombre + "','" + instagram + "','" + telefono + "','" + direccion + "'," + null + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean eliminarVenta(int idCliente) {
        String sql = "CALL actualizarCliente(" + idCliente + "'" + null + "','" + null + "','" + null + "','" + null + "'," + true + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static String getFolio() {
        try {
            String sql = "SELECT max(folio) FROM historial_ventas";
            ResultSet rs = Miscelanea.procedimiento(sql);
            while (rs.next()) {
                return "" + (rs.getInt(1) + 1);
            }
        } catch (SQLException ex) {
        }
        return "1";
    }

    public static ResultSet vistaVentas() {
        String sql = "SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet vistaVentasDe(String fechaIni, String fechaFin) {
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
            sql = "SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas WHERE fecha>='" + inicioFN + "' AND fecha<='" + finFN + "'";
        } catch (ParseException ex) {
        }
        if (sql.isEmpty()) {
            sql = "SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas";
        }
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet vistaVentasDe(String fechaIni, String fechaFin, String estado) {
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
            sql = "SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas WHERE fecha>='" + inicioFN + "' AND fecha<='" + finFN + "'";
            if (!estado.equals("Todos")) {
                sql += " AND estado='" + estado + "'";
            }
            //System.out.println(sql);
        } catch (ParseException ex) {
        }
        if (sql.isEmpty()) {
            sql = "SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas";
        }
        return Miscelanea.procedimiento(sql);
    }
    
     public static ResultSet vistaVentasDe(String nombre, String fechaIni, String fechaFin, String estado) {
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
            sql = "SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas WHERE"
                    + " (fecha >='" + inicioFN + "' OR '" + inicioFN + "' IS NULL)"
                    + " AND (fecha <='" + finFN + "'OR '" + finFN + "' IS NULL)"
                    + " AND Nombre LIKE '%" + nombre + "%'";
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

    public static ResultSet vistaTicket(String folio) {
        String sql = "call ticketDeVenta(" + folio + ")";
        System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    
}
