package dao;

import com.google.common.primitives.Bytes;
import dao.modelo.Usuario;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

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
public class Encriptaciones {

    public Either<String,String> encriptarTexto(Usuario u, String pass) {
        Either<String,String> mensajeEncriptado;
        try {
            byte[] iv = new byte[12];
            byte[] salt = new byte[16];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
            sr.nextBytes(salt);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/noPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            mensajeEncriptado = Either.right(Base64.getUrlEncoder().encodeToString(
                    Bytes.concat(iv, salt,cipher.doFinal(u.getMensaje().getBytes(StandardCharsets.UTF_8)))));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left("Error al desencriptar");
        }
        return mensajeEncriptado;
    }

    public Either<String,String> desencriptarTexto(Usuario u, String pass) {
        Either<String,String> mensajeDesencriptado;
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(u.getMensaje());

            byte[] iv = Arrays.copyOf(decoded, 12);
            byte[] salt = Arrays.copyOfRange(decoded, 12, 28);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/noPADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            mensajeDesencriptado = Either.right(new String(cipher.doFinal(Arrays.copyOfRange(decoded, 28, decoded.length))));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left("Error al descifrar");
        }
        return mensajeDesencriptado;
    }
}

