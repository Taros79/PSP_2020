package notas.Servidor.utils;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class HashPassword {

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append(Constantes.APPEND_HASH);
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String hashPassword(String args) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(Constantes.INSTANCE_DIGEST);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }

        byte[] hashBytes = md.digest(args.getBytes());
        return bytesToHex(hashBytes);
    }

}
