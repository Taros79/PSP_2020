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
            "insert into usuarios (correo, username, hashedPassword, tipoUsuario) values (?, ?, ?, ?)";

    private static final String GET_USUARIO = "select * from usuarios where username = ?";

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
            UsuarioLoginDTO c = null;
            Either<ApiError, UsuarioLoginDTO> resultado = null;

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

    public String addUsuario(UsuarioRegistro u) {
        JdbcTemplate jtm = new JdbcTemplate(
                dbConnection.getDataSource());
        String añadido;

        String password = hash.hashPassword(u.getHashedPassword());
        int resultado = jtm.update(INSERT_USUARIO, u.getCorreo(), u.getUsername(), u.getHashedPassword(), u.getTipoUsuario());

        if (resultado == 1) {
            añadido = "Usuario creado";
        } else {
            añadido = "Fallo al crear usuario";
        }
        return añadido;
    }

//    @PreDestroy
//    public void cerrarSession()
//    {
//        session.close();
//    }


    /*public Usuario addUser(Usuario user) {

        UsuarioEntity userE = new UsuarioEntity();
        userE.setFecha(LocalDateTime.now());
        userE.setName(user.getName());
        userE.setPassword(user.getPassword());
        session.beginTransaction();
        session.save(userE);
        session.getTransaction().commit();

        return user;
    }

    public boolean borrar(String id) {
        return usuarios.remove(usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst().orElse(null));
    }*/
}
