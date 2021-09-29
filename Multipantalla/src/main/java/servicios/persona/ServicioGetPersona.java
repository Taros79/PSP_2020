package servicios.persona;

import dao.DaoPersona;
import dao.modelo.Persona;

import java.util.List;

public class ServicioGetPersona {

    public List<Persona> getPersonas (){
        DaoPersona dao = new DaoPersona();
        return dao.getPersonas();
    }

    public Persona getPersona(int posicion){
        DaoPersona dao = new DaoPersona();
        return dao.getPersona(posicion);
    }
}
