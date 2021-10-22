package pedidos.gui;

import pedidos.dao.modelo.*;
import pedidos.servicios.ServiciosPedido;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Faker f = new Faker();
        ServiciosPedido sp = new ServiciosPedido();
        Scanner sc = new Scanner(System.in);
        setup(sp, f);
        System.out.println(sp.getClientePorEmail("1@1.com"));
        int opcion;
        do {
            do {
                System.out.println("¿Qué desea hacer? \n" +
                        "1. Registrar un Cliente \n" +
                        "2. Crear nueva cuenta de credito \n" +
                        "3. Ingresar saldo \n" +
                        "4. Borrar un Cliente \n" +
                        "5. Añadir un producto nuevo \n" +
                        "6. Actualizar el Stock de un producto existente \n" +
                        "7. Realizar un nuevo pedido \n" +
                        "8. Comprobar sus pedidos \n" +
                        "9. Cobrar pedidos pendientes \n" +
                        "10. Enviar y entregar pedidos cobrados\n" +
                        "11. Salir");
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 11) {
                    System.out.println("Por favor, dime una de las opciones del menu." +
                            "Vuelvo a mostrartelo.");
                }
            } while (opcion < 1 || opcion > 11);
            switch (opcion) {
                case 1:
                    addClienteNuevo(f, sp);
                    break;
                case 2:
                    crearCuenta(sp, f, sc);
                    break;
                case 3:
                    addSaldo(sp, sc);
                    break;
                case 4:
                    deleteCliente(sp, sc);
                    break;
                case 5:
                    addProducto(sp, f);
                    break;
                case 6:
                    updateStock(sp, sc);
                    break;
                case 7:
                    addPedido(sp, sc);
                    break;
                case 8:
                    pedidosPorCliente(sp, sc);
                    break;
                case 9:
                    cobrarPedidos(sp);
                    break;
                case 10:
                    enviarYEntregar(sp);
                    break;
            }
        } while (opcion != 11);

    }

    public static void setup(ServiciosPedido sp, Faker f) {
        Cliente cliente1 = new Cliente(f.gameOfThrones().character(), f.gameOfThrones().city(), f.phoneNumber().toString(), "1@1.com");
        sp.addCliente(cliente1);
        sp.addCuentaACliente("1@1.com", f);
    }

    public static void addClienteNuevo(Faker f, ServiciosPedido sp) {
        String nombre = f.gameOfThrones().character();
        String direccion = f.gameOfThrones().city();
        String tel = f.phoneNumber().toString();
        String email = f.animal().name();
        System.out.println("Este es el correo del nuevo cliente: " + email);
        Cliente cliente = new Cliente(nombre, direccion, tel, email);
        if (sp.addCliente(cliente)) {
            System.out.println("Cliente registrado correctamente");
        } else {
            System.out.println("Se ha producido un error y no se ha podido registrar");
        }
    }

    public static void crearCuenta(ServiciosPedido sp, Faker f, Scanner sc) {
        System.out.println("Dime el email de tu cuenta para poder crearte una nueva cuenta de credito");
        String email = sc.nextLine();
//        if (sp.addCuentaACliente(email, f)) {
//            System.out.println("Ahora tus cuentas son: ");
//            imprimirCuentas(sp.getClientePorEmail(email).getCuentas());
//        } else {
//            System.out.println("Este email no esta asociado a ningun cliente registrado");
//        }
    }

    public static void addSaldo(ServiciosPedido sp, Scanner sc) {
        System.out.println("Dime tu email para comprobar ingresar saldo a tus cuentas");
        String email = sc.nextLine();
        Cliente cliente = sp.getClientePorEmail(email);
        Cuenta cuenta;
        if (cliente != null) {
            cuenta = elegirCuenta(sp, sc, cliente);
            if (cuenta != null) {
                System.out.println("¿Cuanto dinero quieres ingresar en esta cuenta?");
                int masSaldo = sc.nextInt();
                sc.nextLine();
                cuenta.setSaldo(cuenta.getSaldo() + masSaldo);
                System.out.println("Tu cuenta ha quedado asi: " + cuenta);
            }
        }

    }

    public static Cuenta elegirCuenta(ServiciosPedido sp, Scanner sc, Cliente cliente) {
        List<Cuenta> cuentas = sp.cuentasCliente(cliente);
        if (cuentas.size() > 0) {
            imprimirCuentas(cuentas);
            System.out.println("¿Que cuenta quieres elegir?");
            int indice = sc.nextInt() - 1;
            sc.nextLine();
            return cliente.getCuentas().get(indice);
        } else {
            System.out.println("Este cliente no tiene ninguna cuenta");
        }
        return null;
    }

    public static void imprimirCuentas(List<Cuenta> cuentas) {
        for (Cuenta cuenta : cuentas) {
            System.out.println((cuentas.indexOf(cuenta) + 1) + ". " + cuenta);
        }
    }

    public static void deleteCliente(ServiciosPedido sp, Scanner sc) {
        System.out.println("Dime tu email para proceder a darte de baja");
        String email = sc.nextLine();
        if (sp.deleteCliente(email) != null) {
            System.out.println("Cliente eliminado");
        } else {
            System.out.println("Este email no esta asociado a ningun cliente registrado");
        }
    }

    public static void addProducto(ServiciosPedido sp, Faker f) {
        Producto producto = new Producto(f.beer().name(), f.number().numberBetween(10, 100), f.number().numberBetween(1, 4));
        System.out.println("Producto a añadir: " + producto);
        if (sp.addProducto(producto)) {
            System.out.println("Producto registrado correctamente");
        } else {
            System.out.println("No se ha podido registrar el producto en el sistema");
        }
    }

    public static void updateStock(ServiciosPedido sp, Scanner sc) {
        Producto producto = elegirProducto(sp, sc);
        if (producto != null) {
            System.out.println("¿Cuantas unidades nuevas han llegado?");
            int unidadesRecibidas = sc.nextInt();
            sc.nextLine();
            producto.setStock(producto.getStock() + unidadesRecibidas);
        }
    }

    public static Producto elegirProducto(ServiciosPedido sp, Scanner sc) {
        List<Producto> productos = sp.todosProductos();
        if (productos.size() > 0) {
            System.out.println("Dime que producto de la lista quieres");
            for (Producto producto : productos) {
                System.out.println((productos.indexOf(producto) + 1) + ". " + producto);
            }
            int indice = sc.nextInt() - 1;
            sc.nextLine();
            return productos.get(indice);
        } else {
            System.out.println("Actualmente no hay productos en stock");
        }
        return null;
    }

    public static void addPedido(ServiciosPedido sp, Scanner sc) {
        System.out.println("Dime tu email para empezar con el pedido");
        String email = sc.nextLine();
        Cliente cliente = sp.getClientePorEmail(email);
        if (cliente != null && sp.dineroCuenta(cliente)) {
            int masProductos = 0;
            int masPedidos = 0;
            int cantidadTotal = 0;
            PedidoCompuesto pedido = new PedidoCompuesto(cliente);
            do {
                System.out.println("Va a iniciar un pedido simple que sera incluido a su pedido final" +
                        "\nRecuerde que si supera las 20 unidades en total no se añadira el ultimo producto que haya intentado añadir");
                System.out.println("Debe elegir una de sus cuentas, por favor");
                Cuenta cuenta = elegirCuenta(sp, sc, cliente);
                if (cuenta != null) {
                    PedidoSimple pedidoSimple = new PedidoSimple(cuenta);
                    do {
                        Producto producto = elegirProducto(sp, sc);
                        if(producto != null) {
                            System.out.println("¿Cuantas unidades quiere?");
                            int cantidad = sc.nextInt();
                            sc.nextLine();
                            cantidadTotal += cantidad;
                            if (!sp.addLineaPedidoAPedidoSimple(producto, cantidad, pedidoSimple)) {
                                System.out.println("Actualmente no disponemos de suficiente stock de este producto");
                            }
                            System.out.println("¿Quiere algun otro producto?" +
                                    "\n1. SI" +
                                    "\n2. NO");
                            masProductos = sc.nextInt();
                            sc.nextLine();
                        }
                    } while (masProductos == 1 && cantidadTotal < 20);
                    if (cantidadTotal >= 20 && masProductos == 1) {
                        System.out.println("¿Quiere seguir con un nuevo pedido simple?" +
                                "\n1. SI" +
                                "\n2. NO");
                        masPedidos = sc.nextInt();
                        sc.nextLine();
                    } else {
                        masPedidos = 2;
                    }
                    if (pedidoSimple.getLineasPedido().size() > 0) {
                        sp.addPedidoSimpleAPedido(pedidoSimple, pedido);
                        sp.addPedidoAPedidos(pedido);
                    }
                    cantidadTotal = 0;
                }
            } while (masPedidos == 1);
            sp.setTotalPrecio(pedido);
            System.out.println("Este es el pedido que ha realizado: \n" + pedido);
        } else {
            System.out.println("Sentimos informarle de que este cliente no esta registrado o no tiene ninguna cuenta con dinero");
        }
    }

    public static void pedidosPorCliente(ServiciosPedido sp, Scanner sc) {
        System.out.println("Dime tu correo para mostrarte todos los pedidos que has realizado con nosotros");
        String email = sc.nextLine();
        List<PedidoCompuesto> pedidos = sp.getPedidosPorCliente(email);
        for (PedidoCompuesto pc : pedidos) {
            System.out.println((pedidos.indexOf(pc) + 1) + ". " + pc);
        }
    }

    public static void cobrarPedidos(ServiciosPedido sp) {
        List<PedidoCompuesto> pedidosPendCobro = sp.getPedidosPorEstado(Estado.PENDIENTECOBRO);
        System.out.println("Estos son los pedidos que hay pendientes de cobro: \n" +
                pedidosPendCobro +
                "\nSe comprobara que se puedan cobrar y se pasara a su cobro o rechazo\n");
        if (sp.cobrarPedidos()) {
            System.out.println("Se han cobrado y anulado los pedidos correspondientes");
        } else {
            System.out.println("Se ha producido un error, algun pedido no se ha podido cobrar");
        }
        System.out.println("Los pedidos han quedado asi: \n" +
                "Cobrados: \n" +
                sp.getPedidosPorEstado(Estado.COBRADO) +
                "\nRechazados: \n" +
                sp.getPedidosPorEstado(Estado.RECHAZADO));
    }

    public static void enviarYEntregar(ServiciosPedido sp) {
        if (sp.enviarPedidos()) {
            System.out.println("Pedidos enviados");
            System.out.println(sp.getPedidosPorEstado(Estado.REPARTO));
            if (sp.entregarPedidos()) {
                System.out.println("\nPedidos entregados");
                System.out.println(sp.getPedidosPorEstado(Estado.ENTREGADO));
            } else {
                System.out.println("Algun pedido no ha podido ser entregado");
            }
        } else {
            System.out.println("Alguno de los pedidos no ha sido enviado");
        }
    }
}
