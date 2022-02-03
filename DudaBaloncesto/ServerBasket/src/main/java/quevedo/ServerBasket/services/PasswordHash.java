package quevedo.ServerBasket.services;

import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import quevedo.ServerBasket.utils.constantes.HashConfig;

import java.util.HashMap;
import java.util.Map;

public class PasswordHash {


    public static final String PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES = "Pbkdf2PasswordHash.KeySizeBytes";
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    private PasswordHash(Pbkdf2PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String hash(String passw) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put(HashConfig.PBKDF_2_PASSWORD_HASH_ITERATIONS, HashConfig.NUMBER_ITERATIONS);
        parameters.put(HashConfig.PBKDF_2_PASSWORD_HASH_ALGORITHM, HashConfig.PBKDF_2_WITH_HMAC_SHA_512);
        parameters.put(HashConfig.PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES, HashConfig.BYTES_SALT);
        parameters.put(PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES, HashConfig.SIZE_BYTES);
        passwordHash.initialize(parameters);

        return passwordHash.generate(passw.toCharArray());
    }

}
