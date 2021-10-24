package main;

import java.util.Scanner;

public class MainProdutosPractica {

    public static void main(String[] args) {
        SetUp setUp = new SetUp();
        setUp.setupProductos();

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
}
