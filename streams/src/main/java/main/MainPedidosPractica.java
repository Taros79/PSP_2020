package main;

import java.util.Scanner;
public class MainPedidosPractica {

    public static void main(String[] args) {
        SetUp setUp = new SetUp();

        setUp.setupClienteClientes();
        setUp.setupProductos();
        setUp.setupPedidosSimples();

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

}
