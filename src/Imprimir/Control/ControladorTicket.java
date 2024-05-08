package Imprimir.Control;

import Imprimir.Modelo.ParametrosTicket;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

public class ControladorTicket {

    private static ControladorTicket instance;
    private JasperReport ticket;

    public static ControladorTicket getInstance() {
        if (instance == null) {
            instance = new ControladorTicket();
        }
        return instance;
    }

    public void compilarTicket() {
        try {
            ticket = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Imprimir/Plantilla/Ticket.jrxml"));
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    public void imprimirTicket(ParametrosTicket pt) {
        try {
            Map para = new HashMap();
            para.put("Fecha de venta", pt.getFechaPago());
            para.put("Metodo de pago", pt.getMetodoPago());
            para.put("Folio", pt.getFolio());
            para.put("Nombre Cliente", pt.getCliente());
            para.put("Total", pt.getTotal());
            para.put("Importe", pt.getImporte());
            para.put("Cambio", pt.getCambio());
            para.put("qrcode", pt.getQr());
            para.put("logo", pt.getLogo());
            para.put(JRParameter.IS_IGNORE_PAGINATION, true);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pt.getListaVenta());
            JasperPrint print = JasperFillManager.fillReport(ticket, para, dataSource);
            /*JRPdfExporter pdfExporter=new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(print));
            ByteArrayOutputStream pdfReportStream=new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();*/
            view(print);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    private void view(JasperPrint print) {
        JasperViewer.viewReport(print, false);
    }
}
