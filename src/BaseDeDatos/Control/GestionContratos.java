package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionContratos {

    public static ResultSet vistaContratos() {
        String sql = "SELECT * from contratos";
        return Miscelanea.procedimiento(sql);
    }

    public static boolean registrarContrato(String nombreAnfitrion,String total,String fechaInicio,String fechaFinal) {
        String sql = "CALL registrarContrato('" + nombreAnfitrion + "'," + total + ",'" + fechaInicio + "','" + fechaFinal + "')";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean actualizarContrato(int idContrato, String nombreAnfitrion,String total,String fechaInicio,String fechaFinal) {
        String sql = "CALL actualizarContrato(" + idContrato + ",'" + nombreAnfitrion + "'," + total + ",'" + fechaInicio + "','" + fechaFinal + "'," + null + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean eliminarContrato(int idContrato) {
        String sql = "CALL actualizarContrato(" + idContrato + ",null,null,null,null," + false + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static ResultSet busquedaContrato(String nombre) {
        String sql = "SELECT * FROM contratos WHERE Anfitrion LIKE '%" + nombre + "%'";
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet vistaContratosDe(String fechaIni, String fechaFin, int estado) {
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
            if (estado!=0) {
                sql = "call vistaContratos('" + inicioFN + "', '" + finFN + "'," + (estado-1) + ")";
            }else{
                sql = "call vistaContratos('" + inicioFN + "', '" + finFN +"',null)";
            }
        } catch (ParseException ex) {
        }
        if (sql.isEmpty()) {
            sql = "call vistaContratos(null , null, null)";
        }
        System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }

    public static ResultSet buscarContratoPorEstado(int estado) {
        String sql = "call vistaContratos( null , null,";
        if (estado!=0) {
            sql += ""+(estado-1) + ")";
        } else {
            sql += "null)";
        }

        System.out.println(sql);
        return Miscelanea.procedimiento(sql);
    }
    
    public static String getFolio(){
        try {
            String sql="SELECT max(folio) FROM contratos";
            ResultSet rs = Miscelanea.procedimiento(sql);
            while (rs.next()) {
                return ""+(rs.getInt(1)+1);
            }           
        } catch (SQLException ex) {
        }
        return "1";
    }
}
