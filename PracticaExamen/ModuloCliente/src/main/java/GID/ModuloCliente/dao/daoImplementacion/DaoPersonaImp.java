package GID.ModuloCliente.dao.daoImplementacion;

import GID.Commons.EE.errores.ApiError;
import GID.Commons.dao.modelo.Persona;
import GID.ModuloCliente.dao.DaoPersona;
import GID.ModuloCliente.dao.retrofit.PersonaApi;
import GID.ModuloCliente.dao.utils.ConfigurationSingleton_OkHttpClient;
import GID.ModuloCliente.dao.utils.Constantes;
import GID.Commons.EE.utils.ApiRespuesta;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoPersonaImp implements DaoPersona {

    private ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient;

    @Inject
    public DaoPersonaImp(ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient) {
        this.configurationSingleton_okHttpClient = configurationSingleton_okHttpClient;
    }

    PersonaApi personaApi = ConfigurationSingleton_OkHttpClient.getInstance().create(PersonaApi.class);

    @Override
    public Either<String, List<Persona>> getAllPersona() {
        Either<String, List<Persona>> resultado;

        try {
            PersonaApi personaApi = configurationSingleton_okHttpClient.getInstance().create(PersonaApi.class);
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
            PersonaApi personaApi = configurationSingleton_okHttpClient.getInstance().create(PersonaApi.class);
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

        try {PersonaApi personaApi = configurationSingleton_okHttpClient.getInstance().create(PersonaApi.class);
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
    public ApiRespuesta deletePersona(String id) {
        ApiRespuesta resultado;
        try {
            PersonaApi personaApi = configurationSingleton_okHttpClient.getInstance().create(PersonaApi.class);
            Response<ApiRespuesta> response = personaApi.deletePersona(id).execute();

            if (response.isSuccessful()) {
                resultado = response.body();
            } else {
                resultado = new ApiRespuesta(GID.ModuloCliente.gui.utils.Constantes.OBJETO_NO_ENCONTRADO,
                        LocalDateTime.now());
            }

        } catch (IOException e) {
            resultado = new ApiRespuesta(GID.ModuloCliente.gui.utils.Constantes.PROBLEMA_EN_SERVIDOR,
                    LocalDateTime.now());
            log.error(e.getMessage(), e);
        }
        return resultado;
    }

    @Override
    public ApiRespuesta casamientoPareja (String idH, String idM)  {
        ApiRespuesta resultado;
        try {
            PersonaApi personaApi = configurationSingleton_okHttpClient.getInstance().create(PersonaApi.class);
            Response<ApiRespuesta> response = personaApi.casamientoPareja(idH,idM).execute();

            if (response.isSuccessful()) {
                resultado = response.body();
            } else {
                resultado = new ApiRespuesta(response.message(),
                        LocalDateTime.now());
            }
        } catch (IOException e) {
            resultado = new ApiRespuesta(GID.ModuloCliente.gui.utils.Constantes.PROBLEMA_EN_SERVIDOR,
                    LocalDateTime.now());
            log.error(e.getMessage(), e);
        }
        return resultado;
    }

    @Override
    public ApiRespuesta procrear (Persona p, String idPadres)  {
        ApiRespuesta resultado;
        try {
            PersonaApi personaApi = configurationSingleton_okHttpClient.getInstance().create(PersonaApi.class);
            Response<ApiRespuesta> response = personaApi.procrear(p,idPadres).execute();

            if (response.isSuccessful()) {
                resultado = response.body();
            } else {
                resultado = new ApiRespuesta(response.message(),
                        LocalDateTime.now());
            }
        } catch (IOException e) {
            resultado = new ApiRespuesta(GID.ModuloCliente.gui.utils.Constantes.PROBLEMA_EN_SERVIDOR,
                    LocalDateTime.now());
            log.error(e.getMessage(), e);
        }
        return resultado;
    }
}
