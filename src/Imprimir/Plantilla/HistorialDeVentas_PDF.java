package Imprimir.Plantilla;

import Imprimir.Modelo.CabeceraYPieDePagina;
import Imprimir.Modelo.VentaRealizada;
import Utilidades.Notificacion;
import static Utilidades.Recursos.getMesEnEsp;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class HistorialDeVentas_PDF {

    public String fechaComienzo;
    public String fechaCierre;
    List<VentaRealizada> ventaRealizada;
    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;
    boolean tipo;

    public HistorialDeVentas_PDF(String fechaComienzo, String fechaCierre, List<VentaRealizada> ventaRealizada) {
        this.fechaComienzo = fechaComienzo;
        this.fechaCierre = fechaCierre;
        this.ventaRealizada = ventaRealizada;
        documento = new Document(PageSize.LETTER, 75, 75, 75, 75);
        titulo = new Paragraph("Historial de ventas");
    }

    public void crearReporte() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String nInicio="", nFin="";
            try {
                Date dIni = sdf.parse(fechaComienzo);
                Date dFin = sdf.parse(fechaCierre);
                Calendar c = Calendar.getInstance();
                c.setTime(dIni);
                nInicio = c.get(Calendar.DAY_OF_MONTH)+" de "+getMesEnEsp(c.get(Calendar.MONTH))+" del "+c.get(Calendar.YEAR);
                c.setTime(dFin);
                nFin = c.get(Calendar.DAY_OF_MONTH)+" de "+getMesEnEsp(c.get(Calendar.MONTH))+" del "+c.get(Calendar.YEAR);
            } catch (ParseException ex) {
            }
            
            String ruta = System.getProperty("user.home");
            //System.out.println(ruta);
            String rutaArchivo=ruta+File.separator+"Desktop"+File.separator+"documentos"+File.separator+"historial_de_ventas";
            File f=new File(ruta+File.separator+"Desktop"+File.separator+"documentos");
            File f2=new File(rutaArchivo);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!f2.exists()) {
                f2.mkdirs();
            }
            archivo = new FileOutputStream(rutaArchivo+ File.separator+"Ventas_Realizadas_de"+"_"+fechaComienzo+"_a_"+fechaCierre+".pdf");
            
            //archivo = new FileOutputStream("HistorialDeVentas.pdf");
            PdfWriter pdf = PdfWriter.getInstance(documento, archivo);
            
            
            
            pdf.setBoxSize("art", new Rectangle(75, 60, 612 - 75, 792 - 75));
            CabeceraYPieDePagina hf = new CabeceraYPieDePagina(nInicio.replace(" de ", "-").replace(" del ", "-"), nFin.replace(" de ", "-").replace(" del ", "-"));
            pdf.setPageEvent(hf);

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

            

            documento.add(new Paragraph("Lista de las ventas realizadas en el periodo de " + nInicio + " al " + nFin));
            documento.add(Chunk.NEWLINE);
            float[] columnWidths = new float[]{0.10f, 0.35f, 0.2f,0.2f,0.15f};

            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(columnWidths);
            tabla.setWidthPercentage(100);
            PdfPCell folio = new PdfPCell(new Phrase("Folio"));
            folio.setBackgroundColor(BaseColor.ORANGE);
            folio.setHorizontalAlignment(1);
            PdfPCell nombreCliente = new PdfPCell(new Phrase("Nombre del cliente"));
            nombreCliente.setBackgroundColor(BaseColor.ORANGE);
            nombreCliente.setHorizontalAlignment(1);
            PdfPCell total = new PdfPCell(new Phrase("Total"));
            total.setBackgroundColor(BaseColor.ORANGE);
            total.setHorizontalAlignment(1);
            PdfPCell metodo = new PdfPCell(new Phrase("Metodo de pago"));
            metodo.setBackgroundColor(BaseColor.ORANGE);
            metodo.setHorizontalAlignment(1);
            PdfPCell fecha = new PdfPCell(new Phrase("Fecha"));
            fecha.setBackgroundColor(BaseColor.ORANGE);
            fecha.setHorizontalAlignment(1);
            

            tabla.addCell(folio);
            tabla.addCell(nombreCliente);
            tabla.addCell(total);
            tabla.addCell(metodo);
            tabla.addCell(fecha);
            double totalEfectivo=0.00;
            double totalTransferecia=0.00;
            
            double sumaTotal = 0.00;
            for (VentaRealizada pv : ventaRealizada) {
                tabla.addCell(pv.getId());
                tabla.setHorizontalAlignment(1);
                tabla.addCell(pv.getCliente());
                tabla.addCell("$" + pv.getTotal());
                tabla.addCell(pv.getMetodo());
                tabla.addCell(pv.getFecha());
                if (pv.getMetodo().equals("Transferencia")) {
                    totalTransferecia+= Double.parseDouble(pv.getTotal());
                }else{
                    totalEfectivo+= Double.parseDouble(pv.getTotal());
                }
                sumaTotal += Double.parseDouble(pv.getTotal());
            }
            documento.add(tabla);
            Paragraph p = new Paragraph("Efectivo: $" + String.format("%.2f", totalEfectivo));
            p.getFont().setStyle(Font.BOLD);
            p.getFont().setSize(14);
            documento.add(p);
            p = new Paragraph("Transferencia: $" + String.format("%.2f", totalTransferecia));
            p.getFont().setStyle(Font.BOLD);
            p.getFont().setSize(14);
            documento.add(p);
            p = new Paragraph("Total: $" + String.format("%.2f", sumaTotal));
            p.getFont().setStyle(Font.BOLD);
            p.getFont().setSize(16);
            documento.add(p);

            documento.close();
            new Notificacion(0,"El archivo PDF se ha creado exitosamente",false);
        } catch (FileNotFoundException | DocumentException ex) {
        } catch (IOException ex) {
        }

    }

    
}
