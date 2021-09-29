package servicios.persona;

import dao.DaoPersona;
import dao.modelo.Persona;

public class ServiciosUpdatePersona {

    public boolean updatePersona (Persona vieja, Persona nueva){
        DaoPersona dao = new DaoPersona();
        return dao.updatePersona(vieja,nueva);
    }
}
