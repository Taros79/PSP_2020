package videoclub.dao;

import java.util.ArrayList;
import videoclub.dao.modelo.Alquiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoAlquileres {


    // el nif como clave primaria
    private static final Map<String, Alquiler> alquileres = new HashMap<>();

    public boolean addAlquiler(Alquiler alquiler) {
        boolean alquilado = false;
        if (alquileres.get(alquiler.getSocio().getNif()) == null) {
            DaoAlquileres.alquileres.put(alquiler.getSocio().getNif(), alquiler);
            alquilado = true;
        }
        return alquilado;
    }

    public boolean borrarAlquiler(Alquiler alquiler) {
        boolean devuelto = false;
        if (alquileres.remove(alquiler.getSocio().getNif()) != null) {
            devuelto = true;
        }
        return devuelto;
    }

    public Alquiler alquilerSocio(String nif) {
        if (alquileres.size() == 0) {
            return null;
        } else {
            return alquileres.get(nif);
        }
    }
    
    public List<Alquiler> getTodosAlquileres() {
        List<Alquiler> list = new ArrayList(alquileres.values());
        return list;
    }
    
}
