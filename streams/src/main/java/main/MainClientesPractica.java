package main;

import com.github.javafaker.Faker;
import pedidos.dao.modelo.*;
import pedidos.servicios.ServiciosPedido;

import java.util.Random;
import java.util.Scanner;

public class MainClientesPractica {

    public static void main(String[] args) {
        ServiciosPedido sp = new ServiciosPedido();
        Faker f = new Faker();

        setupClienteClientes(sp, f);
        sp.getTodosClientes().forEach(System.out::println);
        StreamsClientes streamsClientes = new StreamsClientes();

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
                        "7. Ejercicio 7 \n" +
                        "8. Ejercicio 8 \n" +
                        "9. Ejercicio 9 \n" +
                        "10. Salir");
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 10) {
                    System.out.println("Por favor, dime una de las opciones del menu." +
                            "Vuelvo a mostrartelo.");
                }
            } while (opcion < 1 || opcion > 11);
            switch (opcion) {
                case 1:
                    System.out.println("Cliente con mas cuentas");
                    streamsClientes.clienteConMasCuentas();
                    break;
                case 2:
                    System.out.println("Cliente + Numero de cuentas de cada cliente.");
                    streamsClientes.clienteYNumeroCuentas();
                    break;
                case 3:
                    System.out.println("Clientes agrupados por el numero de cuentas");
                    streamsClientes.clientesAgrupadosPorNumeroCuentas();
                    break;
                case 4:
                    System.out.println("Clientes que tienen mas cuentas o iguales a la media.");
                    streamsClientes.clientesConMasCuentasQuelaMedia();
                    break;
                case 5:
                    System.out.println("Media de dinero de todas las cuentas");
                    streamsClientes.mediaDineroTodasCuentas();
                    break;
                case 6:
                    System.out.println("Clientes ordenados por el saldo total.");
                    streamsClientes.clientesOrdenadosPorSaldoTotal();
                    break;
                case 7:
                    System.out.println("Cliente con la suma del saldo de todas sus cuentas.");
                    streamsClientes.clientesYSumaSaldoTodasCuentas();
                    break;
                case 8:
                    System.out.println("El cuarto cliente con más dinero");
                    streamsClientes.cuartoClienteConMasDinero();
                    break;
                case 9:
                    System.out.println("Numero de clientes agrupados por dominio del correo ya@gmail.com");
                    streamsClientes.numeroClientesPorDominioCorreo();
                    break;
            }
        } while (opcion != 10);
    }

    private static void setupClienteClientes(ServiciosPedido sp, Faker f) {

        for (int i = 0; i < 30; i++) {
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
}
