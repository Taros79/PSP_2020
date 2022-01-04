package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.modelo.Usuario;
import jakarta.inject.Inject;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.Common.EE.utils.HashPassword;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


/***
 *
 * edte emeetodo
 * @author
 *
 */

@Log4j2
public class DaoUsuario {

    private DBConnectionPool dbConnection;
    private HashPassword hash = new HashPassword();

    private static final String INSERT_USUARIO =
            "insert into usuarios (correo, username, hashedPassword, fechaAlta, tipoUsuario) values (?, ?, ?, ?, ?)";

    private static final String GET_USUARIO = "select * from usuarios where username = ?";

    public static final String UPDATE_USUARIO =
            "UPDATE usuarios SET codActivacion = ?, isActivo = ? , fechaAlta = ? WHERE username = ?";
    @Inject
    public DaoUsuario(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Either<ApiError, List<Usuario>> getUsuarios() {
        Either<ApiError, List<Usuario>> resultado;
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());

        if (!jtm.query("select * from usuarios",
                BeanPropertyRowMapper.newInstance(Usuario.class)).isEmpty()) {
            resultado = Either.right(jtm.query("select * from usuarios",
                    BeanPropertyRowMapper.newInstance(Usuario.class)));
        } else {
            resultado = Either.left(new ApiError("ConstantesDao.NO_HAY_ELEMENTOS", LocalDateTime.now()));
        }
        return resultado;
    }

    public Either<ApiError, UsuarioLoginDTO> getUsuarioLogin(String username) {
        UsuarioLoginDTO c;
        Either<ApiError, UsuarioLoginDTO> resultado;

        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    dbConnection.getDataSource());
            c = jtm.queryForObject(GET_USUARIO, new Object[]{username}, (rs, rowNum) ->
                    new UsuarioLoginDTO(
                            rs.getString("username"),
                            rs.getString("hashedPassword")));
            resultado = Either.right(c);

        } catch (IncorrectResultSizeDataAccessException e) {
            Logger.getLogger("Main").info(e.getMessage());
            resultado = Either.left(new ApiError("Este usuario no existe", LocalDateTime.now()));
        } catch (Exception e) {
            Logger.getLogger("Main").info(e.getMessage());
            resultado = Either.left(new ApiError("Fallo en la bbdd", LocalDateTime.now()));
        }
        return resultado;
    }

    public Either<ApiError, Usuario> getUsuario(String username) {
        Usuario c;
        Either<ApiError, Usuario> resultado;

        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    dbConnection.getDataSource());
            c = jtm.queryForObject(GET_USUARIO, new Object[]{username}, (rs, rowNum) ->
                    new Usuario(
                            rs.getInt("id"),
                            rs.getString("correo"),
                            rs.getString("username"),
                            rs.getString("hashedPassword"),
                            rs.getString("codActivacion"),
                            rs.getInt("isActivo"),
                            rs.getDate("fechaAlta").toLocalDate().atStartOfDay(),
                            rs.getInt("tipoUsuario")));
            resultado = Either.right(c);

        } catch (IncorrectResultSizeDataAccessException e) {
            Logger.getLogger("Main").info(e.getMessage());
            resultado = Either.left(new ApiError("Este usuario no existe", LocalDateTime.now()));
        } catch (Exception e) {
            Logger.getLogger("Main").info(e.getMessage());
            resultado = Either.left(new ApiError("Fallo en la bbdd", LocalDateTime.now()));
        }
        return resultado;
    }


    public String addUsuario(UsuarioRegistro u) {
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());
        String añadido;

        String password = hash.hashPassword(u.getHashedPassword());
        int resultado = jtm.update(INSERT_USUARIO, u.getCorreo(), u.getUsername(), password, LocalDateTime.now(), u.getTipoUsuario());

        if (resultado == 1) {
            añadido = "Usuario creado";
        } else {
            añadido = "Fallo al crear usuario";
        }
        return añadido;
    }

    public String updateUsuario(String codActivacion, int isActivo, LocalDateTime fechaAlta, String username) {
        String resultado;
        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    dbConnection.getDataSource());
            jtm.update(UPDATE_USUARIO, codActivacion, isActivo, fechaAlta, username);
            resultado = "Usuario actualizado";
        } catch (Exception e) {
            java.util.logging.Logger.getLogger("Main").info(e.getMessage());
            resultado = "Error en la bbdd";
        }
        return resultado;
    }
}