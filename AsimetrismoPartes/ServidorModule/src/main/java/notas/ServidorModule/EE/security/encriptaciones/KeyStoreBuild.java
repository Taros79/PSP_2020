package notas.ServidorModule.EE.security.encriptaciones;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import notas.CommonModule.modelo.Usuario;
import notas.ServidorModule.config.ConfigYaml;
import notas.ServidorModule.utils.HashPassword;

import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
public class KeyStoreBuild {

    private final HashPassword hashP;
    private final ConfigYaml configYaml;

    @Inject
    public KeyStoreBuild(ConfigYaml configYaml, HashPassword hashP) {
        this.configYaml = configYaml;
        this.hashP = hashP;
    }


    public Either<String, String> crearKeystoreYCertificado (Usuario u){
            String passwordHasheada = hashP.hashPassword(u.getPass());

            Either<String, String> result;
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyGen = null; // Hace uso del provider BC

            try {
                keyGen = KeyPairGenerator.getInstance(Constantes.ALGORITHM_RSA);
            } catch (NoSuchAlgorithmException e) {
                log.error("No se encontro el algoritmo RSA");
                result = Either.left(Constantes.ALGORITMO_INVALIDO);
            }

            keyGen.initialize(Constantes.KEY_SIZE);  // tamano clave 512 bits
            KeyPair clavesRSA = keyGen.generateKeyPair();

            X509V3CertificateGenerator generatorCert = new X509V3CertificateGenerator();
            generatorCert.setSerialNumber(BigInteger.valueOf(1));   //or generate a random number
            generatorCert.setSubjectDN(new X509Principal(Constantes.CERT_CN + u.getNombre()));  //see examples to add O,OU etc
            generatorCert.setIssuerDN(new X509Principal(Constantes.CERT_CN + "ServidorInsti")); //same since it is self-signed
            generatorCert.setPublicKey(clavesRSA.getPublic());
            generatorCert.setNotBefore(
                    Date.from(LocalDate.now().plus(Constantes.AMOUNT_TO_ADD, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
            generatorCert.setNotAfter(new Date());
            generatorCert.setSignatureAlgorithm(Constantes.ALGORITHM_X509);

            PrivateKey signingKey = clavesRSA.getPrivate();
            X509Certificate cert = null;

            try {
                cert = generatorCert.generateX509Certificate(signingKey);
            } catch (Exception e) {
                log.error("Error al generar el certificado");
                result = Either.left(Constantes.ERROR_EN_EL_CERTIFICADO);
            }

            File f = new File(configYaml.getRutaKeyStore() + u.getNombre() + Constantes.KEYSTORE_PFX);
            String passPFX = Constantes.CONTRASENA_ALL_PFX;
            if (!f.exists()) {
                try {
                    PrivateKey pk = clavesRSA.getPrivate();
                    KeyStore ks = KeyStore.getInstance(Constantes.PKCS_12);
                    ks.load(null, null);
                    ks.setCertificateEntry(Constantes.PUBLICA, cert);
                    ks.setKeyEntry(Constantes.PRIVADA, pk, passwordHasheada.toCharArray(), new Certificate[]{cert});
                    FileOutputStream fos = new FileOutputStream(configYaml.getRutaKeyStore() + u.getNombre() + Constantes.KEYSTORE_PFX);
                    ks.store(fos, passPFX.toCharArray());
                    fos.close();

                    result = Either.right(Constantes.KEYSTORE_CREADO_CORRECTAMENTE);

                } catch (KeyStoreException ks) {
                    log.error(ks.getMessage());
                    result = Either.left(Constantes.FALLO_AL_CREAR_LA_KEYSTORE);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    result = Either.left(Constantes.ERROR_EN_KEY_STORE_BUILD);
                }
            } else {
                result = Either.left(Constantes.KEYSTORE_YA_EXISTE);
            }
            return result;
        }
    }
