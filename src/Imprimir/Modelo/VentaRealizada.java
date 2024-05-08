package Imprimir.Modelo;


public class VentaRealizada {
    private String id;
    private String cliente;
    private String fecha;
    private String total;
    private String metodo;
    public VentaRealizada(String id, String cliente, String fecha, String total,String metodo) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
        this.metodo = metodo;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTotal() {
        return total;
    }

    public String getMetodo() {
        return metodo;
    }
    
    
}
