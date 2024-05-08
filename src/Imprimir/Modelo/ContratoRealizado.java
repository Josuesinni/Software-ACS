package Imprimir.Modelo;

public class ContratoRealizado {
    String folio;
    String nombreAnfitrion;
    String aPartirDe;
    String tiempo;
    String total;

    public ContratoRealizado(String folio, String nombreAnfitrion, String aPartirDe, String tiempo, String total) {
        this.folio = folio;
        this.nombreAnfitrion = nombreAnfitrion;
        this.aPartirDe = aPartirDe;
        this.tiempo = tiempo;
        this.total = total;
    }

    public String getFolio() {
        return folio;
    }

    public String getNombreAnfitrion() {
        return nombreAnfitrion;
    }

    public String getaPartirDe() {
        return aPartirDe;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getTotal() {
        return total;
    }
    
}
