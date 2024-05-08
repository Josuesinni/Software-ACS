package Imprimir.Modelo;

import java.io.InputStream;
import java.util.List;

public class ParametrosTicket {
    String folio;
    String cliente;
    String total;
    String importe;
    String cambio;
    String metodoPago;
    String fechaPago;
    InputStream qr;
    InputStream logo;
    List<ProductoVendido> listaVenta;

    public ParametrosTicket(String folio, String cliente, String total, String importe,String cambio,String metodoPago, String fechaPago, InputStream qr,InputStream logo,List<ProductoVendido> listaVenta) {
        this.folio = folio;
        this.cliente = cliente;
        this.total = total;
        this.importe = importe;
        this.cambio = cambio;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.listaVenta = listaVenta;
        this.qr=qr;
        this.logo=logo;
    }

    public String getFolio() {
        return folio;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTotal() {
        return total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public String getImporte() {
        return importe;
    }

    public String getCambio() {
        return cambio;
    }
    

    public List<ProductoVendido> getListaVenta() {
        return listaVenta;
    }

    public InputStream getQr() {
        return qr;
    }

    public InputStream getLogo() {
        return logo;
    }
    
}
