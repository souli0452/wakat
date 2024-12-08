package wakat.exception;

public class UserNotFoundException extends RuntimeException{
     private String code;

    public UserNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    public UserNotFoundException(String message) {
        super(message);
        this.code = code;
    }
}
