package cn.celess.j2ee.util;

import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 2021/10/22
 * TODO:
 *
 * @author 禾几海
 */
public class AesEncryptUtils {

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     * @return str
     */
    public static String encrypt(String content, String encryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKey(encryptKey).getBytes(StandardCharsets.UTF_8), "AES"));
            byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            // 采用base64算法进行转码,避免出现中文乱码
            return Base64.encodeBase64String(b);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return Strings.EMPTY;
    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return str
     */
    public static String decrypt(String encryptStr, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKey(decryptKey).getBytes(StandardCharsets.UTF_8), "AES"));
            // 采用base64算法进行转码,避免出现中文乱码
            byte[] encryptBytes = Base64.decodeBase64(encryptStr);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return Strings.EMPTY;
    }

    public static String getKey(String key) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(key.repeat(16 / (key.length()) + 1));
        return stringBuffer.substring(0, 16);
    }
}
