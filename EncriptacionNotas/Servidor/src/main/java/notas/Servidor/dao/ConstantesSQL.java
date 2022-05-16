package notas.Servidor.dao;

public class ConstantesSQL {

    public static final String SELECT_ALL_USUARIOS = "SELECT * FROM usuarios";
    public static final String SELECT_USUARIO_BY_USUARIO = "SELECT * FROM usuarios where nombre = ?";

    public static final String SELECT_ALL_PARTES = "SELECT * FROM partes";
    public static final String INSERT_PARTE = "INSERT INTO partes (descripcion, idAlumno, idTipoEstado) VALUES (?, ?, ?)";

    public static final String SELECT_ALL_ALUMNOS = "SELECT * FROM alumnos";

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
