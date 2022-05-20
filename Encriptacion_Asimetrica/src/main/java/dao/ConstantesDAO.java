package dao;

public class ConstantesDAO {

    public static final String ERROR_DESCIFRAR = "Error descifrar";
    public static final String INSTANCE_CIPHER = "AES/GCM/noPadding";
    public static final String INSTANCE_SK_FACTORY = "PBKDF2WithHmacSHA256";
    public static final String ALGORITHM_X509 = "SHA1WithRSAEncryption";
    public static final String ALGORITHM_AES = "AES";
    public static final String ALGORITHM_RSA = "RSA";
    public static final String CERT_CN = "CN=";
    public static final String FILE_PFX = ".pfx";
    public static final String CONTRASENA_ALL_PFX = "amophp";
    public static final int BYTES_IV = 12;
    public static final int BYTES_SALT = 16;
    public static final int BYTES_RANDOM = 16;
    public static final int BYTES_SALT_MAX = 28;  //Da error por alguna razon
    public static final int TAG_T_LENGTH = 128;
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 256;
    public static final int KEY_SIZE = 2048;
    public static final int AMOUNT_TO_ADD = 365;


    public static final String ERROR_BBDD = "Error bbdd";
    public static final String TAG_ERROR = "Error en el Tag (Por las constantes en este caso)";
    public static final String PKCS_12 = "PKCS12";
    public static final String PUBLICA = "publica";
    public static final String PRIVADA = "privada";
    public static final String KEYSTORE_CREADO_CORRECTAMENTE = "Keystore creado correctamente";
    public static final String FALLO_AL_CREAR_LA_KEYSTORE = "Fallo al crear la keystore";
    public static final String ERROR_EN_KEY_STORE_BUILD = "Error en KeyStoreBuild";
    public static final String INVALID_KEY = "Invalid key";
    public static final String ALGORITMO_INVALIDO = "Algoritmo invalido";
    public static final String ERROR_EN_EL_ALGORITMO = "Error en el algoritmo";
    public static final String NO_SUCH_PADDING = "No such padding";
    public static final String BAD_PADDING = "Bad padding";
    public static final String ILLEGAL_BLOCK_SIZE = "Illegal block size";
    public static final String ERROR_EN_EL_CERTIFICADO = "Error en el certificado";
    public static final String ERROR_DE_ENCRIPTACION_ASIMETRICA = "Error de encriptacion asimetrica";
    public static final String ERROR_FATAL = "Error fatal";
    public static final String YA_EXISTE = "Ya existe";
    public static final String EL_SECRETO_NO_EXISTE = "El secreto no existe";
    public static final String SECRETO_COMPARTIDO = "Secreto compartido";
    public static final String ERROR_EN_CREAR_USUARIO = "Error en crear usuario";
    public static final String ERROR_AL_COMPARTIR_SECRETO = "Error al compartir secreto";
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String SECRETO_ENCRIPTADO = "secretoEncriptado";
}
