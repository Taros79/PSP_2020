package GID.ModuloCliente.dao.daoImplementacion;

import GID.Commons.dao.modelo.Persona;
import GID.ModuloCliente.dao.DaoPersona;
import GID.ModuloCliente.dao.retrofit.PersonaApi;
import GID.ModuloCliente.dao.utils.ConfigurationSingleton_OkHttpClient;
import GID.ModuloCliente.dao.utils.Constantes;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoPersonaImp implements DaoPersona {
    public ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient;

    @Inject
    public DaoPersonaImp(ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient) {
        this.configurationSingleton_okHttpClient = configurationSingleton_okHttpClient;
    }

    PersonaApi personaApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PersonaApi.class);

    @Override
    public Either<String, List<Persona>> getAllPersona() {
        Either<String, List<Persona>> resultado;

        try {
            Response<List<Persona>> response = personaApi.getRecursosPersona().execute();

            if (response.isSuccessful() && Objects.requireNonNull(response.body()) != null) {
                assert response.body() != null;
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Persona> getDatosByNombre(String id) {
        Either<String, Persona> resultado;

        try {
            Response<Persona> response = personaApi.getRecursosUnaPersona(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Persona> addPersona(Persona p) {
        Either<String, Persona> resultado;

        try {
            Response<Persona> response = personaApi.addPersona(p).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Persona> deletePersona(String id) {
        Either<String, Persona> resultado;

        try {
            Response<Persona> response = personaApi.deletePersona(id).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }

    @Override
    public Either<String, Persona> actualizarPersona(Persona p) {
        Either<String, Persona> resultado;
        try {
            Response<Persona> response = personaApi.actualizarPersona(p).execute();

            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(Constantes.OBJETO_NO_VALIDO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.ERROR_EN_BBDD);
        }
        return resultado;
    }
}
