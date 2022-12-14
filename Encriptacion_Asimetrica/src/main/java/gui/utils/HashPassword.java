package gui.utils;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public class HashPassword {

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append(ConstantesGUI.APPEND_HASH);
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @SneakyThrows
    public String hashPassword(String args) {

        MessageDigest md = MessageDigest.getInstance(ConstantesGUI.INSTANCE_DIGEST);

        byte[] hashBytes = md.digest(args.getBytes());
        return bytesToHex(hashBytes);
    }

}
