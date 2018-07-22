package by.corporation.quest_fire.dao.exception;

public class ConnectionPoolException extends Exception {

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
