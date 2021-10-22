package videoclub.servicios;

import videoclub.config.Configuration;
import videoclub.dao.DaoAlquileres;
import videoclub.dao.DaoProductos;
import videoclub.dao.DaoSocios;
import videoclub.dao.modelo.*;

import java.time.LocalDateTime;
import java.util.List;
import pedidos.dao.DaoClientes;
import pedidos.dao.modelo.Cliente;

public class ServiciosVideoclub {

    private Object timeUnit;

    // add socio
    public boolean addSocio(Socio socio) {
        DaoSocios daoSocios = new DaoSocios();
        return daoSocios.addSocio(socio);
    }

    // borrarSocio
    public boolean borrarSocio(String dni) {
        DaoSocios daoSocio = new DaoSocios();
        if (daoSocio.getSocioPorNif(dni) != null) {
            return daoSocio.deleteSocio(daoSocio.getSocioPorNif(dni));
        }
        return false;
    }

    public Socio getSocio(String nif) {
        DaoSocios daoSocio = new DaoSocios();
        return daoSocio.getSocioPorNif(nif);
    }

    public boolean addProducto(Producto producto) {
        DaoProductos daoProductos = new DaoProductos();
        return daoProductos.addProducto(producto);
    }

    public void actualizarStockProducto(Producto p, int cantidad) {
        p.setCantidad(p.getCantidad() + cantidad);
    }

    public boolean devolverProducto(String nifSocio, Encuesta e) {
        boolean devolucion = false;
        DaoAlquileres daoAlquileres = new DaoAlquileres();
        Alquiler alquiler = daoAlquileres.alquilerSocio(nifSocio);
        if (alquiler != null) {
            Producto producto = alquiler.getProductoAlquilado();
            producto.setCantidadAlquilada(producto.getCantidadAlquilada() - 1);
            //sancion --> comprobar si se pasa del tiempo
            int tiempoAlquiler;
            if (producto instanceof Documental) {
                tiempoAlquiler = Configuration.getDiasAlquilerPeliculas();
            } else {
                tiempoAlquiler = Configuration.getDiasAlquilerVideojuego();
            }
            if (!daoAlquileres.alquilerSocio(nifSocio).getFechaAlquiler().plusSeconds(tiempoAlquiler).isAfter(LocalDateTime.now())) {
                daoAlquileres.alquilerSocio(nifSocio).getSocio().setSancionado(true);
            }
            devolucion = daoAlquileres.borrarAlquiler(daoAlquileres.alquilerSocio(nifSocio));
            //añadir encuesta a producto
            producto.getEncuestas().add(e);
        }
        return devolucion;
    }

    public String alquilarProducto(Producto p, String nifSocio) {
        String alquilado = null;
        DaoAlquileres daoAlquileres = new DaoAlquileres();
        DaoSocios daoSocios = new DaoSocios();
        if (daoSocios.getSocioPorNif(nifSocio) == null) {
            alquilado = "Lo siento, pero aun no esta registrado.\n"
                    + "Debera primero registrarse para poder realizar un alquiler";
        } else if (daoSocios.getSocioPorNif(nifSocio).isSancionado()) {
            alquilado = "Esta sancionado proceda primero a pagar su multa.\n"
                    + "MUCHAS GRACIAS";
        } else if (daoAlquileres.alquilerSocio(nifSocio) != null) {
            alquilado = "Actualmente tiene un producto alquilado.\n"
                    + "Devuelvalo primero, por favor.";
        } else if ((p.getCantidad() - p.getCantidadAlquilada()) < 1) {
            alquilado = "Actualmente no tenemos este producto disponible.\n"
                    + "Disculpe las molestias";
        } else {
            p.setCantidadAlquilada(p.getCantidadAlquilada() + 1);
            Alquiler alquiler = new Alquiler(LocalDateTime.now(), daoSocios.getSocioPorNif(nifSocio), p);
            if (daoAlquileres.addAlquiler(alquiler)) {
                alquilado = "Producto alquilado correctamente\n"
                        + "MUCHAS GRACIAS";
            }
        }
        return alquilado;
    }

    public List<Pelicula> getTodasPeliculas() {
        DaoProductos daoProductos = new DaoProductos();
        return daoProductos.getTodasPeliculas();
    }

    public List<Documental> getTodosDocumentales() {
        DaoProductos daoProductos = new DaoProductos();
        return daoProductos.getTodosDocumentales();
    }

    public List<Videojuego> getTodosVideoJuegos() {
        DaoProductos daoProductos = new DaoProductos();
        return daoProductos.getTodosVideojuegos();
    }

    public List<Socio> getTodosSocios() {
        DaoSocios daoSocios = new DaoSocios();
        return daoSocios.getTodosSocios();
    }

    public List<Producto> getTodosProductos() {
        DaoProductos daoProductos = new DaoProductos();
        return daoProductos.getTodosProductos();
    }

    public void addAlquiler(Alquiler alquiler) {
        DaoAlquileres daoAlquiler = new DaoAlquileres();
        daoAlquiler.addAlquiler(alquiler);
    }

    public List<Alquiler> getTodosAlquileres() {
        DaoAlquileres daoAlquiler = new DaoAlquileres();
        return daoAlquiler.getTodosAlquileres();
    }

}
