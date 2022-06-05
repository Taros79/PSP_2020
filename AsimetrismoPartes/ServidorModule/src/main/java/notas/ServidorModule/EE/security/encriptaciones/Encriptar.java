package notas.ServidorModule.EE.security.encriptaciones;

import com.google.common.primitives.Bytes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.config.ConfigYaml;
import notas.ServidorModule.utils.HashPassword;

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
public class Encriptar {
    private final HashPassword hashP;
    private final ConfigYaml configYaml;
    private String claveSimetrica;

    @Inject
    public Encriptar(HashPassword hashP, ConfigYaml configYaml) {
        this.hashP = hashP;
        this.configYaml = configYaml;
    }

    public Either<String, String> encriptarAESTextoConRandom(String texto) {
        Either<String, String> mensajeEncriptado;
        try {
            byte[] iv = new byte[Constantes.BYTES_IV];
            byte[] salt = new byte[Constantes.BYTES_SALT];
            byte[] clave = new byte[Constantes.BYTES_RANDOM];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(clave);
            sr.nextBytes(iv);
            sr.nextBytes(salt);

            claveSimetrica = Base64.getUrlEncoder().encodeToString(clave);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(Constantes.TAG_T_LENGTH, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(Constantes.INSTANCE_SK_FACTORY);
            // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
            KeySpec spec = new PBEKeySpec(claveSimetrica.toCharArray(), salt, Constantes.ITERATION_COUNT, Constantes.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), Constantes.ALGORITHM_AES);

            Cipher cipher = Cipher.getInstance(Constantes.INSTANCE_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            mensajeEncriptado = Either.right(Base64.getUrlEncoder().encodeToString(
                    Bytes.concat(iv, salt, cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8)))));
        } catch (InvalidKeyException | InvalidKeySpecException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ALGORITMO_INVALIDO);
        } catch (InvalidAlgorithmParameterException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_EN_EL_ALGORITMO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ILLEGAL_BLOCK_SIZE);
        }
        return mensajeEncriptado;
    }

    public Either<String, String> desencriptarAESTextoConRandom(String texto, String random) {
        Either<String, String> mensajeDesencriptado;
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(texto);
            byte[] iv = Arrays.copyOf(decoded, Constantes.BYTES_IV);
            byte[] salt = Arrays.copyOfRange(decoded, Constantes.BYTES_IV, Constantes.BYTES_SALT_MAX);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(Constantes.TAG_T_LENGTH, iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(Constantes.INSTANCE_SK_FACTORY);
            KeySpec spec = new PBEKeySpec(random.toCharArray(), salt, Constantes.ITERATION_COUNT, Constantes.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), Constantes.ALGORITHM_AES);

            Cipher cipher = Cipher.getInstance(Constantes.INSTANCE_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            mensajeDesencriptado = Either.right(new String(
                    cipher.doFinal(Arrays.copyOfRange(decoded, Constantes.BYTES_SALT_MAX, decoded.length))));
        } catch (AEADBadTagException be) {
            log.error(be.getMessage(), be);
            mensajeDesencriptado = Either.left(Constantes.TAG_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.ERROR_DESCIFRAR);
        }
        return mensajeDesencriptado;
    }

    public Either<String, String> encriptarRSARandomConPublica(String usuario) {

        Either<String, String> mensajeEncriptado = null;
        String clave = Constantes.CONTRASENA_ALL_PFX;
        try {
            File f = new File(configYaml.getRutaKeyStore() + usuario + Constantes.KEYSTORE_PFX);
            if (f.exists()) {
                KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
                ksLoad.load(new FileInputStream(configYaml.getRutaKeyStore() + usuario + Constantes.KEYSTORE_PFX), clave.toCharArray());

                X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);

                Cipher cifrador = Cipher.getInstance(Constantes.ALGORITHM_RSA);

                byte[] bufferPlano = claveSimetrica.getBytes(StandardCharsets.UTF_8); //nuevo//

                // PASO 3a: Poner cifrador en modo CIFRADO
                cifrador.init(Cipher.ENCRYPT_MODE, certLoad.getPublicKey());  // Cifra con la clave publica

                byte[] bufferCifrado = cifrador.doFinal(bufferPlano);

                String claveCifrada = Base64.getUrlEncoder().encodeToString(bufferCifrado);

                mensajeEncriptado = Either.right(claveCifrada);
            }
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ALGORITMO_INVALIDO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ILLEGAL_BLOCK_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_DE_ENCRIPTACION_ASIMETRICA);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_EN_EL_CERTIFICADO);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_EN_KEY_STORE_BUILD);
        }
        return mensajeEncriptado;
    }


    public Either<String, String> desencriptarRSAClaveCifrada(String claveCifrada, Usuario u) {
        Either<String, String> mensajeDesencriptado = null;
        String clave = Constantes.CONTRASENA_ALL_PFX;
        try {
            File f = new File(configYaml.getRutaKeyStore() + u.getNombre() + Constantes.KEYSTORE_PFX);
            if (f.exists()) {
                KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
                ksLoad.load(new FileInputStream(configYaml.getRutaKeyStore() + u.getNombre() + Constantes.KEYSTORE_PFX), clave.toCharArray());

                KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(u.getPass().toCharArray());
                KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(Constantes.PRIVADA, pt);
                PrivateKey keyLoad = privateKeyEntry.getPrivateKey();

                Cipher cifrador = Cipher.getInstance(Constantes.ALGORITHM_RSA);
                byte[] claveEnBase64 = Base64.getUrlDecoder().decode(claveCifrada);

                // PASO 3b: Poner cifrador en modo DESCIFRADO
                cifrador.init(Cipher.DECRYPT_MODE, keyLoad); // Descrifra con la clave privada
                byte[] bufferCifrado = cifrador.doFinal(claveEnBase64);

                String str = new String(bufferCifrado, StandardCharsets.UTF_8);

                //como resultado te devuelve la clave random para abrir el mensaje con el AES
                mensajeDesencriptado = Either.right(str);
            }
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.ALGORITMO_INVALIDO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.ILLEGAL_BLOCK_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.ERROR_DE_ENCRIPTACION_ASIMETRICA);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.ERROR_EN_EL_CERTIFICADO);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
            mensajeDesencriptado = Either.left(Constantes.ERROR_EN_KEY_STORE_BUILD);
        } catch (UnrecoverableEntryException e) {
            mensajeDesencriptado = Either.left(Constantes.ERROR_FATAL);
            log.error(e.getMessage(), e);
        }
        return mensajeDesencriptado;
    }


    //----------------------------------------------------------------------------------------------------------------------//

    public Either<String, String> encriptarRSA_Padres(String usuario, String random) {

        Either<String, String> mensajeEncriptado = null;
        String clave = Constantes.CONTRASENA_ALL_PFX;
        try {
            File f = new File(configYaml.getRutaKeyStore() + usuario + Constantes.KEYSTORE_PFX);
            if (f.exists()) {
                KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
                ksLoad.load(new FileInputStream(configYaml.getRutaKeyStore() + usuario + Constantes.KEYSTORE_PFX), clave.toCharArray());

                X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);

                Cipher cifrador = Cipher.getInstance(Constantes.ALGORITHM_RSA);

                byte[] bufferPlano = random.getBytes(StandardCharsets.UTF_8); //nuevo//

                // PASO 3a: Poner cifrador en modo CIFRADO
                cifrador.init(Cipher.ENCRYPT_MODE, certLoad.getPublicKey());  // Cifra con la clave publica

                byte[] bufferCifrado = cifrador.doFinal(bufferPlano);

                String claveCifrada = Base64.getUrlEncoder().encodeToString(bufferCifrado);

                mensajeEncriptado = Either.right(claveCifrada);
            }
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.INVALID_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ALGORITMO_INVALIDO);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.NO_SUCH_PADDING);
        } catch (BadPaddingException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.BAD_PADDING);
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ILLEGAL_BLOCK_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_DE_ENCRIPTACION_ASIMETRICA);
        } catch (CertificateException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_EN_EL_CERTIFICADO);
        } catch (KeyStoreException e) {
            log.error(e.getMessage(), e);
            mensajeEncriptado = Either.left(Constantes.ERROR_EN_KEY_STORE_BUILD);
        }
        return mensajeEncriptado;
    }

    public Either<String, PrivateKey> getPrivateKey(String nombre, String pass) {
        Either<String, PrivateKey> resultado;
        //Para leer la privada:
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = pass.toCharArray();
            ksLoad.load(new FileInputStream(configYaml.getRutaKeyStore() + nombre + Constantes.KEYSTORE_PFX), Constantes.CONTRASENA_ALL_PFX.toCharArray());
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(Constantes.PRIVADA, pt);
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            resultado = Either.right(privateKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PRIVADA_INTENTELO_MAS_TARDE);
        } catch (UnrecoverableEntryException e) {
            //Es porque la pass es incorrecta.
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.USUARIO_Y_O_CONTRASENA_NO_VALIDO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }
        return resultado;
    }

    public Either<String, PublicKey> getPublicKey(String nombre) {
        Either<String, PublicKey> resultado;
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            ksLoad.load(new FileInputStream(configYaml.getRutaKeyStore() + nombre + Constantes.KEYSTORE_PFX), Constantes.CONTRASENA_ALL_PFX.toCharArray());
            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);
            PublicKey publicKey = certLoad.getPublicKey();
            resultado = Either.right(publicKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PUBLICA_INTENTELO_MAS_TARDE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }

    public Either<String, String> firmar(Usuario u, String mensaje) {
        Either<String, String> result;
        try {
            Either<String, PrivateKey> eitherGetPrivateKey = getPrivateKey(u.getNombre(), u.getPass());

            if (eitherGetPrivateKey.isRight()) {
                Signature sign = Signature.getInstance(Constantes.SHA_256_WITH_RSA);
                MessageDigest hash = MessageDigest.getInstance(Constantes.SHA_512);
                sign.initSign(eitherGetPrivateKey.get());
//                    Firmo el secreto encriptado simetricamente
                sign.update(hash.digest(mensaje.getBytes()));
                byte[] firmaEnBytes = sign.sign();
                String firmaEnBase64 = Base64.getUrlEncoder().encodeToString(firmaEnBytes);

                result = Either.right(firmaEnBase64);
            } else {
                result = Either.left(eitherGetPrivateKey.getLeft());
            }
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        }
        return result;
    }

}

