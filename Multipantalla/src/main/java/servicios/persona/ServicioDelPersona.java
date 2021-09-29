package servicios.persona;

import dao.DaoPersona;
import dao.modelo.Persona;

public class ServicioDelPersona {

    public boolean delPersona (Persona p){
        DaoPersona dao = new DaoPersona();
        return dao.delPersona(p);
    }
}
