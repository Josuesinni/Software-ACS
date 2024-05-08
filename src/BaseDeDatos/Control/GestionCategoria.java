package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCategoria {

    public static ResultSet cargarCategorias() {
        String sql = "SELECT desc_categoria FROM categoria ORDER BY desc_categoria ASC";
        return Miscelanea.procedimiento(sql);
    }

    public static int registrarCategoria(String nombre) {
        String sql = "CALL registrarCategoria('" + nombre + "')";
        return Miscelanea.ejecucionActualizacion(sql);
    }

    public static ResultSet busquedaCategoria(String nombre) {
        String sql = "SELECT id_categoria,desc_categoria FROM categoria WHERE desc_categoria LIKE '" + nombre + "%'";
        //System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    public static boolean isCategoriaRegistrada(String categoria) {
        String sql = "SELECT id_categoria FROM categoria WHERE desc_categoria='"+categoria+"'";
       ResultSet rs=Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs!=null;
            }
        } catch (SQLException ex) {
        }
        return false;
    }
}
