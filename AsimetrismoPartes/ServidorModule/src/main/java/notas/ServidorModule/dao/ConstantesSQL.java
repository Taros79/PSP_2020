package notas.ServidorModule.dao;

public class ConstantesSQL {

    public static final String SELECT_ALL_USUARIOS = "SELECT * FROM usuarios";
    public static final String SELECT_USUARIO_BY_NAME = "SELECT * FROM usuarios where nombre = ?";
    public static final String SELECT_USUARIO_BY_ID = "SELECT * FROM usuarios where id = ?";

    public static final String SELECT_ALL_PARTES = "SELECT * FROM partes";
    public static final String SELECT_PARTE_BY_ID = "SELECT * FROM partes where id = ?";
    public static final String INSERT_PARTE = "INSERT INTO partes (descripcion, idAlumno, idTipoEstado) VALUES (?, ?, ?)";
    public static final String UPDATE_PARTE = "UPDATE partes SET idTipoEstado = ? WHERE id = ?";
    public static final String DELETE_PARTE = "DELETE FROM partes WHERE id = ?";
    public static final String SELECT_PARTES_PADRE_ALUMNOS = "SELECT * FROM partes p\n" +
            "    inner join alumnosPadre a on p.idAlumno = a.id\n" +
            "    inner join usuarios u on a.idUsuario = u.id where u.id = ? and p.idTipoEstado = '2'";

    public static final String DELETE_PARTE_Y_PARTE_COMPARTIDO = "DELETE FROM partes where id = ?;\n" +
            "DELETE FROM partesCompartidos where idParte = ?";
    public static final String SELECT_ALL_PARTESCOMPARTIDOS_JEFATURA = "SELECT * FROM partesCompartidos where idUserACompartir = 1";
    public static final String SELECT_ALL_PARTESCOMPARTIDOS_BY_USER = "SELECT * FROM partesCompartidos where idUserACompartir = ?";
    public static final String SELECT_ALL_ALUMNOS = "SELECT * FROM alumnos";
    public static final String SELECT_ALUMNO_BY_ID = "SELECT * FROM alumnos where id = ?";

    public static final String INSERT_PARTE_COMPARTIDO = "INSERT INTO partesCompartidos (idUserACompartir, idParte, ClaveCifrada) VALUES (?, ?, ?)";
    public static final String SELECT_PARTECOMPARTIDO_BY_IDS = "SELECT * FROM partesCompartidos WHERE idParte = ? and idUserACompartir = 1";

    public static final String SELECT_ID_USUARIO_ALUMNO = "SELECT * FROM alumnosPadre WHERE idAlumno = ?";

    //select de usuario por la id del alumno
    public static final String SELECT_USUARIO_BY_ID_ALUMNO = "SELECT * FROM usuarios WHERE id = (SELECT idUsuario FROM alumnosPadre WHERE idAlumno = ?)";

    public static final String INSERT_USUARIO = "INSERT INTO usuarios (nombre, pass, idTipoUsuario) VALUES (?, ?, ?)";

    //ERRORES
    public static final String ERROR_DEL_SERVIDOR = "Error del servidor";
    public static final String BASE_DE_DATOS_CAIDA = "Base de datos caida";
    public static final String ERROR_AL_DESENCRIPTAR_CLAVECIFRADA = "Error al desencriptar la clave cifrada";
    public static final String ERROR_AL_DESENCRIPTAR_MENSAJE = "Error al desencriptar el mensaje";
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
    public static final String ALUMNO_NO_ENCONTRADO = "Alumno no encontrado";
}
