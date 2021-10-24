package main;

import videoclub.dao.modelo.*;
import videoclub.servicios.ServiciosVideoclub;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public class StreamsVideoClub {

    ServiciosVideoclub sv = new ServiciosVideoclub();

    List<Socio> socios = sv.getTodosSocios();
    List<Producto> productos = sv.getTodosProductos();

    public void numeroSociosSancionados() {
        System.out.println(socios.stream().filter(Socio::isSancionado)
                .collect(Collectors.groupingBy(Socio::isSancionado, counting())));
    }

    public void mediaEdadDeSociosSancionados() {
        double mediaEdadSancionados = socios.stream().filter(Socio::isSancionado)
                .mapToInt(Socio::getEdad).average().getAsDouble();
        System.out.println(mediaEdadSancionados);
    }

    /**
     * DIFICIL *
     */
    public void listaDiezProductosMasAlquilados() {
        System.out.println(productos.stream()
                .sorted(Comparator.comparing(producto -> ((Producto) producto).getCantidadAlquilada())
                        .reversed())
                .limit(10)
                .collect(Collectors.toList()));
    }

    // numero de Peliculas Alquiladas, de Documentales y Videojuegos.
    public void numeroProductosAlquiladosPorTipo() {
        System.out.println(
                productos.stream()
                        .filter(item -> item.getCantidadAlquilada() > 0)
                        .collect(Collectors.groupingBy(item -> item instanceof Pelicula, counting()))
        );
        
    }

    public void todosLosActoresDistintosDeTodasLasPeliculas() {
        System.out.println(
                productos.stream()
                        .filter(producto -> producto instanceof Pelicula)
                        .map(producto -> (Pelicula) producto)
                        .flatMap(producto -> producto.getActores().stream())
                        .distinct()
                        .collect(Collectors.toList())
        );
    }

    public void peliculaConMasActores() {
        System.out.println(
                productos.stream()
                        .filter(producto -> producto instanceof Pelicula)
                        .map(producto -> (Pelicula) producto)
                        .reduce((producto, producto2) -> producto.getActores().size() >= producto2.getActores().size() ? producto : producto2)
                        .get());
    }

    //el listado de productos ordenados de mayor a menor según su valoración media.
    public void productoConSuValoracionMediaOrdenadosDeMayoraMenor() {
        System.out.println(
                productos.stream()
                        .sorted(Comparator.comparing(producto -> ((Producto) producto).getValoracionMedia())
                                .reversed())
                        .collect(Collectors.toList())
        );
    }

    public void las10PeliculasMejorValoradas() {
       System.out.println(
        productos.stream()
               .filter(producto -> producto instanceof Pelicula)
               .sorted(Comparator.comparing(producto -> ((Producto) producto).getEncuestas().stream()
               .mapToInt(Encuesta::getNota).average().getAsDouble()).reversed())
                .limit(10)
               .collect(Collectors.toList()));
    }

    public void los10VideoJuegosMejorValoradas() {
        System.out.println(
        productos.stream()
               .filter(producto -> producto instanceof Videojuego)
               .sorted(Comparator.comparing(producto -> ((Producto) producto).getEncuestas().stream()
               .mapToInt(Encuesta::getNota).average().getAsDouble()).reversed())
                .limit(10)
               .collect(Collectors.toList()));
    }

    // el numero de DVD y Videos que hay.
    public void numeroDocumentalesyPeliculasSegunSuFormato() {

        System.out.println(productos.stream().filter(producto -> producto instanceof Documental)
                        .map(producto -> ((Documental) producto).getFormato())
                .collect(Collectors.groupingBy(Enum::name,Collectors.counting())));
    }

    // conseguir un String con todos los faricantes distintos de videojuegos separados por ,
    public void todosLosFabricantesDistintosDeVideoJuegosEnUnSoloString() {
        System.out.println(
                productos.stream()
                        .filter(producto -> producto instanceof Videojuego)
                        .map(producto -> (Videojuego) producto)
                        .map(Videojuego::getFabricante)
                        .distinct()
                        .collect(Collectors.joining(", "))
        );
    }

}
