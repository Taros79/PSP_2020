package notas.Servidor.EE.security.encriptaciones;

public class Constantes {

    //GenClaves
    public static final String NO_SE_HA_PODIDO_ENCRIPTAR_EL_MENSAJE = "No se ha podido encriptar el mensaje";
    public static final String NO_SE_HA_PODIDO_DESENCRIPTAR_EL_MENSAJE = "No se ha podido desencriptar el mensaje.";
    public static final String RSA = "RSA";
    public static final String NO_HA_SIDO_POSIBLE_CIFRAR_CON_LA_CLAVE_PUBLICA = "No ha sido posible cifrar con la clave pública.";
    public static final String NO_HA_SIDO_POSIBLE_DESCIFRAR_CON_LA_CLAVE_PRIVADA = "No ha sido posible descifrar con la clave privada.";
    public static final String KEYSTORE = "keystore\\";
    public static final String PRIVADA = ".privada";
    public static final String PUBLICA = ".publica";
    public static final String NO_HA_SIDO_POSIBLE_GENERAR_LAS_CLAVES_INTENTELO_MAS_TARDE = "No ha sido posible generar las claves. Inténtelo más tarde.";
    public static final String FALLO_INTERNO = "Fallo interno.";
    public static final String NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PUBLICA_INTENTELO_MAS_TARDE = "No ha sido posible recoger la clave pública. Inténtelo más tarde.";
    public static final String NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PRIVADA_INTENTELO_MAS_TARDE = "No ha sido posible recoger la clave privada. Inténtelo más tarde.";
    public static final String CN = "CN=";
    public static final String SHA_1_WITH_RSA_ENCRYPTION = "SHA1WithRSAEncryption";
    public static final String PKCS_12 = "PKCS12";
    public static final String KEYSTORE_PFX = "keystore.pfx";
    public static final int UNO = 1;
    public static final int KEYSIZE = 2048;
    public static final int AMOUNT_TO_ADD = 365;
    public static final String USUARIO_Y_O_CONTRASENA_NO_VALIDO = "Usuario y/o contraseña no válido";


    //Encriptar
    public static final String INSTANCE_CIPHER = "AES/GCM/noPadding";
    public static final String INSTANCE_SK_FACTORY = "PBKDF2WithHmacSHA256";
    public static final String ALGORITHM_AES = "AES";
    public static final int BYTES_IV = 12;
    public static final int BYTES_SALT = 16;
    public static final int BYTES_RANDOM = 16;
    public static final int BYTES_SALT_MAX = 28;  //Da error por alguna razon
    public static final int TAG_T_LENGTH = 128;
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 256;

    public static final String INVALID_KEY = "Invalid key";
    public static final String ALGORITMO_INVALIDO = "Algoritmo invalido";
    public static final String ERROR_EN_EL_ALGORITMO = "Error en el algoritmo";
    public static final String NO_SUCH_PADDING = "No such padding";
    public static final String BAD_PADDING = "Bad padding";
    public static final String ILLEGAL_BLOCK_SIZE = "Illegal block size";
}
