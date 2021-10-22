package videoclub.dao.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pelicula extends Documental {

    private List<String> actores;

    public Pelicula(String titulo, int cantidad, String genero, FormatoPelicula formato, String director, String duracion) {
        super(titulo, cantidad, genero, formato, director, duracion);
        this.actores = new ArrayList<>();
    }

    public void addActor(String nombre) {
        this.actores.add(nombre);
    }

    public List<String> getActores() {
        return actores;
    }

    @Override
    public String toString() {
        return "Pelicula: (" + super.toString() + ")" + "actores: " + actores.size();
    }
}

