package com.tnduck.newinstitute.util;

import com.tnduck.newinstitute.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 00:25 AM
 */
@Tag("unit")
@DisplayName("Unit tests for AESCipher")
public class AESCipherTest {
    @Test
    @DisplayName("Test class for encryption and decryption scenarios")
    public void testEncryptionAndDecryption() throws Exception {
        String plainText = "Hello, World!";

        String encryptedText = AESCipher.encrypt(plainText, Constants.APP_SECRET_KEY);
        String decryptedText = AESCipher.decrypt(encryptedText, Constants.APP_SECRET_KEY);

        assertEquals(plainText, decryptedText);
    }
}
