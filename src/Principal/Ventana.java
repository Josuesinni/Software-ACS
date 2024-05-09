package Principal;

import BaseDeDatos.ConexionBD;
import InicioSesion.IniciarSesion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Utilidades.Fuente;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

public class Ventana extends JFrame {

    public static CambiarPaneles cp;
    
    public Ventana() {
        
        UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Sistema de Gestions ACS");
        setLayout(new BorderLayout());
        setSize(screenSize);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        movimientoEntreVentanas(screenSize);
        setUndecorated(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("/res/imagenes/iu/icon.png")));
        } catch (IOException ex) {
        }
    }

    public void movimientoEntreVentanas(Dimension d) {
        Fuente f=new Fuente();
        JPanel principal = new JPanel();
        IniciarSesion is = new IniciarSesion(d);
        principal.setLayout(new BorderLayout());
        cp = new CambiarPaneles(principal);
        cp.cargarPanel(is);
        cp.siguientePanel(cp.panelActual());
        add(principal, BorderLayout.CENTER);
        
    }
    
    public static Ventana vta;
    public static String usuario="root";
    public static String pass="root";
    public static void main(String[] args) {
        vta=new Ventana();
        vta.setFocusable(true);
    }
    
}
