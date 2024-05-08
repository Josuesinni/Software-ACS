package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionClientes {
    public static ResultSet busquedaCliente(String nombre,String filtro) {
        String sql;
        if (!filtro.equals("Ninguno")) {
            sql= "SELECT * FROM clientes WHERE "+filtro+" LIKE '%" + nombre + "%' AND estado='Activo'";
        }else{
            sql= "SELECT * FROM clientes WHERE nombre LIKE '%" + nombre + "%'"
                    + " OR instagram LIKE '" + nombre + "%' AND estado='Activo'";
        }
        return Miscelanea.procedimiento(sql);
    }
    public static void busquedaCliente(String nombre,JTable tblDatos,String filtro) {
        String sql;
        if (!filtro.equals("Ninguno")) {
            sql= "SELECT * FROM clientes WHERE "+filtro+" LIKE '%" + nombre + "%' AND estado='Activo'";
        }else{
            sql= "SELECT * FROM clientes WHERE nombre LIKE '%" + nombre + "%'"
                    + " OR instagram LIKE '" + nombre + "%' "
                    + " AND estado='Activo'";
        }
        ResultSet rs=Miscelanea.procedimiento(sql);
        tablaBusquedaCliente(tblDatos,rs);
    }
    public static void tablaBusquedaCliente(JTable tblDatos,ResultSet rs){
        DefaultTableModel model = (DefaultTableModel) tblDatos.getModel();
        String datos[] = new String[tblDatos.getColumnCount()];
        Miscelanea.LimpiarTabla(tblDatos);
        try {
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(4);
                model.addRow(datos);
            }
            tblDatos.setModel(model);
        } catch (SQLException ex) {
        }
    }
    public static void vistaClientes(JTable tblDatos) {
        String sql = "SELECT * FROM clientes WHERE estado='Activo'";
        ResultSet rs = Miscelanea.procedimiento(sql);
        tablaBusquedaCliente(tblDatos,rs);
    }   
    public static ResultSet vistaClientes() {
        String sql = "SELECT * FROM clientes";
        return Miscelanea.procedimiento(sql);
    }
    public static ResultSet vistaClientesporEstado(String estado) {
        String sql = "SELECT * FROM clientes WHERE estado='"+estado+"'";
        return Miscelanea.procedimiento(sql);
    }
    public static String clienteSeleccionado(String idCliente) {
        String sql = "SELECT nombre FROM vistaClientes WHERE id=" + idCliente;
        ResultSet rs = Miscelanea.procedimiento(sql);
        String nombreCliente = "";
        try {
            while (rs.next()) {
                nombreCliente = rs.getString(1);
            }
        } catch (SQLException ex) {
        }
        return nombreCliente;
    }
    public static boolean registrarCliente(String nombre,String instagram,String telefono,String direccion){
        String sql="CALL registrarCliente('"+nombre+"','"+telefono+"','"+instagram+"','"+direccion+"')";
        return Miscelanea.ejecucionActualizacion(sql)!=0;
    }
    public static boolean actualizarCliente(int idCliente,String nombre,String instagram,String telefono,String direccion,boolean estado){
        String sql="CALL actualizarCliente("+idCliente+",'"+nombre+"','"+telefono+"','"+instagram+"','"+direccion+"',"+estado+")";
        return Miscelanea.ejecucionActualizacion(sql)!=0;
    }
    public static boolean eliminarCliente(int idCliente){
        String sql="CALL actualizarCliente("+idCliente+",null,null,null,null,"+false+")";
        return Miscelanea.ejecucionActualizacion(sql)!=0;
    }
    public static boolean esPosibleEliminarCliente(int idCliente){
        String sql="SELECT count(sePuedeEliminarCliente("+idCliente+"))";
        ResultSet rs= Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1)==0;
            }
        } catch (SQLException ex) {
        }
        return true;
    }
    public static boolean existeCliente(String nombre){
        if (nombre.isEmpty()) {
            nombre="Publico General";
        }
        String sql="SELECT count(*) from cliente where nombre='"+nombre+"' AND estado='1'";
        System.out.println(sql);
        ResultSet rs= Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1)!=0;
            }
        } catch (SQLException ex) {
        }
        return true;
    }
}
