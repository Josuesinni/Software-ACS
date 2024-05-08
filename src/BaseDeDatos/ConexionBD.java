package BaseDeDatos;

import Utilidades.Notificacion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {

    public static Connection con;
    public static String bd = "andreaconceptstore";
    public static String url = "jdbc:mysql://localhost:3306/" + bd+"?autoReconnect=true&useSSL=false";

    public static Connection conectar(String usuario,String contra) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, usuario, contra);
            /*if (con != null) {
                System.out.println("Conexión exitosa");
            }*/
        } catch (ClassNotFoundException | SQLException e) {
            new Notificacion(1,"Error de conexión a la base de datos",false);
            //System.out.println("Error de conexión");
            //e.printStackTrace();
        }
        return con;
    }

    public static Connection getCon() {
        return con;
    }
    

    public static void cerrar() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }
}
