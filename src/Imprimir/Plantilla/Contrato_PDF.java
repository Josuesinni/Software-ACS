package Imprimir.Plantilla;

import BaseDeDatos.Control.GestionAnfitriones;
import Imprimir.Modelo.ContratoRealizado;
import Utilidades.Notificacion;
import static Utilidades.Recursos.getMesEnEsp;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Contrato_PDF {

    private ContratoRealizado contratoRealizado;
    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;

    public Contrato_PDF(ContratoRealizado contratoRealizado) {
        this.contratoRealizado = contratoRealizado;
        documento = new Document(PageSize.LETTER, 75, 75, 75, 75);
        titulo = new Paragraph("Contrato");
    }

    public void crearReporte() {
        try {
            String ruta = System.getProperty("user.home");
            //System.out.println(ruta);
            String rutaArchivo = ruta + File.separator + "Desktop" + File.separator + "documentos" + File.separator + "contratos";
            File f = new File(ruta + File.separator + "Desktop" + File.separator + "documentos");
            File f2 = new File(rutaArchivo);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!f2.exists()) {
                f2.mkdirs();
            }
            //ruta+rutaEscritorio+File.separator
            archivo = new FileOutputStream(rutaArchivo + File.separator + "Contrato_" + contratoRealizado.getFolio() + "_" + contratoRealizado.getNombreAnfitrion() + "_" + ".pdf");

            //archivo = new FileOutputStream("Contrato_"+contratoRealizado.getFolio()+".pdf");
            PdfWriter pdf = PdfWriter.getInstance(documento, archivo);

            documento.open();
            titulo.getFont().setStyle(Font.BOLD);
            titulo.getFont().setSize(18);
            titulo.setAlignment(1);
            titulo.setSpacingBefore(30);
            titulo.setSpacingAfter(30);
            documento.add(titulo);

            Image image = Image.getInstance(getClass().getResource("/res/imagenes/iu/logo.png"));//carga imagen
            image.scaleAbsolute(150, 100);//cambia tamaño
            image.setAbsolutePosition(25, 692 - 25);
            documento.add(image);

            documento.add(Chunk.NEWLINE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String nFecha = "";
            try {
                Date dIni = sdf.parse(contratoRealizado.getaPartirDe());
                Calendar c = Calendar.getInstance();
                c.setTime(dIni);
                nFecha = c.get(Calendar.DAY_OF_MONTH) + " de " + getMesEnEsp(c.get(Calendar.MONTH)).toLowerCase() + " del " + c.get(Calendar.YEAR);
            } catch (ParseException ex) {
            }
            Paragraph p = new Paragraph("Anfitrión: " + contratoRealizado.getNombreAnfitrion());
            p.getFont().setStyle(Font.BOLD);
            p.getFont().setSize(16);
            documento.add(p);
            documento.add(Chunk.NEWLINE);
            p = new Paragraph("Se expide el contrato a partir del día " + nFecha + " por un periodo de " 
                    + contratoRealizado.getTiempo() + " con los requerimientos establecidos "
                            + "en el contrato físico entregado por Andrea Murrieta.");
            documento.add(p);
            documento.add(Chunk.NEWLINE);

            p = new Paragraph("Por un total de: $" + contratoRealizado.getTotal());
            p.getFont().setStyle(Font.BOLD);
            p.getFont().setSize(14);
            documento.add(p);

            PdfContentByte cb = pdf.getDirectContent();
            cb.saveState();
            //Monitor mon=Configuration.getInstance().getMonitor(tag_);
            cb.setColorStroke(BaseColor.BLACK);
            cb.moveTo(60, 300);
            cb.lineTo(275, 300);
            
            Font font = FontFactory.getFont("/res/fuentes/roboto/Roboto-Regular.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.5f, Font.NORMAL, BaseColor.BLACK);
            BaseFont baseFont = font.getBaseFont();
            cb.setFontAndSize(baseFont, 10);//165
            cb.showTextAligned(1, GestionAnfitriones.getNombreRepresentante(contratoRealizado.getNombreAnfitrion()), 150, 280, 0);
            cb.showTextAligned(1, "Andrea Murrieta", 450, 280, 0);
            cb.moveTo(350, 300);
            cb.lineTo(550, 300);
            cb.stroke();
            cb.restoreState();
            documento.close();
            new Notificacion(0, "El archivo PDF se ha creado exitosamente", false);
        } catch (FileNotFoundException | DocumentException ex) {
        } catch (IOException ex) {
        }

    }
}
