package main;

import com.github.javafaker.Faker;
import pedidos.dao.modelo.Cliente;
import pedidos.dao.modelo.Cuenta;
import pedidos.dao.modelo.Producto;
import pedidos.servicios.ServiciosPedido;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import pedidos.dao.modelo.LineaPedido;
import pedidos.dao.modelo.PedidoCompuesto;
import pedidos.dao.modelo.PedidoSimple;

public class MainPedidosPractica {

    public static void main(String[] args) {
        ServiciosPedido sp = new ServiciosPedido();
        Random r = new Random();
        Faker f = new Faker();

        setupClienteClientes(sp, f);
        setupProductos(sp, f);

//        sp.todosProductos().forEach(System.out::println);
//        sp.getTodosClientes().forEach(System.out::println);
        setupPedidosSimples(sp, r);
//        sp.getTodosPedidos().forEach(System.out::println);

        StreamsProductos streamProductos = new StreamsProductos();
        StreamsPedidos streamPedidos = new StreamsPedidos();

        //EJERCICIO 1
        System.out.println("PRODUCTO MAS CARO");
        streamProductos.productoMasCaro();

        //EJERCICIO 2
        System.out.println("PRODUCTO MAS BARATO");
        streamProductos.productoMasBarato();

        //EJERCICIO 3
        System.out.println("MEDIA PRECIOS DE TODOS LOS PRODUCTOS");
        System.out.println("La media es ");
        streamProductos.mediaPrecioTodosLosProductos();

        //EJERCICIO 4
        System.out.println("PRODUCTOS AGRUPADOS POR RANGO");
        streamProductos.productosAgrupadosPorRangoPrecio10en10();


        //EJERCICIO 5
        System.out.println("PRODUCTOS CON PRECIO Y STOCK MAYOR");
        streamProductos.productosConPrecio11a20YStockMayor5();

        //EJERCICIO 6 STREAMS PEDIDOS
        System.out.println("PRODUCTOS AGRUPADOS POR VECES PEDIDOS");
        streamPedidos.productosAgrupadosPorCantidadDeVecesPedidos();
        
        //EJERCICIO 8 STREAMS PEDIDOS
        System.out.println("MEDIA PEDIDA DE CADA PRODUCTO");
        streamPedidos.lacantidadMediaPedidaDeCadaProductoEnCadaPedidoCompuesto();
        
        //EJERCICIO 9 STREAMS PEDIDOS
        System.out.println("PEDIDO SIMPLE CON MAS LINEAS DE PEDIDO");
        streamPedidos.pedidoSimpleConMasLineasdePedido();

    }

    private static void setupClienteClientes(ServiciosPedido sp, Faker f) {

        for (int i = 0; i < 100; i++) {
            String nombre = f.gameOfThrones().character();
            String direccion = f.gameOfThrones().city();
            String tel = f.phoneNumber().phoneNumber();
            String email = f.internet().emailAddress();
            Cliente cliente = new Cliente(nombre, direccion, tel, email);
            sp.addCliente(cliente);
            Random r = new Random();
            int numeroCuentas = r.nextInt(10) + 1;
            for (int j = 0; j < numeroCuentas; j++) {
                sp.addCuentaACliente(email, f).setSaldo(r.nextInt(100) + 100);
            }
        }
    }

    private static void setupProductos(ServiciosPedido sp, Faker f) {
        for (int i = 0; i < 100; i++) {
            String nombre = f.dragonBall().character();
            int stock = f.number().numberBetween(100, 300);
            int precio = f.number().numberBetween(10, 300);
            Producto producto = new Producto(nombre, stock, precio);
            sp.addProducto(producto);
        }
    }

    private static void setupPedidosSimples(ServiciosPedido sp, Random r) {
        for (int i = 0; i < 100; i++) {
            Cliente cliente = sp.getTodosClientes().get(i);
            Cuenta cuenta = cliente.getCuentas().get(r.nextInt(cliente.getCuentas().size()));

            PedidoCompuesto pedidoCompuesto = new PedidoCompuesto(cliente);
            sp.addPedidoAPedidos(pedidoCompuesto);
            int randomizau = r.nextInt(10);

            for (int j = 0; j < randomizau; j++) {
                PedidoSimple pedidoSimple = new PedidoSimple(cuenta);
                sp.addPedidoSimpleAPedido(pedidoSimple, pedidoCompuesto);
                int randomizau2 = r.nextInt(10);
                for (int k = 0; k < randomizau2; k++) {
                    int cantidad = r.nextInt(5);
                    Producto producto = sp.todosProductos().get(r.nextInt(sp.todosProductos().size()));
                    sp.addLineaPedidoAPedidoSimple(producto, cantidad, pedidoSimple);
                }
                
                sp.cobrarPedidos();
            }

        }

    }

}
