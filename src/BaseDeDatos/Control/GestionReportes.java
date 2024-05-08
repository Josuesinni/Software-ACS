package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionReportes {

    public static ResultSet vistaVentas() {
        String sql = "SELECT * FROM reporte_ventas";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet busquedaVentas(String filtro, String text, String fechaIni, String fechaFin) {
        String[] fechas = Miscelanea.formatoFechas(fechaIni, fechaFin);
        String sql = "SELECT id, nombre, precio, sum(cantidad) AS cantidad, ROUND(sum(subtotal),2) AS subtotal,anfitrion,categoria,reporte_ventas.estado "
                + "FROM reporte_ventas "
                + "JOIN venta ON venta.id_venta=reporte_ventas.id_venta "
                + "WHERE venta.fecha>='" + fechas[0] + "' AND venta.fecha<= '" + fechas[1] + "' AND venta.estado=1";
        switch (filtro) {
            case "Ninguno":
                break;
            default:
                sql += " AND "+filtro+" LIKE '%" + text + "%'";
                break;
        }
        sql += " GROUP BY id,nombre,precio,anfitrion,categoria,detalle_venta.estado";
        //System.out.println(sql);

        return Miscelanea.procedimiento(sql);
    }
    public static ResultSet buscadorReporte(int filtro,String texto){
        String sql="";
        switch (filtro) {
            case 0:
                sql="SELECT id_anfitrion,nombre_anfitrion FROM anfitrion WHERE nombre_anfitrion LIKE '"+texto+"%'";
                break;
            case 1:
                sql="SELECT id,nombre FROM inventario WHERE nombre LIKE '"+texto+"%'";
                break;
            case 2:
                sql="SELECT id_categoria,desc_categoria FROM categoria WHERE desc_categoria LIKE '"+texto+"%'";
                break;
        }
        //System.out.println(sql);
        return Miscelanea.procedimiento(sql);
        
    }
    public static ResultSet ventasRealizadasEn(String fechaIni, String fechaFin) {
        String[] fechas = Miscelanea.formatoFechas(fechaIni, fechaFin);
        String sql = "call vistaReporteVentas('" + fechas[0] + "', '" + fechas[1] + "',null)";
        //System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }
    public static ResultSet ventasRealizadasEn(String fechaIni, String fechaFin,String anfitrion) {
        String[] fechas = Miscelanea.formatoFechas(fechaIni, fechaFin);
        String sql = "call vistaReporteVentas('" + fechas[0] + "', '" + fechas[1] + "','"+anfitrion+"')";
        System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    public static String totalVentas(String fechaIni, String fechaFin) {
        String[] fechas = Miscelanea.formatoFechas(fechaIni, fechaFin);
        String sql = "SELECT totalVenta('" + fechas[0] + "','" + fechas[1] + "')";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                if (rs != null) {
                    if (rs.getString(1) == null) {
                        return "0.00";
                    }
                    return rs.getString(1);
                }
            }
        } catch (SQLException ex) {
        }
        return "0.00";
    }

    public static boolean cancelarVenta(String folio) {
        String sql = "call actualizarVenta(" + folio + ",0)";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }
}
