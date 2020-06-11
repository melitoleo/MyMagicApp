package com.example.mymagicapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private Security security = new Security(appContext);
    @Test
    public void useAppContext() {
        // Context of the app under test.

        assertEquals("com.example.mymagicapp", appContext.getPackageName());
    }

    @Test
    public void encryptKey(){
        String valueToEncript = "Password1!";
        String password = "Password1!";

        String salt = security.generateSalt(password);

        String encrypt = security.Encrypt(valueToEncript, password,salt);

        assertNotEquals("",encrypt);

        String decrypt = security.Decrypt(encrypt, password,salt);

        assertEquals(valueToEncript, decrypt);
    }
}

