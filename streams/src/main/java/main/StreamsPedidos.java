package main;

import pedidos.dao.modelo.LineaPedido;
import pedidos.dao.modelo.PedidoCompuesto;
import pedidos.servicios.ServiciosPedido;

import java.util.List;
import java.util.stream.Collectors;

public class StreamsPedidos {

    ServiciosPedido sp = new ServiciosPedido();
    List<PedidoCompuesto> pedidoCompuesto = sp.getTodosPedidos();

    // un map con nombre de producto y cantidad de veces pedido
    public void productosAgrupadosPorCantidadDeVecesPedidos() {
        System.out.println(
                pedidoCompuesto.stream()
                        .flatMap(pedido -> pedido.getPedidosSimples().stream())
                        .flatMap(pedidoSimple -> pedidoSimple.getLineasPedido().stream())
                        .map(LineaPedido::getProducto)
                        .collect(Collectors.groupingBy(
                                producto -> pedidoCompuesto.stream()
                                        .flatMap(pedidoCompuesto -> pedidoCompuesto.getPedidosSimples().stream())
                                        .flatMap(pedidoSimple -> pedidoSimple.getLineasPedido().stream())
                                        .filter(lp -> lp.getProducto().equals(producto)).count())));
    }

    public void clienteQueMasDineroSehaGastado() {
        System.out.println(
                pedidoCompuesto.stream()
                        .flatMap(pedido -> pedido.getPedidosSimples().stream())
                        .flatMap(pedidoSimple -> pedidoSimple.getLineasPedido().stream())
                        .map(LineaPedido::getPrecioTotal).collect(Collectors.toList()));
    }


    // La cantidad media de producto por pedido simple, sumando todas las lineas de pedido de cada simple
    public void lacantidadMediaPedidaDeCadaProductoEnCadaPedidoCompuesto() {
        System.out.println(
                pedidoCompuesto.stream()
                        .flatMap(pedido -> pedido.getPedidosSimples().stream())
                        .flatMap(pedidoSimple -> pedidoSimple.getLineasPedido().stream())
                        .mapToInt(pedido -> pedido.getCantidad())
                        .average().getAsDouble()
        );
    }


    public void pedidoSimpleConMasLineasdePedido() {
        System.out.println(
                pedidoCompuesto.stream()
                        .flatMap(pedido -> pedido.getPedidosSimples().stream())
                        .reduce((pedidoSimple, pedidoSimple2) -> pedidoSimple.getLineasPedido().size()
                                >= pedidoSimple2.getLineasPedido().size() ? pedidoSimple : pedidoSimple2));
    }


    public void todoelDineroFacturadoEnTotalentodosLosPedidos() {
        System.out.println(
                pedidoCompuesto.stream()
                        .flatMap(pedido -> pedido.getPedidosSimples().stream())
                        .flatMap(pedidoSimple -> pedidoSimple.getLineasPedido().stream())
                        .mapToInt(LineaPedido::getPrecioTotal)
                        .sum()

        );

    }

    public void setFacturaTotal() {

    }

}