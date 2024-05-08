package Imprimir.Modelo;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class CabeceraYPieDePagina extends PdfPageEventHelper {

    String fechaComienzo;
    String fechaCierre;

    public CabeceraYPieDePagina(String fechaComienzo, String fechaCierre) {
        this.fechaComienzo = fechaComienzo;
        this.fechaCierre = fechaCierre;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, new Phrase("Periodo: " + fechaComienzo + " / " + fechaCierre),
                rect.getRight(), rect.getTop() + 25, 0);
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_RIGHT, new Phrase(String.format("%d", writer.getPageNumber())),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }
}