package notas.Servidor.dao;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.Common.modelo.Alumno;
import notas.Servidor.dao.errores.BaseDatosCaidaException;
import notas.Servidor.dao.errores.OtraException;
import notas.Servidor.dao.jdbc.DBConnectionPool;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@Log4j2
public class DaoAlumno {

    private final DBConnectionPool pool;

    @Inject
    public DaoAlumno(DBConnectionPool pool) {
        this.pool = pool;
    }


    public List<Alumno> getAllAlumnos() {
        List<Alumno> result;
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            result = jdbcTemplate.query(ConstantesSQL.SELECT_ALL_ALUMNOS,
                    new BeanPropertyRowMapper<>(Alumno.class));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new BaseDatosCaidaException(ConstantesSQL.BASE_DE_DATOS_CAIDA);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OtraException(ConstantesSQL.ERROR_DEL_SERVIDOR);
        }
        return result;
    }
}
