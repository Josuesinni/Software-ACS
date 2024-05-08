package BaseDeDatos.Control;

import Calendario.DateChooser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;

public class GestionVentas {

    public static boolean registrarVenta(int usuario, String cliente,int metodoPago) {
        if (cliente.isEmpty()) {
            cliente="Publico General";
        }
        String sql = "CALL registrarVenta("+ usuario + ",'" + cliente+ "',"+metodoPago+")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static boolean registarProductoVenta(JTable tblLista,int inVenta) {
        String[] datos=new String[tblLista.getColumnCount()-1];
        String sql;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            for (int j = 0; j < datos.length; j++) {
                datos[j]=tblLista.getValueAt(i, j).toString();
            }
            sql = "CALL registrarDetalleVenta("+inVenta+",'" + datos[0] + "'," + datos[1].substring(1) + "," + datos[2] + "," + datos[3].substring(1) + ")";
            if (Miscelanea.ejecucionActualizacion(sql)==0) {
                sql="DELETE FROM detalle_venta WHERE id_venta="+inVenta;
                Miscelanea.ejecucionActualizacion(sql);
                sql="DELETE FROM venta WHERE id_venta="+inVenta;
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
    public static String getFolio(){
        try {
            String sql="SELECT max(folio) FROM historial_ventas";
            ResultSet rs = Miscelanea.procedimiento(sql);
            while (rs.next()) {
                return ""+(rs.getInt(1)+1);
            }           
        } catch (SQLException ex) {
        }
        return "1";
    }
    public static ResultSet vistaVentas(){
        String sql="SELECT Folio,Nombre,Fecha,Total,Metodo_Pago,Estado FROM historial_ventas";
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
    public static ResultSet vistaTicket(String folio){
        String sql="call ticketDeVenta("+folio+")";
        return Miscelanea.procedimiento(sql);
    }
    public static void cancelarProductosVenta(){
        
    }
    public static String getFechaMin(){
        DateChooser dc = new DateChooser();
         try {
            String sql="select min(fecha) from venta";
            ResultSet rs = Miscelanea.procedimiento(sql);
            while (rs.next()) {
                String fecha=rs.getString(1);
                String y=fecha.substring(0, 4);
                String m=fecha.substring(5, 7);
                String d=fecha.substring(8, 10);
                return d+"-"+m+"-"+y;
            }           
        } catch (SQLException ex) {
        }
        return dc.getSelectedDate().getDay() + "-" + dc.getSelectedDate().getMonth() + "-" + dc.getSelectedDate().getYear();
    }
}
