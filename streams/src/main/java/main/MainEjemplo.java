package main;

import com.github.javafaker.Faker;
import pedidos.dao.modelo.Cliente;
import pedidos.servicios.ServiciosPedido;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public class MainEjemplo {

  public static void main(String[] args) {


    setupClienteCuentas();
    ServiciosPedido sp = new ServiciosPedido();

    List<Cliente> clientes = sp.getTodosClientes();
    // Cliente con mas cuentas
    clientes.stream().reduce((cliente, cliente2) -> cliente.getCuentas().size() >= cliente2.getCuentas().size() ? cliente : cliente2).get();

    // Clientes agrupados por el numero de cuentas


    // Cliente con la suma del saldo de todas sus cuentas.


    // Clientes que tienen mas cuentas o iguales a la media.
    final double media = clientes.stream().mapToInt(value -> value.getCuentas().size()).average().getAsDouble();

    System.out.println(media);
    System.out.println(clientes.stream()
        .filter(cliente -> cliente.getCuentas().size() >= media)
        .collect(Collectors.toList()).size());



    double mediaCuentas = clientes.stream()
        .flatMap(cliente -> cliente.getCuentas().stream())
        .mapToInt(value -> value.getSaldo())
        .average().orElse(0);
    System.out.println(mediaCuentas);

    //clientes que tienen todas las cuentas mayores a la media de dinero en cuenta.
    clientes.stream().filter(cliente -> !cliente.getCuentas().isEmpty()
        && cliente.getCuentas().stream().allMatch(cuenta -> cuenta.getSaldo() >= mediaCuentas))
        .forEach(System.out::println);

    //numero de clientes según dominio del correo
    System.out.println(clientes.stream().collect(Collectors.groupingBy(cliente -> cliente.getEmail().substring(cliente.getEmail().indexOf("@")+1),counting())).toString());


  }

  private static void setupClienteCuentas() {
    Faker f = new Faker();
    ServiciosPedido sp = new ServiciosPedido();

    for (int i = 0; i < 100; i++) {
      String nombre = f.gameOfThrones().character();
      String direccion = f.gameOfThrones().city();
      String tel = f.phoneNumber().toString();
      String email = f.internet().emailAddress();
      Cliente cliente = new Cliente(nombre, direccion, tel, email);
      sp.addCliente(cliente);
      Random r = new Random();
      int numeroCuentas = r.nextInt(100);
      for (int j = 0; j < numeroCuentas; j++) {
        sp.addCuentaACliente(email, f).setSaldo(r.nextInt(100) + 100);
      }
    }
  }

  private static void setupProductos(){

  }


  private static void setPedidos(){

  }
}
