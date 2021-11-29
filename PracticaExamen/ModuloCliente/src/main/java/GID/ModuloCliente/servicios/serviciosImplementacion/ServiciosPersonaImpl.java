package GID.ModuloCliente.servicios.serviciosImplementacion;

import GID.Commons.EE.errores.ApiError;
import GID.Commons.dao.modelo.Persona;
import GID.ModuloCliente.dao.DaoPersona;
import GID.Commons.EE.utils.ApiRespuesta;
import io.vavr.control.Either;
import GID.ModuloCliente.servicios.ServiciosPersona;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPersonaImpl implements ServiciosPersona {

    private DaoPersona daoPersona;

    @Inject
    public ServiciosPersonaImpl(DaoPersona daoPersona) {
        this.daoPersona = daoPersona;
    }

    @Override
    public Either<String, List<Persona>> getAllPersona() {
        return daoPersona.getAllPersona();
    }

    @Override
    public Either<String, Persona> getDatosByNombre(String id) {
        return daoPersona.getDatosByNombre(id);
    }

    @Override
    public Either<String, Persona> addPersona(Persona p) {
        return daoPersona.addPersona(p);
    }

    @Override
    public ApiRespuesta deletePersona(String id) {
        return daoPersona.deletePersona(id);
    }

    @Override
    public ApiRespuesta casamientoPareja (String idH, String idM)  {
        return daoPersona.casamientoPareja(idH,idM);
    }

    @Override
    public ApiRespuesta procrear (Persona p, String idPadres)  {
        return daoPersona.procrear(p, idPadres);
    }
}
