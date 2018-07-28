package by.corporation.quest_fire.service.exception;

public class QuestAlreadyExistException extends Exception {

    public QuestAlreadyExistException() {
        super();
    }

    public QuestAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public QuestAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestAlreadyExistException(String message) {
        super(message);
    }
}
