package org.example.ModuloCliente.dao;

import io.vavr.control.Either;
import javax.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.modelo.Usuario;
import org.example.ModuloCliente.dao.retrofit.BaloncestoApi;
import org.example.ModuloCliente.config.ConfigurationSingleton_OkHttpClient;
import org.example.ModuloCliente.dao.utils.Constantes;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoUsuario {

    private ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient;

    @Inject
    public DaoUsuario(ConfigurationSingleton_OkHttpClient configurationSingleton_okHttpClient) {
        this.configurationSingleton_okHttpClient = configurationSingleton_okHttpClient;
    }

    public Either<ApiError, List<Usuario>> getAllUsuario() {
        Either<ApiError, List<Usuario>> usuarios;
        try {
            BaloncestoApi baloncestoApi = configurationSingleton_okHttpClient.getInstance().create(BaloncestoApi.class);
            Response<List<Usuario>> response = baloncestoApi.getUsuarios().execute();
            if (response.isSuccessful()) {
                usuarios = Either.right(response.body());
            } else {
                usuarios = Either.left(new ApiError(Constantes.OBJETO_NO_VALIDO, LocalDateTime.now()));
            }
        } catch (IOException e) {
            usuarios = Either.left(new ApiError(Constantes.PROBLEMA_SERVIDOR, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }

        return usuarios;
    }

    public Either<String, Usuario> getDatosByNombre(String id) {
        return null;
    }

    public Either<String, Usuario> addUsuario(Usuario p) {
        return null;
    }

    public ApiRespuesta deleteUsuario(String id) {
        return null;
    }
}
