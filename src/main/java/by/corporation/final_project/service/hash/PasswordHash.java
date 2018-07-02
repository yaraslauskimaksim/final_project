package by.corporation.final_project.service.hash;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordHash {
    public static String hash(String value) {
        String hashedValue = DigestUtils.sha256Hex(value);
        return hashedValue;
    }

    public static void main(String [] args){
        String la = hash("Password2!");
        System.out.print(la);
    }


}
