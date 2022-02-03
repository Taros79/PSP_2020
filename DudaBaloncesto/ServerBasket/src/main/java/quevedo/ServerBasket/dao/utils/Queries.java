package quevedo.ServerBasket.dao.utils;

public class Queries {
    public static final String INSERT_USER = "insert into usuarios (password, correo, codigoActivacion, fechaLimiteActivacion, activo, idTipoUser)\n" +
            "values (?,?,?,?,?,?)";
    public static final String ACTIVAR_QUERIE = "update usuarios set activo = ? where codigoActivacion = ? ";
    public static final String OBTENER_FECHA_QUERIE = "SELECT fechaLimiteActivacion from usuarios where codigoActivacion = ?";
    public static final String ADD_MINS_QUERIE = "update usuarios set fechaLimiteActivacion = ?, codigoActivacion = ? where codigoActivacion = ? ";
    public static final String CONFIRMAR_CORREO_QUERIE = "SELECT count(idUser) from usuarios where correo = ?";
    public static final String ADD_TIME_AND_COOD_QUERIE = "update usuarios set codPassw = ?, fechaLimiteActivacion = ? where correo = ?";
    public static final String PASSW_MODIFICATION_QUIERIE = "update usuarios set password = ? where codPassw = ? ";
    public static final String TIEMPO_ACTIVACION_ACTUALIZACINO_PASSW_QUIERIE = "SELECT fechaLimiteActivacion from usuarios where codPassw = ?";
    public static final String GET_USER_CORREO_QUERIE = "SELECT * from usuarios where correo = ?";
    public static final String GET_PARTIDOS_Y_RESULTADOS_QUERIE = "select equiposL.nombre as local, resultadoLocal ,\n" +
            "       equiposV.nombre as visitante,resultadoVisitante,\n" +
            "       jornadas.fecha\n" +
            "from (((partidos join equipos equiposL on equiposL.id = partidos.idEquipoLocal)\n" +
            "    join jornadas on jornadas.id = partidos.idJornada)\n" +
            "         join equipos equiposV on equiposV.id = partidos.idEquipoVisitante\n" +
            "    )\n" +
            "order by fecha desc;\n" +
            "\n";
    public static final String PARTIDOS_POR_EQUIPO_QUERIE = "select equiposL.nombre as local, resultadoLocal ,\n" +
            "        equiposV.nombre as visitante,resultadoVisitante,\n" +
            "        jornadas.fecha\n" +
            "        from (((partidos join equipos equiposL on equiposL.id = partidos.idEquipoLocal)\n" +
            "        join jornadas on jornadas.id = partidos.idJornada)\n" +
            "        join equipos equiposV on equiposV.id = partidos.idEquipoVisitante\n" +
            "        )\n" +
            "        where idEquipoLocal = (select id from equipos where nombre = ?)\n" +
            "        or idEquipoVisitante = (select id from equipos where nombre = ?);";
    public static final String PARTIDOS_JORNADA_QUERIE = "select equiposL.nombre as local, resultadoLocal , equiposV.nombre as visitante,resultadoVisitante,jornadas.fecha\n" +
            "               from (((partidos join equipos equiposL on equiposL.id = partidos.idEquipoLocal)\n" +
            "                   join jornadas on jornadas.id = partidos.idJornada)\n" +
            "                   join equipos equiposV on equiposV.id = partidos.idEquipoVisitante)\n" +
            "\n" +
            "where fecha = ?;";
    public static final String INSERT_JORNADA_QUERIE = "insert into jornadas (fecha) value (?);";
    public static final String DEL_JORNADA_QUERIE = "delete from jornadas where fecha = ?;";
    public static final String INSERT_EQUIPO_QUERIE = "insert into equipos (nombre) value (?);";
    public static final String DEL_EQUIPO_QUERIE = "delete from equipos where nombre = ?;";
    public static final String INSERT_PARTIDO_QUERIE = "insert into partidos (idJornada, IdEquipoLocal, IdEquipoVisitante, " +
            "resultadoLocal, resultadoVisitante) values (?,?,?,?,?);";
    public static final String DEL_PARTIDO_QUERIE = "delete from equipos where idPartido = ?;";
    public static final String GET_PARTIDOS_QUERIE = "select * from partidos;";
    public static final String SELECT_FROM_EQUIPOS = "SELECT * from equipos;";
}
