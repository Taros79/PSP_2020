package main;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import pedidos.dao.modelo.Cliente;
import pedidos.dao.modelo.Cuenta;
import pedidos.servicios.ServiciosPedido;

public class StreamsClientes {

    ServiciosPedido sp = new ServiciosPedido();

    List<Cliente> clientes = sp.getTodosClientes();

    // Cliente con mas cuentas
    public void clienteConMasCuentas() {
        System.out.println(
                clientes.stream()
                        .reduce((cliente, cliente2) -> cliente.getCuentas().size() >= cliente2.getCuentas().size() ? cliente : cliente2)
                        .get());

    /*    System.out.println(
                clientes.stream()
                .sorted(Comparator.comparing(cliente -> ((Cliente) cliente).getCuentas().size()).reversed())
                .findFirst().get());*/
    }

    // Cliente + Numero de cuentas de cada cliente.
    public void clienteYNumeroCuentas() {
        clientes.stream().forEach(cliente -> System.out.println(cliente.getNombre() + " " + cliente.getCuentas().size()));
    }

    // Clientes agrupados por el numero de cuentas
    public void clientesAgrupadosPorNumeroCuentas() {
        System.out.println(clientes.stream().collect(Collectors.groupingBy(cliente -> cliente.getCuentas().size())));
    }

    // Clientes que tienen mas cuentas o iguales a la media.
    public void clientesConMasCuentasQuelaMedia() {
        final double media = clientes.stream().mapToInt(value -> value.getCuentas().size()).average().getAsDouble();

        System.out.println("La media de cuentas seria de " + media);
        System.out.println(clientes.stream()
                .filter(cliente -> cliente.getCuentas().size() >= media)
                .collect(Collectors.toList()).size());
    }

    // media de dinero de todas las cuentas
    public void mediaDineroTodasCuentas() {
        double mediaCuentas = clientes.stream()
                .flatMap(cliente -> cliente.getCuentas().stream())
                .mapToInt(Cuenta::getSaldo)
                .average().getAsDouble();
        System.out.println(mediaCuentas);
    }

    // Clientes ordenados por el saldo total.
    public void clientesOrdenadosPorSaldoTotal() {
        clientes.stream().sorted(Comparator
                        .comparingInt(cliente -> cliente.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum()))
                .forEach(System.out::println);
    }

    // Cliente con la suma del saldo de todas sus cuentas.
    public void clientesYSumaSaldoTodasCuentas() {
        clientes.stream().forEach(cliente
                -> System.out.println(cliente.getNombre() + " " + cliente.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum()));
    }

    // el cuarto cliente con más dinero
    public void cuartoClienteConMasDinero() {
 /*       clientes.stream()
                .sorted(Comparator
                        .comparingInt(cliente -> ((Cliente) cliente).getCuentas()
                                .stream().mapToInt(Cuenta::getSaldo).sum()).reversed())
                .skip(3)
                .findFirst().get();*/

        System.out.println(clientes.stream()
                .sorted((o1, o2) -> o2.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum()
                - o1.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum())
                .skip(3)
                .findFirst().get());
    }

    // numero de clientes agrupados por dominio del correo ya@gmail.com
    public void numeroClientesPorDominioCorreo() {
        System.out.println(clientes.stream().collect(
                Collectors.groupingBy(cliente -> cliente.getEmail().substring(cliente.getEmail().indexOf("@") + 1),
                         Collectors.counting())));
    }
}
