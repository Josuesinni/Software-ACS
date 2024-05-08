package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionUsuarios {

    public static boolean registarUsuario(String nombre, String password) {
        String sql = "CALL registrarUsuario('" + nombre + "','" + password + "')";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean consultarUsuario(String nombre, String password) {
        String sql = "SELECT count(usuarioValido('" + nombre + "','" + password+ "'))";
        //System.out.println(sql);
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1) != 0;
            }
        } catch (SQLException ex) {
        }
        return true;
    }

    public static boolean esAdmin(String nombre, String pass) {
        String sql = "SELECT count(esAdmin('" + nombre + "','" + pass + "'))";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1) != 0;
            }
        } catch (SQLException ex) {
        }
        return true;
    }
    
    public static boolean esAdminBD(String nombre, String pass) {
        String sql = "SELECT count(esAdminBD('" + nombre + "','" + pass + "'))";
        System.out.println(sql);
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1) != 0;
            }
        } catch (SQLException ex) {
        }
        return true;
    }

    public static ResultSet vistaUsuarios() {
        String sql = "SELECT * FROM usuarios";
        return Miscelanea.procedimiento(sql);
    }

    public static boolean actualizarUsuario(int idCliente, String nombre, String password, boolean is_admin, boolean estado) {
        String sql = "CALL actualizarUsuario(" + idCliente + ",'" + nombre + "','" + password + "',"+is_admin+"," + estado + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean eliminarUsuario(int idCliente) {
        String sql = "CALL actualizarUsuario(" + idCliente + ",null,null,null," + false + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }
}
