package by.corporation.quest_fire.service.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
