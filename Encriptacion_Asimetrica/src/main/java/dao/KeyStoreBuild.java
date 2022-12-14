package dao;

import dao.modelo.Usuario;
import gui.utils.HashPassword;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
public class KeyStoreBuild {

    private HashPassword hashP = new HashPassword();

    public Either<String,String> crearKeystoreYCertificado(Usuario u) throws Exception {
        String passwordHasheada = hashP.hashPassword(u.getPassword());

        Either<String,String> result = null;
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ConstantesDAO.ALGORITHM_RSA); // Hace uso del provider BC
        keyGen.initialize(ConstantesDAO.KEY_SIZE);  // tamano clave 512 bits
        KeyPair clavesRSA = keyGen.generateKeyPair();

        X509V3CertificateGenerator generatorCert = new X509V3CertificateGenerator();
        generatorCert.setSerialNumber(BigInteger.valueOf(1));   //or generate a random number
        generatorCert.setSubjectDN(new X509Principal(ConstantesDAO.CERT_CN + u.getNombre()));  //see examples to add O,OU etc
        generatorCert.setIssuerDN(new X509Principal(ConstantesDAO.CERT_CN + u.getNombre())); //same since it is self-signed
        generatorCert.setPublicKey(clavesRSA.getPublic());
        generatorCert.setNotBefore(
                Date.from(LocalDate.now().plus(ConstantesDAO.AMOUNT_TO_ADD, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
        generatorCert.setNotAfter(new Date());
        generatorCert.setSignatureAlgorithm(ConstantesDAO.ALGORITHM_X509);

        PrivateKey signingKey = clavesRSA.getPrivate();
        X509Certificate cert =  generatorCert.generate(signingKey);

        File f = new File(u.getNombre() + ConstantesDAO.FILE_PFX);
        String passPFX = ConstantesDAO.CONTRASENA_ALL_PFX;
        if(!f.exists()) {
            try {
                PrivateKey pk = clavesRSA.getPrivate();
                KeyStore ks = KeyStore.getInstance(ConstantesDAO.PKCS_12);
                ks.load(null, passPFX.toCharArray());
                ks.setCertificateEntry(ConstantesDAO.PUBLICA, cert);
                ks.setKeyEntry(ConstantesDAO.PRIVADA, pk, passwordHasheada.toCharArray(), new Certificate[]{cert});
                FileOutputStream fos = new FileOutputStream(u.getNombre() + ConstantesDAO.FILE_PFX);
                ks.store(fos, passPFX.toCharArray());
                fos.close();

                result = Either.right(ConstantesDAO.KEYSTORE_CREADO_CORRECTAMENTE);

            } catch (KeyStoreException ks) {
                log.error(ks.getMessage());
                result = Either.left(ConstantesDAO.FALLO_AL_CREAR_LA_KEYSTORE);
            } catch (Exception e) {
                log.error(e.getMessage());
                result = Either.left(ConstantesDAO.ERROR_EN_KEY_STORE_BUILD);
            }
        }
        return result;
    }
}
