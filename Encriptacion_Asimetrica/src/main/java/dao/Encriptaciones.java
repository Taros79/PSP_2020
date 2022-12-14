package dao;

import com.google.common.primitives.Bytes;
import dao.modelo.Secreto;
import dao.modelo.SecretoCompartido;
import dao.modelo.Usuario;
import gui.utils.HashPassword;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public class Encriptaciones {
    private HashPassword hashP = new HashPassword();
    private String claveSimetrica;

    public Either<String, String> encriptarAESTextoConRandom(Secreto s) {
        Either<String, String> mensajeEncriptado;
        try {
            byte[] iv = new byte[ConstantesDAO.BYTES_IV];
            byte[] salt = new byte[ConstantesDAO.BYTES_SALT];
            byte[] clave = new byte[ConstantesDAO.BYTES_RANDOM];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(clave);
            sr.nextBytes(iv);
            sr.nextBytes(salt);

            claveSimetrica = Base64.getUrlEncoder().encodeToString(clave);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(ConstantesDAO.TAG_T_LENGTH, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ConstantesDAO.INSTANCE_SK_FACTORY);
            // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
            KeySpec spec = new PBEKeySpec(claveSimetrica.toCharArray(), salt, ConstantesDAO.ITERATION_COUNT, ConstantesDAO.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ConstantesDAO.ALGORITHM_AES);

            Cipher cipher = Cipher.getInstance(ConstantesDAO.INSTANCE_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            mensajeEncriptado = Either.right(Base64.getUrlEncoder().encodeToString(
                    Bytes.concat(iv, salt, cipher.doFinal(s.getSecretoEncriptado().getBytes(StandardCharsets.UTF_8)))));
        } catch (InvalidKeyException | InvalidKeySpecException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ALGORITMO_INVALIDO);
        } catch (InvalidAlgorithmParameterException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_EN_EL_ALGORITMO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ILLEGAL_BLOCK_SIZE);
        }
        return mensajeEncriptado;
    }

  public Either<String, String> desencriptarAESTextoConRandom(Secreto s, String pass) {
        Either<String, String> mensajeDesencriptado;
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(s.getSecretoEncriptado());

            byte[] iv = Arrays.copyOf(decoded, ConstantesDAO.BYTES_IV);
            byte[] salt = Arrays.copyOfRange(decoded, ConstantesDAO.BYTES_IV, ConstantesDAO.BYTES_SALT_MAX);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(ConstantesDAO.TAG_T_LENGTH, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ConstantesDAO.INSTANCE_SK_FACTORY);
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, ConstantesDAO.ITERATION_COUNT, ConstantesDAO.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ConstantesDAO.ALGORITHM_AES);

            Cipher cipher = Cipher.getInstance(ConstantesDAO.INSTANCE_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            mensajeDesencriptado = Either.right(new String(cipher.doFinal(Arrays.copyOfRange(decoded, ConstantesDAO.BYTES_SALT_MAX, decoded.length))));
        } catch (AEADBadTagException be) {
            log.error(be.getMessage(), be);
            mensajeDesencriptado = Either.left(ConstantesDAO.TAG_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.ERROR_DESCIFRAR);
        }
        return mensajeDesencriptado;
    }


    public Either<String, String> encriptarRSARandomConPublica(Usuario u) {

        Either<String, String> mensajeEncriptado = null;
        String clave = ConstantesDAO.CONTRASENA_ALL_PFX;
        try {
            File f = new File(u.getNombre() + ConstantesDAO.FILE_PFX);
            if (f.exists()) {
                KeyStore ksLoad = KeyStore.getInstance(ConstantesDAO.PKCS_12);
                ksLoad.load(new FileInputStream(u.getNombre() + ConstantesDAO.FILE_PFX), clave.toCharArray());

                X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(ConstantesDAO.PUBLICA);

                Cipher cifrador = Cipher.getInstance(ConstantesDAO.ALGORITHM_RSA);

                byte[] bufferPlano = claveSimetrica.getBytes();

                // PASO 3a: Poner cifrador en modo CIFRADO
                cifrador.init(Cipher.ENCRYPT_MODE, certLoad.getPublicKey());  // Cifra con la clave publica

                byte[] bufferCifrado = cifrador.doFinal(bufferPlano);

                String claveCifrada = Base64.getUrlEncoder().encodeToString(bufferCifrado);

                mensajeEncriptado = Either.right(claveCifrada);
            }
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ALGORITMO_INVALIDO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ILLEGAL_BLOCK_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_DE_ENCRIPTACION_ASIMETRICA);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_EN_EL_CERTIFICADO);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_EN_KEY_STORE_BUILD);
        }
        return mensajeEncriptado;
    }


    public Either<String, String> encriptarRSAConPublicaDestinatario(Usuario u, String c) {
        Either<String, String> mensajeEncriptado = null;
        String clave = ConstantesDAO.CONTRASENA_ALL_PFX;
        try {
            File f = new File(u.getNombre() + ConstantesDAO.FILE_PFX);
            if (f.exists()) {
                KeyStore ksLoad = KeyStore.getInstance(ConstantesDAO.PKCS_12);
                ksLoad.load(new FileInputStream(u.getNombre() + ConstantesDAO.FILE_PFX), clave.toCharArray());

                X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(ConstantesDAO.PUBLICA);

                Cipher cifrador = Cipher.getInstance(ConstantesDAO.ALGORITHM_RSA);
                byte[] bufferPlano = c.getBytes(StandardCharsets.UTF_8);

                // PASO 3a: Poner cifrador en modo CIFRADO
                cifrador.init(Cipher.ENCRYPT_MODE, certLoad.getPublicKey());  // Cifra con la clave publica

                byte[] bufferCifrado = cifrador.doFinal(bufferPlano);

                String claveCifrada = Base64.getUrlEncoder().encodeToString(bufferCifrado);

                mensajeEncriptado = Either.right(claveCifrada);
            }
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ALGORITMO_INVALIDO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ILLEGAL_BLOCK_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_DE_ENCRIPTACION_ASIMETRICA);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_EN_EL_CERTIFICADO);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(ConstantesDAO.ERROR_EN_KEY_STORE_BUILD);
        }
        return mensajeEncriptado;
    }


    public Either<String, String> desencriptarRSAClaveCifrada(SecretoCompartido s, Usuario u) {
        Either<String, String> mensajeDesencriptado = null;
        String clave = ConstantesDAO.CONTRASENA_ALL_PFX;
        try {
            File f = new File(u.getNombre() + ConstantesDAO.FILE_PFX);
            if (f.exists()) {
                KeyStore ksLoad = KeyStore.getInstance(ConstantesDAO.PKCS_12);
                ksLoad.load(new FileInputStream(u.getNombre()+ ConstantesDAO.FILE_PFX), clave.toCharArray());

                KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(u.getPassword().toCharArray());
                KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(ConstantesDAO.PRIVADA, pt);
                PrivateKey keyLoad = privateKeyEntry.getPrivateKey();

                Cipher cifrador = Cipher.getInstance(ConstantesDAO.ALGORITHM_RSA);
                byte[] claveEnBase64 = Base64.getUrlDecoder().decode(s.getClaveCifrada());

                // PASO 3b: Poner cifrador en modo DESCIFRADO
                cifrador.init(Cipher.DECRYPT_MODE, keyLoad); // Descrifra con la clave privada
                byte[] bufferCifrado = cifrador.doFinal(claveEnBase64);

                String str = new String(bufferCifrado, StandardCharsets.UTF_8);

                mensajeDesencriptado = Either.right(str);
            }
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.ALGORITMO_INVALIDO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.ILLEGAL_BLOCK_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.ERROR_DE_ENCRIPTACION_ASIMETRICA);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.ERROR_EN_EL_CERTIFICADO);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(ConstantesDAO.ERROR_EN_KEY_STORE_BUILD);
        } catch (UnrecoverableEntryException e) {
            mensajeDesencriptado = Either.left(ConstantesDAO.ERROR_FATAL);
            log.error(e.getMessage(), e);
        }
        return mensajeDesencriptado;
    }

}

