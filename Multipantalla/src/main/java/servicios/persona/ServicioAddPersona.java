package servicios.persona;

import dao.DaoPersona;
import dao.modelo.Persona;

public class ServicioAddPersona {

    public boolean addPersona(Persona p){
        DaoPersona dao = new DaoPersona();
        return dao.addPersona(p);
    }
}
