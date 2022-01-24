package at.jku.userservice;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JWT {
    public static String jwt(User u) {
        try {
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String body = u.toString();

            String encodedHeader = Base64.getEncoder().encodeToString(header.getBytes());
            String encodedBody = Base64.getEncoder().encodeToString(body.getBytes());

            System.out.println(header);
            System.out.println(encodedHeader);
            System.out.println(body);
            System.out.println(encodedBody);

            String jwt_part = encodedHeader + "." + encodedBody;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedhash = digest.digest(
                    (jwt_part + "secretsalt").getBytes(StandardCharsets.UTF_8));
            return jwt_part + "." + bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean isValid(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    (parts[0] + "." + parts[1] + "secretsalt").getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash).equals(parts[2]);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }
}
