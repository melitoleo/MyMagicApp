package com.example.mymagicapp;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    private static final String ALGORITHM = "AES";
    private Context context;

    public Security(Context context){this.context = context;}

    public String Encrypt(String valueToEnc, String password) {
        try {
            //Key key = generateKey(password);

            String key1 = generateKey(password); // 128 bit key
            String key2 = "ThisIsASecretKet";

            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"),
                    "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(valueToEnc.getBytes());
            return Base64.encodeToString(encrypted,Base64.DEFAULT);
        }catch (Exception e){
            return "";
        }
    }

    public String Decrypt(String encryptedValue, String password) {
        try {
            //Key key = generateKey(password);

            String key1 = generateKey(password);//"Bar12345Bar12345"; // 128 bit key
            String key2 = "ThisIsASecretKet";

            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));

            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encryptedValue, Base64.DEFAULT));

            return new String(original);
        }catch (Exception e){
            return "";
        }
    }

    private String generateKey(String password) throws Exception {
        String key = String.format("%s%s", Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID), password);

        return key;
    }
}

