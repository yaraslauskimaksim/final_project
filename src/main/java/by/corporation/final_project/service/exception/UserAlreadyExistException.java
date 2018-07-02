package by.corporation.final_project.service.exception;

public class UserAlreadyExistException extends Exception {
     public UserAlreadyExistException(String message, Throwable cause){
         super(message, cause);
     }
    public UserAlreadyExistException(String message){
        super(message);
    }
}
