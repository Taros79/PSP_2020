package main;

import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.Random;
import videoclub.dao.modelo.Alquiler;
import videoclub.dao.modelo.Documental;
import videoclub.dao.modelo.Encuesta;
import videoclub.dao.modelo.FormatoPelicula;
import videoclub.dao.modelo.Pelicula;
import videoclub.dao.modelo.Producto;
import videoclub.dao.modelo.Socio;
import videoclub.dao.modelo.Videojuego;
import videoclub.servicios.ServiciosVideoclub;

public class MainVideoclubPractica {

    public static void main(String[] args) {
        Random r = new Random();
        ServiciosVideoclub sv = new ServiciosVideoclub();
        Faker f = new Faker();
        setupSocioSocios(sv, f);

        System.out.println(sv.getTodosSocios());
        setupProductos(sv, f, r);
        setAlquiler(sv, r);
        
         StreamsVideoClub streamVideoclub = new StreamsVideoClub();

//        sv.getTodosProductos().forEach(System.out::println);
//        System.out.println("** ALQUILADOS **");
//        sv.getTodosAlquileres().forEach(System.out::println);

//        devolverAlquiler(sv, r);
//        System.out.println("** ALQUILERES RESTANTES **");
//        sv.getTodosAlquileres().forEach(System.out::println);

        //EJERCICIO 1
        System.out.println("NUMERO SOCIOS SANCIONADOS");
        for (int i = 0; i < 5; i++){
        sv.getTodosSocios().get(r.nextInt(sv.getTodosSocios().size())).setSancionado(true);
        }
        streamVideoclub.numeroSociosSancionados();
        
        //EJERCICIO 2
        System.out.println("MEDIA EDAD SOCIOS");
        streamVideoclub.mediaEdadDeSociosSancionados();
        
        //EJERCICIO 3
        System.out.println("LISTA PRODUCTOS MAS ALQUILADOS");
        for (int i = 0; i < 10; i++) {
        sv.getTodosProductos().get(i).setCantidadAlquilada(sv.getTodosProductos().get(i).getCantidad());
        }
        streamVideoclub.listaDiezProductosMasAlquilados();
        
        //EJERCICIO 4
        System.out.println("PRODUCTOS ALQUILADOS POR TIPO");
        streamVideoclub.numeroProductosAlquiladosPorTipo();
        
        //EJERCICIO 5
        System.out.println("ACTORES DISTINTOS");
        streamVideoclub.todosLosActoresDistintosDeTodasLasPeliculas();
        
        //EJERCICIO 6
        System.out.println("PELI CON MAS ACTORES");
        streamVideoclub.peliculaConMasActores();
        
        //EJERCICIO 7
        System.out.println("VALORACIONES MEDIAS DE MAYOR A MENOR");
        streamVideoclub.productoConSuValoracionMediaOrdenadosDeMayoraMenor();
        
        //EJERCICIO 8
        System.out.println("PELIS MEJOR VALORADAS");
        streamVideoclub.las10PeliculasMejorValoradas();
        
        //EJERCICIO 9
        System.out.println("JUEGOS MEJOR VALORADOS");
        streamVideoclub.los10VideoJuegosMejorValoradas();
        
        //EJERCICIO 11
        System.out.println("FABRICANTES DISTINTOS DE JUEGOS");
        streamVideoclub.todosLosFabricantesDistintosDeVideoJuegosEnUnSoloString();
        
      

    }

    private static void setupSocioSocios(ServiciosVideoclub sv, Faker f) {
        for (int i = 0; i < 100; i++) {
            String nif = f.pokemon().name();
            String nombre = f.gameOfThrones().character();
            String direccion = f.gameOfThrones().city();
            String telefono = f.phoneNumber().phoneNumber();
            int edad = f.number().numberBetween(0, 100);
            Socio socio = new Socio(nif, nombre, direccion, telefono, edad);
            sv.addSocio(socio);
        }
    }

    private static void setupProductos(ServiciosVideoclub sv, Faker f, Random r) {
        for (int i = 0; i < 100; i++) {
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

        for (int i = 0; i < 100 ; i++) {
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
        
        for (int i = 0; i < 100; i++) {
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
    
    private static void setAlquiler(ServiciosVideoclub sv, Random r) {
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

