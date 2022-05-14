package notas.Servidor.dao;

public class ConstantesSQL {

    public static final String SELECT_ALL_DOTES = "SELECT * FROM dotes";
    public static final String SELECT_ALL_OBJETOS = "SELECT * FROM objetos";
    public static final String SELECT_ALL_ESTADISTICAS = "SELECT * FROM estadisticas";
    public static final String SELECT_ALL_HECHIZOS = "SELECT * FROM hechizos";
    public static final String SELECT_ALL_PARTIDAS = "SELECT * FROM partidas";
    public static final String SELECT_ALL_PERSONAJES = "SELECT * FROM personajes";
    public static final String SELECT_ALL_USUARIOS = "SELECT * FROM usuarios";

    public static final String INSERT_DOTES = "insert into dotes (nombre, descripcion) values (?,?)";
    public static final String INSERT_OBJETOS = "insert into objetos (nombre, descripcion, ranura, nivel, peso, precio) values (?,?,?,?,?,?)";
    public static final String INSERT_ESTADISTICAS = "insert into estadisticas (vida, ac, fortaleza, reflejos, voluntad, fuerza, destreza, constitucion, inteligencia, sabiduria, carisma) values (?,?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_HECHIZOS = "insert into hechizos (nombre, descripcion, nivel) values (?,?,?)";
    public static final String INSERT_PARTIDAS = "insert into partidas (horaInicio, duracion, id_Master) values (?,?,?)";
    public static final String INSERT_PERSONAJES = "insert into personajes (nombre, raza, clase, alineamiento, nivel, experiencia, id_estadistica) values (?,?,?,?,?,?,?)";
    public static final String INSERT_USUARIOS = "insert into usuarios (correo, contraseña, tipo_Usuario, baneado) values (?,?,?,?)";

    public static final String DEL_DOTES = "delete from dotes where id = ?";
    public static final String DEL_OBJETOS = "delete from objetos where id = ?";
    public static final String DEL_ESTADISTICAS = "delete from estadisticas where id = ?";
    public static final String DEL_HECHIZOS = "delete from hechizos where id = ?";
    public static final String DEL_PARTIDAS = "delete from partidas where id = ?";
    public static final String DEL_PERSONAJES = "delete from personajes where id = ?";
    public static final String DEL_USUARIOS = "delete from usuarios where id = ?";

    public static final String UPDATE_DOTES = "update dotes set nombre = ?, descripcion = ? WHERE id = ?";
    public static final String UPDATE_OBJETOS = "update objetos set nombre = ?, descripcion = ?, ranura = ?, nivel = ?, peso = ?, precio = ? WHERE id = ?";
    public static final String UPDATE_ESTADISTICAS = "update estadisticas set vida = ?, ac = ?, fortaleza = ?, reflejos = ?, voluntad = ?, fuerza = ?, destreza = ?, constitucion = ?, inteligencia = ?, sabiduria = ?, carisma = ? WHERE id = ?";
    public static final String UPDATE_HECHIZOS = "update hechizos set nombre = ?, descripcion = ?, nivel = ? WHERE id = ?";
    public static final String UPDATE_PARTIDAS = "update partidas set horaInicio = ?, duracion = ?, id_Master = ? WHERE id = ?";
    public static final String UPDATE_PERSONAJES = "update personajes set nombre = ?, raza = ?, clase = ?, alineamiento = ?, nivel = ?, experiencia = ?, id_estadistica = ? WHERE id = ?";
    public static final String UPDATE_USUARIOS = "update usuarios set correo = ?, contraseña = ?, tipo_Usuario = ? WHERE id = ?";


    public static final String SELECT_ESTADISTICA_BY_ID = "SELECT * FROM estadisticas where id = ?";
    public static final String SELECT_DOTES_BY_ID_PERSONAJE = "SELECT d.id, d.nombre, d.descripcion FROM dotes d " +
            "inner join dotesPersonaje dP on d.id = dP.id_Dote " +
            "inner join personajes p on dP.id_Personaje = p.id where p.id = ?";
    public static final String SELECT_HECHIZOS_BY_ID_PERSONAJE = "SELECT h.id, h.nombre, h.descripcion, h.nivel FROM hechizos h " +
            "inner join hechizosPersonaje hP on h.id = hP.id_Hechizo " +
            "inner join personajes p on hP.id_Personaje = p.id where p.id = ?";
    public static final String SELECT_OBJETOS_BY_ID_PERSONAJE = "SELECT o.id, o.nombre, o.descripcion, o.ranura, o.nivel, o.peso, o.precio FROM objetos o " +
            "inner join objetosPersonaje oP on o.id = oP.id_Objeto " +
            "inner join personajes p on oP.id_Personaje = p.id where p.id = ?";
    public static final String SELECT_PERSONAJE_BY_ID_USUARIO = "SELECT p.id,p.nombre,p.raza,p.clase,p.alineamiento,p.nivel,p.experiencia,p.id_estadistica FROM personajes p " +
            "inner join personajesUsuario po on p.id = po.id_Personaje " +
            "inner join usuarios u on po.id_Usuario = u.id where u.id = ?";


    public static final String INSERT_HECHIZO_TO_PERSONAJE = "insert into hechizosPersonaje (id_Hechizo, id_Personaje) values (?,?)";
    public static final String INSERT_DOTE_TO_PERSONAJE = "insert into dotesPersonaje (id_Dote, id_Personaje) values (?,?)";
    public static final String INSERT_OBJETO_TO_PERSONAJE = "insert into objetosPersonaje (id_Objeto, id_Personaje) values (?,?)";

    public static final String DEL_HECHIZO_TO_PERSONAJE = "delete from hechizosPersonaje where id_Hechizo = ? and id_Personaje = ?";
    public static final String DEL_DOTE_TO_PERSONAJE = "delete from dotesPersonaje where id_Dote = ? and id_Personaje = ?";
    public static final String DEL_OBJETO_TO_PERSONAJE = "delete from objetosPersonaje where id_Objeto = ? and id_Personaje = ?";

    public static final String DEL_ALL_HECHIZO_TO_PERSONAJE = "delete from hechizosPersonaje where id_Personaje = ?";
    public static final String DEL_ALL_DOTE_TO_PERSONAJE = "delete from dotesPersonaje where id_Personaje = ?";
    public static final String DEL_ALL_OBJETO_TO_PERSONAJE = "delete from objetosPersonaje where id_Personaje = ?";
    public static final String SELECT_USUARIO_BY_CORREO = "SELECT * FROM usuarios where correo = ?";


    public static final String SELECT_PARTIDAS_BY_MASTER = "SELECT * FROM partidas WHERE id_Master = ?";
    public static final String SELECT_PARTIDAS_BY_USUARIO =
            "SELECT * FROM partidas WHERE id IN (SELECT id_Partida FROM usuariosPartida WHERE id_Usuario = ?)";

    public static final String ADD_PERSONAJE_TO_USUARIO = "insert into personajesUsuario (id_Personaje, id_Usuario) values (?,?)";

    //ERRORES
    public static final String ERROR_DEL_SERVIDOR = "Error del servidor";
    public static final String BASE_DE_DATOS_CAIDA = "Base de datos caida";
    public static final String YA_EXISTE = "ya existe";
    public static final String ANADIDO_CON_EXITO = "Añadido con exito";
    public static final String NO_SE_PUDO_ANADIR = "No se pudo añadir";
    public static final String NO_HAY_DATOS = "No se han encontrado datos";
    public static final String BORRADO_CON_EXITO = "Borrado con exito";
    public static final String NO_SE_PUDO_BORRAR = "No se ha borrado ningún registro";
    public static final String ACTUALIZADO_CON_EXITO = "Actualizado con exito";
    public static final String NO_SE_HA_ACTUALIZADO = "No se ha actualizado ningún registro";
    public static final String DATOS_RELACIONADOS_NO_SE_PUEDE_BORRAR = "Existen datos relacionados, no se puede borrar";
    public static final String CONTRASEÑA_CORREO_INCORRECTO = "Contraseña o correo incorrecto";
    private static final String GET_SERIES_DISPONIBLES =
            "select * from series where id not in (select id_serie from actoresSeries where id_actor = ?)";

}
