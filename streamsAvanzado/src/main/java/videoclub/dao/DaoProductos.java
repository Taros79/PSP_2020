package videoclub.dao;

import videoclub.dao.modelo.Documental;
import videoclub.dao.modelo.Pelicula;
import videoclub.dao.modelo.Producto;
import videoclub.dao.modelo.Videojuego;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaoProductos {


    private static final List<Producto> productos = new ArrayList<>();

    public boolean addProducto(Producto producto) {
        boolean productoAdded = false;
        if (!productos.contains(producto)) {
            productos.add(producto);
            productoAdded = true;
        }
        return productoAdded;
    }

    public boolean borrarProducto(Producto producto) {
        return productos.remove(producto);
    }


    public List<Pelicula> getTodasPeliculas() {
        return productos.stream()
                .filter(producto -> producto instanceof Pelicula)
                .map(producto -> (Pelicula) producto)
                .collect(Collectors.toList());
    }

    public List<Videojuego> getTodosVideojuegos() {
        return productos.stream()
                .filter(producto -> producto instanceof Videojuego)
                .map(producto -> (Videojuego) producto)
                .collect(Collectors.toList());
    }

    public List<Documental> getTodosDocumentales() {
        return productos.stream()
                .filter(producto -> !(producto instanceof Pelicula)
                        && (producto instanceof Documental))
                .map(producto -> (Documental) producto)
                .collect(Collectors.toList());
    }
    
    public List<Producto> getTodosProductos() {
        return productos.stream().collect(Collectors.toList());
    }


}
