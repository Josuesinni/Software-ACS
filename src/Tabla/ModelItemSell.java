
package Tabla;

import java.text.DecimalFormat;

public class ModelItemSell {

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ModelItemSell(String productName, int qty, double price, double total) {
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public ModelItemSell() {
    }

    private String productName;
    private int qty;
    private double price;
    private double total;

    public Object[] toTableRow(int rowNum) {
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return new Object[]{this, df.format(rowNum), productName, df.format(qty), "$ " + df.format(price), "$ " + df.format(total)};
    }
}
