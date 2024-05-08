package Utilidades;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class Fuente {

    public static Font fuenteBold, fuenteNormal;
    
    public Fuente() {
        try {
            InputStream in = getClass().getResourceAsStream("/res/fuentes/roboto/Roboto-Bold.ttf");
            //File f = new File(getClass().getResource("/res/fuentes/roboto/Roboto-Bold.ttf").toURI());
            fuenteBold = Font.createFont(Font.TRUETYPE_FONT, in);
            InputStream in2 = getClass().getResourceAsStream("/res/fuentes/roboto/Roboto-Regular.ttf");
            //File f2 = new File(getClass().getResource("/res/fuentes/roboto/Roboto-Regular.ttf").toURI());
            fuenteNormal = Font.createFont(Font.TRUETYPE_FONT, in2);
        } catch (FontFormatException ex) {
            System.out.println("Fallo");
        } catch (IOException ex) {
            System.out.println("Fallo 2");
        }
    }

}
