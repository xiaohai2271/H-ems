package cn.celess.j2ee.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2021/10/24
 * TODO:
 *
 * @author 禾几海
 */
class AesEncryptUtilsTest {

    @Test
    void encrypt() {
        String encrypt = AesEncryptUtils.encrypt("123456789", "a@celess.cn");
        assertEquals("so+cIsiS3Al83lKVYTpcHQ==", encrypt);
        System.out.println(encrypt);
    }

    @Test
    void decrypt() {
        String decrypt = AesEncryptUtils.decrypt("so+cIsiS3Al83lKVYTpcHQ==", "a@celess.cn");
        assertEquals("123456789", decrypt);
        System.out.println(decrypt);
    }

    @Test
    void getKey() {
        String key = AesEncryptUtils.getKey("a@celess.cn");
        assertEquals(16, key.length());
        System.out.println(key);
    }
}