package Imprimir.Plantilla;

import Imprimir.Modelo.CabeceraYPieDePagina;
import Imprimir.Modelo.ProductoVendido;
import Utilidades.Notificacion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

public class ReporteDeVentas_PDF {

    public String fechaComienzo;
    public String fechaCierre;
    public String nombreAnfitrion;
    List<ProductoVendido> productosVendidos;
    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;
    boolean tipo;

    public ReporteDeVentas_PDF(String fechaComienzo, String fechaCierre, String nombreAnfitrion, List<ProductoVendido> productosVendidos, boolean tipo) {
        this.fechaComienzo = fechaComienzo;
        this.fechaCierre = fechaCierre;
        this.nombreAnfitrion = nombreAnfitrion;
        this.productosVendidos = productosVendidos;
        this.tipo = tipo;
        documento = new Document(PageSize.LETTER, 75, 75, 75, 75);
        //Font f= BaseFont.(Recursos.FUENTE_TITULO);
        titulo = new Paragraph("Reporte de ventas");
    }

    public void crearReporte() {
        try {
            String ruta = System.getProperty("user.home");
            //System.out.println(ruta);
            String rutaArchivo=ruta+File.separator+"Desktop"+File.separator+"documentos"+File.separator+"reporte_de_ventas";
            File f=new File(ruta+File.separator+"Desktop"+File.separator+"documentos");
            File f2=new File(rutaArchivo);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!f2.exists()) {
                f2.mkdirs();
            }
            archivo = new FileOutputStream(rutaArchivo+ File.separator+"RV_"+nombreAnfitrion+"_"+fechaComienzo+"_a_"+fechaCierre+".pdf");
            
            PdfWriter pdf = PdfWriter.getInstance(documento, archivo);

            pdf.setBoxSize("art", new Rectangle(75, 60, 612 - 75, 792 - 75));
            CabeceraYPieDePagina hf = new CabeceraYPieDePagina(fechaComienzo, fechaCierre);
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
            documento.add(new Paragraph("Reporte de productos vendidos de " + nombreAnfitrion));
            documento.add(Chunk.NEWLINE);
            float[] columnWidths;
            int numCol=tipo?7:6;
            if (tipo) {
                columnWidths = new float[]{0.05f, 0.3f, 0.1f, 0.08f, 0.1f, 0.2f, 0.17f};
            }else{
                columnWidths = new float[]{0.05f, 0.35f, 0.1f, 0.08f, 0.1f, 0.22f};
            }
            PdfPTable tabla = new PdfPTable(numCol);
            tabla.setWidths(columnWidths);
            tabla.setWidthPercentage(100);
            PdfPCell id = new PdfPCell(new Phrase("Id"));
            id.setBackgroundColor(BaseColor.ORANGE);
            id.setHorizontalAlignment(1);
            PdfPCell nombreproducto = new PdfPCell(new Phrase("Nombre del producto"));
            nombreproducto.setBackgroundColor(BaseColor.ORANGE);
            nombreproducto.setHorizontalAlignment(1);
            PdfPCell unidades = new PdfPCell(new Phrase("Uds."));
            unidades.setBackgroundColor(BaseColor.ORANGE);
            unidades.setHorizontalAlignment(1);
            PdfPCell precio = new PdfPCell(new Phrase("Precio"));
            precio.setBackgroundColor(BaseColor.ORANGE);
            precio.setHorizontalAlignment(1);
            PdfPCell Stotal = new PdfPCell(new Phrase("Subtotal"));
            Stotal.setBackgroundColor(BaseColor.ORANGE);
            Stotal.setHorizontalAlignment(1);
            PdfPCell NomA=null;
            if (tipo) {
                NomA = new PdfPCell(new Phrase("Nombre del anfitrion"));
                NomA.setBackgroundColor(BaseColor.ORANGE);
                NomA.setHorizontalAlignment(1);
            }

            PdfPCell categoria = new PdfPCell(new Phrase("Categoria"));
            categoria.setBackgroundColor(BaseColor.ORANGE);
            categoria.setHorizontalAlignment(1);
            tabla.addCell(id);
            tabla.addCell(nombreproducto);
            tabla.addCell(precio);
            tabla.addCell(unidades);
            tabla.addCell(Stotal);
            if (tipo) {
                tabla.addCell(NomA);
            }
            
            tabla.addCell(categoria);
            double total = 0.00;
            for (ProductoVendido pv : productosVendidos) {
                tabla.addCell(pv.getId() + "");
                tabla.setHorizontalAlignment(1);
                tabla.addCell(pv.getNombre());
                tabla.addCell("$" + pv.getPrecio() + "");
                tabla.addCell(pv.getUnidades() + "");
                tabla.addCell("$" + pv.getSubtotal() + "");
                if (tipo) {
                    tabla.addCell(pv.getNombreA());
                }
                tabla.addCell(pv.getCategoria());
                total += Double.parseDouble(pv.getSubtotal());
            }
            documento.add(tabla);
            Paragraph p = new Paragraph("Total: $" + total);
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