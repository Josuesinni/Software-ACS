package BaseDeDatos.Control;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionPagos {

    public static void vistaPagos(JTable tblLista, String folio) {
        String sql = "call vistaPagos(" + folio + ")";
        ResultSet rs = Miscelanea.procedimiento(sql);
        DefaultTableModel model = (DefaultTableModel) tblLista.getModel();
        String datos[] = new String[tblLista.getColumnCount()];
        Miscelanea.LimpiarTabla(tblLista);
        try {
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(4);
                model.addRow(datos);
            }
        } catch (SQLException ex) {
        }

    }

    public static String obtenerAnticipo(String folio) {
        String sql = "SELECT cantidad FROM pago WHERE id_pago=(SELECT min(id_pago) FROM pago WHERE id_apartado=" + folio + ")";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
        }

        return "0.00";
    }

    public static String obtenerPagado(String folio) {
        String sql = "SELECT TOTALAPARTADO(" + folio + ")";
        ResultSet rs = Miscelanea.procedimiento(sql);
        try {
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
        }
        return "0.00";
    }

    public static boolean registrarPago(String folio, double cantidad, boolean metodoPago) {
        String sql = "CALL registrarPago(" + folio + "," + cantidad + "," + metodoPago + ")";
        return Miscelanea.ejecucionActualizacion(sql) != 0;
    }

    public static int getMetodoPagoUsado(int inApartado) {
        String sql = "select metodoPagoApartado(" + inApartado + ")";
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
