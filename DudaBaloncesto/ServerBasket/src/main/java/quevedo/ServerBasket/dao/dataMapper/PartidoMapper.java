package quevedo.ServerBasket.dao.dataMapper;

import org.springframework.jdbc.core.RowMapper;
import quevedo.ServerBasket.utils.constantes.ConstantesParametros;
import quevedo.common.modelo.ResultadosPartidos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartidoMapper implements RowMapper<ResultadosPartidos> {

    @Override
    public ResultadosPartidos mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ResultadosPartidos(
                rs.getString(ConstantesParametros.LOCAL),
                rs.getString(ConstantesParametros.VISITANTE),
                rs.getDate(ConstantesParametros.FECHA).toLocalDate(),
                rs.getInt(ConstantesParametros.RESULTADO_LOCAL),
                rs.getInt(ConstantesParametros.RESULTADO_VISITANTE)
        );
    }
}
