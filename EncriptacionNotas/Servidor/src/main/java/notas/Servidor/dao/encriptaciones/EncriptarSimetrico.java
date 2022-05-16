package notas.Servidor.dao.encriptaciones;

import com.google.common.primitives.Bytes;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public class EncriptarSimetrico {

    private static final String PASS = "papichulo";

    public Either<String, String> encriptarTexto(String descripcion) {
        Either<String, String> mensajeEncriptado;
        try {
            byte[] iv = new byte[ConstantesEncriptar.BYTES_IV];
            byte[] salt = new byte[ConstantesEncriptar.BYTES_SALT];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
            sr.nextBytes(salt);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(ConstantesEncriptar.TAG_T_LENGTH, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ConstantesEncriptar.INSTANCE_SK_FACTORY);
            // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
            KeySpec spec = new PBEKeySpec(PASS.toCharArray(), salt, ConstantesEncriptar.ITERATION_COUNT, ConstantesEncriptar.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ConstantesEncriptar.ALGORITHM);

            Cipher cipher = Cipher.getInstance(ConstantesEncriptar.INSTANCE_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            mensajeEncriptado = Either.right(Base64.getUrlEncoder().encodeToString(
                    Bytes.concat(iv, salt, cipher.doFinal(descripcion.getBytes(StandardCharsets.UTF_8)))));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesEncriptar.ERROR_DESENCRIPTAR);
        }
        return mensajeEncriptado;
    }

    public Either<String, String> desencriptarTexto(String descripcion) {
        Either<String, String> mensajeDesencriptado;
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(descripcion);

            byte[] iv = Arrays.copyOf(decoded, 12);
            byte[] salt = Arrays.copyOfRange(decoded, 12, 28);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(ConstantesEncriptar.TAG_T_LENGTH, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ConstantesEncriptar.INSTANCE_SK_FACTORY);
            KeySpec spec = new PBEKeySpec(PASS.toCharArray(), salt, ConstantesEncriptar.ITERATION_COUNT, ConstantesEncriptar.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ConstantesEncriptar.ALGORITHM);

            Cipher cipher = Cipher.getInstance(ConstantesEncriptar.INSTANCE_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            mensajeDesencriptado = Either.right(new String(cipher.doFinal(Arrays.copyOfRange(decoded, 28, decoded.length))));
        } catch (AEADBadTagException be) {
            log.error(be.getMessage(), be);
            mensajeDesencriptado = Either.left(ConstantesEncriptar.TAG_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesEncriptar.ERROR_DESCIFRAR);
        }
        return mensajeDesencriptado;
    }
}

