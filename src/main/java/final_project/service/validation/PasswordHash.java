package final_project.service.validation;

import org.apache.commons.codec.digest.DigestUtils;


public class PasswordHash {

    public static String hashPassword(String plainTextPassword){
        String md5= DigestUtils.md5Hex(plainTextPassword);
        return md5;
    }

    public static void main(String [] args){
        String h = hashPassword("Password2!");
        System.out.println(h);
    }

}
