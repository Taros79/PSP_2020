package videoclub.dao.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Producto {

    private String titulo;
    private List<Encuesta> encuestas;
    private int cantidad;
    private int cantidadAlquilada;
    private String genero;
    private double valoracionMedia;

    public Producto(String titulo, int cantidad, String genero) {
        this();
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.genero = genero;
    }

    public Producto() {
        Random r = new Random();
        encuestas = new ArrayList<>();
        this.cantidadAlquilada = 0;
        this.valoracionMedia = 0;
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", cantidad=" + cantidad +
                ", cantidadAlquilada=" + cantidadAlquilada +
                ", cantidadDisponible=" + (cantidad - cantidadAlquilada) +
                ", genero='" + genero+
                ", VALORACION_MEDIA='" + valoracionMedia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Encuesta> getEncuestas() {
        return encuestas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {

        this.cantidad = cantidad;
    }

    public int getCantidadAlquilada() {
        return cantidadAlquilada;
    }

    public void setCantidadAlquilada(int cantidadAlquilada) {
        this.cantidadAlquilada = cantidadAlquilada;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getValoracionMedia() {
        return valoracionMedia;
    }

    public void setValoracionMedia(double valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }
}
