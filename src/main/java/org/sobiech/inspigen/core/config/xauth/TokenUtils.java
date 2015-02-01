package org.sobiech.inspigen.core.config.xauth;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa zawierająca metody operujące na tokenach autoryzujących
 * 
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 */
public class TokenUtils {
	
	@Autowired
	Settings settings;

    public static final String MAGIC_KEY = "obfuscate";

    //Metoda tworząca token autoryzujący
    public String createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 5;
        return userDetails.getUsername() + ":" + expires + ":" + computeSignature(userDetails, expires);
    }

    //Metoda obliczająca sygnaturę dla tokena
    public String computeSignature(UserDetails userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername()).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append(userDetails.getPassword()).append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    //Metoda pobierajaca nazwę użytkownika zawartą w tokenie
    public String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String[] parts = authToken.split(":");
        return parts[0];
    }

    //Metoda sprawdzająca poprawność tokena
    public boolean validateToken(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        String signatureToMatch = computeSignature(userDetails, expires);
        return expires >= System.currentTimeMillis() && signature.equals(signatureToMatch);
    }
}