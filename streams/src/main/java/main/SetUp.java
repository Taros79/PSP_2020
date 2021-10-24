package main;

import com.github.javafaker.Faker;
import pedidos.dao.modelo.Cliente;
import pedidos.dao.modelo.Cuenta;
import pedidos.dao.modelo.PedidoCompuesto;
import pedidos.dao.modelo.PedidoSimple;
import pedidos.servicios.ServiciosPedido;
import videoclub.dao.modelo.*;
import videoclub.servicios.ServiciosVideoclub;

import java.time.LocalDateTime;
import java.util.Random;

public class SetUp {

    Random r = new Random();
    Faker f = new Faker();
    ServiciosPedido sp = new ServiciosPedido();
    ServiciosVideoclub sv = new ServiciosVideoclub();

    public void setupClienteClientes() {
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

    public void setupPedidosSimples() {
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
                    pedidos.dao.modelo.Producto producto = sp.todosProductos().get(r.nextInt(sp.todosProductos().size()));
                    sp.addLineaPedidoAPedidoSimple(producto, cantidad, pedidoSimple);
                }
                sp.cobrarPedidos();
            }

        }

    }

    public void setupSocioSocios() {
        for (int i = 0; i < 30; i++) {
            String nif = f.pokemon().name();
            String nombre = f.gameOfThrones().character();
            String direccion = f.gameOfThrones().city();
            String telefono = f.phoneNumber().phoneNumber();
            int edad = f.number().numberBetween(0, 100);
            Socio socio = new Socio(nif, nombre, direccion, telefono, edad);
            sv.addSocio(socio);
        }
    }

    public void setupProductos() {
        for (int i = 0; i < 30; i++) {
            String titulo = f.dragonBall().character();
            int cantidad = f.number().numberBetween(0, 100);
            String genero = f.funnyName().name();
            String director = f.artist().name();
            String duracion = ("Demasiado");
            int numeroActores = r.nextInt(50);
            boolean opcion = r.nextBoolean();
            FormatoPelicula formato;
            if (opcion) {
                formato = FormatoPelicula.DVD;
            } else {
                formato = FormatoPelicula.VIDEO;
            }
            Pelicula pelicula = new Pelicula(titulo, cantidad, genero, formato, director, duracion);
            for (int j = 0; j < numeroActores; j++) {
                ((Pelicula) pelicula).addActor(f.leagueOfLegends().champion());
            }
            sv.addProducto(pelicula);
            int nota = r.nextInt(10);
            Encuesta encuesta = new Encuesta(nota, true);
            pelicula.getEncuestas().add(encuesta);
        }

        for (int i = 0; i < 30; i++) {
            String titulo = f.animal().name();
            int cantidad = f.number().numberBetween(0, 100);
            String genero = f.funnyName().name();
            String director = f.artist().name();
            String duracion = ("Lo suficiente");
            boolean opcion = r.nextBoolean();
            FormatoPelicula formato;
            if (opcion) {
                formato = FormatoPelicula.DVD;
            } else {
                formato = FormatoPelicula.VIDEO;
            }
            Documental documental = new Documental(titulo, cantidad, genero, formato, director, duracion);
            sv.addProducto(documental);
            int nota = r.nextInt(10);
            Encuesta encuesta = new Encuesta(nota, true);
            documental.getEncuestas().add(encuesta);
        }

        for (int i = 0; i < 30; i++) {
            String titulo = f.howIMetYourMother().character();
            int cantidad = f.number().numberBetween(0, 100);
            String genero = f.funnyName().name();
            String fabricante = f.friends().character();
            Videojuego videojuego = new Videojuego(titulo, cantidad, genero, fabricante);
            sv.addProducto(videojuego);
            int nota = r.nextInt(10);
            Encuesta encuesta = new Encuesta(nota, true);
            videojuego.getEncuestas().add(encuesta);
        }
    }

    public void setAlquiler() {
        for (int i = 0; i < 100; i++) {
            Socio socio = sv.getTodosSocios().get(r.nextInt(sv.getTodosSocios().size()));
            Producto producto = sv.getTodosProductos().get(r.nextInt(sv.getTodosProductos().size() - 75));

            Alquiler alquiler = new Alquiler(LocalDateTime.MIN, socio, producto);
            producto.setCantidadAlquilada(producto.getCantidadAlquilada() + 1);
            producto.setCantidad(producto.getCantidad() - 1);
            sv.addAlquiler(alquiler);
        }
    }
}
