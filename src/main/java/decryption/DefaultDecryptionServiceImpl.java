package decryption;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class DefaultDecryptionServiceImpl implements DecryptionService {
    private static Logger logger = Logger.getLogger(DefaultDecryptionServiceImpl.class);
    private String encryptionKey = "";
    private String sunbirdEncryption = "ON";
    private final String ON = "ON";
    private Cipher c;

    public DefaultDecryptionServiceImpl() {
        try {
            encryptionKey = getSalt();
            Key key = generateKey();
            c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            logger.error("DefaultDecryptionServiceImpl:DefaultDecryptionServiceImpl:Exception occurred with error message = " + e.getMessage());
        }
    }


    @Override
    public String decryptData(String data) {
        return decryptData(data);
    }

    private String decrypt(String value) {
        try {
            String dValue = null;
            String valueToDecrypt = value.trim();
            for (int i = 0; i < ITERATIONS; i++) {
                byte[] decodedValue = new sun.misc.BASE64Decoder().decodeBuffer(valueToDecrypt);
                byte[] decValue = c.doFinal(decodedValue);
                dValue =
                        new String(decValue, StandardCharsets.UTF_8).substring(encryptionKey.length());
                valueToDecrypt = dValue;
            }
            return dValue;
        } catch (Exception ex) {
            return value;
        }
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGORITHM);
    }

    private String getSalt() {
        String key = System.getenv("sunbird_encryption_key");
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("Invalid encryption key");
        }
        return key;
    }
}
