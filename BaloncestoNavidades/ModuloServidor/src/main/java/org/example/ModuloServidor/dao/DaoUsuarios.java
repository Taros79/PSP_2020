package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.EE.utils.ApiRespuesta;
import org.example.Common.EE.utils.HashPassword;
import org.example.Common.modelo.Usuario;
import org.example.Common.modelo.UsuarioLoginDTO;
import org.example.Common.modelo.UsuarioRegistro;
import org.example.ModuloServidor.dao.jdbc.DBConnectionPool;
import org.example.ModuloServidor.utils.Constantes;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoUsuarios {

    public static final String UPDATE_USUARIO =
            "UPDATE usuarios SET codActivacion = ?, isActivo = ? , fechaAlta = ? WHERE username = ?";
    public static final String DELETE_USUARIO =
            "delete from usuarios where username = ?";
    private static final String INSERT_USUARIO =
            "insert into usuarios (correo, username, hashedPassword,codActivacion,isActivo, fechaAlta, tipoUsuario) values (?, ?, ?, ?, ?,?,?)";

    private static final String GET_USUARIO = "select * from usuarios where username = ?";
    private DBConnectionPool dbConnection;
    private HashPassword hash = new HashPassword();

    @Inject
    public DaoUsuarios(DBConnectionPool dbConnection) {
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
            resultado = Either.left(new ApiError(Constantes.NO_HAY_ELEMENTOS, LocalDateTime.now()));
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
                            rs.getString(Constantes.USERNAME),
                            rs.getString(Constantes.HASHED_PASSWORD),
                            rs.getInt(Constantes.IS_ACTIVO),
                            rs.getInt(Constantes.TIPO_USUARIO)));
            resultado = Either.right(c);

        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(Constantes.NO_EXISTE_OBJETO, LocalDateTime.now()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(Constantes.FALLO_EN_LA_BBDD, LocalDateTime.now()));
        }
        return resultado;
    }

    public Either<ApiError, Usuario> getUsuario(String username) {
        Usuario c;
        Either<ApiError, Usuario> resultado;

        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());
            c = jtm.queryForObject(GET_USUARIO, new Object[]{username}, (rs, rowNum) ->
                    new Usuario(
                            rs.getInt(Constantes.ID),
                            rs.getString(Constantes.CORREO),
                            rs.getString(Constantes.USERNAME),
                            rs.getString(Constantes.HASHED_PASSWORD),
                            rs.getString(Constantes.COD_ACTIVACION),
                            rs.getInt(Constantes.IS_ACTIVO),
                            rs.getDate(Constantes.FECHA_ALTA).toLocalDate().atStartOfDay(),
                            rs.getInt(Constantes.TIPO_USUARIO)));
            resultado = Either.right(c);

        } catch (IncorrectResultSizeDataAccessException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(Constantes.NO_EXISTE_OBJETO, LocalDateTime.now()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(Constantes.FALLO_EN_LA_BBDD, LocalDateTime.now()));
        }
        return resultado;
    }


    public String addUsuario(UsuarioRegistro u) {
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());
        String añadido;

        int resultado = jtm.update(INSERT_USUARIO, u.getCorreo(), u.getUsername(),
                u.getHashedPassword(), u.getCodActivacion(),
                u.getIsActivo(), LocalDateTime.now(), u.getTipoUsuario());

        if (resultado == 1) {
            añadido = Constantes.CREADO;
        } else {
            añadido = Constantes.FALLO_AL_CREAR;
        }
        return añadido;
    }

    public String updateUsuario(String codActivacion, int isActivo, LocalDateTime fechaAlta, String username) {
        String resultado;
        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    dbConnection.getDataSource());
            jtm.update(UPDATE_USUARIO, codActivacion, isActivo, fechaAlta, username);
            resultado = Constantes.ACTUALIZADO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Constantes.FALLO_EN_LA_BBDD;
        }
        return resultado;
    }

    public Either<ApiError, ApiRespuesta> delUsuario(String u) {

        Either<ApiError, ApiRespuesta> resultado;
        if (u != null) {
            try {
                JdbcTemplate jtm = new JdbcTemplate(
                        dbConnection.getDataSource());
                jtm.update(DELETE_USUARIO, u);
                resultado = Either.right(new ApiRespuesta(Constantes.BORRADO, LocalDateTime.now()));
            } catch (DataAccessException e) {
                resultado = Either.left(new ApiError(Constantes.FALLO_EN_LA_BBDD, LocalDateTime.now()));
                log.error(e.getMessage(), e);
            }
        } else {
            resultado = Either.left(new ApiError(Constantes.NO_EXISTE_OBJETO, LocalDateTime.now()));
        }

        return resultado;
    }


}