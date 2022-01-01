package org.example.ModuloServidor.dao;

import io.vavr.control.Either;
import org.example.Common.EE.errores.ApiError;
import org.example.Common.modelo.Usuario;
import jakarta.inject.Inject;
import org.hibernate.Session;
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

public class DaoUsuario {

    private DBConnectionPool dbConnection;
    private Session session;


    @Inject
    public DaoUsuario(DBConnectionPool dbConnection,Session session) {
        this.session = session;
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
