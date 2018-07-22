package by.corporation.quest_fire.service.validation;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final Pattern EMAIL =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL.matcher(email);
        return matcher.find();
    }
    public static boolean validatePassword(String password) {
        return password.length() >=6;
    }

    public static boolean validateEmptyFields(String email , String password) {
        return !(email == null || email.equals("")) & !(password == null || password.equals(""));

    }


}
