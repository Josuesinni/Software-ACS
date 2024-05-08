package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionAnfitriones {

    public static ResultSet cargarAnfitriones() {
        String sql = "SELECT anfitrion FROM contratos WHERE estado='Activo'";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet cargarAnfitrionesParaReporte(String fechaInicio, String fechaFin) {
        String[] fechas = Miscelanea.formatoFechas(fechaInicio, fechaFin);
        String sql = "SELECT anfitrion"
                + " FROM reporte_ventas "
                + " JOIN venta ON venta.id_venta=reporte_ventas.id_venta"
                + " WHERE venta.fecha>='" + fechas[0] + "' AND venta.fecha<='" + fechas[1] + "' AND venta.estado=1"
                + " GROUP BY anfitrion";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet anfitrionesDisponibles() {
        String sql = "SELECT nombre_anfitrion FROM anfitrion WHERE (anfitrion.id_anfitrion NOT IN (SELECT id_anfitrion FROM contrato) OR anfitrionConContratoActivo(id_anfitrion)<1) AND estado=1;";
        return Miscelanea.procedimiento(sql);
    }

    public static boolean hayAnfitrionesConContrato() {
        String sql = "SELECT COUNT(*) FROM contratos WHERE estado='Activo'";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    public static boolean hayAnfitrionesDisponibles() {
        String sql = "SELECT COUNT(*) FROM anfitrion WHERE (anfitrion.id_anfitrion NOT IN (SELECT id_anfitrion FROM contrato) OR anfitrionConContratoActivo(id_anfitrion)<1) AND estado=1";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    public static ResultSet vistaAnfitriones() {
        String sql = "call vistaAnfitriones()";
        return Miscelanea.procedimiento(sql);
    }

    public static boolean registrarAnfitrion(String nombre, String nombre_representante, String correo, String telefono, String instagram) {
        String sql = "CALL registrarAnfitrion('" + nombre + "','" + nombre_representante + "','" + correo + "','" + telefono + "','" + instagram + "')";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean actualizarAnfitrion(int idAnfitrion, String nombre, String nombre_representante, String correo, String telefono, String instagram, int estado) {
        String sql = "CALL actualizarAnfitrion(" + idAnfitrion + ",'" + nombre + "','" + nombre_representante + "','" + telefono + "','" + correo + "','" + instagram + "'," + estado + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean eliminarAnfitrion(int idAnfitrion) {
        String sql = "CALL actualizarAnfitrion(" + idAnfitrion + ",null,null,null,null,null," + false + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean esPosibleEliminarAnfitrion(int idAnfitrion) {
        String sql = "SELECT count(sePuedeEliminarAnfitrion(" + idAnfitrion + "))";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException ex) {
        }
        return true;
    }

    public static ResultSet busquedaAnfitrion(String nombre, String filtro) {
        String sql;
        if (!filtro.equals("Ninguno")) {
            sql = "SELECT * FROM anfitriones WHERE " + filtro + " LIKE '%" + nombre + "%'";
        } else {
            sql = "SELECT * FROM anfitriones WHERE Nombre_Anfitrion LIKE '%" + nombre + "%'"
                    + " OR Nombre_Representante LIKE '%" + nombre + "%'"
                    + " OR Instagram LIKE '" + nombre + "%'";
        }
        return Miscelanea.procedimiento(sql);
    }

    public static String getNombreRepresentante(String anfitrion) {
        String sql = "SELECT nombre_representante from anfitrion WHERE nombre_anfitrion='"+anfitrion+"'";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
        }
        return null;
    }
}
