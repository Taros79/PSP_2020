package org.example.ModuloCliente.dao;

import io.vavr.control.Either;
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
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoUsuario extends DaoGenerics {

    private final Producers producers;
    private HashPassword hash = new HashPassword();

    @Inject
    public DaoUsuario(Producers producers) {
        this.producers = producers;
    }

    public Either<ApiError, List<Usuario>> getAllUsuario() {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return safeApicall(usuariosApi.getUsuarios());
    }

    public Either<ApiError, UsuarioRegistro> addUsuarioRegistro(UsuarioRegistro u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return safeApicall(usuariosApi.addUsuarioRegistro(u));
    }

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(String u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return safeApicall(usuariosApi.getUsuarioLogin(u));
    }

    public String mandarMail(String correo, String username) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return this.apiCallPersonalizado(usuariosApi.mandarMail(correo, username));
    }

    public Either<ApiError, ApiRespuesta> deleteUsuario(String u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return this.safeApicall(usuariosApi.deletePersona(u));
    }

    public Either<ApiError, ApiRespuesta> updateUsuario(Usuario u) {
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        return this.safeApicall(usuariosApi.updateUsuario(u));
    }

    public String login(String username, String password) {

        String resultado;
        UsuariosApi usuariosApi = producers.createApi(producers.createRetrofit());
        String passwordHash = hash.hashPassword(password);
        try {
            Response<Void> response = usuariosApi.login(username, passwordHash).execute();
            if (response.isSuccessful()) {
                resultado = Constantes.BIENVENIDO + " " + username;
            } else {
                resultado = response.message();
                ;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Constantes.PROBLEMA_EN_SERVIDOR;
        }
        return resultado;
    }
}
