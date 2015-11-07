package com.devrebel.inspigen.core.config.encryption;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Service
public class TextEncryptionService {

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String ENCRYPTION_KEY = "MZygpewJsCpRrfOr";
    private static final int CIPHER_ENCRYPTION_MODE = Cipher.ENCRYPT_MODE;
    private static final int CIPHER_DECRYPTION_MODE = Cipher.DECRYPT_MODE;

    private Key getAESKey() {
       return new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ENCRYPTION_ALGORITHM);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(CIPHER_ENCRYPTION_MODE, getAESKey());
        byte[] plainTextAsBytes = plainText.getBytes();
        byte[] encryptedBytes = cipher.doFinal(plainTextAsBytes);

        return Base64.encodeBase64String(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception
    {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(CIPHER_DECRYPTION_MODE, getAESKey());
        byte[] decodedBytes = Base64.decodeBase64(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return new String(decryptedBytes);
    }
}