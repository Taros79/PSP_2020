package pedidos.dao;

import pedidos.dao.modelo.*;

import java.util.ArrayList;
import java.util.List;

public class DaoPedidos {
    private static List<PedidoCompuesto> pedidos = new ArrayList<>();

    public List<PedidoCompuesto> getTodosPedidos() {
        return pedidos;
    }

    public boolean addPedidoCompuesto(PedidoCompuesto pedidoCompuesto){
        return pedidos.add(pedidoCompuesto);
    }

    public boolean addPedidoSimple(PedidoCompuesto pedidoCompuesto,PedidoSimple pedidoSimple) {
        return pedidoCompuesto.getPedidosSimples().add(pedidoSimple);
    }

    public void addLineaPedido(LineaPedido lineaPedido, PedidoSimple pedidoSimple) {
        pedidoSimple.getLineasPedido().add(lineaPedido);
    }

    public List<PedidoCompuesto> getPedidosPorCliente(Cliente cliente){
        List<PedidoCompuesto> pedidosCliente = new ArrayList<>();
        for(PedidoCompuesto pc : pedidos){
            if(pc.getCliente().equals(cliente)){
                pedidosCliente.add(pc);
            }
        }
        return pedidosCliente;
    }

    public List<PedidoCompuesto> getPedidoPorEstado(Estado estado){
        List<PedidoCompuesto> pedidosEstado = new ArrayList<>();
        for(PedidoCompuesto pc : pedidos){
            if(pc.getEstado().equals(estado)){
                pedidosEstado.add(pc);
            }
        }
        return pedidosEstado;
    }
}
