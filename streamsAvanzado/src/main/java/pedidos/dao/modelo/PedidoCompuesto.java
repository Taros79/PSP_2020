package pedidos.dao.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidoCompuesto {
    private Estado estado;
    private Cliente cliente;
    private int totalFactura;
    private List<PedidoSimple> pedidosSimples;

    public PedidoCompuesto(Cliente cliente) {
        this.estado = Estado.PENDIENTECOBRO;
        this.cliente = cliente;
        this.pedidosSimples = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\nPedidoCompuesto{" +
                "estado=" + estado +
                ", total=" + totalFactura +
                ", \npedido=" + pedidosSimples +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoCompuesto that = (PedidoCompuesto) o;
        return estado == that.estado &&
                Objects.equals(cliente, that.cliente) &&
                Objects.equals(pedidosSimples, that.pedidosSimples);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estado, cliente, pedidosSimples);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(int totalFactura) {
        this.totalFactura = totalFactura;
    }

    public List<PedidoSimple> getPedidosSimples() {
        return pedidosSimples;
    }
}
