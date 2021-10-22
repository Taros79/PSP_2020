package pedidos.dao.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidoSimple {
    private Cuenta cuenta;
    private int total;
    private List<LineaPedido> lineasPedido;

    public PedidoSimple(Cuenta cuenta) {
        this.cuenta = cuenta;
        this.lineasPedido = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\n- PedidoSimple{" +
                "cuenta=" + cuenta +
                ", lineasPedido=" + lineasPedido +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoSimple that = (PedidoSimple) o;
        return Objects.equals(cuenta, that.cuenta) &&
                Objects.equals(lineasPedido, that.lineasPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuenta, lineasPedido);
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

}
