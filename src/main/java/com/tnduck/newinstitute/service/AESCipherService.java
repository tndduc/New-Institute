package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.util.AESCipher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:23 PM
 */
@Service
@Slf4j
public class AESCipherService {
    public String encrypt(String plainText, String secretKey) throws Exception {
        return AESCipher.encrypt(plainText, secretKey);
    }

    public String decrypt(String encryptedText, String secretKey) throws Exception {
        return AESCipher.decrypt(encryptedText, secretKey);
    }
}
