package com.devrebel.inspigen.core.config.encryption;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

@Service
public class TextEncryptionService {

    private String encryptionKey = "MZygpewJsCpRrfOr";

    public Key getAESKey() {
       return new SecretKeySpec(encryptionKey.getBytes(), "AES");
    }

    public byte[] encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getAESKey());
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        System.err.println(new String(encrypted));

        return encrypted; //Base64.encodeBase64String(encryptedBytes);
    }

    public String decrypt(byte[] encrypted) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getAESKey());
        String decrypted = new String(cipher.doFinal(encrypted));
        System.err.println(decrypted);

       /* byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(plainBytes);*/

        return decrypted;
    }


}
