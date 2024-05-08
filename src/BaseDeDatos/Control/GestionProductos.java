package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionProductos {
    
    public static ResultSet vistaMovimientos() {
        String sql = "SELECT * FROM movimientos";
        return Miscelanea.procedimiento(sql);
    }
    
    public static ResultSet vistaMovimientosProducto(String nombre) {
        String sql = "SELECT * FROM movimientos where nombre like '%"+nombre+"%'";
        return Miscelanea.procedimiento(sql);
    }
    
    public static ResultSet vistaBusquedaProductos() {
        String sql = "SELECT * FROM inventario WHERE cantidad>0";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet vistaProductos() {
        String sql = "SELECT * FROM inventario";
        return Miscelanea.procedimiento(sql);
    }
    
    public static ResultSet vistaProductosPorEstado(String estado) {
        String sql = "SELECT * FROM inventario WHERE estado='"+estado+"'";
        return Miscelanea.procedimiento(sql);
    }
    
    public static ResultSet busquedaProducto(String nombre) {
        String sql = "SELECT * FROM inventario WHERE nombre LIKE '%" + nombre + "%' AND cantidad>0 "
                + "OR id='" + nombre + "' AND cantidad>0 ";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet busquedaProducto(String nombre, String filtro, boolean venta) {
        String sql;
        if (!filtro.equals("Ninguno")) {
            if (filtro.equals("Clave")) {
                filtro = "id";
            }
            sql = "SELECT * FROM inventario WHERE " + filtro + " LIKE '" + nombre + "%' AND cantidad>0";
        } else {
            sql = "SELECT * FROM inventario WHERE"
                    + " id LIKE '" + nombre + "%' AND cantidad>0"
                    + " OR nombre LIKE '%" + nombre + "%' AND cantidad>0"
                    + " OR anfitrión LIKE '%" + nombre + "%' AND cantidad>0"
                    + " OR categoria LIKE '%" + nombre + "%' AND cantidad>0";
        }
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet busquedaProducto(String nombre, String filtro) {
        String sql;
        if (!filtro.equals("Ninguno")) {
            if (filtro.equals("Clave")) {
                filtro = "id";
            }
            sql = "SELECT * FROM inventario WHERE " + filtro + " LIKE '%" + nombre + "%'";
        } else {
            sql = "SELECT * FROM inventario WHERE"
                    + " id LIKE '" + nombre + "%'"
                    + " OR nombre LIKE '%" + nombre + "%'"
                    + " OR anfitrión LIKE '%" + nombre + "%'"
                    + " OR categoria LIKE '%" + nombre + "%'";
        }
        return Miscelanea.procedimiento(sql);
    }

    public static void addProductoLista(String nombre, JTable tblLista) {
        String sql = "SELECT * FROM inventario WHERE nombre = '" + nombre + "'";
        ResultSet rs = Miscelanea.procedimiento(sql);
        DefaultTableModel dft = (DefaultTableModel) tblLista.getModel();
        String[] datos = new String[tblLista.getColumnCount() - 1];
        try {
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = "$" + rs.getString(3);
                datos[2] = "1";
                datos[3] = "$" + rs.getString(3);
            }
            dft.addRow(datos);
        } catch (SQLException ex) {

        }
    }

    public static String[] productoSeleccionado(int idProducto) {
        String sql = "SELECT nombre,precio FROM inventario WHERE id=" + idProducto;
        ResultSet rs = Miscelanea.procedimiento(sql);
        String datos[] = new String[2];
        try {
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
            }
        } catch (SQLException ex) {
        }
        return datos;
    }

    public static boolean actualizarProducto(int idProducto, String nombre, int cantidad, double precio, String idAnfitrion, String idCategoria,int estado) {
        String sql = "CALL actualizarProducto(" + idProducto + ",'" + nombre + "'," + precio + "," + cantidad + ",'" + idAnfitrion + "','" + idCategoria + "'," + estado + ")";
        //System.out.println(sql);
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean registarProducto(String nombre, int cantidad, double precio, String idAnfitrion, String idCategoria) {
        String sql = "CALL registrarProducto('" + nombre + "'," + precio + "," + cantidad + ",'" + idAnfitrion + "','" + idCategoria + "')";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean eliminarProducto(int idProducto) {
        String sql = "CALL actualizarProducto(" + idProducto + "," + null + "," + null + "," + null + "," + null + "," + null + "," + false + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean ajustarCantidadProducto(int idProducto, int cantidad, int razon) {
        String sql = "CALL registrarDetalleProducto(" + idProducto + "," + cantidad + "," + razon + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean existeProducto(String producto) {
        String sql = "SELECT id FROM inventario WHERE nombre='" + producto + "' AND cantidad>0";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs != null;
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    public static int cantidadProductoExistente(String nombreProducto) {
        String sql = "SELECT cantidad FROM inventario WHERE nombre='" + nombreProducto + "'";
        //System.out.println(sql);
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return 0;
    }
}
