package com.example.mymagicapp;
import android.content.Context;
import android.provider.Settings;
import android.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    private Context context;

    public Security(Context context){this.context = context;}

    public  String Encrypt(String valueToEnc, String password, String salt) {
        try {
            String key1 = generateKey(password, salt);
            String key2 = getSecretKey();

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

    public  String Decrypt(String encryptedValue, String password, String salt) {
        try {
            String key1 = generateKey(password, salt);
            String key2 = getSecretKey();

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

    public  String generateKey(String password, String salt){
        return String.format("%s%s",password, salt);
    }

    public  String generateSalt(String password){
        int keyByte = 32 - password.length();

        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[keyByte];
        random.nextBytes(r);

        return Base64.encodeToString(r, Base64.DEFAULT).substring(0, keyByte);
    }

    private String getSecretKey() {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}

