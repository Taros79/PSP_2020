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

        setupProductos(sp, f);

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

    private static void setupProductos(ServiciosPedido sp, Faker f) {
        for (int i = 0; i < 20; i++) {
            String nombre = f.dragonBall().character();
            int stock = f.number().numberBetween(100, 300);
            int precio = f.number().numberBetween(10, 300);
            Producto producto = new Producto(nombre, stock, precio);
            sp.addProducto(producto);
        }
    }
}
