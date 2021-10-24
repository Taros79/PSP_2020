package main;

import com.github.javafaker.Faker;
import pedidos.dao.modelo.Cliente;
import pedidos.dao.modelo.Cuenta;
import pedidos.dao.modelo.Producto;
import pedidos.servicios.ServiciosPedido;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
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
        setupPedidosSimples(sp, r);

//        sp.getTodosPedidos().forEach(System.out::println);

        StreamsPedidos streamPedidos = new StreamsPedidos();

        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            do {
                System.out.println("¿Qué desea hacer? \n" +
                        "1. Ejercicio 1 \n" +
                        "2. Ejercicio 2 \n" +
                        "3. Ejercicio 3 \n" +
                        "4. Ejercicio 4 \n" +
                        "5. Ejercicio 5 \n" +
                        "6. Ejercicio 6 \n" +
                        "7. Salir");
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 7) {
                    System.out.println("Por favor, dime una de las opciones del menu." +
                            "Vuelvo a mostrartelo.");
                }
            } while (opcion < 1 || opcion > 8);
            switch (opcion) {
                case 1:
                    System.out.println("PRODUCTOS AGRUPADOS POR VECES PEDIDOS");
                    streamPedidos.productosAgrupadosPorCantidadDeVecesPedidos();
                    break;
                case 2:
                    System.out.println("CLIENTE QUE MAS DINERO HA GASTADO");
                    streamPedidos.clienteQueMasDineroSehaGastado();
                    break;
                case 3:
                    System.out.println("MEDIA PEDIDA DE CADA PRODUCTO");
                    streamPedidos.lacantidadMediaPedidaDeCadaProductoEnCadaPedidoCompuesto();
                    break;
                case 4:
                    System.out.println("PEDIDO SIMPLE CON MAS LINEAS DE PEDIDO");
                    streamPedidos.pedidoSimpleConMasLineasdePedido();
                    break;
                case 5:
                    System.out.println("TODO EL DINERO FACTURADO EN TODOS LOS PEDIDOS");
                    streamPedidos.todoelDineroFacturadoEnTotalentodosLosPedidos();
                    break;
                case 6:
                    System.out.println("FACTURA TOTAL");
                    streamPedidos.setFacturaTotal();
                    break;
            }
        } while (opcion != 7);
    }

    private static void setupClienteClientes(ServiciosPedido sp, Faker f) {

        for (int i = 0; i < 20; i++) {
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
        for (int i = 0; i < 20; i++) {
            String nombre = f.dragonBall().character();
            int stock = f.number().numberBetween(100, 300);
            int precio = f.number().numberBetween(10, 300);
            Producto producto = new Producto(nombre, stock, precio);
            sp.addProducto(producto);
        }
    }

    private static void setupPedidosSimples(ServiciosPedido sp, Random r) {
        for (int i = 0; i < 20; i++) {
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
