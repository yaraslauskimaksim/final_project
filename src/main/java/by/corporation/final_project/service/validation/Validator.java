package by.corporation.final_project.service.validation;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final Pattern EMAIL =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmailPassword(String email , String password) {
        Matcher matcher = EMAIL.matcher(email);
        return matcher.find() & password.length() >= 6;
    }


}
