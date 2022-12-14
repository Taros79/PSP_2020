package notas.Servidor.EE.security.encriptaciones;


public class GenClaves {

   /* ConfigYaml config;

    @Inject
    public GenClaves(ConfigYaml config) {
        this.config = config;
    }

    public Either<String, Boolean> crearClavesYCert(String nombre, String pass) {
        Either<String, Boolean> resultado;
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Constantes.RSA);
            keyGen.initialize(Constantes.KEYSIZE);
            KeyPair clavesRSA = keyGen.generateKeyPair();

            X509V3CertificateGenerator cert1 = new X509V3CertificateGenerator();
            cert1.setSerialNumber(BigInteger.valueOf(Constantes.UNO));   //or generate a random number
            cert1.setSubjectDN(new X509Principal(Constantes.CN + nombre));  //Aqui se pone el nombre del usuario.
            cert1.setIssuerDN(new X509Principal(Constantes.CN + nombre)); //El emisor (En el futuro el server, ahora decimos que es el mismo)
            cert1.setPublicKey(clavesRSA.getPublic());
            cert1.setNotBefore(
                    Date.from(LocalDate.now().plus(Constantes.AMOUNT_TO_ADD, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
            cert1.setNotAfter(new Date());
            cert1.setSignatureAlgorithm(Constantes.SHA_1_WITH_RSA_ENCRYPTION);

            PrivateKey signingKey = clavesRSA.getPrivate();
            X509Certificate cert = cert1.generate(signingKey);

            //Para hacer el keyStore
            KeyStore ks = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = config.getPassKeyStore().toCharArray();
            char[] passwordUser = pass.toCharArray();
            ks.load(null, null);
            ks.setCertificateEntry(Constantes.PUBLICA, cert);
            ks.setKeyEntry(Constantes.PRIVADA, clavesRSA.getPrivate(), passwordUser, new Certificate[]{cert}); //Aqui la contraseña del usuario.
            FileOutputStream fos = new FileOutputStream(Constantes.KEYSTORE + nombre + Constantes.KEYSTORE_PFX); //Aqui la ruta bien.
            ks.store(fos, password);
            fos.close();
            //Too correcto
            resultado = Either.right(true);

        } catch (NoSuchAlgorithmException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_GENERAR_LAS_CLAVES_INTENTELO_MAS_TARDE);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }


    public Either<String, PublicKey> getPublicKey(String nombre) {
        Either<String, PublicKey> resultado;
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = config.getPassKeyStore().toCharArray();
            ksLoad.load(new FileInputStream(Constantes.KEYSTORE + nombre + Constantes.KEYSTORE_PFX), password);
            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate(Constantes.PUBLICA);
            PublicKey publicKey = certLoad.getPublicKey();
            resultado = Either.right(publicKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PUBLICA_INTENTELO_MAS_TARDE);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }

    public Either<String, PrivateKey> getPrivateKey(String nombre, String pass) {
        Either<String, PrivateKey> resultado;
        //Para leer la privada:
        try {
            KeyStore ksLoad = KeyStore.getInstance(Constantes.PKCS_12);
            char[] password = config.getPassKeyStore().toCharArray();
            char[] passwordUser = pass.toCharArray();
            ksLoad.load(new FileInputStream(Constantes.KEYSTORE + nombre + Constantes.KEYSTORE_PFX), password);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(passwordUser);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(Constantes.PRIVADA, pt);
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            resultado = Either.right(privateKey);
        } catch (NoSuchAlgorithmException | IOException e) {
            resultado = Either.left(Constantes.NO_HA_SIDO_POSIBLE_RECOGER_LA_CLAVE_PRIVADA_INTENTELO_MAS_TARDE);
        } catch (UnrecoverableEntryException e) {
            //Es porque la pass es incorrecta.
            resultado = Either.left(Constantes.USUARIO_Y_O_CONTRASENA_NO_VALIDO);
        } catch (Exception e) {
            resultado = Either.left(Constantes.FALLO_INTERNO);
        }

        return resultado;
    }*/
}
