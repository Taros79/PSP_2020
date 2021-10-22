package main;

import java.util.List;
import java.util.stream.Collectors;
import pedidos.dao.modelo.Producto;
import pedidos.servicios.ServiciosPedido;

public class StreamsProductos {

    ServiciosPedido sp = new ServiciosPedido();

    List<Producto> productos = sp.todosProductos();

    // con reduce y con sorted
    public void productoMasCaro() {
        System.out.println(
                productos.stream()
                        .reduce((producto, producto2) -> producto.getPrecio() >= producto2.getPrecio() ? producto : producto2)
                        .get());
    }

    //con reduce y con sorted
    public void productoMasBarato() {
        System.out.println(
                productos.stream()
                        .reduce((producto, producto2) -> producto.getPrecio() >= producto2.getPrecio() ? producto2 : producto)
                        .get());
    }

    public void mediaPrecioTodosLosProductos() {
        double mediaProductos = productos.stream()
                .mapToInt(value -> value.getPrecio())
                .average().getAsDouble();
        System.out.println(mediaProductos);
    }

    // ****  % un grupo de producto por cada franja de 10, de 0 a 10, 11 a 20, etc...
    public void productosAgrupadosPorRangoPrecio10en10() {
        System.out.println(
        productos.stream()
                .collect(Collectors.groupingBy(producto -> {
                    String solucion = "";
                    int centenisha =  producto.getPrecio() / 10;
                    solucion = centenisha + "_10";
                    return solucion;
                })).toString());
    }

    // de los productos que tenga precio de 11 a 20, indicar cuales tienen stock mayor que 5
    public void productosConPrecio11a20YStockMayor5() {
       System.out.println(productos.stream()
               .filter((producto) -> producto.getPrecio() < 20 && producto.getPrecio() > 10)
               .filter((producto) -> producto.getStock() > 5)
               .collect(Collectors.toList()));
    }
}
