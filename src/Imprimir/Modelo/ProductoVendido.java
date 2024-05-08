package Imprimir.Modelo;

public class ProductoVendido {
    private String id;
    private String nombre;
    private String unidades;
    private String precio;
    private String subtotal;
    private String nombreA;
    private String categoria;

    public ProductoVendido(String id, String nombre, String precio, String unidades, String subtotal, String nombreA, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.unidades = unidades;
        this.precio = precio;
        this.subtotal = subtotal;
        this.nombreA = nombreA;
        this.categoria = categoria;
    }

    public ProductoVendido(String id, String nombre, String precio, String unidades,  String subtotal, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.unidades = unidades;
        this.subtotal = subtotal;
        this.categoria = categoria;
    }
    
    public ProductoVendido(String id, String nombre, String precio, String unidades,  String subtotal) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.unidades = unidades;
        this.subtotal = subtotal;
    }

    public ProductoVendido(String nombre, String unidades, String precio, String subtotal) {
        this.nombre = nombre;
        this.unidades = unidades;
        this.precio = precio;
        this.subtotal = subtotal;
    }
    
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUnidades() {
        return unidades;
    }

    public String getPrecio() {
        return precio;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getNombreA() {
        return nombreA;
    }

    public String getCategoria() {
        return categoria;
    }

}
