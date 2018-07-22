package by.corporation.quest_fire.service.hash;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordHash {
    public static String hash(String value) {
        String hashedValue = DigestUtils.sha256Hex(value);
        return hashedValue;
    }


}
