package org.example.ModuloCliente.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloCliente.config.CacheAuthorization;
import org.example.ModuloCliente.dao.retrofit.UsuariosApi;
import org.example.ModuloCliente.dao.utils.Constantes;
import org.example.ModuloCliente.gui.Producers;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class DaoUsuarios extends DaoGenerics {

    private HashPassword hash = new HashPassword();
    private final Producers producers;
    private CacheAuthorization cache;

    @Inject
    public DaoUsuarios(Producers producers, CacheAuthorization cache) {
        this.producers = producers;
        this.cache = cache;
    }

    public Either<ApiError, List<Usuario>> getAllUsuario() {
        UsuariosApi usuariosApi = producers.createApi(cache);
        return safeApicall(usuariosApi.getUsuarios());
    }

    public Either<ApiError, UsuarioRegistro> addUsuarioRegistro(UsuarioRegistro u) {
        UsuariosApi usuariosApi = producers.createApi(cache);
        return safeApicall(usuariosApi.addUsuarioRegistro(u));
    }

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(String u) {
        UsuariosApi usuariosApi = producers.createApi(cache);
        return safeApicall(usuariosApi.getUsuarioLogin(u));
    }

    public String mandarMail(String correo, String username) {
        UsuariosApi usuariosApi = producers.createApi(cache);
        return this.apiCallPersonalizado(usuariosApi.mandarMail(correo, username));
    }

    public Either<ApiError, ApiRespuesta> deleteUsuario(String u) {
        UsuariosApi usuariosApi = producers.createApi(cache);
        return this.safeApicall(usuariosApi.deletePersona(u));
    }

    public Either<ApiError, ApiRespuesta> updateUsuario(Usuario u) {
        UsuariosApi usuariosApi = producers.createApi(cache);
        return this.safeApicall(usuariosApi.updateUsuario(u));
    }

    public String login(String username, String password) {

        String resultado;
        UsuariosApi usuariosApi = producers.createApi(cache);
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
