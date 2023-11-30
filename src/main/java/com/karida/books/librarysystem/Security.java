package com.karida.books.librarysystem;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class Security {
    private static final String ENCRYPT_KEY = "aB3*dfG7$2hK9!pQ";
    public Security(){

    }
    public String hidingData(String text) {
        try {
            byte[] keyBytes = Arrays.copyOf(ENCRYPT_KEY.getBytes("UTF-8"), 16);

            Key aesKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            System.out.println("Error al encriptar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
