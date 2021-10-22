package main;

import com.github.javafaker.Faker;
import pedidos.dao.modelo.*;
import pedidos.servicios.ServiciosPedido;

import java.util.Random;
import java.util.Scanner;

public class MainProdutosPractica {

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
                        "6. Salir");
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 6) {
                    System.out.println("Por favor, dime una de las opciones del menu." +
                            "Vuelvo a mostrartelo.");
                }
            } while (opcion < 1 || opcion > 7);
            switch (opcion) {
                case 1:
                    System.out.println("PRODUCTO MAS CARO");
                    streamProductos.productoMasCaro();
                    break;
                case 2:
                    System.out.println("PRODUCTO MAS BARATO");
                    streamProductos.productoMasBarato();
                    break;
                case 3:
                    System.out.println("MEDIA PRECIOS DE TODOS LOS PRODUCTOS");
                    System.out.println("La media es ");
                    streamProductos.mediaPrecioTodosLosProductos();
                    break;
                case 4:
                    System.out.println("PRODUCTOS AGRUPADOS POR RANGO");
                    streamProductos.productosAgrupadosPorRangoPrecio10en10();
                    break;
                case 5:
                    System.out.println("PRODUCTOS CON PRECIO Y STOCK MAYOR");
                    streamProductos.productosConPrecio11a20YStockMayor5();
                    break;
            }
        } while (opcion != 6);
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
