package notas.Servidor.dao.encriptaciones;

public class ConstantesEncriptar {

    public static final String ERROR_DESENCRIPTAR = "Error desencriptar";
    public static final String ERROR_DESCIFRAR = "Error descifrar";
    public static final String INSTANCE_CIPHER = "AES/GCM/noPadding";
    public static final String INSTANCE_SK_FACTORY = "PBKDF2WithHmacSHA256";
    public static final String ALGORITHM = "AES";
    public static final int BYTES_IV = 12;
    public static final int BYTES_SALT = 16;
    public static final int BYTES_SALT_MAX = 28;  //Da error por alguna razon
    public static final int TAG_T_LENGTH = 128;
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 256;


    public static final String ERROR_BBDD = "Error bbdd";
    public static final String DATOS_INCORRECTOS = "Datos incorrectos";
    public static final String NOMBRE = "nombre";
    public static final String MENSAJE = "mensaje";
    public static final String TAG_ERROR = "Error en el Tag (Por las constantes en este caso)";
}
