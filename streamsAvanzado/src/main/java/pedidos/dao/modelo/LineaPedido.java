package pedidos.dao.modelo;

import java.util.Objects;

public class LineaPedido {
    private Producto producto;
    private int cantidad;
    private int precioTotal;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = this.cantidad * this.producto.getPrecio();
    }

    @Override
    public String toString() {
        return "\n      - LineaPedido{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioTotal=" + precioTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaPedido that = (LineaPedido) o;
        return cantidad == that.cantidad &&
                precioTotal == that.precioTotal &&
                Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, cantidad, precioTotal);
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

}
