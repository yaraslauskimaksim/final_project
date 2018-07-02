package by.corporation.final_project.service.exception;

public class CommentSavingException extends Exception {
    public CommentSavingException(String message, Throwable cause){
        super(message, cause);
    }
    public CommentSavingException(String message){
        super(message);
    }
}
