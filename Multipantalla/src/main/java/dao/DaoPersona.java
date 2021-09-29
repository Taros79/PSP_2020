package dao;


import dao.modelo.Persona;

import java.util.ArrayList;
import java.util.List;

public class DaoPersona {

    public static List<Persona> personas;


    public DaoPersona() {
        if (personas == null) {
            personas = new ArrayList<>();
        }
    }

    public List<Persona> getPersonas(){
        return personas;
    }

    public Persona getPersona(int posicion){
        return personas.get(posicion);
    }

    public boolean addPersona(Persona p) {
        return personas.add(p);
    }

    public boolean delPersona(Persona p) {
        return personas.remove(p);
    }

    public boolean updatePersona(Persona vieja, Persona nueva) {
        personas.remove(vieja);
        return personas.add(nueva);
    }

}




