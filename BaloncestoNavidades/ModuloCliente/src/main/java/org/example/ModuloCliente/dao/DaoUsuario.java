package org.example.ModuloCliente.dao;

import com.google.gson.stream.MalformedJsonException;
import io.vavr.control.Either;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.dao.retrofit.UsuariosApi;
import org.example.ModuloCliente.dao.utils.Constantes;
import org.example.ModuloCliente.gui.Producers;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoUsuario extends DaoGenerics {

    private final Producers producers;
    private HashPassword hash = new HashPassword();

    @Inject
    public DaoUsuario(Producers producers) {
        this.producers = producers;
    }

    public Either<ApiError, List<Usuario>> getAllUsuario() {
        Either<ApiError, List<Usuario>> usuarios;
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        try {
            Response<List<Usuario>> response = usuariosApi.getUsuarios().execute();
            if (response.isSuccessful()) {
                usuarios = Either.right(response.body());
            } else {
                usuarios = Either.left(new ApiError(Constantes.OBJETO_NO_VALIDO, LocalDateTime.now()));
            }
        } catch (IOException e) {
            usuarios = Either.left(new ApiError(Constantes.PROBLEMA_SERVIDOR, LocalDateTime.now()));
            log.error(e.getMessage(), e);
        }
        return safeApicall(usuariosApi.getUsuarios());
    }

    public Either<ApiError, UsuarioRegistro> addUsuarioRegistro(UsuarioRegistro u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return safeApicall(usuariosApi.addUsuarioRegistro(u));
    }

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(UsuarioLoginDTO u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return safeApicall(usuariosApi.getUsuarioLogin(u.getUsername()));
    }

    public String mandarMail(String correo, String username) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return this.apiCallPersonalizado(usuariosApi.mandarMail(correo, username));
    }

    public Either<ApiError, ApiRespuesta> deleteUsuario(String u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return this.safeApicall(usuariosApi.deletePersona(u));
    }

    public String login(String username, String password) {

        String resultado;
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        String passwordHash = hash.hashPassword(password);
        try {
            Response<Void> response = usuariosApi.login(username, passwordHash).execute();
            if (response.isSuccessful()) {
                resultado = "Bienvenido " + username;
            } else {
                resultado = response.message();;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = "Constantes.SERVIDOR_CAIDO_EN_COMBATE";
        }
        return resultado;
    }
}
